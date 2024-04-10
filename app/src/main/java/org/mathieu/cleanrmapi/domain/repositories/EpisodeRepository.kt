package org.mathieu.cleanrmapi.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.domain.models.character.Character
import org.mathieu.cleanrmapi.domain.models.character.Episode

interface EpisodeRepository {

    /**
     * Fetches a list of episodes from the data source. The function streams the results
     * as a [Flow] of [List] of [Episode] objects.
     *
     * @return A flow emitting a list of episodes.
     */
    suspend fun getEpisodes(): Flow<List<Episode>>

    /**
     * Fetches the details of a specific episode based on the provided ID.
     *
     * @param id The unique identifier of the episode to be fetched.
     * @return Details of the specified episode.
     */
    suspend fun getEpisode(id: Int): Episode
}