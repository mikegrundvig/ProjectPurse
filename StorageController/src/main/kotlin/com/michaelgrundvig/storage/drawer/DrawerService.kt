package com.michaelgrundvig.storage.drawer

import com.michaelgrundvig.storage.JsonDbService
import io.jsondb.JsonDBTemplate

class DrawerService(private val template: JsonDBTemplate): JsonDbService(template) {

    init {
        createCollection(Drawer::class.java)
    }

    fun create(drawer: Drawer) {
        template.insert<Drawer>(drawer)
    }

    fun byId(id: Int): Drawer? {
        return template.findById(id, Drawer::class.java)
    }


}