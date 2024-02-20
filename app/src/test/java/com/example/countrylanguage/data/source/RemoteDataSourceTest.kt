package com.example.countrylanguage.data.source

import com.example.countrylanguage.data.countryLanguageEntity
import com.example.countrylanguage.data.model.CountryLanguageEntity
import com.example.countrylanguage.data.source.remote.ApiService
import com.example.countrylanguage.data.source.remote.RemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import retrofit2.Response

private const val requestBody =
    """{"query":"query { countries { code,name,languages { code,name }}}"}"""

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        remoteDataSource = RemoteDataSource(apiService)
    }

    @Test
    fun `getCountriesAndLanguages should call ApiService and return success`() = runTest {
        val expectedResponse = Response.success(countryLanguageEntity)
        whenever(apiService.getCountryAndLanguage(requestBody)).thenReturn(expectedResponse)

        val response = remoteDataSource.getCountriesAndLanguages()

        verify(apiService).getCountryAndLanguage(requestBody)
        assert(response == expectedResponse)
    }

    @Test
    fun `getCountriesAndLanguages should call ApiService and return error`() = runTest {
        val errorResponse =
            Response.error<CountryLanguageEntity>(404, okhttp3.ResponseBody.create(null, ""))
        whenever(apiService.getCountryAndLanguage(requestBody)).thenReturn(errorResponse)

        val response = remoteDataSource.getCountriesAndLanguages()

        verify(apiService).getCountryAndLanguage(requestBody)
        assert(response == errorResponse)
    }

}