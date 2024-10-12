package com.example.bookshelf.data

import Book
import com.example.bookshelf.network.FakeBookApiService

class BookRepositoryImpl(private val booksApiService:  FakeBookApiService) : FakeBookRepository {
    override suspend fun getBooks(query: String): List<Book> {
        return booksApiService.getBooks(query)
    }
}
