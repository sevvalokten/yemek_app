
package com.example.yemeksiparisapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yemeksiparisapp.databinding.ItemYemekBinding
import com.example.yemeksiparisapp.model.Yemekler

class YemekAdapter(private val mContext: Context, private val yemekList: List<Yemekler>)
    : RecyclerView.Adapter<YemekAdapter.YemekHolder>() {

    inner class YemekHolder(val binding: ItemYemekBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YemekHolder {
        val binding = ItemYemekBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return YemekHolder(binding)
    }

    override fun onBindViewHolder(holder: YemekHolder, position: Int) {
        val yemek = yemekList[position]
        val b = holder.binding

        b.textViewYemekAd.text = yemek.yemek_adi
        b.textViewFiyat.text = "${yemek.yemek_fiyat} ₺"

        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
        Glide.with(mContext).load(imageUrl).into(b.imageViewYemek)

        b.root.setOnClickListener {
            val intent = Intent(mContext, DetayActivity::class.java)
            intent.putExtra("yemek", yemek)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return yemekList.size
    }
}
