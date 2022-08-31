package com.michaelgrundvig.storage.tag

import com.michaelgrundvig.storage.JsonDbService
import io.jsondb.JsonDBTemplate

class TagService(private val template: JsonDBTemplate): JsonDbService(template) {

    init {
        createCollection(Tag::class.java)
    }

    fun create(tag: Tag) {
        template.insert<Tag>(tag)
    }

    fun byId(id: Int): Tag? {
        return template.findById(id, Tag::class.java)
    }

    fun getAll():List<Tag> {
        return template.findAll("Tags")
    }
}