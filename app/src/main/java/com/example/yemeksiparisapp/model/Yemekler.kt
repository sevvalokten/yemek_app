
package com.example.yemeksiparisapp.model

import java.io.Serializable

data class Yemekler(
    val yemek_id: String,
    val yemek_adi: String,
    val yemek_resim_adi: String,
    val yemek_fiyat: String
) : Serializable
