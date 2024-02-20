package com.example.countrylanguage.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Country::class, Language::class, CountryLanguageJoin::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryLanguageDao(): CountryLanguageDao
}