package com.michaelgrundvig.storage

import io.jsondb.JsonDBTemplate

open class JsonDbService(private val template: JsonDBTemplate) {

    fun createCollection(clazz: Class<*>) {
        if (!template.collectionExists(clazz)) {
            template.createCollection(clazz)
        }
    }

    fun findHighestId(storable: Storable) : Int {
        val results: List<Storable> = template.findAll(storable::class.java,  { a, b ->
            if (a.id == b.id) {
                0
            } else if (a.id > b.id) {
                -1
            } else {
                1
            }
        }, "0:1:1")

        return if(results.isEmpty()) {
            1
        } else {
            results[0].id + 1
        }
    }

}

interface Storable {
   var id: Int
}