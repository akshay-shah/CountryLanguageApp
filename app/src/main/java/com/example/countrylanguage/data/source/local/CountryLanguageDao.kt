package com.example.countrylanguage.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryLanguageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: Country)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguage(language: Language)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountryLanguageJoin(crossRef: CountryLanguageJoin)

    @Transaction
    @Query("SELECT * FROM Country")
    fun getCountriesWithLanguages(): Flow<List<CountryWithLanguages>>

    @Transaction
    @Query("SELECT * FROM Language")
    fun getLanguagesWithCountries(): Flow<List<LanguageWithCountries>>
}