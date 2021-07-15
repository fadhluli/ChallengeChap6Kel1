package com.fadtech.challengechap6kel1.data.preference

import android.content.Context
import android.content.SharedPreferences

class UserPreference (val context : Context) {
    private var preference : SharedPreferences = context.getSharedPreferences(NAME, MODE)

    companion object{
        private const val NAME = "RockPaperScissorsGame" //app name or else
        private const val MODE = Context.MODE_PRIVATE
        private val PREF_USER_NAME = Pair("USER_NAME", null)
        private val PREF_USER_NAME_PLAYER2 = Pair("USER_NAME", null)
    }

    var userNamePlayer1: String?
        get() = preference.getString(PREF_USER_NAME.first, PREF_USER_NAME.second)
        set(value) = preference.edit {
            it.putString(PREF_USER_NAME.first, value)
        }

    var userNamePlayer2: String?
        get() = preference.getString(PREF_USER_NAME_PLAYER2.first, PREF_USER_NAME_PLAYER2.second)
        set(value) = preference.edit {
            it.putString(PREF_USER_NAME_PLAYER2.first, value)
        }
}

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
}