package com.fadtech.challengechap6kel1.ui.login

import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.BaseAuthResponse
import com.fadtech.challengechap6kel1.data.network.datasource.BinarDataSource
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.LoginResponse
import com.fadtech.challengechap6kel1.data.network.entity.requests.authentication.LoginRequest


class LoginRepository(private val binarDataSource: BinarDataSource) {
    suspend fun postLoginData(loginRequest: LoginRequest) : BaseAuthResponse<LoginResponse, String> {
        return binarDataSource.postLoginData(loginRequest)
    }
}