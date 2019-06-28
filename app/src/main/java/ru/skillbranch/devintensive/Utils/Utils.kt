package ru.skillbranch.devintensive.Utils

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
        return if ("$firstInitial$lastInitial".isEmpty()) "null" else "$firstInitial$lastInitial"
    }
}