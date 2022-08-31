package com.michaelgrundvig.storage.item

import com.michaelgrundvig.storage.JsonDbService
import io.jsondb.JsonDBTemplate

class ItemService(private val template: JsonDBTemplate): JsonDbService(template) {

    init {
        createCollection(Item::class.java)
    }

    fun save(item: Item) {
        template.upsert<Item>(item)
    }

    fun byId(id: Int): Item? {
        return template.findById(id, Item::class.java)
    }

    fun getAll():List<Item> {
        return template.findAll("Items")
    }

    fun findItemsByTags(vararg tags:Int): List<Item> {
        val clause = tags.joinToString(" | ") { String.format("//tags[%s]/..", it) }
        println(clause)
        return template.find(
            clause,
            Item::class.java)
    }
}