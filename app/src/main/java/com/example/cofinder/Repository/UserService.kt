package com.example.cofinder.Repository

import com.example.cofinder.Data.UserData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/api/users/register")
    fun registerUser(@Body user: UserData): Call<Void>
}

