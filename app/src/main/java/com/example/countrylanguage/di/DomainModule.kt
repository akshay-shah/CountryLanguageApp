package com.example.countrylanguage.di

import com.example.countrylanguage.domain.IRepository
import com.example.countrylanguage.domain.usecase.GetCountriesWithLanguageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
    @Provides
    fun provideGetCountriesWithLanguagesUseCase(repository: IRepository): GetCountriesWithLanguageUseCase {
        return GetCountriesWithLanguageUseCase(repository)
    }
}