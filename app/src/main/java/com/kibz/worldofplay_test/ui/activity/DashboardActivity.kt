package com.kibz.worldofplay_test.ui.activity

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kibz.worldofplay_test.R
import com.kibz.worldofplay_test.controller.DashboardController
import com.kibz.worldofplay_test.data.Story
import com.kibz.worldofplay_test.databinding.ActivityDashboardBinding
import com.kibz.worldofplay_test.ui.BaseActivity
import com.kibz.worldofplay_test.ui.DashboardAdapter
import com.kibz.worldofplay_test.ui.callback.EndlessRecyclerOnScrollListener
import com.kibz.worldofplay_test.ui.callback.Interfaces
import com.kibz.worldofplay_test.util.Constants.BUNDLE_STORY_MODEL
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity<ActivityDashboardBinding>(), Interfaces.OnRecyclerItemClick {
    override fun getLayoutId(): Int {
        return R.layout.activity_dashboard
    }

    private var controller: DashboardController? = null
    private lateinit var endlessScrollListener: EndlessRecyclerOnScrollListener

    override fun setupScreen() {
        controller = DashboardController(this)
        isApiCalling(false)

        val layoutManager = LinearLayoutManager(this)

        story_list.layoutManager = layoutManager

        endlessScrollListener = object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(current_page: Int) {
                //Do implementation
                controller?.loadData()
            }
        }
        story_list.addOnScrollListener(endlessScrollListener)

        swipeRefreshLayout.setOnRefreshListener {
            // do something
            controller?.onRefreshData()
        }
        controller?.loadData()
    }

    fun isApiCalling(apiCalling: Boolean) {
        loading?.visibility = if (apiCalling) View.VISIBLE else View.GONE
    }

    fun setData(stories: ArrayList<Story>) {
        if (story_list.adapter == null) {
            //create adapter
            story_list.adapter = DashboardAdapter(stories, this)
        } else
        //update adapter
            (story_list.adapter as DashboardAdapter).updateData(stories)


        // after refresh is done, remember to call the following code
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false;  // This hides the spinner
        }

        //after done re-enable load more
        endlessScrollListener.setLoading(false)
    }

    override fun onItemClick(v: View, listItem: Any) {
        if (listItem is Story) {
            val intent = Intent(this, StoryActivity::class.java)
            intent.putExtra(BUNDLE_STORY_MODEL, listItem)
            startActivity(intent)
        }
    }
}