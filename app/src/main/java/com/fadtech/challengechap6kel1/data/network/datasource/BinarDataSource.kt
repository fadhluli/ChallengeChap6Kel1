package com.fadtech.challengechap6kel1.data.network.datasource

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