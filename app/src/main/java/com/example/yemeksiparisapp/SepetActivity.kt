
package com.example.yemeksiparisapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yemeksiparisapp.databinding.ActivitySepetBinding
import com.example.yemeksiparisapp.model.SepetYemek
import com.example.yemeksiparisapp.retrofit.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SepetActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySepetBinding
    private lateinit var sepetAdapter: SepetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySepetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewSepet.layoutManager = LinearLayoutManager(this)

        sepetiYukle()
    }

    private fun sepetiYukle() {
        binding.progressBar.visibility = View.VISIBLE

        ApiUtils.getYemeklerDao().sepettekiYemekleriGetir("demo_kullanici")
            .enqueue(object : Callback<List<SepetYemek>> {
                override fun onResponse(call: Call<List<SepetYemek>>, response: Response<List<SepetYemek>>) {
                    if (response.isSuccessful) {
                        val liste = response.body() ?: emptyList()
                        sepetAdapter = SepetAdapter(this@SepetActivity, liste)
                        binding.recyclerViewSepet.adapter = sepetAdapter

                        // toplam tutar hesapla
                        val toplam = liste.sumOf {
                            it.yemek_fiyat.toInt() * it.yemek_siparis_adet.toInt()
                        }
                        binding.textViewToplam.text = "Toplam: ${toplam} ₺"
                    }
                    binding.progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<List<SepetYemek>>, t: Throwable) {
                    Toast.makeText(this@SepetActivity, "Hata: ${t.message}", Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                }
            })
    }
}
