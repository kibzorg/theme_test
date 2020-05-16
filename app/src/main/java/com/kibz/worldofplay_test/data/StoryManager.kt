package com.kibz.worldofplay_test.data

import androidx.lifecycle.MutableLiveData
import com.kibz.worldofplay_test.data.response.NetworkStory
import com.kibz.worldofplay_test.service.RestClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object StoryManager {

    /*private var story: MutableLiveData<Story> = MutableLiveData()

    fun getStory(id: Long) {
        RestClient().apiService.getStory(id).enqueue(object : Callback<NetworkStory> {
            override fun onFailure(call: Call<NetworkStory>, t: Throwable) {
                story.value = null
            }

            override fun onResponse(call: Call<NetworkStory>, response: Response<NetworkStory>) {
                if (response.isSuccessful)
                    story.value = response.body()?.toStory()
                else story.value = null
            }
        })
    }*/

    private var storyList: MutableLiveData<MutableList<Story>> = MutableLiveData()
    var normalList = ArrayList<Story>()
    fun getStoryList(): MutableLiveData<MutableList<Story>> {
        return storyList
    }

    fun getStories(ids: MutableList<Long>): MutableLiveData<MutableList<Story>> {
        storyList = MutableLiveData<MutableList<Story>>()
        if (ids.isNotEmpty()) {
            val requests: ArrayList<Observable<NetworkStory>> = ArrayList()
            for (id in ids) {
                requests.add(RestClient().apiService.getStoryObservable(id))
            }

            var disposable = Observable.zip(requests) {
                normalList = ArrayList()
                for (result in it) {
                    if (result is NetworkStory)
                        normalList.add(result.toStory())
                }
            }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    storyList.value = (normalList.toMutableList())//completed api call
                }, {
                    storyList.value = null//trigger on error
                    print("on error " + it.message)
                })
        }
        return storyList
    }

    fun clearList() {
        storyList.value?.clear()
    }
}

