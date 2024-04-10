package org.mathieu.cleanrmapi.data.remote.responses

/**
 * Represents detailed information about a character, typically received from an API response.
 *
 * @property id Unique identifier of the character.
 * @property name Name of the character.
 * @property air_date Release date.
 * @property episode Episode identification code.
 * @property characters Character featured in the episode.
 * @property url Episode url.
 * @property created Episode creation date.
 */
data class EpisodeResponse(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)