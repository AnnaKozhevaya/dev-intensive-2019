package ru.skillbranch.devintensive.Models

import ru.skillbranch.devintensive.Extensions.humanizeDiff
import java.util.*

class ImageMessage(
    id: String,
    from: User?,
    chat: Chat,
    isIncoming: Boolean = false,
    date: Date = Date(),
    var image: String?
): BaseMessage(id, from, chat, isIncoming, date) {
    override fun formateMessage(): String =
        "id: $id ${from?.firstName} ${if (isIncoming) "получил" else "отправил"} изображение \"$image\" ${date.humanizeDiff()}"
}