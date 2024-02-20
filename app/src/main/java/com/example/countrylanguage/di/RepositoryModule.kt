package com.example.countrylanguage.di

import com.example.countrylanguage.data.Repository
import com.example.countrylanguage.data.source.local.CountryLanguageDao
import com.example.countrylanguage.data.source.local.LocalDataSource
import com.example.countrylanguage.data.source.remote.ApiService
import com.example.countrylanguage.data.source.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesRemote(apiService: ApiService): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    fun providesLocal(countryLanguageDao: CountryLanguageDao): LocalDataSource {
        return LocalDataSource(countryLanguageDao)
    }

    @Provides
    fun provideRepository(local: LocalDataSource, remote: RemoteDataSource): Repository {
        return Repository(local, remote)
    }
}