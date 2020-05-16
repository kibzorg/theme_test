package com.kibz.worldofplay_test.util

import android.text.TextUtils
import android.util.Patterns
import androidx.core.util.PatternsCompat
import java.util.regex.Pattern


object Validator {

    //if string is not empty and is valid mail id return true
    fun isValidMail(email: String?): Boolean {
        return (!email.isNullOrEmpty()
                && PatternsCompat.EMAIL_ADDRESS.matcher(email)
            .matches())
    }

    // 8-16 characters password with at least one digit, at least one
    // lowercase letter at least one uppercase letter, at least one
    // special character with no whitespaces
    private const val PASSWORD_REGEX =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$"

    private val PASSWORD_PATTERN: Pattern = Pattern.compile(PASSWORD_REGEX)
    fun isValidPass(pass: String?): Boolean {
        return (!pass.isNullOrEmpty() && PASSWORD_PATTERN.matcher(pass).matches())
    }
}