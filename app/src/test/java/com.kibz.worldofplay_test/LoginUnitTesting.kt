package com.kibz.worldofplay_test

import android.app.Activity
import androidx.lifecycle.Observer
import com.kibz.worldofplay_test.data.LoginManager
import com.kibz.worldofplay_test.data.request.LoginRequest
import com.kibz.worldofplay_test.ui.activity.LoginActivity
import com.kibz.worldofplay_test.util.Validator
import org.junit.Test

class LoginUnitTesting {

    @Test
    fun checkEmailValidation() {
        val someMails =
            arrayOf(null, "", "sdf", "wrongMailid", "abc@g.com", "abc@gmail.com", "xy@.com")
        val validator = Validator

        for (mailId in someMails) {
            val result = validator.isValidMail(mailId)
            println("Email '$mailId' is valid : $result")
        }
    }

    @Test
    fun checkPassValidations() {
        val somePasswords =
            arrayOf(
                "password", null, "", "Password", "MorePassword", "Password1",
                "CorrectPass@0", "VeryLong@Password12"
            )
        val validator = Validator

        for (pass in somePasswords) {
            val result = validator.isValidPass(pass)
            println("Password : '$pass' is valid : $result")
        }
    }

    @Test
    fun checkLoginCredential() {
        LoginManager.getMockLogin(
            context = Activity(),//dummy
            request = LoginRequest("test@worldofplay.in", "Worldofplay@2020")
        ).observe(LoginActivity(), Observer {
            print("Response of login : $it")
        }
        )
    }
}