package com.viplearner.feature.home.domain.usecase
//
//import com.viplearner.common.domain.Result
//import com.viplearner.feature.home.domain.repository.FakeHomeRepository
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Test
//
//class GetListUseCaseTest {
//    private lateinit var fakeHomeRepository: FakeHomeRepository
//    private lateinit var getListUseCase: GetListUseCase
//
//    @Before
//    fun setup() {
//        fakeHomeRepository = FakeHomeRepository()
//        getListUseCase = GetListUseCase(fakeHomeRepository)
//    }
//
//    @Test
//    fun `get 10 elements every page`() {
//        runTest {
//            val result = getListUseCase.invoke().first { result -> result is Result.Success }
//            assert(result.data?.list?.size == 10)
//        }
//    }
//
//    @Test
//    fun `search kedi1 get 2 items`() {
//        runTest {
//            val result = getListUseCase.invoke().first { result -> result is Result.Success }
//            assert(result.data?.list?.size == 2)
//        }
//    }
//
//    @Test
//    fun `search unknown item get emptyList`() {
//        runTest {
//            val result = getListUseCase.invoke().first { result -> result is Result.Success }
//            assert(result.data?.list?.size == 0)
//        }
//    }
//}