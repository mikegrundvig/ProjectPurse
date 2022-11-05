package com.michaelgrundvig.storage.tag

import com.michaelgrundvig.storage.JsonDbService
import io.jsondb.JsonDBTemplate

class TagService(private val template: JsonDBTemplate): JsonDbService(template) {

    init {
        createCollection(Tag::class.java)
    }

    fun byId(id: Int): Tag? {
        return template.findById(id, Tag::class.java)
    }

    fun getAll():List<Tag> {
        return template.findAll("Tags")
    }

    fun save(tag: Tag) {
        template.upsert<Tag>(tag)
    }

    fun delete(tag: Tag) {
        template.remove(tag, Tag::class.java)
    }
}