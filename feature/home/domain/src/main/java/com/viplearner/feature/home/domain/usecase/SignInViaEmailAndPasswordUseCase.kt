package com.viplearner.feature.home.domain.usecase

import com.viplearner.feature.home.domain.repository.HomeRepository
import javax.inject.Inject

class SignInViaEmailAndPasswordUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        homeRepository.signIn(email, password)
}
