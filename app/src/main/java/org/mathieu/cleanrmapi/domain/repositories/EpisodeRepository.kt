package org.mathieu.cleanrmapi.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.domain.models.character.Episode

interface EpisodeRepository {

    suspend fun getEpisodes(): Flow<List<Episode>>

    suspend fun getEpisode(id: Int): Episode
}