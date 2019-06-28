package ru.skillbranch.devintensive.Extensions

import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

enum class TimeUtils {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy") : String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUtils = TimeUtils.SECOND) : Date {
    var time = this.time

    time += when (units) {
        TimeUtils.SECOND -> value * SECOND
        TimeUtils.MINUTE -> value * MINUTE
        TimeUtils.HOUR -> value * HOUR
        TimeUtils.DAY -> value * DAY
    }

    this.time = time

    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    return ""
}