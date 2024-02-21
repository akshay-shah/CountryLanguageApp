package com.example.countrylanguage.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countrylanguage.domain.model.Country
import com.example.countrylanguage.domain.usecase.GetCountriesWithLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val usecase: GetCountriesWithLanguageUseCase) :
    ViewModel() {

    private val _countries = MutableStateFlow<Result<List<Country>>>(Result.Loading)
    val countries: StateFlow<Result<List<Country>>> = _countries

    init {
        viewModelScope.launch {
            usecase.invoke()
                .collect { result ->
                    _countries.value = result
                }
        }
    }
}