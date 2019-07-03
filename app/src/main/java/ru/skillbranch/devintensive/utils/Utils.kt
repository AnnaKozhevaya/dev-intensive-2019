package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?) : Pair<String?, String?> {
        if (fullName == null || fullName.trim().isEmpty()) {
            return null to null
        }

        val parts : List<String>? = fullName.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val transliterationMap: HashMap<String, String> =
            hashMapOf(
                "а" to "a",
                "б" to "b",
                "в" to "v",
                "г" to "g",
                "д" to "d",
                "е" to "e",
                "ё" to "e",
                "ж" to "zh",
                "з" to "z",
                "и" to "i",
                "й" to "i",
                "к" to "k",
                "л" to "l",
                "м" to "m",
                "н" to "n",
                "о" to "o",
                "п" to "p",
                "р" to "r",
                "с" to "s",
                "т" to "t",
                "у" to "u",
                "ф" to "f",
                "х" to "h",
                "ц" to "c",
                "ч" to "ch",
                "ш" to "sh",
                "щ" to "sh'",
                "ъ" to "",
                "ы" to "i",
                "ь" to "",
                "э" to "e",
                "ю" to "yu",
                "я" to "ya"
            )

        var buffer = ""
        for (i in payload.indices) {
            if (payload[i].toString() == " ") {
                buffer += divider
                continue
            }

            if (!transliterationMap[payload[i].toString()].isNullOrEmpty()){
                buffer += transliterationMap[payload[i].toString()]
                continue
            }

            if (!transliterationMap[payload[i].toString().toLowerCase()].isNullOrEmpty()) {
                buffer += transliterationMap[payload[i].toString().toLowerCase()]?.toUpperCase()
                continue
            }

            buffer += payload[i].toString()
        }

        return buffer
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstInitial = firstName?.trim()?.firstOrNull()?.toUpperCase() ?: ""
        val lastInitial = lastName?.trim()?.firstOrNull()?.toUpperCase() ?: ""
        return if ("$firstInitial$lastInitial".isEmpty()) null else "$firstInitial$lastInitial"
    }

    fun getDeclensionSeconds(seconds: Int): String {
        return when(if (seconds < 20) seconds else seconds % 10) {
            0, in 5.. 20 -> "секунд"
            1 -> "секунду"
            else -> "секунды"
        }
    }

    fun getDeclensionMinutes(minutes: Int): String {
        return when(if (minutes < 20) minutes else minutes % 10) {
            0, in 5.. 20 -> "минут"
            1 -> "минуту"
            else -> "минуты"
        }
    }

    fun getDeclensionHours(hours: Int): String {
        return when(if (hours < 20) hours else hours % 10) {
            0, in 5.. 20 -> "часов"
            1 -> "час"
            else -> "часа"
        }
    }

    fun getDeclensionDays(days: Int): String {
        return when(if (days < 20) days else days % 10) {
            0, in 5.. 20 -> "дней"
            1 -> "день"
            else -> "дня"
        }
    }
}