package com.fadtech.challengechap6kel1.data.network.datasource

import com.fadtech.challengechap6kel1.data.network.entity.responses.History.GetHistoryResponse
import com.fadtech.challengechap6kel1.data.network.service.HistoryApiServices

class HistoryDataSource(private val historyApiServices: HistoryApiServices) {

    suspend fun getHistory(): GetHistoryResponse {
        return historyApiServices.getHistory()
    }
}