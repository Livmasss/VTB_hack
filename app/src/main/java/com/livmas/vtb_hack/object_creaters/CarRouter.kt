package com.livmas.vtb_hack.object_creaters

import android.graphics.Color
import android.util.Log
import com.livmas.vtb_hack.MapObjectsHolder
import com.livmas.vtb_hack.enums.MyObjects
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObjectCollection

class CarRouter(private val objects: MapObjectCollection, private val holder: MapObjectsHolder) {
    private val router = DirectionsFactory.getInstance().createDrivingRouter()
    private val animator: PolylineAnimator = PolylineAnimator(objects, holder, PolylineAnimator.PolylineAttrs(
        Color.GREEN,
        5f,
        Color.BLACK,
        1f,
        0f,
        0f
    )
    )
    private val vehicleOptions = VehicleOptions()
    private val drivingOptions = DrivingOptions().apply {
        routesCount = 1
    }

    private val drivingRouteListener = object : DrivingSession.DrivingRouteListener {
        override fun onDrivingRoutes(drivingRoutes: MutableList<DrivingRoute>) {
            val polylineMP = objects.addPolyline(drivingRoutes[0].geometry)
            polylineMP.setStrokeColor(Color.TRANSPARENT)

            holder.addObject(MyObjects.ROUTE, polylineMP)
            animator.showPolyline(drivingRoutes[0].geometry.points)
        }

        override fun onDrivingRoutesError(p0: com.yandex.runtime.Error) {
            Log.e("route", p0.toString())
        }
    }

    fun buildRoute(start: Point, end: Point): DrivingSession {
        val points = buildList {
            add(RequestPoint(start, RequestPointType.WAYPOINT, null, null))
            add(RequestPoint(end, RequestPointType.WAYPOINT, null, null))
        }
        return router.requestRoutes(
            points,
            drivingOptions,
            vehicleOptions,
            drivingRouteListener
        )
    }
}