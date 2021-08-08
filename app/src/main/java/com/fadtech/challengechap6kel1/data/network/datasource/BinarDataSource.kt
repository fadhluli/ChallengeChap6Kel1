package com.fadtech.challengechap6kel1.data.network.datasource

import com.fadtech.challengechap6kel1.data.network.entity.requests.authentication.LoginRequest
import com.fadtech.challengechap6kel1.data.network.entity.requests.authentication.RegisterRequest
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.BaseAuthResponse
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.LoginResponse
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.UserResponse
import com.fadtech.challengechap6kel1.data.network.services.BinarApiServices


class BinarDataSource(private val binarApiServices: BinarApiServices) {
    suspend fun postLoginData(loginRequest: LoginRequest): BaseAuthResponse<LoginResponse, String> {
        return binarApiServices.postLoginData(loginRequest)
    }

    suspend fun postRegisterData(registerRequest: RegisterRequest): BaseAuthResponse<UserResponse, String> {
        return binarApiServices.postRegisterData(registerRequest)

    }

    suspend fun getSyncData(): BaseAuthResponse<UserResponse, String> {
        return binarApiServices.getSyncData()
    }

//    fun postRegisterData(registerRequest: RegisterRequest): com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.BaseAuthResponse<UserResponse, String> {
//
//    }

}