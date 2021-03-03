package com.rolla.testapp.model

data class Button(
    val name: String,
    val position: Int,
    var inQueue: Boolean = false
)