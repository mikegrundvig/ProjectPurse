package com.michaelgrundvig.storage.drawer

import com.michaelgrundvig.storage.JsonDbService
import io.jsondb.JsonDBTemplate

class DrawerService(private val template: JsonDBTemplate): JsonDbService(template) {

    init {
        createCollection(DrawerDTO::class.java)

        template.findAll<DrawerDTO>("Drawers").forEach { println(it.toDrawer()) }
    }

    fun create(drawer: DrawerDTO) {
        template.insert<DrawerDTO>(drawer)
    }

    fun byId(id: Int): DrawerDTO? {
        return template.findById(id, DrawerDTO::class.java)
    }


}