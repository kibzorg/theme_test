package com.kibz.worldofplay_test.util

import android.app.Activity

import android.content.Intent
import com.kibz.worldofplay_test.R
import com.kibz.worldofplay_test.util.PreferenceHelper.set

object ThemeHelper {
    var sTheme = 0

    //private set
    const val THEME_LIGHT = 0
    const val THEME_DARK = 1

    fun justUpdateThemeValue(activity: Activity, themeChecked: Boolean) {
        sTheme = if (themeChecked) THEME_DARK else THEME_LIGHT
        PreferenceHelper.defaultPrefs(activity)[Constants.theme_key] = sTheme
    }

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    fun changeToTheme(activity: Activity, theme: Int) {
        //Updating theme to preference
        PreferenceHelper.defaultPrefs(activity)[Constants.theme_key] = theme

        sTheme = theme
        activity.finish()
        activity.startActivity(Intent(activity, activity.javaClass))
    }

    /** Set the theme of the activity, according to the configuration.  */
    fun onActivityCreateSetTheme(activity: Activity) {
        when (sTheme) {
            THEME_LIGHT -> activity.setTheme(R.style.Theme_Light)
            THEME_DARK -> activity.setTheme(R.style.Theme_Dark)
            else -> activity.setTheme(R.style.Theme_Light)
        }
    }
}