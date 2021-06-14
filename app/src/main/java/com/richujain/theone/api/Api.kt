package com.richujain.theone.api

import retrofit2.Call
import com.richujain.theone.models.DefaultResponse
import com.richujain.theone.models.LoginResponse
import retrofit2.http.*
import okhttp3.*

interface Api {

    @FormUrlEncoded
    @POST("createuser")
    fun createUser(
        @Field("email") email:String,
        @Field("name") name:String,
        @Field("password") password:String,
        @Field("school") school:String
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("test.php")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String
    ): Call<LoginResponse>

}