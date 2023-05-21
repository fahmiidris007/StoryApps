package com.fahmiproduction.storyapps.data.database

import android.content.Context
import com.fahmiproduction.storyapps.api.response.User

class Preferences(context: Context) {
    private val preferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    companion object {
        private const val PREF = "pref"
        private const val NAME = "name"
        private const val ID = "userId"
        private const val TOKEN = "token"

        @Volatile
        private var instace: Preferences? = null
        fun getInstance(context: Context): Preferences = instace ?: synchronized(this) {
            instace ?: Preferences(context)
        }
    }

    fun setUser(value: User) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(ID, value.userId)
        editor.putString(TOKEN, value.token)
        editor.apply()
    }

    fun getUser(): User {
        return User(
            preferences.getString(NAME, "").toString(),
            preferences.getString(ID, "").toString(),
            preferences.getString(TOKEN, "").toString(),
        )
    }

    fun deleteUser() {
        val editor = preferences.edit()
        editor.putString(NAME, "")
        editor.putString(ID, "")
        editor.putString(TOKEN, "")
        editor.apply()
    }
}