package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Int): String {
        var result = "$value "
        result += when (this) {
            SECOND -> Utils.getDeclensionSeconds(value)
            MINUTE -> Utils.getDeclensionMinutes(value)
            HOUR -> Utils.getDeclensionHours(value)
            DAY -> Utils.getDeclensionDays(value)
        }
        return result
    }
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy") : String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND) : Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time

    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = ((date.time - this.time) / SECOND).toInt()

    var res = ""
    when (abs(diff)) {
        in DateIntervals.JustNow.range -> res = DateIntervals.JustNow.getHumanizeName(diff).trim()
        in DateIntervals.FewSecondsAgo.range -> res = DateIntervals.FewSecondsAgo.getHumanizeName(diff).trim()
        in DateIntervals.MinuteAgo.range -> res = DateIntervals.MinuteAgo.getHumanizeName(diff).trim()
        in DateIntervals.FewMinutesAgo.range -> res = DateIntervals.FewMinutesAgo.getHumanizeName(diff).trim()
        in DateIntervals.HourAgo.range -> res = DateIntervals.HourAgo.getHumanizeName(diff).trim()
        in DateIntervals.FewHoursAgo.range -> res = DateIntervals.FewHoursAgo.getHumanizeName(diff).trim()
        in DateIntervals.DayAgo.range -> res = DateIntervals.DayAgo.getHumanizeName(diff).trim()
        in DateIntervals.FewDaysAgo.range -> res = DateIntervals.FewDaysAgo.getHumanizeName(diff).trim()
        in DateIntervals.MoreYear.range -> res = DateIntervals.MoreYear.getHumanizeName(diff).trim()
    }

    return res
}

enum class DateIntervals(val range: IntRange) {
    JustNow(0..1),
    FewSecondsAgo(1..45),
    MinuteAgo(45..75),
    FewMinutesAgo(75..45*60),
    HourAgo(45*60..75*60),
    FewHoursAgo(75*60..22*60*60),
    DayAgo(22*60*60..26*60*60),
    FewDaysAgo(26*60*60..360*60*60*24),
    MoreYear(360*60*60*24..Int.MAX_VALUE);

    fun getHumanizeName(secons: Int) : String {
        when (this) {
            JustNow -> return "только что"
            FewSecondsAgo -> {
                return if (secons > 0) "несколько секунд назад" else "через несколько секунд"
            }
            MinuteAgo -> {
                return if (secons > 0) "минуту назад" else "через минуту"
            }
            FewMinutesAgo ->  {
                val minutes = abs(secons / 60)
                return "${if (secons > 0) "" else "через "}$minutes ${Utils.getDeclensionMinutes(minutes)} ${if (secons > 0) "назад" else ""}"
            }
            HourAgo -> {
                return if (secons > 0) "час назад" else "через час"
            }
            FewHoursAgo -> {
                val hour = abs(secons / (60 * 60))
                return "${if (secons > 0) "" else "через "}$hour ${Utils.getDeclensionHours(hour)} ${if (secons > 0) "назад" else ""}"
            }
            DayAgo -> {
                return if (secons > 0) "день назад" else "через день"
            }
            FewDaysAgo -> {
                val days = abs(secons / (60 * 60 * 24))
                return "${if (secons > 0) "" else "через "}$days ${Utils.getDeclensionDays(days)} ${if (secons > 0) "назад" else ""}"
            }
            MoreYear -> return if (secons > 0) "более года назад" else "более чем через год"
        }
    }
}