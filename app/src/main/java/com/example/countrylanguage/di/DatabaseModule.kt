package com.example.countrylanguage.di

import android.content.Context
import androidx.room.Room
import com.example.countrylanguage.data.source.local.AppDatabase
import com.example.countrylanguage.data.source.local.CountryLanguageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "country_language_database"
        ).build()
    }

    @Provides
    fun provideCountryLanguageDao(database: AppDatabase): CountryLanguageDao {
        return database.countryLanguageDao()
    }
}