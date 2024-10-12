package com.example.bookshelf.network
import Book
import retrofit2.http.GET
import retrofit2.http.Query

interface FakeBookApiService {
    @GET("volumes")
    suspend fun getBooks(@Query("q") query: String): List<Book>
}
