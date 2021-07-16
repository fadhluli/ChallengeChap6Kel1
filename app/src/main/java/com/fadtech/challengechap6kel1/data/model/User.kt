package com.fadtech.challengechap6kel1.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "username")
    var username: String?,

    @ColumnInfo(name = "total_win")
    var totalWin: Int = 0,
): Parcelable
