package com.example.countrylanguage.data

import com.example.countrylanguage.data.model.CountryLanguageDataEntity
import com.example.countrylanguage.data.source.local.CountryWithLanguages
import com.example.countrylanguage.data.source.local.LocalDataSource
import com.example.countrylanguage.data.source.remote.RemoteDataSource
import com.example.countrylanguage.model.Country
import com.example.countrylanguage.model.Language
import com.example.countrylanguage.presentation.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

typealias CountryModel = com.example.countrylanguage.data.source.local.Country
typealias LanguageModel = com.example.countrylanguage.data.source.local.Language

class Repository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) {

    suspend fun getCountryWithLanguages(): Flow<Result<List<Country>>> = flow {
        emit(Result.Loading)
        val localData = local.getCountriesWithLanguages().first()
        if (localData.isEmpty()) {
            val response = remote.getCountriesAndLanguages()
            val countryWithLanguage = response.body()?.data?.toDbModel() ?: emptyList()
            local.saveCountriesWithLanguages(countryWithLanguage)
        }
        emit(Result.Success(local.getCountriesWithLanguages().first().map { it.toDomainModel() }))
    }.catch { e ->
        emit(Result.Error(e.message.toString()))
    }

    private fun CountryWithLanguages.toDomainModel(): Country = Country(
        countryCode = country.countryCode,
        name = country.name,
        language = languages.map { Language(it.languageCode, it.name) }
    )

    private fun CountryLanguageDataEntity.toDbModel(): List<CountryWithLanguages> =
        countries.map { country ->
            CountryWithLanguages(
                CountryModel(country.code, country.name),
                country.languages.map { language ->
                    LanguageModel(language.code, language.name)
                }
            )
        }
}