package com.fadtech.challengechap6kel1.data.local.room.datasource

import com.fadtech.challengechap6kel1.data.local.room.dao.UserDao
import com.fadtech.challengechap6kel1.data.model.User


class UserDataSource(private val userDao: UserDao) {
    suspend fun insertUser(user: User): Long{
        return userDao.insertUser(user)
    }

    suspend fun getUserRanking(): List<User> {
        return userDao.getUserRankingList()
    }

}