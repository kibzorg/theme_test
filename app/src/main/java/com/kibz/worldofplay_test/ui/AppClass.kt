package com.kibz.worldofplay_test.ui

import android.app.Application
import android.content.SharedPreferences
import com.kibz.worldofplay_test.service.RestClient
import com.kibz.worldofplay_test.util.Constants
import com.kibz.worldofplay_test.util.PreferenceHelper
import com.kibz.worldofplay_test.util.ThemeHelper

class AppClass : Application() {
    lateinit var prefs: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceHelper.defaultPrefs(this)

        ThemeHelper.sTheme = PreferenceHelper.defaultPrefs(this)
            .getInt(Constants.theme_key, ThemeHelper.THEME_LIGHT)
        //Rest service client initialized
        RestClient()
    }
}