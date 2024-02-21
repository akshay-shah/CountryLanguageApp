package com.example.countrylanguage.domain

import com.example.countrylanguage.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getCountryWithLanguages(): Flow<List<Country>>
}