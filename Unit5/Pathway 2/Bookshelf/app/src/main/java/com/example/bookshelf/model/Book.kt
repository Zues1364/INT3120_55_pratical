import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookResponse(
    @SerialName("kind") val kind: String,
    @SerialName("totalItems") val totalItems: Int,
    @SerialName("items") val books: List<Book>
)

@Serializable
data class Book(
    @SerialName("id") val id: String,
    @SerialName("etag") val etag: String,
    @SerialName("selfLink") val selfLink: String,
    @SerialName("volumeInfo") val volumeInfo: VolumeInfo,
    @SerialName("saleInfo") val saleInfo: SaleInfo,
    @SerialName("accessInfo") val accessInfo: AccessInfo,
    @SerialName("searchInfo") val searchInfo: SearchInfo? = null
)

@Serializable
data class VolumeInfo(
    @SerialName("title") val title: String,
    @SerialName("authors") val authors: List<String>? = null,
    @SerialName("publisher") val publisher: String? = null,
    @SerialName("publishedDate") val publishedDate: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("industryIdentifiers") val industryIdentifiers: List<IndustryIdentifier>? = null,
    @SerialName("readingModes") val readingModes: ReadingModes,
    @SerialName("pageCount") val pageCount: Int? = null,
    @SerialName("printType") val printType: String? = null,
    @SerialName("categories") val categories: List<String>? = null,
    @SerialName("imageLinks") val imageLinks: ImageLink? = null,
    @SerialName("language") val language: String,
    @SerialName("previewLink") val previewLink: String,
    @SerialName("infoLink") val infoLink: String,
    @SerialName("canonicalVolumeLink") val canonicalVolumeLink: String
)

@Serializable
data class IndustryIdentifier(
    @SerialName("type") val type: String,
    @SerialName("identifier") val identifier: String
)

@Serializable
data class ReadingModes(
    @SerialName("text") val text: Boolean,
    @SerialName("image") val image: Boolean
)

@Serializable
data class ImageLink(
    @SerialName("smallThumbnail") val smallThumbnailUrl: String? = null,
    @SerialName(value = "thumbnail") val thumbnailUrl: String
)

@Serializable
data class SaleInfo(
    @SerialName("country") val country: String,
    @SerialName("saleability") val saleability: String,
    @SerialName("isEbook") val isEbook: Boolean
)

@Serializable
data class AccessInfo(
    @SerialName("country") val country: String,
    @SerialName("viewability") val viewability: String,
    @SerialName("embeddable") val embeddable: Boolean,
    @SerialName("publicDomain") val publicDomain: Boolean,
    @SerialName("textToSpeechPermission") val textToSpeechPermission: String,
    @SerialName("epub") val epub: Epub,
    @SerialName("pdf") val pdf: Pdf,
    @SerialName("webReaderLink") val webReaderLink: String,
    @SerialName("accessViewStatus") val accessViewStatus: String,
    @SerialName("quoteSharingAllowed") val quoteSharingAllowed: Boolean
)

@Serializable
data class Epub(
    @SerialName("isAvailable") val isAvailable: Boolean
)

@Serializable
data class Pdf(
    @SerialName("isAvailable") val isAvailable: Boolean,
    @SerialName("acsTokenLink") val acsTokenLink: String? = null
)

@Serializable
data class SearchInfo(
    @SerialName("textSnippet") val textSnippet: String
)
