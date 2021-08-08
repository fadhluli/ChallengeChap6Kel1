package com.fadtech.challengechap6kel1.data.network.entity.responses.authentication

import com.google.gson.annotations.SerializedName

data class BaseAuthResponse<T, E>(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: T,
    @SerializedName("errors")
    val errors: E
)