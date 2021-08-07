package com.fadtech.challengechap6kel1.data.network.entity.responses.authentification


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("username")
    val username: String? = null
)