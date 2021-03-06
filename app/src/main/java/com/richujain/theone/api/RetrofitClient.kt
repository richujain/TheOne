package com.richujain.theone.api


import android.util.Base64
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {

    private val AUTH = "Basic "+ Base64.encodeToString("ubuntu:nine9eightzero".toByteArray(), Base64.NO_WRAP)

    private const val BASE_URL = "http://192.168.0.10:80"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method, original.body)

            val request = requestBuilder.build()
            chain.proceed(request)
        }.connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    val instance: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        retrofit.create(Api::class.java)
    }

}