package com.fadtech.challengechap6kel1.preference

import android.content.Context
import android.content.SharedPreferences

class UserPreference(var context: Context) {

    private var preference: SharedPreferences = context.getSharedPreferences(NAME, MODE)

    companion object {
        private const val NAME = "RockScissorPaper"
        private const val MODE = Context.MODE_PRIVATE
        private val PREF_USER_NAME_PLAYER_ONE = Pair("IS_USER_NAME1", null)
        private val PREF_USER_NAME_PLAYER_TWO = Pair("IS_USER_NAME2", null)
    }

    var userNamePlayerOne : String?
        get() = preference.getString(PREF_USER_NAME_PLAYER_ONE.first, PREF_USER_NAME_PLAYER_ONE.second)
        set(value) = preference.edit {
            it.putString(PREF_USER_NAME_PLAYER_ONE.first, value)
        }

    var userNamePlayerTwo : String?
        get() = preference.getString(PREF_USER_NAME_PLAYER_TWO.first, PREF_USER_NAME_PLAYER_TWO.second)
        set(value) = preference.edit {
            it.putString(PREF_USER_NAME_PLAYER_TWO.first, value)
        }

}

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
}