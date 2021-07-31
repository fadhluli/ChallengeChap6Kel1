package com.fadtech.challengechap6kel1.ui.ranking

import com.fadtech.challengechap6kel1.data.local.room.datasource.UserDataSource
import com.fadtech.challengechap6kel1.data.model.User

class RankingRepository(private val dataSource: UserDataSource) {

    suspend fun getUserRanking(): List<User> {
        return dataSource.getUserRanking()
    }
}