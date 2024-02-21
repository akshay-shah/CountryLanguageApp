package com.example.countrylanguage.data

import com.example.countrylanguage.data.model.CountryLanguageDataEntity
import com.example.countrylanguage.data.source.local.CountryWithLanguages
import com.example.countrylanguage.data.source.local.LocalDataSource
import com.example.countrylanguage.data.source.remote.RemoteDataSource
import com.example.countrylanguage.domain.IRepository
import com.example.countrylanguage.domain.model.Country
import com.example.countrylanguage.domain.model.Language
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

typealias CountryModel = com.example.countrylanguage.data.source.local.Country
typealias LanguageModel = com.example.countrylanguage.data.source.local.Language

class Repository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : IRepository {

    override suspend fun getCountryWithLanguages(): Flow<List<Country>> = flow {
        try {
            val localData = local.getCountriesWithLanguages().first()
            if (localData.isEmpty()) {
                val response = remote.getCountriesAndLanguages()
                val countryWithLanguage = response.body()?.data?.toDbModel() ?: emptyList()
                local.saveCountriesWithLanguages(countryWithLanguage)
            }
            emit(local.getCountriesWithLanguages().first().map { it.toDomainModel() })
        } catch (exception: Exception) {
            throw exception
        }
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