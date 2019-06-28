package ru.skillbranch.devintensive.Extensions

import ru.skillbranch.devintensive.Models.User
import ru.skillbranch.devintensive.Models.UserView
import ru.skillbranch.devintensive.Utils.Utils

fun User.toUserView() : UserView {

    val nickName = Utils.transliteration("$firstName $lastName")
    val initials = Utils.toInitials(firstName, lastName)
    val status = if (lastVisit == null) "Еще ни разу не быд" else if (isOnline) "online" else "Последний раз был ${humanizeDiff()}"

    return UserView(
        id,
        fullName = "$firstName $lastName",
        avatar = avatar,
        nickName = nickName ,
        initials = initials ,
        status = status
    )
}


