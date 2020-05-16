package com.kibz.worldofplay_test.ui

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.kibz.worldofplay_test.service.ApiResponseListener
import com.kibz.worldofplay_test.util.ThemeHelper
import com.kibz.worldofplay_test.util.Utils
import retrofit2.Call


abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity(), View.OnClickListener,
    ApiResponseListener {

    lateinit var binding: B

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun setupScreen()

    var mLastClickTime: Long = 0
    var preventionTime: Long = 1000
    private lateinit var handler: Handler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        //Setting theme
        ThemeHelper.onActivityCreateSetTheme(this);

        bind()
        Utils.hideSoftKeyboard(this)
        handler = Handler()
        setupScreen()
    }

    private fun bind() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.executePendingBindings()
    }

    override fun onApiResponse(call: Call<Any>, response: Any, reqCode: Int) {
    }

    override fun onApiError(call: Call<Any>, t: Throwable) {
    }

    override fun onClick(v: View?) {
        Utils.hideSoftKeyboard(v)
        if (SystemClock.elapsedRealtime() - mLastClickTime < preventionTime) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()

        v?.isEnabled = false
        handler.postDelayed({ v?.isEnabled = (true) }, preventionTime)
    }
}