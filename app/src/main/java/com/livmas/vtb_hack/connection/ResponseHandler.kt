package com.livmas.vtb_hack.connection

import android.util.Log
import android.widget.Toast
import com.livmas.vtb_hack.MainActivity
import com.livmas.vtb_hack.R
import com.livmas.vtb_hack.object_creaters.Marker
import retrofit2.Response

class ResponseHandler(private val activity: MainActivity) {
    private val marker = Marker(activity)

    private val tag = "http"
    fun handle(response: Response<List<BankResponse>>) {

        val body = response.body()
        if (body == null) {
            Toast.makeText(
                activity,
                R.string.server_error,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        Log.d(tag, response.code().toString())
        when (response.code()) {
            200 -> {
                for (i in body) {
                    val point = com.yandex.mapkit.geometry.Point(i.latitude, i.longitude)
                    activity.runOnUiThread {
                        marker.putMark(point, i.workload)
                    }
                }
            }
            else ->
                activity.runOnUiThread {
                    Toast.makeText(
                        activity,
                        R.string.server_error,
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }
}