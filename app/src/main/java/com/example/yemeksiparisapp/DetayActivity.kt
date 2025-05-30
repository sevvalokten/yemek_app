
package com.example.yemeksiparisapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.yemeksiparisapp.databinding.ActivityDetayBinding
import com.example.yemeksiparisapp.model.Yemekler
import com.example.yemeksiparisapp.retrofit.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetayBinding
    private var adet = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val yemek = intent.getSerializableExtra("yemek") as Yemekler

        binding.textViewYemekAd.text = yemek.yemek_adi
        binding.textViewYemekFiyat.text = "${yemek.yemek_fiyat} ₺"
        Glide.with(this)
            .load("http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}")
            .into(binding.imageViewYemek)

        binding.textViewAdet.text = adet.toString()

        binding.buttonArttir.setOnClickListener {
            adet++
            binding.textViewAdet.text = adet.toString()
        }

        binding.buttonAzalt.setOnClickListener {
            if (adet > 1) {
                adet--
                binding.textViewAdet.text = adet.toString()
            }
        }

        binding.buttonSepeteEkle.setOnClickListener {
            sepeteEkle(yemek)
        }
    }

    private fun sepeteEkle(yemek: Yemekler) {
        ApiUtils.getYemeklerDao().sepeteYemekEkle(
            yemek.yemek_adi,
            yemek.yemek_resim_adi,
            yemek.yemek_fiyat.toInt(),
            adet,
            "demo_kullanici"
        ).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@DetayActivity, "Sepete eklendi", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@DetayActivity, "Hata: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
