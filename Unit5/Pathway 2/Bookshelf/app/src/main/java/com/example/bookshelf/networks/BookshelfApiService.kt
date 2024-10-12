package com.example.bookshelf.networks

import BookResponse

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private val json = Json {
    ignoreUnknownKeys = true // Ignore unknown JSON keys
}
private const val BASE_URL = "https://www.googleapis.com/books/v1/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface BookshelfApiService {
    @GET("volumes")
    suspend fun searchBooks(@Query("q") query: String): BookResponse
}