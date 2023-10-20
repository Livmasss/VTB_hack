package com.livmas.vtb_hack

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.livmas.vtb_hack.enums.MyObjects
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.PolylineMapObject
import java.lang.Exception
import java.util.EnumMap
import java.util.LinkedList

class MapObjectsHolder(private val mapObjectsColl: MapObjectCollection) {
    private val currentObjects = EnumMap<MyObjects, MapObject>(MyObjects::class.java)
    private var animation = LinkedList<PolylineMapObject>()
    private var marksList = LinkedList<PlacemarkMapObject>()
    var location: Point? = null
    var goal: Point? = null

    companion object {
        val tag = "MapObjs"
    }

    fun hasObject(obj: MyObjects): Boolean {
        return currentObjects[obj] != null
    }

    fun addObject(type: MyObjects, obj: MapObject) {
        if (currentObjects[type] != null)
            mapObjectsColl.remove(currentObjects[type]!!)
        currentObjects[type] = obj
    }

    fun removeRoute() {
        if (currentObjects[MyObjects.ROUTE] == null) {
            Log.d(tag, "Nothing to cancel")
            return
        }
        try {
            mapObjectsColl.remove(currentObjects[MyObjects.ROUTE]!!)
            currentObjects.remove(MyObjects.ROUTE)
        }
        catch (e: Exception) {
            Log.e(tag, e.message.toString())
        }
    }

    fun clearAnim() {
        try {
            repeat (animation.size - 1) {
                mapObjectsColl.remove(animation.pop())
            }
        }
        catch (e: Exception) {
            Log.e(tag, e.message.toString())
        }
    }
    fun removeAnimation() {
        try {
            repeat (animation.size) {
                mapObjectsColl.remove(animation.pop())
            }
        }
        catch (e: Exception) {
            Log.e(tag, e.message.toString())
        }
    }

    fun addAnimation(line: PolylineMapObject) {
        animation.add(line)
    }
    fun addMark(mark: PlacemarkMapObject) {
        marksList.add(mark)
    }
    fun addMarks(marks: List<PlacemarkMapObject>) {
        for (i in marks)
            marksList.add(i)
    }
    fun clearMarks() {
        repeat(marksList.size) {
            mapObjectsColl.remove(marksList.pop())
        }
    }
}