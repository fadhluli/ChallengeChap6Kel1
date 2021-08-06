package com.fadtech.challengechap6kel1.ui.register

import com.fadtech.challengechap6kel1.data.network.datasource.BinarDataSource
import com.fadtech.challengechap6kel1.data.network.entity.request.authentification.RegisterRequest
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentification.BaseAuthResponse
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentification.UserResponse

class RegisterRepository(private val binarDataSource: BinarDataSource) {
    suspend fun postRegisterData(registerRequest: RegisterRequest) : BaseAuthResponse<UserResponse, String> {
        return binarDataSource.postRegisterData(registerRequest)
    }
}