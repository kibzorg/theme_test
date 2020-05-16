package com.kibz.worldofplay_test.service

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.kibz.worldofplay_test.BuildConfig
import com.kibz.worldofplay_test.R
import com.kibz.worldofplay_test.util.Constants
import com.kibz.worldofplay_test.util.Utils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class RestClient {

    //private val context: Context = context
    private var restrictedAPI: ArrayList<String> = ArrayList()

    lateinit var apiService: APIService

    init {
        setupRestClient()
        restrictedAPI.add(Constants.BASE_URL + "some api")
    }

    private fun setupRestClient() {
        val gson = GsonBuilder().setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").excludeFieldsWithoutExposeAnnotation().create()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getHttpClient()).build()

        apiService = retrofit.create(APIService::class.java)
    }

    private fun getHttpClient(): OkHttpClient {
        val builderOkHttp = OkHttpClient.Builder()
        builderOkHttp.connectTimeout(15, TimeUnit.SECONDS)
        builderOkHttp.readTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builderOkHttp.addInterceptor(httpLoggingInterceptor)
        }
        return builderOkHttp.build()
    }

    companion object {
        fun makeApiRequest(
            activity: Context, call: Any, apiResponseListener: ApiResponseListener?, reqCode: Int
        ) {
            try {
                //            final DatabaseHelper databaseHelper = PetBubsApplication.application.getDatabaseHelper();
                val objectCall = call as Call<Any>
                //Check token available if logged in else show login screen
                //            if (restrictedApi.size() > 0 && ((PetBubsApplication) context.getApplicationContext()).getPreferencesString(context.getString(R.string.pref_token)).length() == 0 && restrictedApi.contains(objectCall.request().url().toString())) {
                //                Utils.startActivityForResultWithoutAnim(activity, new Intent(activity, LoginActivity.class), Constants.REQ_LOGIN_ACTIVITY);
                //                return;
                //            }
                Utils.hideSoftKeyboard(activity)
                if (Utils.isConnected(activity)) {
                    objectCall.enqueue(object : Callback<Any> {
                        override fun onResponse(
                            call: Call<Any>,
                            response: retrofit2.Response<Any>?
                        ) {
                            try {
                                if (response != null)
                                    apiResponseListener?.onApiResponse(call, response, reqCode)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        override fun onFailure(call: Call<Any>, t: Throwable) {
                            //There is more than just a failing request (like: no internet connection)
                            Log.e("error", "" + t.toString())
                            //Connection error when no internet available show some offline not enabled for this page error.
                            if (!Utils.isConnected(activity)) {
                                apiResponseListener?.onApiError(
                                    call, Throwable(activity.getString(R.string.msg_no_internet), t)
                                )
                            } else {
                                if (t is TimeoutException || t is SocketTimeoutException)
                                    apiResponseListener?.onApiError(
                                        call,
                                        Throwable(activity.getString(R.string.msg_conn_timeout), t)
                                    )
                                else
                                    apiResponseListener?.onApiError(
                                        call, Throwable(
                                            activity.getString(R.string.msg_something_went_wrong), t
                                        )
                                    )
                            }
                        }
                    })
                } else {
                    apiResponseListener?.onApiError(
                        call, Throwable(activity.getString(R.string.msg_no_internet))
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


    }
}