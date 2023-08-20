package com.viplearner.feature.home.domain.entity

sealed class HomeError {
    data object GetListError : HomeError()
    data object AddNoteError : HomeError()
    data object UnknownError : HomeError()
}
