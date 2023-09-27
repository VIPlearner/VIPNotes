package com.viplearner.common.data.remote.auth_repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.viplearner.common.domain.auth_repository.AuthRepository
import com.viplearner.common.domain.entity.UserData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class AuthRepositoryImpl: AuthRepository {

    val auth = Firebase.auth
    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun signIn(
        email: String,
        password: String,
    ): UserData {
        try{
            val result = auth.signInWithEmailAndPassword(email, password).await()
            return UserData(
                result.user?.uid?: throw NullPointerException("UserID cannot be null"),
                result.user?.email ?: throw NullPointerException("Email cannot be null"),
                result.user?.photoUrl.toString()
            )
        }
        catch(e: Exception) {
            Timber.d("AuthRepository signIn: $e")
            throw e
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun signUp(email: String, password: String): UserData {
        try{
            Timber.d("AuthRepository signUp: $email $password")
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Timber.d("AuthRepository signUp: $result")
            return UserData(
                result.user?.uid ?: throw NullPointerException("UserID cannot be null"),
                result.user?.email ?: throw NullPointerException("Email cannot be null"),
                result.user?.photoUrl.toString()
            )
        }
        catch(e: Exception) {
            Timber.d("AuthRepository signUp: $e")
            throw e
        }
    }
}