package com.fadtech.challengechap6kel1.data.network.datasource

import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.BaseAuthResponse
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.UserResponse
import com.fadtech.challengechap6kel1.data.network.services.ProfileApiServices
import okhttp3.MultipartBody

class ProfileDataSource(private val profileApiService: ProfileApiServices) {
    suspend fun getUserData() : BaseAuthResponse<UserResponse, String> {
        return profileApiService.getUserData()
    }

    suspend fun putUserData(username:String, email:String) : BaseAuthResponse<UserResponse, String> {
        val updateProfileRequest = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("email", email)
            .addFormDataPart("username", username)
            .build()
        return profileApiService.putUserData(updateProfileRequest)
    }
}