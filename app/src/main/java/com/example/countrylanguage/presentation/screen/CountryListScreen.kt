package com.example.countrylanguage.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countrylanguage.model.Country
import com.example.countrylanguage.presentation.MainViewModel
import com.example.countrylanguage.presentation.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesListScreen(viewModel: MainViewModel = viewModel()) {
    val countriesResult by viewModel.countries.collectAsState()

    when (countriesResult) {
        is Result.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is Result.Error -> {
            Text(
                text = "Error: ${(countriesResult as Result.Error).msg}",
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f)
            )
        }

        is Result.Success -> {
            val countries = (countriesResult as Result.Success<List<Country>>).data
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Country",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold)
                        )
                    },
                )
                ExpandableCountryList(countries)
            }

        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableCountryList(countries: List<Country>) {
    var expandedCountryIndex by remember { mutableStateOf(-1) }

    LazyColumn {
        itemsIndexed(countries) { index, country ->
            val paddingAnim = animateDpAsState(
                targetValue = if (index == expandedCountryIndex) 24.dp else 16.dp,
                animationSpec = tween(300), label = ""
            )
            Column(modifier = Modifier
                .fillMaxWidth(1f)
                .clickable {
                    expandedCountryIndex = if (expandedCountryIndex == index) -1 else index
                }
                .padding(horizontal = paddingAnim.value)
            ) {
                Text(
                    text = country.name,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                AnimatedVisibility(visible = index == expandedCountryIndex) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        country.language.forEach { language ->
                            Text(
                                text = language.name,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .align(Alignment.CenterHorizontally)
                                    .padding(start = 16.dp, bottom = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}