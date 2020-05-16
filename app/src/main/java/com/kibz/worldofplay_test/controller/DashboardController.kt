package com.kibz.worldofplay_test.controller

import androidx.lifecycle.Observer
import com.kibz.worldofplay_test.R
import com.kibz.worldofplay_test.data.Story
import com.kibz.worldofplay_test.data.StoryManager
import com.kibz.worldofplay_test.data.TopStoryManager
import com.kibz.worldofplay_test.ui.activity.DashboardActivity
import com.kibz.worldofplay_test.util.Utils

class DashboardController(private val activity: DashboardActivity) {

    private var currPage = 0

    private fun initialiseIds() {
        TopStoryManager.getTopStory().observe(activity, Observer {
            if (it == null) {
                activity.isApiCalling(false)
                Utils.showToast(activity, activity.getString(R.string.msg_something_went_wrong))
            } else {
                currPage = 0
                loadData()
            }
        })

    }

    fun loadData() {
        activity.isApiCalling(true)

        if (TopStoryManager.getIdList().value == null || TopStoryManager.getIdList().value!!.size == 0) {
            initialiseIds()
            //TopStoryManager.getTopStory()
        } else {
            //check page index
            val fromIndex = (currPage * 20)
            var toIndex = fromIndex + 20
            if (toIndex > TopStoryManager.getIdList().value?.size ?: 0)
                toIndex = TopStoryManager.getIdList().value?.size ?: 0

            var ids = TopStoryManager.getIdList().value?.subList(fromIndex, toIndex)

            ids?.let {
                StoryManager.getStories(it).observe(activity, Observer { stories ->
                    //show data to list
                    gotStoryData(stories)
                })
            }
            //continue
            currPage++
        }
    }

    fun onRefreshData() {
        currPage = 0

        TopStoryManager.getIdList().value?.clear()
        StoryManager.clearList()
        mainStories.clear()

        StoryManager.getStoryList().value = StoryManager.getStoryList().value

        loadData()
    }

    private var mainStories = ArrayList<Story>()

    private fun gotStoryData(stories: MutableList<Story>) {
        mainStories.addAll(stories)

        activity.setData(mainStories)
        activity.isApiCalling(false)
    }

}