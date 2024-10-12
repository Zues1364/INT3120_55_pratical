package com.example.bookshelf.data
import Book

interface FakeBookRepository {
    suspend fun getBooks(query: String): List<Book>
}
