package com.example.countrylanguage.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(@PrimaryKey val countryCode: String, val name: String)

@Entity
data class Language(@PrimaryKey val languageCode: String, val name: String)

@Entity(primaryKeys = ["countryCode", "languageCode"])
data class CountryLanguageJoin(
    val countryCode: String,
    val languageCode: String
)