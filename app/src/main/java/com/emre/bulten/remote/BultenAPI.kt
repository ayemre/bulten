package com.emre.bulten.remote

import com.emre.bulten.remote.models.NoticesResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface BultenAPI {

    @GET("getprebultendelta")
    fun getNotices() : Call<NoticesResponse>


    @GET("{matchCode}/lastmatches")
    fun getDetail(@Path("matchCode") matchCode:String) : Call<String>

    companion object {
        var BULTEN_BASE_URL = "https://bulten.nesine.com/api/bulten/"
        var ISTATISTIK_BASE_URL = " https://istatistik.nesine.com/api/v2/"

        fun createBulten() : BultenAPI {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BULTEN_BASE_URL)
                .build()
            return retrofit.create(BultenAPI::class.java)

        }
        fun createIstatistik() : BultenAPI {

            val retrofit = Retrofit.Builder()
                /**
                 * bozuk gelen json datasını hatasız alınmasını sağlayan kütüphane kodu.
                 * gelen data json formatında değildi.
                 */
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ISTATISTIK_BASE_URL)
                .build()
            return retrofit.create(BultenAPI::class.java)

        }
    }
}