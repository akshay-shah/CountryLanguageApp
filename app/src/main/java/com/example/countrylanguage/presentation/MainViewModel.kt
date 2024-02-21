package com.example.countrylanguage.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countrylanguage.data.Repository
import com.example.countrylanguage.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _countries = MutableStateFlow<Result<List<Country>>>(Result.Loading)
    val countries: StateFlow<Result<List<Country>>> = _countries

    init {
        viewModelScope.launch {
            repository.getCountryWithLanguages()
                .collect { result ->
                    _countries.value = result
                }
        }
    }
}