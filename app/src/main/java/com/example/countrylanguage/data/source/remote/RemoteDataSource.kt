package com.example.countrylanguage.data.source.remote

import com.example.countrylanguage.data.model.CountryLanguageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

private const val requestBody =
    """{"query":"query { countries { code,name,languages { code,name }}}"}"""

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getCountriesAndLanguages(): Response<CountryLanguageEntity> =
        withContext(Dispatchers.IO) {
            apiService.getCountryAndLanguage(requestBody)
        }
}