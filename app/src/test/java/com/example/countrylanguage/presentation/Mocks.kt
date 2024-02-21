package com.example.countrylanguage.presentation

import com.example.countrylanguage.domain.model.Country
import com.example.countrylanguage.domain.model.Language

val viewModelCountryListMock = Country(
    countryCode = "in",
    name = "India",
    language = listOf(Language("hi", "Hindi"))
)
