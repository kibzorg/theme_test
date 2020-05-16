package com.kibz.worldofplay_test.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresPermission


class Utils {

    /**
     * object instantiation is lazy. In other words, it will be instantiated only when needed the first
     */
    companion object {

        /**
         * Check if there is any connectivity
         *
         * @param context the context
         * @return boolean boolean
         */
        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
        fun isConnected(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            return if (connectivityManager is ConnectivityManager) {
                val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
                networkInfo?.isConnected ?: false
            } else false
        }

        @JvmStatic
        fun hideSoftKeyboard(context: Context) {
            try {
                var inputmngr =
                    context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputmngr.hideSoftInputFromWindow(null, 0)
            } catch (e: Exception) {
            }
        }

        @JvmStatic
        fun hideSoftKeyboard(view: View?) {
            try {
                var inputmngr =
                    view?.context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputmngr.hideSoftInputFromWindow(view.windowToken, 0)
            } catch (e: Exception) {
            }
        }

        @JvmStatic
        fun hideSoftKeyboard(activity: Activity) {
            try {
                var inputmngr =
                    activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputmngr.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
            } catch (e: Exception) {
            }
        }

        fun showSoftKeyboard(view: View) {
            try {
                var inputmngr =
                    view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputmngr.toggleSoftInputFromWindow(view.windowToken, 0, 0)
            } catch (e: Exception) {
            }
        }

        fun showSoftKeyboard(activity: Activity) {
            try {
                var inputmngr =
                    activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputmngr.toggleSoftInputFromWindow(activity.currentFocus?.windowToken, 0, 0)
            } catch (e: Exception) {
            }
        }

        private var toast: Toast? = null

        /**
         * shows short duration toast messages
         */
        fun showToast(context: Context, message: String) {
            toast?.cancel()
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toast?.show()
        }

        /**
         * Function used to customize start activity function
         */
        fun startAct(context: Context, nextActivity: Activity) {
            context.startActivity(Intent(context, nextActivity.javaClass))
        }

    }

}