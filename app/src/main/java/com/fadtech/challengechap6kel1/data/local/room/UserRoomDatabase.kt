package com.fadtech.challengechap6kel1.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fadtech.challengechap6kel1.data.local.room.dao.UserDao
import com.fadtech.challengechap6kel1.data.model.User


@Database(entities = [User::class], version = 1)
abstract class UserRoomDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null
        fun getInstance(context: Context): UserRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserRoomDatabase::class.java,
                    "user_db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}