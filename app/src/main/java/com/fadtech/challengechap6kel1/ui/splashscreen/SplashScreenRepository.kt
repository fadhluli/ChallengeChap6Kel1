package com.fadtech.challengechap6kel1.ui.splashscreen

import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.BaseAuthResponse

import com.fadtech.challengechap6kel1.data.network.datasource.BinarDataSource
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.UserResponse

class SplashScreenRepository(private val binarDataSource: BinarDataSource) {
    suspend fun getSyncData() : BaseAuthResponse<UserResponse, String> {
        return binarDataSource.getSyncData()
    }
}