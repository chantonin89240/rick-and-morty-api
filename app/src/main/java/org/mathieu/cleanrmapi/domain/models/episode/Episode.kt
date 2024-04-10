package org.mathieu.cleanrmapi.domain.models.character

data class Episode(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,  // URLs to character resources
    val url: String,
    val created: String
)

