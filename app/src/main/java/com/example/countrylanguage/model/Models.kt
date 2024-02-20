package com.example.countrylanguage.model


data class Language(
    val languageCode: String,
    val name: String
)

data class Country(
    val countryCode: String,
    val name: String,
    val language: List<Language>
)

