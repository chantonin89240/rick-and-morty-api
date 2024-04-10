package org.mathieu.cleanrmapi.data.local

import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mathieu.cleanrmapi.data.local.objects.EpisodeObject

internal class EpisodeLocal(private val database: RealmDatabase) {

    // Récupère tous les épisodes de la base de données Realm sous forme de flux
    suspend fun getEpisodes(): Flow<List<EpisodeObject>> = database.use {
        // Effectue une requête pour récupérer tous les objets EpisodeObject de la base de données
        query<EpisodeObject>().find().asFlow().map { it.list }
    }

    // Récupère un épisode spécifique de la base de données Realm en fonction de son ID
    suspend fun getEpisode(id: Int): EpisodeObject? = database.use {
        // Effectue une requête pour récupérer l'objet EpisodeObject avec l'ID spécifié
        query<EpisodeObject>("id == $id").first().find()
    }

    // Enregistre une liste d'épisodes dans la base de données Realm
    suspend fun saveEpisodes(episodes: List<EpisodeObject>) = episodes.onEach {
        // Insère chaque épisode de la liste dans la base de données Realm
        insert(it)
    }

    // Insère un épisode dans la base de données Realm
    suspend fun insert(episode: EpisodeObject) {
        // Ouvre la base de données Realm en mode écriture
        database.write {
            // Copie l'objet EpisodeObject spécifié dans la base de données Realm
            // La stratégie UpdatePolicy.ALL indique que l'objet doit être mis à jour s'il existe déjà
            copyToRealm(episode, UpdatePolicy.ALL)
        }
    }
}