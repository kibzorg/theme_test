package com.kibz.worldofplay_test.service

import com.kibz.worldofplay_test.data.request.LoginRequest
import com.kibz.worldofplay_test.data.response.LoginResponse
import com.kibz.worldofplay_test.data.response.NetworkStory
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {

    @POST("mob-login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("https://hacker-news.firebaseio.com/v0/topstories.json")
    fun getTopStories(): Call<List<Long>>

    @GET("https://hacker-news.firebaseio.com/v0/item/{id}.json")
    fun getStory(@Path("id") id: Long): Call<NetworkStory>

    @GET("https://hacker-news.firebaseio.com/v0/item/{id}.json")
    fun getStoryObservable(@Path("id") id: Long): Observable<NetworkStory>
}