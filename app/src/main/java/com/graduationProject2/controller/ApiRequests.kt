package com.graduationProject2.controller

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.security.auth.callback.Callback

interface ApiRequests {

    @GET("values")
    fun getAllValues(): Call<InformationResponse>

    @POST("post")
    fun sendAllValues(@Body info: AllValues):Call<Any>

}
