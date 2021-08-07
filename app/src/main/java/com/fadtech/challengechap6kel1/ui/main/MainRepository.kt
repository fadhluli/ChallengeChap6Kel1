package com.fadtech.challengechap6kel1.ui.main

import com.fadtech.challengechap6kel1.data.local.room.datasource.UserDataSource
import com.fadtech.challengechap6kel1.data.model.User
import com.fadtech.challengechap6kel1.data.network.datasource.HistoryDataSource
import com.fadtech.challengechap6kel1.data.network.entity.requests.HistoryRequest
import com.fadtech.challengechap6kel1.data.network.entity.responses.History.PostHistoryResponse

class MainRepository(private val dataSource: UserDataSource, private val historyDataSource: HistoryDataSource) {
    suspend fun insertUser(user: User): Long {
        return dataSource.insertUser(user)
    }

    suspend fun postHistory(historyRequest: HistoryRequest): PostHistoryResponse {
        return historyDataSource.postHistory(historyRequest)
    }
}