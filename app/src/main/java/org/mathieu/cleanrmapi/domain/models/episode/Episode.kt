package org.mathieu.cleanrmapi.domain.models.character

/**
 * Represents a detailed characterization, typically derived from a data source or API.
 *
 * @property id Unique identifier of the character.
 * @property name Name of the character.
 * @property air_date Release date.
 * @property episode Episode identification code.
 * @property characters Character featured in the episode.
 * @property url Episode url.
 * @property created Episode creation date.
 */
data class Episode(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,  // URLs to character resources
    val url: String,
    val created: String
)

