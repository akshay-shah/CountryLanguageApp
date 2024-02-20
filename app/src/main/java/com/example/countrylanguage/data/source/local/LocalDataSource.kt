package com.example.countrylanguage.data.source.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val countryLanguageDao: CountryLanguageDao) {

    fun getCountriesWithLanguages(): Flow<List<CountryWithLanguages>> =
        countryLanguageDao.getCountriesWithLanguages()


    suspend fun saveCountriesWithLanguages(data: List<CountryWithLanguages>) =
        with(countryLanguageDao) {
            data.forEach { countryWithLanguage ->
                insertCountry(
                    Country(
                        countryWithLanguage.country.countryCode,
                        countryWithLanguage.country.name
                    )
                )
                countryWithLanguage.languages.forEach { language ->
                    insertLanguage(
                        Language(
                            language.languageCode,
                            language.name
                        )
                    )
                    insertCountryLanguageJoin(
                        CountryLanguageJoin(
                            countryWithLanguage.country.countryCode,
                            language.languageCode
                        )
                    )
                }
            }
        }

}