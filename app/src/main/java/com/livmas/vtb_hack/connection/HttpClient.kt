package com.livmas.vtb_hack.connection

import android.util.Log
import android.view.View
import com.livmas.vtb_hack.BuildConfig
import com.livmas.vtb_hack.MainActivity
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.Date


class HttpClient(private val activity: MainActivity) {
    private val tag = "http"
    private var api: MainApi
    private var handler: ResponseHandler
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    init {
        api = retrofit.create(MainApi::class.java)
        handler = ResponseHandler(activity)
    }

    fun query(point: Point, criteria: String) {
        Log.d(tag, "Criteria: $criteria")

        val date = Date(System.currentTimeMillis())
        val time = "${date.hours}:${date.minutes}"
        Log.d(tag, time)
        val call = api.sendLocation(
            BankRequest(
                point.latitude,
                point.longitude,
                criteria,
                true,
                "16:40"
            )
        )
        Log.d(tag, "Call performed")

        CoroutineScope(Dispatchers.IO).launch{
            try {
                val res = call.execute()
                Log.d(tag, "Response appeared")
                handler.handle(res)
                activity.runOnUiThread {
                    activity.binding.pbLoading.visibility = View.GONE

                }
                Log.d(tag, "Response handled")
            }
            catch (e: Exception) {
                Log.e(tag, e.message.toString())
            }
        }
    }
}