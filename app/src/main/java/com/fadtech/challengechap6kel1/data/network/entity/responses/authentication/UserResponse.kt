package com.fadtech.challengechap6kel1.data.network.entity.responses.authentication

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("username")
    val username: String? = null
)
