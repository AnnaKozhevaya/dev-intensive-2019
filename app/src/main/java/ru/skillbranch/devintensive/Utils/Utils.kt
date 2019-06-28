package ru.skillbranch.devintensive.Utils

object Utils {
    fun parseFullName(fullName: String?) : Pair<String?, String?> {
        //TODO FIX ME!!!
        val parts : List<String>? = fullName?.split(" ")

        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        return ""
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        return ""
    }
}