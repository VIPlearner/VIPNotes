package com.viplearner.feature.home.presentation.component.sign_in

fun String.isValidPassword(): Boolean {
    // A strong password consists of at least 8 characters
    // and at least 1 number, 1 uppercase, 1 lowercase, and 1 special character
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*_=+-/]).{8,}$".toRegex()
    return passwordPattern.matches(this)
}