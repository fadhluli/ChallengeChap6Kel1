package com.fadtech.challengechap6kel1.data.network.entity.responses.History


import com.google.gson.annotations.SerializedName

data class PostHistoryResponse(
    @SerializedName("data")
    val postHistoryData: PostHistoryData,
    @SerializedName("success")
    val success: Boolean
)