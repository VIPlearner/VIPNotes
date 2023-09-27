package com.viplearner.common.domain.auth_repository

import com.viplearner.common.domain.entity.UserData
interface AuthRepository {
    suspend fun signOut()
    suspend fun signIn(
        email: String,
        password: String,
    ) : UserData
    suspend fun signUp(
        email: String,
        password: String,
    ) : UserData
//    suspend fun resetPassword(email: String)
//    suspend fun updatePassword(password: String)
//    suspend fun updateEmail(email: String)
//    suspend fun deleteAccount()
}