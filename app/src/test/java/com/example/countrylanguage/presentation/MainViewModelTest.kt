package com.example.countrylanguage.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.countrylanguage.domain.usecase.GetCountriesWithLanguageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: GetCountriesWithLanguageUseCase

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `countries flow should emit Result_Success when repository returns success`() = runTest {
        val countries = listOf(viewModelCountryListMock)
        val flow = flowOf(Result.Success(countries))
        `when`(useCase.invoke()).thenReturn(flow)


        viewModel = MainViewModel(useCase)


        val result = viewModel.countries.value
        assertTrue(result is Result.Success)
        assertEquals(countries, (result as Result.Success).data)
    }


    @Test
    fun `countries flow should emit Result_Error when repository returns error`() = runTest {
        val flow = flowOf(Result.Error("Unknown Error"))
        `when`(useCase.invoke()).thenReturn(flow)


        val viewModel = MainViewModel(useCase)


        val result = viewModel.countries.value
        assertTrue(result is Result.Error)
        assertEquals("Unknown Error", (result as Result.Error).msg)
    }

    @Test
    fun `countries flow should emit Result_Loading when repository returns loading`() = runTest {
        val flow = flowOf(Result.Loading)
        `when`(useCase.invoke()).thenReturn(flow)


        val viewModel = MainViewModel(useCase)


        val result = viewModel.countries.value
        assertTrue(result is Result.Loading)
    }
}
