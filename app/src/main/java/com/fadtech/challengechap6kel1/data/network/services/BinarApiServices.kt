package com.fadtech.challengechap6kel1.data.network.services

import BaseAuthResponse
import com.fadtech.challengechap6kel1.BuildConfig
import com.fadtech.challengechap6kel1.data.local.sharepreference.SessionPreference
import com.catnip.covidapp.data.network.entity.responses.authentification.LoginResponse
import com.fadtech.challengechap6kel1.data.network.entity.request.authentification.LoginRequest
import com.fadtech.challengechap6kel1.data.network.entity.request.authentification.RegisterRequest
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentification.UserResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface BinarApiServices {


    @POST("api/v1/auth/register")
    suspend fun postRegisterData(@Body registerRequest: RegisterRequest) : BaseAuthResponse<UserResponse, String>
    @POST("api/v1/auth/login")
    suspend fun postLoginData(@Body loginRequest: LoginRequest) : BaseAuthResponse<LoginResponse, String>

    /*@POST("api/v1/auth/register")
    suspend fun postRegisterData(@Body registerRequest: RegisterRequest) : BaseAuthResponse<UserResponse,String> */

    @GET("api/v1/auth/me")
    suspend fun getSyncData() : BaseAuthResponse<UserResponse,String>


    companion object{
        private var retrofitServices : BinarApiServices? = null
        fun getInstance(sessionPreference: SessionPreference) : BinarApiServices?{
            if(retrofitServices == null){
                //todo : add interceptor token
                val authInterceptor = Interceptor{
                    val requestBuilder = it.request().newBuilder()
                    sessionPreference.authToken?.let { token ->
                        requestBuilder.addHeader("Authorization", "Bearer $token")
                    }
                    it.proceed(requestBuilder.build())
                }

                //initialize
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(authInterceptor)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL_BINAR_AUTH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
                retrofitServices = retrofit.create(BinarApiServices::class.java)
            }
            return retrofitServices
        }
    }

}