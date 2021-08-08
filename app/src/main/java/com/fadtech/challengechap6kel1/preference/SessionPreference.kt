package com.fadtech.challengechap6kel1.preference

import android.content.Context
import android.content.SharedPreferences

class SessionPreference(var context: Context) {

    private var preference: SharedPreferences = context.getSharedPreferences(NAME, MODE)

    companion object {
        private const val NAME = "RockScissorPaper"
        private const val MODE = Context.MODE_PRIVATE
        private val PREF_AUTH_TOKEN = Pair("PREF_AUTH_TOKEN", null)
    }

    var authToken: String?
        get() = preference.getString(PREF_AUTH_TOKEN.first, PREF_AUTH_TOKEN.second)
        set(value) = preference.edit {
            it.putString(PREF_AUTH_TOKEN.first, value)
        }


    fun deleteSession() {
        preference.delete()
    }
}

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
}

private fun SharedPreferences.delete() {
    edit().clear().apply()
}
