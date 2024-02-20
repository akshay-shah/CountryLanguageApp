package com.example.countrylanguage.data.model

data class CountryEntity(
    val code: String,
    val languages: List<LanguageEntity>,
    val name: String
)