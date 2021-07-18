package com.fadtech.challengechap6kel1.data.local.room.dao

import androidx.room.*
import com.fadtech.challengechap6kel1.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY total_win DESC")
    suspend fun getUserRankingList(): List<User>

    @Query("SELECT * FROM user WHERE id == :id")
    suspend fun getUserById(id: Int): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(todo: User): Long

}