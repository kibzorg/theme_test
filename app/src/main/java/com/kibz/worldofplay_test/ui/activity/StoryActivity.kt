package com.kibz.worldofplay_test.ui.activity

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.kibz.worldofplay_test.R
import com.kibz.worldofplay_test.data.Story
import com.kibz.worldofplay_test.databinding.ActivityStoryBinding
import com.kibz.worldofplay_test.ui.BaseActivity
import com.kibz.worldofplay_test.util.Constants
import kotlinx.android.synthetic.main.activity_story.*


class StoryActivity : BaseActivity<ActivityStoryBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_story
    }

    override fun setupScreen() {
        val story = intent?.getSerializableExtra(Constants.BUNDLE_STORY_MODEL) as? Story

        binding.story = story
        webView.webViewClient = AppWebViewClients(loading)
        webView.loadUrl(story?.storyUrl ?: "www.google.co.in")
    }

    class AppWebViewClients(private val progressBar: ProgressBar) : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            // TODO Auto-generated method stub
            view?.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }

        init {
            progressBar.visibility = View.VISIBLE
        }
    }
}