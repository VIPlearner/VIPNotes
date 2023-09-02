package com.viplearner.feature.home.domain.entity

sealed class HomeError {
    data object GetListError : HomeError()
    data object GetListBySearchTextError : HomeError()
    data object AddNoteError : HomeError()
    data object DeleteNoteError : HomeError()
    data object PinNotesError : HomeError()
    data object UnpinNotesError : HomeError()
    data object UnknownError : HomeError()
}
