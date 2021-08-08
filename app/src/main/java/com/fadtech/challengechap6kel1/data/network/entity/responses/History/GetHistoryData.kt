package com.fadtech.challengechap6kel1.data.network.entity.responses.History


import com.google.gson.annotations.SerializedName

data class GetHistoryData(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("mode")
    val mode: String,
    @SerializedName("result")
    val result: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)