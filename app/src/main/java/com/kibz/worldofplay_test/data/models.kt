package com.kibz.worldofplay_test.data

import java.io.Serializable

data class Story(
    var id: Long = 0L,
    var title: String = "",
    var by: String = "",
    var score: Int = 0,
    var storyUrl: String = ""
) : Serializable