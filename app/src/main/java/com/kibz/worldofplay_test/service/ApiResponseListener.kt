package com.kibz.worldofplay_test.service;

import retrofit2.Call;

interface ApiResponseListener {
    /**
     * Call on success API response with request code
     */
    @Throws(Exception::class)
    fun onApiResponse(call: Call<Any>, response: Any, reqCode: Int)

    fun onApiError(call: Call<Any>, t: Throwable)

}