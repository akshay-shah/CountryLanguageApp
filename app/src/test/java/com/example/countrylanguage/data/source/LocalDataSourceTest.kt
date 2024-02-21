package com.example.countrylanguage.data.source

import com.example.countrylanguage.data.insertDBCountryWithLanguages
import com.example.countrylanguage.data.listCountryWithLanguages
import com.example.countrylanguage.data.source.local.CountryLanguageDao
import com.example.countrylanguage.data.source.local.CountryLanguageJoin
import com.example.countrylanguage.data.source.local.LocalDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LocalDataSourceTest {

    @Mock
    private lateinit var countryLanguageDao: CountryLanguageDao

    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        localDataSource = LocalDataSource(countryLanguageDao)
    }

    @Test
    fun `getCountriesWithLanguages() should return a flow`() = runTest {
        whenever(countryLanguageDao.getCountriesWithLanguages()).thenReturn(
            flowOf(
                listCountryWithLanguages
            )
        )
        val result = localDataSource.getCountriesWithLanguages()
        assert(result.first() == listCountryWithLanguages)
        assert(result.first().size == listCountryWithLanguages.size)
    }

    @Test
    fun `getCountriesWithLanguages() should return a emptyflow`() = runTest {
        whenever(countryLanguageDao.getCountriesWithLanguages()).thenReturn(
            flowOf(
                emptyList()
            )
        )
        val result = localDataSource.getCountriesWithLanguages()
        assert(result.first().isEmpty())
    }

    @Test
    fun `saveCountriesWithLanguages should call CountryLanguageDao insert methods`() = runTest {

        val countries = insertDBCountryWithLanguages.map { it.country }
        val languages =
            insertDBCountryWithLanguages.flatMap { it.languages }.distinctBy { it.languageCode }
        val joins = insertDBCountryWithLanguages.flatMap { countryWithLanguages ->
            countryWithLanguages.languages.map { language ->
                CountryLanguageJoin(countryWithLanguages.country.countryCode, language.languageCode)
            }
        }

        localDataSource.saveCountriesWithLanguages(insertDBCountryWithLanguages)

        verify(countryLanguageDao).insertCountries(countries)
        verify(countryLanguageDao).insertLanguages(languages)
        verify(countryLanguageDao).insertCountryLanguageJoins(joins)
    }
}