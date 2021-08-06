package com.catnip.covidapp.data.network.entity.responses.authentification

import com.google.gson.annotations.SerializedName

data class  BaseAuthResponse<T, E>(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: T,
    @SerializedName("errors")
    val errors: E
)