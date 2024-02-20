package com.example.countrylanguage.data

import com.example.countrylanguage.data.model.CountryEntity
import com.example.countrylanguage.data.model.CountryLanguageDataEntity
import com.example.countrylanguage.data.model.CountryLanguageEntity
import com.example.countrylanguage.data.model.LanguageEntity
import com.example.countrylanguage.data.source.local.CountryLanguageJoin
import com.example.countrylanguage.data.source.local.CountryWithLanguages

val listCountryWithLanguages = listOf(
    CountryWithLanguages(
        CountryModel("US", "United States"),
        listOf(LanguageModel("en", "English"))
    )
)

val countryLanguageEntity = CountryLanguageEntity(
    CountryLanguageDataEntity(
        listOf(
            CountryEntity(
                code = "US",
                name = "United States",
                languages = listOf(
                    LanguageEntity("en", name = "English")
                )
            )
        )
    )
)

val countryLanguageJoin = CountryLanguageJoin("US", "en")