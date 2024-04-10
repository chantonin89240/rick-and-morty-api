package org.mathieu.cleanrmapi.data.local.objects

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mathieu.cleanrmapi.data.remote.responses.EpisodeResponse
import org.mathieu.cleanrmapi.domain.models.character.Episode

/**
 * Represents a character entity stored in the SQLite database. This object provides fields
 * necessary to represent all the attributes of a character from the data source.
 * The object is specifically tailored for SQLite storage using Realm.
 *
 * @property id Unique identifier of the character.
 * @property name Name of the character.
 * @property air_date Release date.
 * @property episode Episode identification code.
 * @property characters Character featured in the episode.
 * @property url Episode url.
 * @property created Episode creation date.
 */
internal class EpisodeObject: RealmObject {
    @PrimaryKey
    var id: Int = -1
    var name: String = ""
    var air_date: String = ""
    var episode: String = ""
    var characters: List<String> = listOf()
    var url: String = ""
    var created: String = ""
}


internal fun EpisodeResponse.toRealmObject() = EpisodeObject().also { obj ->
    obj.id = id
    obj.name = name
    obj.air_date = air_date
    obj.episode = episode
    obj.characters = characters
    obj.url = url
    obj.created = created
}

internal fun EpisodeObject.toModel() = Episode(
    id = id,
    name = name,
    air_date = air_date,
    episode = episode,
    characters = characters,
    url = url,
    created = created
)