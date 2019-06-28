package ru.skillbranch.devintensive.Extensions

import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

enum class TimeUnit {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy") : String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnit = TimeUnit.SECOND) : Date {
    var time = this.time

    time += when (units) {
        TimeUnit.SECOND -> value * SECOND
        TimeUnit.MINUTE -> value * MINUTE
        TimeUnit.HOUR -> value * HOUR
        TimeUnit.DAY -> value * DAY
    }

    this.time = time

    return this
}

fun Date.humanizeDifferent() : String {
    return humanizeDiff(this)
}

fun humanizeDiff(date: Date = Date()): String {
    val diff = ((Date().time - date.time) / SECOND).toInt()

    var res = ""
    when (abs(diff)) {
        in DateIntervals.JustNow.range -> res = DateIntervals.JustNow.getHumanizeName(diff)
        in DateIntervals.FewSecondsAgo.range -> res = DateIntervals.FewSecondsAgo.getHumanizeName(diff)
        in DateIntervals.MinuteAgo.range -> res = DateIntervals.MinuteAgo.getHumanizeName(diff)
        in DateIntervals.FewMinutesAgo.range -> res = DateIntervals.FewMinutesAgo.getHumanizeName(diff)
        in DateIntervals.HourAgo.range -> res = DateIntervals.HourAgo.getHumanizeName(diff)
        in DateIntervals.FewHoursAgo.range -> res = DateIntervals.FewHoursAgo.getHumanizeName(diff)
        in DateIntervals.DayAgo.range -> res = DateIntervals.DayAgo.getHumanizeName(diff)
        in DateIntervals.FewDaysAgo.range -> res = DateIntervals.FewDaysAgo.getHumanizeName(diff)
        in DateIntervals.MoreYear.range -> res = DateIntervals.MoreYear.getHumanizeName(diff)
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
                return "${if (secons > 0) "" else "через "}$minutes ${getDeclensionMinutes(minutes)} ${if (secons > 0) "назад" else ""}"
            }
            HourAgo -> {
                return if (secons > 0) "час назад" else "через час"
            }
            FewHoursAgo -> {
                val hour = abs(secons / (60 * 60))
                return "${if (secons > 0) "" else "через "}$hour ${getDeclensionHours(hour)} ${if (secons > 0) "назад" else ""}"
            }
            DayAgo -> {
                return if (secons > 0) "день назад" else "через день"
            }
            FewDaysAgo -> {
                val days = abs(secons / (60 * 60 * 24))
                return "${if (secons > 0) "" else "через "}$days ${getDeclensionDays(days)} ${if (secons > 0) "назад" else ""}"
            }
            MoreYear -> return if (secons > 0) "более года назад" else "более чем через год"
        }
    }

    private fun getDeclensionMinutes(minutes: Int): String {
        return when(if (minutes < 20) minutes else minutes % 10) {
            0, in 5.. 20 -> "минут"
            1 -> "минуту"
            else -> "минуты"
        }
    }

    private fun getDeclensionHours(hours: Int): String {
        return when(if (hours < 20) hours else hours % 10) {
            0, in 5.. 20 -> "часов"
            1 -> "час"
            else -> "часа"
        }
    }

    private fun getDeclensionDays(days: Int): String {
        return when(if (days < 20) days else days % 10) {
            0, in 5.. 20 -> "дней"
            1 -> "день"
            else -> "дня"
        }
    }
}