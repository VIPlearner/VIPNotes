package com.viplearner.feature.home.domain.entity

sealed class HomeError {
    data object GetListError : HomeError()
    data object GetListBySearchTextError : HomeError()
    data object LoadUserDataError : HomeError()
    data object AddNoteError : HomeError()
    data object DeleteNoteError : HomeError()
    data object PinNotesError : HomeError()
    data object UnpinNotesError : HomeError()
    data object SignOutError : HomeError()
    data object SignInError : HomeError()
    data object SignUpError : HomeError()
    data object NetworkError : HomeError()
    data object SyncNotesError: HomeError()
    data object LoadSyncStateError : HomeError()
    data object UnknownError : HomeError()
}
