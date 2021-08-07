package com.fadtech.challengechap6kel1.data.network.datasource

import com.fadtech.challengechap6kel1.data.network.entity.requests.HistoryRequest
import com.fadtech.challengechap6kel1.data.network.entity.responses.History.GetHistoryResponse
import com.fadtech.challengechap6kel1.data.network.entity.responses.History.PostHistoryResponse
import com.fadtech.challengechap6kel1.data.network.services.HistoryApiServices

class HistoryDataSource(private val historyApiServices: HistoryApiServices) {

    suspend fun getHistory(): GetHistoryResponse {
        return historyApiServices.getHistory()
    }

    suspend fun postHistory(historyRequest: HistoryRequest): PostHistoryResponse{
        return historyApiServices.postHistory(historyRequest)
    }
}