package com.fadtech.challengechap6kel1.ui.profile

import com.fadtech.challengechap6kel1.data.network.datasource.ProfileDataSource
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.BaseAuthResponse
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.UserResponse

class ProfileRepository(private val profileDataSource: ProfileDataSource) {
    suspend fun getUserData() : BaseAuthResponse<UserResponse, String> {
        return profileDataSource.getUserData()
    }

    suspend fun putUserData(username:String, email:String) : BaseAuthResponse<UserResponse, String> {
        return profileDataSource.putUserData(username, email)
    }
}