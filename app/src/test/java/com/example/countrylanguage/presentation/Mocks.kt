package com.example.countrylanguage.presentation

import com.example.countrylanguage.model.Country
import com.example.countrylanguage.model.Language

val viewModelCountryListMock = Country(
    countryCode = "in",
    name = "India",
    language = listOf(Language("hi", "Hindi"))
)
