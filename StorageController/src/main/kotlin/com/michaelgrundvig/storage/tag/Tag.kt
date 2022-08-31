package com.michaelgrundvig.storage.tag

import com.michaelgrundvig.storage.Storable
import io.jsondb.annotation.Document
import io.jsondb.annotation.Id

@Document(collection = "Tags", schemaVersion = "1.0")
data class Tag(
    @Id
    override var id: Int = 0,
    val name: String = ""
) : Storable

data class TagDTO(
    val id: Int = 0,
    val name: String = ""
) {
    constructor(tag:Tag): this(
        id = tag.id,
        name = tag.name
    )

    fun toTag(newId: Int = this.id): Tag {
        return Tag(newId, name)
    }
}