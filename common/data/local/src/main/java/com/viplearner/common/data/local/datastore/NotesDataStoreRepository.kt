/*
 * Copyright (c) 2022. Nomba Financial Services
 *
 * author: Victor Shoaga
 * email: victor.shoaga@nomba.com
 * github: @inventvictor
 *
 */

package com.viplearner.common.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface NotesDataStoreRepository {

    suspend fun clearData()

    suspend fun saveUserData(state: String)
    fun getUserData(): Flow<String>
}