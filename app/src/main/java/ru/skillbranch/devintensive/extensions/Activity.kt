package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    if (isKeyboardClosed()) return

    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
}

fun Activity.isKeyboardOpen(): Boolean = isKeyboardShown()

fun Activity.isKeyboardClosed(): Boolean = !isKeyboardShown()

fun Activity.isKeyboardShown(): Boolean {
    val rect = Rect()
    val rootView = findViewById<View>(android.R.id.content)
    rootView.getWindowVisibleDisplayFrame(rect)
    val hightDiff = rootView.height - rect.height()
    val marginOfError = Math.round(
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            50F,
            this.resources.displayMetrics))

    return hightDiff > marginOfError
}