
package com.example.yemeksiparisapp.retrofit

import com.example.yemeksiparisapp.model.YemeklerCevap
import retrofit2.Call
import retrofit2.http.GET

interface YemeklerDao {
    @GET("yemekler/tumYemekleriGetir.php")
    fun tumYemekleriGetir(): Call<YemeklerCevap>
}
