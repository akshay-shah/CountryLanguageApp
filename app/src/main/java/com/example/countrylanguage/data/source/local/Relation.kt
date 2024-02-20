package com.example.countrylanguage.data.source.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CountryWithLanguages(
    @Embedded val country: Country,
    @Relation(
        parentColumn = "countryCode",
        entityColumn = "languageCode",
        associateBy = Junction(CountryLanguageJoin::class)
    )
    val languages: List<Language>
)


//Future use if required
data class LanguageWithCountries(
    @Embedded val language: Language,
    @Relation(
        parentColumn = "languageCode",
        entityColumn = "countryCode",
        associateBy = Junction(CountryLanguageJoin::class)
    )
    val countries: List<Country>
)