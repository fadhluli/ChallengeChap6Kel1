package com.fadtech.challengechap6kel1.data.network.entity.responses.History


import com.google.gson.annotations.SerializedName

data class GetHistoryResponse(
    @SerializedName("data")
    val data: List<GetHistoryData>,
    @SerializedName("success")
    val success: Boolean
)