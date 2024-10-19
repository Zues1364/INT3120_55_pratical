package com.example.flightSearch.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flightSearch.data.Flight

@Composable
fun FavoritesBody(
    favorites: List<Flight>,
    onFavoriteClicked: (flight: Flight) -> Unit,
    modifier: Modifier = Modifier
) {
    Column()
    {
        Text(
            text = "Favorite routes",
            modifier = Modifier
                .padding(start = 16.dp, bottom = 12.dp),
            style = MaterialTheme.typography.titleMedium
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.fillMaxSize()
        ) {
            items(items = favorites) { favorite ->
                FlightCard(
                    flight = favorite,
                    onFavoriteClicked = onFavoriteClicked
                )
            }
        }
    }
}
