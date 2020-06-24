package com.learn.android.khmer24clone.model.repo

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.learn.android.khmer24clone.BuildConfig
import com.learn.android.khmer24clone.MainApplication
import java.io.Serializable
import java.lang.Exception

object SharedPrefRepo {
    val pref = MainApplication.instance.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

    fun saveObject(obj: Any?, key: String) {
        try {

            if (obj == null) {
                pref.edit(true) {
                    remove(key)
                }
                return
            }

            val jsonObj = Gson().toJson(obj)
            pref.edit(true) {
                putString(key, jsonObj)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inline fun <reified T: Serializable> readObject(key: String): T? {
        try {
            val jsonString = pref.getString(key, null)
            return Gson().fromJson(jsonString, T::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}