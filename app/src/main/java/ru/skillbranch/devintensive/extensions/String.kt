package ru.skillbranch.devintensive.extensions

fun String.truncate(count: Int = 16): String {
    var res = this.trim()

    if (res.length > count) {
        res = "${res.substring(0, count).trim()}..."
    }

    return res
}

fun String.stripHtml() = replace("&(?:[a-z\\d]+|#\\d+|#x[a-f\\d]+);".toRegex(), "")
    .replace("<(.|\\n)*?>".toRegex(), "")
    .replace("\\s+".toRegex(), " ")
