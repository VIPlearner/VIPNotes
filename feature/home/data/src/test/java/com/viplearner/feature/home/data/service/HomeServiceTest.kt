package com.viplearner.feature.home.data.service

import com.viplearner.feature.home.data.repository.HomeRepositoryImpl
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers

class HomeServiceTest {
    private val homeService = mockk<HomeService>(relaxed = true)
    private val homeRepository = HomeRepositoryImpl(homeService, Dispatchers.IO)

//    @Test
//    fun `get empty list when search string is wrong`() {
//        runTest {
//            coEvery {
//                homeService.getListBySearchText("köpek").first()
//            } returns
//
//            val result = homeRepository.getList("köpek"){}
//            coVerify { homeService.getListBySearchText("köpek") }
//            assert(result.data?.list?.isEmpty() == true)
//        }
//    }
//
//    @Test
//    fun `throw GetListError when errorCode is GET_LIST_ERROR(9141L)`() {
//        runTest {
//            coEvery {
//                homeService.getList(-1, "kedi")
//            } returns Response(
//                status = Status.FAIL,
//                result = null,
//                errorCode = HomeErrorCode.GET_LIST_ERROR
//            )
//
//            val result = homeRepository.getList(-1, "kedi").first { result ->
//                result is Result.Error
//            }
//
//            coVerify { homeService.getList(-1, "kedi") }
//            assert(result.error == HomeError.GetListError)
//        }
//    }
//
//    @Test
//    fun `throw UnknownError when an exception caused`() {
//        runTest {
//            coEvery {
//                homeService.getList(-1, "kedi")
//            } answers {
//                throw RuntimeException()
//            }
//
//            val result = homeRepository.getList(-1, "kedi").first { result ->
//                result is Result.Error
//            }
//
//            coVerify { homeService.getList(-1, "kedi") }
//            assert(result.error == HomeError.UnknownError)
//        }
//    }
}