/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.flightSearch.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightSearch.data.Airport
import com.example.flightSearch.data.Flight
import com.example.flightSearch.ui.AppViewModelProvider
import com.example.flightSearch.ui.FlightSearchViewModel
import com.example.flightSearch.ui.components.AppTopBar
import com.example.flightSearch.ui.components.SearchingBar
import com.example.flightSearch.ui.navigation.NavigationDestination
import com.example.flightSearch.R

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FlightSearchApp(
    viewModel: FlightSearchViewModel = viewModel(factory = AppViewModelProvider.factory)
) {
    val homeUiState by viewModel.flightSearchUiState.collectAsState()
    val searchBarUiState by viewModel.searchBarUiState.collectAsState()
    val query by viewModel.searchQuery.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(null) {
        viewModel.initialize()
    }
    SearchScreenLayout(
        uiState = homeUiState,
        searchBarUiState = searchBarUiState,
        query = query,
        onSearchInputChanged = { input -> viewModel.updateSearchQuery(input) },
        onSearchInputClicked = { viewModel.searchBarDeactivated() },
        onClearInputClicked = { viewModel.clearSearchQuery() },
        onChevronClicked = {
            viewModel.searchBarDeactivated()
            keyboardController?.hide()
        },
        onItemClicked = viewModel::onSearchClicked,
        onFavoriteClicked = viewModel::onFavoriteClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenLayout(
    uiState: FlightSearchViewModel.FlightSearchUiState,
    searchBarUiState: FlightSearchViewModel.SearchBarUiState,
    query: String,
    onSearchInputChanged: (String) -> Unit,
    onSearchInputClicked: () -> Unit,
    onClearInputClicked: () -> Unit,
    onChevronClicked: () -> Unit,
    onItemClicked: (Airport) -> Unit,
    onFavoriteClicked: (flight: Flight) -> Unit,

    ) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            SearchingBar(
                searchBarState = searchBarUiState,
                inputText = query,
                onClearInputClicked = onClearInputClicked,
                onSearchInputChanged = onSearchInputChanged,
                onClicked = onSearchInputClicked,
                onChevronClicked = onChevronClicked,
            )
            Spacer(modifier = Modifier.height(20.dp))
            when (uiState) {
                is FlightSearchViewModel.FlightSearchUiState.IdleScreen -> {
                    if (uiState.favoriteList.isNotEmpty()) {
                        FavoritesBody(
                            favorites = uiState.favoriteList,
                            onFavoriteClicked = onFavoriteClicked,
                            modifier = Modifier.fillMaxSize(),
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.undraw_search),
                                contentDescription = "Illustration",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                is FlightSearchViewModel.FlightSearchUiState.LoadingScreen -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is FlightSearchViewModel.FlightSearchUiState.ErrorScreen -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Error :(", color = MaterialTheme.colorScheme.error)
                    }
                }

                is FlightSearchViewModel.FlightSearchUiState.EmptyScreen -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No results for this input")
                    }
                }

                is FlightSearchViewModel.FlightSearchUiState.SearchResultFetched -> {
                    val airports by uiState.data.collectAsState(initial = emptyList()) // Collect the flow
                    SuggestionsBody(
                        airports = airports, // Pass the collected list
                        modifier = Modifier.fillMaxSize(),
                        onItemClicked = onItemClicked
                    )
                }

                is FlightSearchViewModel.FlightSearchUiState.SearchFlightClicked -> {
                    val flightsFrom = uiState.departureAirport
                    val flightsTo = uiState.flightList
                    FlightsBody(
                        flightsFrom = flightsFrom,
                        flightsTo = flightsTo,
                        onFavoriteClicked = onFavoriteClicked,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

