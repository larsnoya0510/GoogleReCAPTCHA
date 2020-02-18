package com.example.cccmp.api

import com.example.cccmp.data_class.DataModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("public/home")
    fun getHomeData(
        @Header("Authorization") token: String

    ): Call<DataModel.HomeResponseData>
}