package com.example.zmice.logic

import kotlin.math.pow
import kotlin.math.sqrt

object Angle{
    fun getAngle(x1: Float, y1: Float, x2: Float, y2: Float): Double {
        val rad = Math.atan2((y1 - y2).toDouble(), (x2 - x1).toDouble()) + Math.PI
        return (rad * 180 / Math.PI + 180) % 360
    }
    fun inRange(angle: Double, init: Float, end: Float): Boolean {
        return angle >= init && angle < end
    }
    fun fromAngle(angle: Double): Int {
        return if (inRange(angle, 45F, 135F)) {
            1
        } else if (inRange(angle, 0F, 45F) || inRange(angle, 315F, 360F)) {
            2
        } else if (inRange(angle, 225F, 315F)) {
            3
        } else {
            4
        }
    }
    fun getDistance(x1: Float, y1: Float, x2: Float, y2: Float):Float{
        val distance = sqrt((x1-x2).pow(2) + (y1-y2).pow(2))
        return distance
    }

}