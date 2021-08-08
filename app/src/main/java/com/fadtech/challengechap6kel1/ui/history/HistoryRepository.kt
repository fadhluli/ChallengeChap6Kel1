package com.fadtech.challengechap6kel1.ui.history

import com.fadtech.challengechap6kel1.data.network.datasource.HistoryDataSource
import com.fadtech.challengechap6kel1.data.network.entity.responses.History.GetHistoryResponse

class HistoryRepository(private val dataSource: HistoryDataSource) {
    suspend fun getHistory(): GetHistoryResponse {
        return dataSource.getHistory()
    }
}