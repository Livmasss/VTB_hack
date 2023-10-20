package com.livmas.vtb_hack.object_creaters

import com.livmas.vtb_hack.MapObjectsHolder
import com.livmas.vtb_hack.enums.RouteType
import com.yandex.mapkit.map.MapObjectCollection

class TotalRouter(mapObjects: MapObjectCollection, private val holder: MapObjectsHolder) {
    private val driving: CarRouter
    private val walking: WalkingRouter
    private val mass: MassRouter
    var type: RouteType = RouteType.Driving

    init {
        mass = MassRouter(mapObjects, holder)
        driving = CarRouter(mapObjects, holder)
        walking = WalkingRouter(mapObjects, holder)
    }

    fun buildRoute() {
        when (type) {
            RouteType.Driving -> putDriving()
            RouteType.Walking -> putWalking()
            RouteType.Mass -> putMass()
        }
    }

    private fun putDriving() {
        holder.goal?.let { goal ->
            holder.location?.let { loc ->
                driving.buildRoute(loc, goal)
            }
        }
    }
    private fun putWalking() {
        holder.location?.let { loc ->
            holder.goal?.let { goal ->
                walking.createWalkingRoute(loc, goal)
            }
        }
    }
    private fun putMass() {
        holder.location?.let { loc ->
            holder.goal?.let { goal ->
                mass.createRoute(loc, goal)
            }
        }
    }
}