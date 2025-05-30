
package com.example.yemeksiparisapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yemeksiparisapp.databinding.ItemSepetBinding
import com.example.yemeksiparisapp.model.SepetYemek
import com.example.yemeksiparisapp.retrofit.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SepetAdapter(private val mContext: Context, private val sepetList: List<SepetYemek>) :
    RecyclerView.Adapter<SepetAdapter.SepetHolder>() {

    inner class SepetHolder(val binding: ItemSepetBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SepetHolder {
        val binding = ItemSepetBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return SepetHolder(binding)
    }

    override fun onBindViewHolder(holder: SepetHolder, position: Int) {
        val sepetYemek = sepetList[position]
        val b = holder.binding

        b.textViewAd.text = sepetYemek.yemek_adi
        b.textViewFiyat.text = "${sepetYemek.yemek_fiyat} ₺ x ${sepetYemek.yemek_siparis_adet}"

        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${sepetYemek.yemek_resim_adi}"
        Glide.with(mContext).load(imageUrl).into(b.imageView)

        b.buttonSil.setOnClickListener {
            ApiUtils.getYemeklerDao().sepettenYemekSil(
                sepetYemek.sepet_yemek_id.toInt(),
                "demo_kullanici"
            ).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Toast.makeText(mContext, "Silindi", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(mContext, "Hata: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return sepetList.size
    }
}
