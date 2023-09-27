package com.viplearner.common.domain.entity

sealed class AuthError {
    data object SignInError : AuthError()
    data object SignUpError : AuthError()
}