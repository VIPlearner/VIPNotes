package com.viplearner.common.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)
