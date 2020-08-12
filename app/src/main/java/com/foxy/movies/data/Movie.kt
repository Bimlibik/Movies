package com.foxy.movies.data

import com.google.gson.annotations.SerializedName

data class Movie(
    var id: Int = 0,

    @SerializedName("localized_name")
    var localizedName: String = "Movie name not found",

    @SerializedName("name")
    var name: String = "Movie name not found",

    @SerializedName("year")
    var year: String = "-",

    @SerializedName("rating")
    var rating: String = "-",

    @SerializedName("image_url")
    var imgUrl: String = "Url not found",

    @SerializedName("description")
    var description: String = "Movie description not found",

    @SerializedName("genres")
    var genres: List<String> = emptyList()
) {
    val isEmpty
        get() =
            (localizedName.isEmpty() || localizedName == "Movie name not found") &&
                    (name.isEmpty() || name == "Movie name not found") &&
                    (year.isEmpty() || name == "-") &&
                    (rating.isEmpty() || rating == "-") &&
                    (imgUrl.isEmpty() || imgUrl == "Url not found") &&
                    (description.isEmpty() || description == "Movie description not found") &&
                    (genres.isEmpty())

}