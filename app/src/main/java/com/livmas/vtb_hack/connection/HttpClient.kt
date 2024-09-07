package com.livmas.vtb_hack.connection

import android.util.Log
import com.livmas.vtb_hack.BuildConfig
import com.livmas.vtb_hack.MainActivity
import com.livmas.vtb_hack.object_creaters.Marker
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
    private val marker = Marker(activity)

    init {
        api = retrofit.create(MainApi::class.java)
        handler = ResponseHandler(activity)
    }

    fun query(point: Point, criteria: String) {
        Log.d(tag, "Criteria: $criteria")

        val date = Date(System.currentTimeMillis())
        val time = "${date.hours}:${date.minutes}"
        Log.d(tag, time)
//        val call = api.sendLocation(
//            BankRequest(
//                point.latitude,
//                point.longitude,
//                criteria,
//                true,
//                "16:40"
//            )
//        )
        Log.d(tag, "Call performed")

        CoroutineScope(Dispatchers.IO).launch{
            try {
                Log.d(tag, "Response appeared")

//                val res = call.execute()
//                handler.handle(res)
//                activity.runOnUiThread {
//                    activity.binding.pbLoading.visibility = View.GONE
//                }
                val bank1 = BankResponse(
                    address = "Пушкинская",
                    55.681122,
                    37.557435,
                    criteria = "",
                    type = true,
                    workload = 0.4f,
                    id = 123
                )
                val bank2 = BankResponse(
                    address = "Кемеровская",
                    55.770844,
                    37.689903,
                    criteria = "",
                    type = true,
                    workload = 0.2f,
                    id = 12312
                )

                val point1 = Point(bank1.latitude, bank1.longitude)
                val point2 = Point(bank2.latitude, bank2.longitude)
                activity.runOnUiThread {
                    marker.putMark(point1, bank1.workload)
                    marker.putMark(point2, bank2.workload)
                }
                Log.d(tag, "Response handled")
            }
            catch (e: Exception) {
                Log.e(tag, e.message.toString())
            }
        }
    }
}