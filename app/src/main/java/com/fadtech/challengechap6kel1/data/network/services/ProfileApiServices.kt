package com.fadtech.challengechap6kel1.data.network.services

import com.fadtech.challengechap6kel1.BuildConfig
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.BaseAuthResponse
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.UserResponse
import com.fadtech.challengechap6kel1.preference.SessionPreference
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import java.util.concurrent.TimeUnit

interface ProfileApiServices {
    @GET("api/v1/users")
    suspend fun getUserData() : BaseAuthResponse<UserResponse, String>

    @PUT("api/v1/users")
    suspend fun putUserData(@Body updateProfileRequest: RequestBody) : BaseAuthResponse<UserResponse, String>


    companion object{
        private var retrofitServices : ProfileApiServices? = null
        fun getInstance(sessionPreference : SessionPreference) : ProfileApiServices?{
            if(retrofitServices == null){
                //token interceptor
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
                    .baseUrl(BuildConfig.BASE_URL_BINAR)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
                retrofitServices = retrofit.create(ProfileApiServices::class.java)
            }
            return retrofitServices
        }
    }
}