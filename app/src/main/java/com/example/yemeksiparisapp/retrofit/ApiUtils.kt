
package com.example.yemeksiparisapp.retrofit

object ApiUtils {
    fun getYemeklerDao(): YemeklerDao {
        return RetrofitClient.getClient().create(YemeklerDao::class.java)
    }
}
