package com.livmas.vtb_hack.object_creaters

import android.util.Log
import com.livmas.vtb_hack.MapObjectsHolder
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

class Locator(mapkit: MapKit, private val mapView: MapView, private val holder: MapObjectsHolder) {
    val tag = "location"
    init {
        val locationLayer = mapkit.createUserLocationLayer(mapView.mapWindow)
        locationLayer.isVisible = true

        mapkit.createLocationManager().subscribeForLocationUpdates(
            0.0, 0, 0.0, true, FilteringMode.ON,
            object:com.yandex.mapkit.location.LocationListener {
                override fun onLocationUpdated(p0: com.yandex.mapkit.location.Location) {
                    val loc = p0.position
                    Log.d(tag, "${loc.latitude}; ${loc.longitude}")
                    if (holder.location == null) {
                        holder.location = loc
                        zoom()
                    }
                }

                override fun onLocationStatusUpdated(p0: LocationStatus) {
                    Log.d(tag, "Status: ${p0.name}")
                }
            })
    }

    private fun zoom() {
        if (holder.location == null)
            return
        mapView.map.move(
            CameraPosition(
                holder.location!!,
                15f,
                0f,
                0f
            ),
            Animation(
                Animation.Type.LINEAR,
                2f
            ),
            null
        )
    }
}