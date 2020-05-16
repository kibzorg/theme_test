package com.kibz.worldofplay_test.controller

import android.text.TextUtils
import androidx.lifecycle.Observer
import com.kibz.worldofplay_test.R
import com.kibz.worldofplay_test.data.LoginManager
import com.kibz.worldofplay_test.data.request.LoginRequest
import com.kibz.worldofplay_test.ui.activity.DashboardActivity
import com.kibz.worldofplay_test.ui.activity.LoginActivity
import com.kibz.worldofplay_test.util.ThemeHelper
import com.kibz.worldofplay_test.util.Utils
import com.kibz.worldofplay_test.util.Validator
import javax.net.ssl.HttpsURLConnection

class LoginController(private val activity: LoginActivity) {
    private var themeValue: Int = ThemeHelper.sTheme

    fun isLightTheme(): Boolean {
        return themeValue == ThemeHelper.THEME_LIGHT
    }

    fun onThemeChange(isLightChecked: Boolean) {
        ThemeHelper.justUpdateThemeValue(activity, isLightChecked)
    }

    fun checkLoginButton(email: String?, password: String?) {
        if (TextUtils.isEmpty(email) || email?.trim()?.isEmpty()!!
            || TextUtils.isEmpty(password) || password?.trim()?.isEmpty()!!
        )
            activity.enableLogin(false)
        else
            activity.enableLogin(true)
    }

    private fun validate(email: String?, password: String?): Boolean {
        var message = ""
        if (TextUtils.isEmpty(email)) {
            message = activity.getString(R.string.msg_ent_email)
        } else if (!Validator.isValidMail(email)) {
            message = activity.getString(R.string.msg_ent_valid_email)
        } else if (TextUtils.isEmpty(password)) {
            message = activity.getString(R.string.msg_ent_pass)
        } else if (!Validator.isValidPass(password)) {
            message = activity.getString(R.string.msg_ent_valid_pass)
        }
        return if (!TextUtils.isEmpty(message)) {
            Utils.showToast(activity, message)
            false
        } else {
            true
        }
    }

    fun callLoginAction(email: String?, password: String?) {
        if (!validate(email, password)) return

        activity?.isApiCalling(true)
        LoginManager.getMockLogin(
            activity,
            /*getLogin(*/
            LoginRequest(email, password)
        )
            .observe(activity, Observer {
                if (it != null) {
                    when (it.statusCode) {
                        HttpsURLConnection.HTTP_OK -> {//success
                            Utils.showToast(activity, it.token)
                            Utils.startAct(activity, DashboardActivity())
                            activity.finish()
                        }
                        HttpsURLConnection.HTTP_UNAUTHORIZED -> {
                            Utils.showToast(activity, it.message)
                        }
                        HttpsURLConnection.HTTP_BAD_REQUEST -> {
                            Utils.showToast(activity, it.message)
                        }
                    }
                }
                activity.isApiCalling(false)
            })
    }


}