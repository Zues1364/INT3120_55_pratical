package com.example.bookshelf

import com.example.bookshelf.data.BookRepositoryImpl
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.VolumeInfo
import com.example.bookshelf.data.FakeBookRepository
import com.example.bookshelf.network.FakeBookApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FakeBookRepositoryTest {
    private lateinit var booksApiService: FakeBookApiService
    private lateinit var bookRepository: FakeBookRepository

    @Before
    fun setup() {
        booksApiService = mock()
        bookRepository = BookRepositoryImpl(booksApiService)
    }

}
