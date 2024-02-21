package com.example.countrylanguage.domain

import com.example.countrylanguage.domain.model.Country
import com.example.countrylanguage.domain.model.Language
import com.example.countrylanguage.domain.usecase.GetCountriesWithLanguageUseCase
import com.example.countrylanguage.presentation.Result
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCountriesWithLanguageUseCaseTest {

    @Mock
    private lateinit var repository: IRepository

    private lateinit var getCountriesWithLanguageUseCase: GetCountriesWithLanguageUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getCountriesWithLanguageUseCase = GetCountriesWithLanguageUseCase(repository)
    }

    @Test
    fun `invoke should return success with data when repository has data`() = runTest {
        val countryList = listOf(Country("US", "United States", listOf(Language("en", "English"))))
        `when`(repository.getCountryWithLanguages()).thenReturn(flowOf(countryList))

        val result = getCountriesWithLanguageUseCase().toList()

        assertEquals(2, result.size)
        assertEquals(Result.Loading, result[0])
        assertEquals(Result.Success(countryList), result[1])
    }

    @Test
    fun `invoke should return error when repository has no data`() = runTest {
        `when`(repository.getCountryWithLanguages()).thenReturn(flowOf(emptyList()))

        val result = getCountriesWithLanguageUseCase().toList()

        assertEquals(2, result.size)
        assertEquals(Result.Loading, result[0])
        assertEquals(Result.Error("Error: No countries available"), result[1])
    }

    @Test
    fun `invoke should return error when repository throws exception`() = runTest {
        val exception = CancellationException("Network error")
        `when`(repository.getCountryWithLanguages()).thenThrow(exception)

        val result = getCountriesWithLanguageUseCase().toList()

        assertEquals(2, result.size)
        assertEquals(Result.Loading, result[0])
        assertEquals(Result.Error("Network error"), result[1])
    }
}
