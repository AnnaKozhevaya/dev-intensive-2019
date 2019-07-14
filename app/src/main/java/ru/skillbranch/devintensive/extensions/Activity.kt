package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    if (isKeyboardClosed()) return

    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
}

fun Activity.isKeyboardOpen(): Boolean = getDiffVisibleHeightAndAllHeight(this) != 0

fun Activity.isKeyboardClosed(): Boolean = getDiffVisibleHeightAndAllHeight(this) == 0

fun getDiffVisibleHeightAndAllHeight(activity: Activity): Int {
    val rect = Rect()
    val rootView = activity.window.decorView
    rootView.getWindowVisibleDisplayFrame(rect)
    rootView.rootView.height
    Log.d("M_Activity","DiffVisibleHeightAndAllHeight = ${rootView.rootView.height - (rect.bottom - rect.top)}")
    return rootView.rootView.height - (rect.bottom - rect.top)
}