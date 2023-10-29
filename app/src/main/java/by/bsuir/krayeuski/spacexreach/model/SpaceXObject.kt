package by.bsuir.krayeuski.spacexreach.model

import com.google.gson.Gson

data class SpaceXObject(
    val id: String,
    val name: String,
    val date_utc: String,
    val date_local: String,
    val rocket: String,
    val success: Boolean,
    val details: String,
    val links: SpaceObjectLinks,
    var isFavorite: Boolean = false
)

// Сериализация объекта в JSON
fun SpaceXObject.toJson(): String {
    return Gson().toJson(this)
}

// Десериализация JSON в объект
fun String.toSpaceXObject(): SpaceXObject {
    return Gson().fromJson(this, SpaceXObject::class.java)
}


data class SpaceObjectLinks(
    val patch: SpaceObjectPatch?,
    val reddit: SpaceObjectReddit?,
    val flickr: SpaceObjectFlickr?,
    val presskit: String?,
    val webcast: String?,
    val youtubeId: String?,
    val article: String?,
    val wikipedia: String?
)

data class SpaceObjectPatch(
    val small: String?,
    val large: String?
)

data class SpaceObjectReddit(
    val campaign: String?,
    val launch: String?,
    val media: String?,
    val recovery: String?
)

data class SpaceObjectFlickr(
    val small: List<String>?,
    val original: List<String>?
)
