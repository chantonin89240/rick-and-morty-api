package org.mathieu.cleanrmapi.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import org.mathieu.cleanrmapi.data.remote.responses.EpisodeResponse
import org.mathieu.cleanrmapi.data.remote.responses.PaginatedResponse

internal class EpisodeApi(private val client: HttpClient) {

    // Récupère une liste d'épisodes depuis l'API en fonction du numéro de page spécifié
    suspend fun getEpisodes(page: Int?): PaginatedResponse<EpisodeResponse> = client
        .get("episode/") { // Effectue une requête GET sur l'endpoint "episode/"
            if (page != null)
                url { // Si un numéro de page est spécifié, ajoute un paramètre d'URL pour la pagination
                    parameter("page", page)
                }
        }
        .accept(HttpStatusCode.OK) // Accepte seulement les réponses avec le code de statut HTTP 200 (OK)
        .body() // Extrait le corps de la réponse et le convertit en objet PaginatedResponse<EpisodeResponse>

    // Récupère un épisode spécifique depuis l'API en fonction de son ID
    suspend fun getEpisode(id: Int): EpisodeResponse? = client
        .get("episode/$id") { // Effectue une requête GET sur l'endpoint "episode/{id}"
        }
        .accept(HttpStatusCode.OK) // Accepte seulement les réponses avec le code de statut HTTP 200 (OK)
        .body() // Extrait le corps de la réponse et le convertit en objet EpisodeResponse (ou null si la réponse est vide)
}