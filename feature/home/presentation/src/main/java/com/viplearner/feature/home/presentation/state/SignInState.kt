package com.viplearner.feature.home.presentation.state

import com.viplearner.common.domain.entity.UserData

sealed class SignInState {
    data object Init: SignInState()
    data object SignUp: SignInState()
    data object Loading: SignInState()
    data class SignInSuccess(val userData: UserData, val isSyncing: Boolean): SignInState()
}