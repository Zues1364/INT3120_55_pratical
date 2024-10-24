package com.example.bookshelf

import AccessInfo
import Book
import BookResponse
import Epub
import Pdf
import ReadingModes
import SaleInfo
import VolumeInfo
import com.example.bookshelf.data.BookRepository
import com.example.bookshelf.data.BookRepositoryImpl
import com.example.bookshelf.network.FakeBookApiService

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class BookRepositoryTest {
    private lateinit var booksApiService: FakeBookApiService
    private lateinit var bookRepository: BookRepository

    @Before
    fun setup() {
        booksApiService = mock() // Tạo một mock cho BookshelfApiService
        bookRepository = BookRepositoryImpl(booksApiService) // Khởi tạo BookRepositoryImpl với mock
    }

    @Test
    fun `getBooks returns correct data from API`() = runBlocking {
        // Dữ liệu giả lập để trả về
        val fakeResponse = BookResponse(
            kind = "books#volumes",
            totalItems = 2,
            books = listOf(
                Book(
                    id = "1",
                    etag = "etag1",
                    selfLink = "http://example.com/book1",
                    volumeInfo = VolumeInfo(
                        title = "Fake Book 1",
                        authors = listOf("Fake Author 1"),
                        readingModes = ReadingModes(text = true, image = false),
                        language = "en",
                        previewLink = "http://example.com/preview1",
                        infoLink = "http://example.com/info1",
                        canonicalVolumeLink = "http://example.com/canonical1"
                    ),
                    saleInfo = SaleInfo(
                        country = "US",
                        saleability = "FOR_SALE",
                        isEbook = true
                    ),
                    accessInfo = AccessInfo(
                        country = "US",
                        viewability = "ANYONE",
                        embeddable = true,
                        publicDomain = false,
                        textToSpeechPermission = "ALLOWED",
                        epub = Epub(isAvailable = true),
                        pdf = Pdf(isAvailable = true),
                        webReaderLink = "http://example.com/webReader1",
                        accessViewStatus = "ALLOWED",
                        quoteSharingAllowed = true
                    )
                ),
                Book(
                    id = "2",
                    etag = "etag2",
                    selfLink = "http://example.com/book2",
                    volumeInfo = VolumeInfo(
                        title = "Fake Book 2",
                        authors = listOf("Fake Author 2"),
                        readingModes = ReadingModes(text = true, image = false),
                        language = "en",
                        previewLink = "http://example.com/preview2",
                        infoLink = "http://example.com/info2",
                        canonicalVolumeLink = "http://example.com/canonical2"
                    ),
                    saleInfo = SaleInfo(
                        country = "US",
                        saleability = "FOR_SALE",
                        isEbook = true
                    ),
                    accessInfo = AccessInfo(
                        country = "US",
                        viewability = "ANYONE",
                        embeddable = true,
                        publicDomain = false,
                        textToSpeechPermission = "ALLOWED",
                        epub = Epub(isAvailable = true),
                        pdf = Pdf(isAvailable = true),
                        webReaderLink = "http://example.com/webReader2",
                        accessViewStatus = "ALLOWED",
                        quoteSharingAllowed = true
                    )
                )
            )
        )

        // Đặt hành vi cho mock
        whenever(booksApiService.searchBooks("query")).thenReturn(fakeResponse)

        // Gọi hàm để lấy sách
        val books = bookRepository.getBooks("query")

        // Kiểm tra xem dữ liệu trả về có đúng không
        assertEquals(2, books.size)
        assertEquals("Fake Book 1", books[0].volumeInfo.title)
        assertEquals("Fake Book 2", books[1].volumeInfo.title)
    }
}
