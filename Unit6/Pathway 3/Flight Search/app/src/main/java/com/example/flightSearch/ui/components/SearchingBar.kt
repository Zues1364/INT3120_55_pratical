package com.example.flightSearch.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.flightSearch.ui.FlightSearchViewModel
import com.example.flightSearch.R

enum class IconType {
    Search,
    Clear,
    Chevron,
}

@Composable
fun SearchingBar(
    searchBarState: FlightSearchViewModel.SearchBarUiState,
    inputText : String,
    onSearchInputChanged: (String) -> Unit,
    onClearInputClicked: () -> Unit,
    onClicked: () -> Unit,
    onChevronClicked: () -> Unit,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 8.dp, end = 12.dp)
            ,
        value = inputText,
        onValueChange = { newText -> onSearchInputChanged(newText) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        shape = RoundedCornerShape(32.dp),
        leadingIcon = {
            when (searchBarState) {
                FlightSearchViewModel.SearchBarUiState.Idle -> SearchIcon(IconType.Search)
                FlightSearchViewModel.SearchBarUiState.EmptyActive,
                FlightSearchViewModel.SearchBarUiState.WithInputActive -> SearchIcon(
                    IconType.Chevron,
                    onChevronClicked
                )
            }
        },
        trailingIcon = {
            if (searchBarState is FlightSearchViewModel.SearchBarUiState.WithInputActive) {
                SearchIcon(IconType.Clear, onClearInputClicked)
            }
        },
        placeholder = {
            Text(
                text = "What are you searching for?",
            )
        },
        interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
            LaunchedEffect(key1 = interactionSource) {
                interactionSource.interactions.collect { interaction ->
                    if (interaction is PressInteraction.Release) {
                        onClicked.invoke()
                    }
                }
            }
        },
    )
}
@Composable
fun SearchIcon(iconType: IconType, onClick: (() -> Unit)? = null) {
    val imageVector = when (iconType) {
        IconType.Search -> Icons.Filled.Search
        IconType.Clear -> Icons.Filled.Close
        IconType.Chevron -> Icons.Filled.KeyboardArrowRight
    }
    val contentDescription = when (iconType) {
        IconType.Search -> stringResource(id = R.string.search)
        IconType.Clear -> stringResource(id = R.string.close)
        IconType.Chevron -> stringResource(id = R.string.chevron)
    }

    IconButton(onClick = onClick ?: {}) { // Use empty lambda if onClick is null
        Icon(imageVector = imageVector, contentDescription = contentDescription)
    }
}



