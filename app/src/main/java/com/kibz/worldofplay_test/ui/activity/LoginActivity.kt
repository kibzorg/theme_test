package com.kibz.worldofplay_test.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.kibz.worldofplay_test.R
import com.kibz.worldofplay_test.controller.LoginController
import com.kibz.worldofplay_test.databinding.ActivityLoginBinding
import com.kibz.worldofplay_test.ui.BaseActivity
import com.kibz.worldofplay_test.util.Constants
import com.kibz.worldofplay_test.util.PreferenceHelper
import com.kibz.worldofplay_test.util.ThemeHelper
import com.kibz.worldofplay_test.util.Utils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<ActivityLoginBinding>(), TextWatcher {

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    private var controller: LoginController? = null

    override fun setupScreen() {
        controller = LoginController(this)
        //Initial UI setup
        isApiCalling(false)

        sw_theme?.isChecked = PreferenceHelper.defaultPrefs(this)
            .getInt(Constants.theme_key, ThemeHelper.THEME_LIGHT) == ThemeHelper.THEME_DARK

        bt_login?.setOnClickListener(this)
        sw_theme?.setOnClickListener(this)
        edt_username?.addTextChangedListener(this)
        edt_password?.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable?) {
        controller?.checkLoginButton(edt_username?.text?.toString(), edt_password?.text?.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    fun enableLogin(isEnable: Boolean) {
        bt_login?.isEnabled = isEnable
    }

    fun isApiCalling(apiCalling: Boolean) {
        edt_username?.isEnabled = !apiCalling
        edt_password?.isEnabled = !apiCalling
        bt_login?.visibility = if (apiCalling) View.INVISIBLE else View.VISIBLE
        loading?.visibility = if (apiCalling) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        if (v?.visibility == View.VISIBLE) {
            when (v.id) {
                bt_login.id -> {
                    //pass login to controller for validation then login api call
                    Utils.hideSoftKeyboard(this)
                    controller?.callLoginAction(
                        edt_username?.text?.toString(),
                        edt_password?.text?.toString()
                    )
                }
                sw_theme.id -> {
                    //pass to controller for storing theme value
                    controller?.onThemeChange(sw_theme.isChecked)
                }
            }
        }
    }

}