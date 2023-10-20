package com.livmas.vtb_hack.object_creaters

import android.animation.ValueAnimator
import com.livmas.vtb_hack.MapObjectsHolder
import com.livmas.vtb_hack.enums.MyObjects
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.MapObjectCollection

class PolylineAnimator(
    private val objects: MapObjectCollection, private val holder: MapObjectsHolder,
    private val attrs: PolylineAttrs
    ) {

    class PolylineAttrs(
        val lineColor: Int,
        val lineWidth: Float,
        val outColor: Int,
        val outWidth: Float,
        val gap: Float,
        val dash: Float
    )

    fun showPolyline(pointsList: List<Point>) {
        val valueAnimator = ValueAnimator.ofInt(1, pointsList.size)
        valueAnimator.duration = 1500

        valueAnimator.addUpdateListener(object: ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator) {
                if (!holder.hasObject(MyObjects.ROUTE)) {
                    animation.removeUpdateListener(this)
                    holder.removeAnimation()
                    return
                }
                val polylineMP = objects.addPolyline(
                    Polyline(pointsList.subList(0, animation.animatedValue as Int))
                ).apply {
                    attrs.apply {
                        setStrokeColor(lineColor)
                        strokeWidth = lineWidth
                        outlineColor = outColor
                        outlineWidth = outWidth

                        gapLength = gap
                        dashLength = dash
                    }
                }
                holder.clearAnim()
                holder.addAnimation(polylineMP)
            }
        })
        valueAnimator.start()
    }
}