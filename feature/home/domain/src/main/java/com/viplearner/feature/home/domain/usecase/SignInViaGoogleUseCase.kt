package com.viplearner.feature.home.domain.usecase

import com.viplearner.feature.home.domain.repository.HomeRepository
import javax.inject.Inject

class SignInViaGoogleUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() =
        homeRepository.signInViaGoogle()
}