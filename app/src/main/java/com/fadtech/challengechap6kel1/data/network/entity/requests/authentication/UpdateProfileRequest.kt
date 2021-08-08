package com.fadtech.challengechap6kel1.data.network.entity.requests.authentication


import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("email")
    private var email : String,
    @SerializedName("username")
    private var username : String,

    )