package com.example.countrylanguage.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.countrylanguage.data.source.local.CountryWithLanguages
import com.example.countrylanguage.data.source.local.LocalDataSource
import com.example.countrylanguage.data.source.remote.RemoteDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource


    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = Repository(localDataSource, remoteDataSource)
    }

    @Test
    fun `getCountryWithLanguages should return success when local data is available`() =
        runTest {
            val localData = listCountryWithLanguages
            `when`(localDataSource.getCountriesWithLanguages()).thenReturn(flowOf(localData))

            val result = repository.getCountryWithLanguages().toList()

            assertEquals(1, result.size)
            val countries = result[0]
            assertEquals(1, countries.size)
            assertEquals("US", countries[0].countryCode)
            assertEquals("United States", countries[0].name)
            assertEquals(1, countries[0].language.size)
            assertEquals("en", countries[0].language[0].languageCode)
            assertEquals("English", countries[0].language[0].name)
        }

    @Test
    fun `getCountryWithLanguages should fetch remote data and save it locally when local data is empty`() =
        runTest {
            val localData = mutableListOf<CountryWithLanguages>()
            `when`(localDataSource.getCountriesWithLanguages())
                .thenReturn(flowOf(localData))
                .thenReturn(flowOf(listCountryWithLanguages))
            val remoteData = countryLanguageEntity
            `when`(remoteDataSource.getCountriesAndLanguages()).thenReturn(
                Response.success(
                    remoteData
                )
            )

            val result = repository.getCountryWithLanguages().toList()

            assertEquals(1, result.size)
            val countries = result[0]
            assertEquals(1, countries.size)
            assertEquals("US", countries[0].countryCode)
            assertEquals("United States", countries[0].name)
            assertEquals(1, countries[0].language.size)
            assertEquals("en", countries[0].language[0].languageCode)
            assertEquals("English", countries[0].language[0].name)
        }

    @Test
    fun `getCountryWithLanguages should return error when remote data fetch fails`() =
        runTest {
            val exception = CancellationException("Network error")
            `when`(localDataSource.getCountriesWithLanguages()).thenReturn(flowOf(emptyList()))
            `when`(remoteDataSource.getCountriesAndLanguages()).thenThrow(exception)

            try {
                repository.getCountryWithLanguages().toList()
            } catch (e: Exception) {
                assertEquals("Network error", e.message)
            }
        }
}
