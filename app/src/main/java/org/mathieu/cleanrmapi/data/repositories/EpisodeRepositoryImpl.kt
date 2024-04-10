package org.mathieu.cleanrmapi.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.mathieu.cleanrmapi.data.local.EpisodeLocal
import org.mathieu.cleanrmapi.data.local.objects.EpisodeObject
import org.mathieu.cleanrmapi.data.local.objects.toModel
import org.mathieu.cleanrmapi.data.local.objects.toRealmObject
import org.mathieu.cleanrmapi.data.remote.EpisodeApi
import org.mathieu.cleanrmapi.data.remote.responses.EpisodeResponse
import org.mathieu.cleanrmapi.domain.models.character.Character
import org.mathieu.cleanrmapi.domain.models.character.Episode
import org.mathieu.cleanrmapi.domain.repositories.EpisodeRepository

private const val EPISODE_PREFS = "episode_repository_preferences"
private val nextPage = intPreferencesKey("next_episodes_page_to_load")

private val Context.dataStore by preferencesDataStore(
    name = EPISODE_PREFS
)

internal class EpisodeRepositoryImpl(
    private val context: Context,
    private val episodeApi: EpisodeApi,
    private val episodeLocal: EpisodeLocal
) : EpisodeRepository {

    override suspend fun getEpisodes(): Flow<List<Episode>> =
        episodeLocal
            .getEpisodes()
            .mapElement(transform = EpisodeObject::toModel)
            .also { if (it.first().isEmpty()) fetchNext() }


    private suspend fun fetchNext() {
    // Récupère le numéro de la prochaine page à charger depuis le DataStore
        val page = context.dataStore.data.map { prefs -> prefs[nextPage] }.first()

        // Vérifie que le numéro de la page n'est pas égal à -1
        if (page != -1) {
            // Appelle l'API pour récupérer les épisodes de la page actuelle
            val response = episodeApi.getEpisodes(page)

            // Extrait le numéro de la prochaine page à charger depuis la réponse de l'API et met à jour le DataStore
            val nextPageToLoad = response.info.next?.split("?page=")?.last()?.toInt() ?: -1
            context.dataStore.edit { prefs -> prefs[nextPage] = nextPageToLoad }

            // Convertit les objets de réponse en objets EpisodeObject
            val objects = response.results.map(transform = EpisodeResponse::toRealmObject)

            // Enregistre les objets dans la base de données locale
            episodeLocal.saveEpisodes(objects)
        }

    }

    /**
     * Retrieves the character with the specified ID.
     *
     * The function follows these steps:
     * 1. Tries to fetch the character from the local storage.
     * 2. If not found locally, it fetches the character from the API.
     * 3. Upon successful API retrieval, it saves the character to local storage.
     * 4. If the character is still not found, it throws an exception.
     *
     * @param id The unique identifier of the character to retrieve.
     * @return The [Episode] object representing the episode details.
     * @throws Exception If the episode cannot be found both locally and via the API.
     */
    override suspend fun getEpisode(id: Int): Episode =
        episodeLocal.getEpisode(id)?.toModel()
            ?: episodeApi.getEpisode(id = id)?.let { response ->
                val obj = response.toRealmObject()
                episodeLocal.insert(obj)
                obj.toModel()
            }
            ?: throw Exception("Character not found.")


}

