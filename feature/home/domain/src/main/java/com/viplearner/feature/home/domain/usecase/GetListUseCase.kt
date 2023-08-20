package com.viplearner.feature.home.domain.usecase

import com.viplearner.common.domain.Result
import com.viplearner.feature.home.domain.entity.GetNoteEntityListResponse
import com.viplearner.feature.home.domain.entity.HomeError
import com.viplearner.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(
    ): Flow<Result<GetNoteEntityListResponse, HomeError>> = homeRepository.getList()
}