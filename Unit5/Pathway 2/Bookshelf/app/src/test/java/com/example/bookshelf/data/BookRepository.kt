package com.example.bookshelf.data
import Book

interface BookRepository {
    suspend fun getBooks(query: String): List<Book>
}
