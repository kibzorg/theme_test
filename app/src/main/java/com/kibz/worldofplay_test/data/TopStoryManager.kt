package com.kibz.worldofplay_test.data

import androidx.lifecycle.MutableLiveData
import com.kibz.worldofplay_test.service.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object TopStoryManager {
    private var idList: MutableLiveData<MutableList<Long>> = MutableLiveData()

    fun getIdList(): MutableLiveData<MutableList<Long>> {
        return idList
    }

    fun getTopStory(): MutableLiveData<MutableList<Long>> {
        idList = MutableLiveData<MutableList<Long>>()

        RestClient().apiService.getTopStories().enqueue(object : Callback<List<Long>> {
            override fun onFailure(call: Call<List<Long>>, t: Throwable) {
                idList.value = null
            }

            override fun onResponse(
                call: Call<List<Long>>, response: Response<List<Long>>
            ) {
                if (response.isSuccessful) {
                    //StoryManager.clearData()
                    idList.value = response.body()?.toMutableList()
                } else idList.value = null
            }

        })
        return idList
    }


}