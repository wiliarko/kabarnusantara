package com.kabar.nusantara.network

import retrofit2.Call
import retrofit2.http.*

interface APIRequests {

    @FormUrlEncoded
    @POST("save_fcm.php")
    fun registrasi(@Field("token") token: String): Call<String>


}