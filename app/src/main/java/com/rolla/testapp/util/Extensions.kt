package com.rolla.testapp.util

import com.rolla.testapp.model.Button

fun isInList(button: Button, buttons: MutableList<Button>): Boolean {
    for (btn in buttons) {
        if (btn.position == button.position) {
            return true
        }
    }
    return false
}

fun findPositionInList(button: Button, buttons: MutableList<Button>): Int {
    for (i in 0 until buttons.size) {
        if (buttons[i].position == button.position) {
            return i
        }
    }
    return -1
}