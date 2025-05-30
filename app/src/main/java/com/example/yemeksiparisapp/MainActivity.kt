
package com.example.yemeksiparisapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.yemeksiparisapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var yemekAdapter: YemekAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        getYemekler()
    }

    private fun getYemekler() {
        binding.progressBar.visibility = View.VISIBLE

        ApiUtils.getYemeklerDao().tumYemekleriGetir().enqueue(object : Callback<YemeklerCevap> {
            override fun onResponse(call: Call<YemeklerCevap>, response: Response<YemeklerCevap>) {
                if (response.isSuccessful) {
                    val liste = response.body()?.yemekler
                    if (liste != null) {
                        yemekAdapter = YemekAdapter(this@MainActivity, liste)
                        binding.recyclerView.adapter = yemekAdapter
                    }
                }
                binding.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<YemeklerCevap>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Hata: ${t.message}", Toast.LENGTH_LONG).show()
                binding.progressBar.visibility = View.GONE
            }
        })
    }
}
