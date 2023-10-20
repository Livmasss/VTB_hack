package com.livmas.vtb_hack.object_creaters

import android.util.Log
import com.livmas.vtb_hack.MainActivity
import com.livmas.vtb_hack.MapObjectsHolder
import com.livmas.vtb_hack.R
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.RotationType
import com.yandex.runtime.image.ImageProvider

class Marker(private val activity: MainActivity) {
    fun putMark(point: Point, workload: Float) {

        val imageProvider = ImageProvider.fromResource(activity,
            if (workload < 25)
                R.drawable.icon_base
            else if (workload < 50)
                R.drawable.icon_yellow
            else if (workload < 75)
                R.drawable.icon_orange
            else
                R.drawable.icon_red
        )
        val placemark = activity.binding.mvMap.map.mapObjects.addPlacemark().apply {
            geometry = point
            setIcon(imageProvider)
            addTapListener(placemarkTapListener)
            setIconStyle(
                IconStyle(null, RotationType.ROTATE, null, null, null, 0.1f, null)
            )
        }

        activity.holder.addMark(placemark)
    }
    private val placemarkTapListener = MapObjectTapListener { it, _ ->
        if (activity.holder.location == null)
            return@MapObjectTapListener true

        activity.holder.goal = (it as PlacemarkMapObject).geometry
        activity.router.buildRoute()
        true
    }
}