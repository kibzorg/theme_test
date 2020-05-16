package com.kibz.worldofplay_test.data.response

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kibz.worldofplay_test.data.Story

abstract class CommonResponse(
    @SerializedName("description") @Expose var message: String = "",
    @SerializedName("StatusCode") @Expose var statusCode: Int = 0,
    @SerializedName("success") @Expose var success: Boolean = false,
    @SerializedName("error") @Expose var error: String = ""
)

data class LoginResponse(
    @SerializedName("token") @Expose var token: String
) : CommonResponse()

data class NetworkStory(@SerializedName("id") @Expose var id: Long) {
    @SerializedName("score")
    @Expose
    var score = 0

    @SerializedName("by")
    @Expose
    var by: String? = null

    @SerializedName("time")
    @Expose
    var time = 0L

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("descendants")
    @Expose
    var descendants = 0

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("kids")
    @Expose
    var kids: LongArray? = null

    fun toStory() = Story(
        id = id,
        title = if (title.isNullOrBlank()) "" else title.toString(),
        by = if (by.isNullOrBlank()) "" else by.toString(),
        score = score,
        storyUrl = if (url.isNullOrBlank()) "" else url.toString()
    )
}