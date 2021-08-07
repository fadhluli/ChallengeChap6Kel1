package com.fadtech.challengechap6kel1.ui.login

import com.fadtech.challengechap6kel1.data.network.datasource.BinarDataSource
import com.catnip.covidapp.data.network.entity.responses.authentification.BaseAuthResponse
import com.catnip.covidapp.data.network.entity.responses.authentification.LoginResponse
import com.fadtech.challengechap6kel1.data.network.entity.request.authentification.LoginRequest


class LoginRepository(private val binarDataSource: BinarDataSource) {
    suspend fun postLoginData(loginRequest: LoginRequest) : BaseAuthResponse<LoginResponse, String> {
        return binarDataSource.postLoginData(loginRequest)
    }
}