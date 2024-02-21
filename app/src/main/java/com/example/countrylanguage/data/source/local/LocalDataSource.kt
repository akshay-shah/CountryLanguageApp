package com.example.countrylanguage.data.source.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val countryLanguageDao: CountryLanguageDao) {

    fun getCountriesWithLanguages(): Flow<List<CountryWithLanguages>> =
        countryLanguageDao.getCountriesWithLanguages()


    suspend fun saveCountriesWithLanguages(data: List<CountryWithLanguages>) =
        withContext(Dispatchers.IO) {
            val countries = data.map { it.country }
            val languages = data.flatMap { it.languages }.distinctBy { it.languageCode }
            val joins = data.flatMap { countryWithLanguages ->
                countryWithLanguages.languages.map { language ->
                    CountryLanguageJoin(
                        countryWithLanguages.country.countryCode,
                        language.languageCode
                    )
                }
            }

            countryLanguageDao.insertCountries(countries)
            countryLanguageDao.insertLanguages(languages)
            countryLanguageDao.insertCountryLanguageJoins(joins)
        }

}