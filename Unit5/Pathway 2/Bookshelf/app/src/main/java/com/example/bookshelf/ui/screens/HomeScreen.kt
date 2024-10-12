package com.example.bookshelf.ui.screens

import Book

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R


@Composable
fun HomeScreen(
    bookshelfUiState: BookshelfUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    retryAction: () -> Unit,
    bookshelfViewModel: BookshelfViewModel
) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(contentPadding)) {
        SearchBar(bookshelfViewModel)
        when (bookshelfUiState) {
            is BookshelfUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
            is BookshelfUiState.Success -> CardGridScreen(bookshelfUiState.books, modifier)
            is BookshelfUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        }
    }
}

@Composable
fun SearchBar(viewModel: BookshelfViewModel) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text("Search books") },
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = { viewModel.getBooks(textState.text) },
            modifier = Modifier.padding(start = 8.dp),

            ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon"
            )
            Text("Search", modifier = Modifier.padding(start = 4.dp))
        }
    }
}

@Composable
fun CardGridScreen(
    books: List<Book>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 8.dp),
        contentPadding = contentPadding,
        columns = GridCells.Fixed(2)
    ) {
        items(items = books, key = { book -> book.id }) { book ->
            BookCover(
                book,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun BookCover(book: Book, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(book.volumeInfo.imageLinks?.thumbnailUrl?.trim()?.replace("http:", "https:"))
            .crossfade(true)
            .build(),
        contentDescription = null,
        error = painterResource(R.drawable.ic_broken_image),
        placeholder = painterResource(R.drawable.loading_img),
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}