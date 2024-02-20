package com.example.countrylanguage.data.source.remote

import com.example.countrylanguage.data.model.CountryLanguageEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/graphql")
    suspend fun getCountryAndLanguage(@Body query: String): Response<CountryLanguageEntity>
}