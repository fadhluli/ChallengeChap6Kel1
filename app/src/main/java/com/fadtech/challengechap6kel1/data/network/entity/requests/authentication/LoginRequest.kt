package com.fadtech.challengechap6kel1.data.network.entity.requests.authentication

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("password")
    val password: String? = null
)