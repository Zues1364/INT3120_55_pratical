package com.example.bookshelf.data

import Book
import com.example.bookshelf.network.FakeBookApiService

class BookRepositoryImpl(private val booksApiService:  FakeBookApiService) : BookRepository {
    override suspend fun getBooks(query: String): List<Book> {
        val response = booksApiService.searchBooks(query)
        return response.books // Trả về danh sách sách từ phản hồi
    }
}
