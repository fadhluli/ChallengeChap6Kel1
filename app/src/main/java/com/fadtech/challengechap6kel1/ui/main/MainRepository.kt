package com.fadtech.challengechap6kel1.ui.main

import com.fadtech.challengechap6kel1.data.local.room.datasource.UserDataSource
import com.fadtech.challengechap6kel1.data.model.User

class MainRepository(private val dataSource: UserDataSource) {
    suspend fun insertUser(user: User): Long {
        return dataSource.insertUser(user)
    }
}