package com.example.bookshelf.data

import Book

import com.example.bookshelf.networks.BookshelfApiService

interface BookshelfRepository {
    suspend fun searchBook(query: String): List<Book>
}

class NetworkBookshelfRepository(private val bookshelfApiService: BookshelfApiService) :
    BookshelfRepository {
    override suspend fun searchBook(query: String): List<Book> {
        return bookshelfApiService.searchBooks(query).books
    }
}