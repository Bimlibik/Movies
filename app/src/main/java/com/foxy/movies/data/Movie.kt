package com.foxy.movies.data

import com.google.gson.annotations.SerializedName

data class Movie(val id: Int,

                 @SerializedName("localized_name")
                 val localizedName: String,

                 @SerializedName("name")
                 val name: String,

                 @SerializedName("year")
                 val year: String,

                 @SerializedName("rating")
                 val rating: String,

                 @SerializedName("image_url")
                 val imgUrl: String,

                 @SerializedName("description")
                 val description: String,

                 @SerializedName("genres")
                 val genres: List<String>
)