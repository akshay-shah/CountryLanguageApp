package com.example.countrylanguage.domain.usecase


import com.example.countrylanguage.domain.IRepository
import com.example.countrylanguage.domain.model.Country
import com.example.countrylanguage.presentation.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCountriesWithLanguageUseCase @Inject constructor(private val repository: IRepository) {
    suspend operator fun invoke(): Flow<Result<List<Country>>> = flow {
        try {
            emit(Result.Loading)
            repository.getCountryWithLanguages().collect { data: List<Country> ->
                if (data.isEmpty()) {
                    emit(Result.Error("Error: No countries available"))
                } else {
                    emit(Result.Success(data))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}