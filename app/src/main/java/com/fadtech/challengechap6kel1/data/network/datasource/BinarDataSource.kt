package com.fadtech.challengechap6kel1.data.network.datasource

import com.catnip.covidapp.data.network.entity.responses.authentification.BaseAuthResponse
import com.catnip.covidapp.data.network.entity.responses.authentification.LoginResponse
import com.catnip.covidapp.data.network.entity.responses.authentification.UserResponse
import com.fadtech.challengechap6kel1.data.network.entity.request.authentification.LoginRequest
import com.fadtech.challengechap6kel1.data.network.services.BinarApiServices


class BinarDataSource(private val binarApiServices: BinarApiServices) {
    suspend fun postLoginData(loginRequest: LoginRequest) : BaseAuthResponse<LoginResponse, String> {
        return binarApiServices.postLoginData(loginRequest)
    }
   /* suspend fun postRegisterData(registerRequest: RegisterRequest) : BaseAuthResponse<UserResponse, String> {
        return binarApiServices.postRegisterData(registerRequest)

    }*/
    suspend fun getSyncData() : BaseAuthResponse<UserResponse, String>{
        return binarApiServices.getSyncData()
    }

}

import com.fadtech.challengechap6kel1.data.network.entity.request.authentification.RegisterRequest
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentification.BaseAuthResponse
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentification.UserResponse
import com.fadtech.challengechap6kel1.data.network.services.BinarApiServices

class BinarDataSource(private val binarApiServices: BinarApiServices) {
    //todo : nambah postlogindata

    suspend fun postRegisterData(registerRequest: RegisterRequest) : BaseAuthResponse<UserResponse, String> {
        return binarApiServices.postRegisterData(registerRequest)

    }
    suspend fun getSyncData() : BaseAuthResponse<UserResponse, String>{
        return binarApiServices.getSyncData()
    }
}