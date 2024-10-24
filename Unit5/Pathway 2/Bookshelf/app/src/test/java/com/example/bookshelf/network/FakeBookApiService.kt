package com.example.bookshelf.network
import AccessInfo
import Book
import BookResponse
import Epub
import Pdf
import ReadingModes
import SaleInfo
import VolumeInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface BookshelfApiService {
    @GET("volumes")
    suspend fun searchBooks(@Query("q") query: String): BookResponse
}

class FakeBookApiService : BookshelfApiService {
    override suspend fun searchBooks(query: String): BookResponse {
        return BookResponse(
            kind = "books#volumes", // Cung cấp giá trị cho kind
            totalItems = 2, // Cung cấp giá trị cho totalItems
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
    }
}


