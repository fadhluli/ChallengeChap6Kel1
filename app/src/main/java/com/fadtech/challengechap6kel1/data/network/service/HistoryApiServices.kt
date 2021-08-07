package com.fadtech.challengechap6kel1.data.network.service

import com.fadtech.challengechap6kel1.BuildConfig
import com.fadtech.challengechap6kel1.data.network.entity.requests.HistoryRequest
import com.fadtech.challengechap6kel1.data.network.entity.responses.History.GetHistoryResponse
import com.fadtech.challengechap6kel1.data.network.entity.responses.History.PostHistoryResponse
import com.fadtech.challengechap6kel1.preference.SessionPreference
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface HistoryApiServices {

    @POST("api/v1/battle")
    suspend fun postHistory(@Body historyRequest: HistoryRequest): PostHistoryResponse

    @GET("api/v1/battle")
    suspend fun getHistory(): GetHistoryResponse

    companion object {
        private var retrofitServices: HistoryApiServices? = null
        fun getInstance(sessionPreference: SessionPreference): HistoryApiServices? {
            if (retrofitServices == null) {
                val authInterceptor = Interceptor {
                    val requestBuilder = it.request().newBuilder()
                    sessionPreference.authToken?.let { token ->
                        requestBuilder.addHeader("Authorization", "Bearer $token")
                    }
                    it.proceed(requestBuilder.build())
                }

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
                retrofitServices = retrofit.create(HistoryApiServices::class.java)
            }
            return retrofitServices
        }
    }

}