package ru.skillbranch.devintensive.extensions

fun String.truncate(count: Int = 16): String {
    var res = this.trim()

    if (res.length > count) {
        res = "${res.substring(0, count).trim()}..."
    }

    return res
}

fun String.stripHtml(): String {
    var buffer = ""
    var openIndex = 0
    var closeIndex = 0
    for (i in this.indices) {
        if (this[i].toString() == "<" || i == this.length) {
            openIndex = i
        }
        if (this[i].toString() == ">") {
            closeIndex = i + 1
        }

        if (openIndex > closeIndex) {
            buffer += this.substring(closeIndex, openIndex).trim()
            closeIndex = openIndex
        }
    }

    buffer = buffer.replace("\\s+".toRegex(), " ")

    return buffer
}