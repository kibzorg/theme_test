package com.kibz.worldofplay_test.data

import android.content.Context
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.kibz.worldofplay_test.data.request.LoginRequest
import com.kibz.worldofplay_test.data.response.LoginResponse
import com.kibz.worldofplay_test.service.RestClient
import com.kibz.worldofplay_test.util.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

object LoginManager {

    private lateinit var data: MutableLiveData<LoginResponse>

    var mockLoginRequest = LoginRequest("test@worldofplay.in", "Worldofplay@2020")

    fun mockLoginResponse(request: LoginRequest): LoginResponse {
        return if (request == mockLoginRequest) {
            //success login
            val response = LoginResponse("VwvYXBpXC9")
            response.statusCode = HttpsURLConnection.HTTP_OK
            response.success = true
            response
        } else {
            //invalid credential
            val errResponse = LoginResponse("")
            errResponse.statusCode = HttpsURLConnection.HTTP_UNAUTHORIZED
            errResponse.success = false
            errResponse.error = "invalid_credentials"
            errResponse.message = "Email address and password is not a valid combination"
            errResponse
        }
    }

    fun mockBadRequestResult(): LoginResponse {
        val errResponse = LoginResponse("")
        errResponse.statusCode = HttpsURLConnection.HTTP_BAD_REQUEST
        errResponse.success = false
        errResponse.error = "bad_request"
        errResponse.message = "Network communication error."
        return errResponse
    }

    fun getMockLogin(context: Context, request: LoginRequest): MutableLiveData<LoginResponse> {
        data = MutableLiveData<LoginResponse>()

        Handler().postDelayed({
            if (Utils.isConnected(context))
                data.value = mockLoginResponse(request)
            else data.value = mockBadRequestResult()
        }, 3000)
        return data
    }

    fun getLogin(request: LoginRequest): MutableLiveData<LoginResponse> {
        data = MutableLiveData<LoginResponse>()

        RestClient().apiService.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                data.value = mockBadRequestResult()
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                data.value = mockLoginResponse(request)
            }
        })
        return data
    }

}
