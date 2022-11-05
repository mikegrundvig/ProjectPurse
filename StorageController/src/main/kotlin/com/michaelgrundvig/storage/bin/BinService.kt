package com.michaelgrundvig.storage.bin

import com.michaelgrundvig.storage.JsonDbService
import io.jsondb.JsonDBTemplate

class BinService(private val template:JsonDBTemplate): JsonDbService(template) {

    init {
        createCollection(Bin::class.java)
    }

    fun byId(id: Int): Bin? {
        return template.findById(id, Bin::class.java)
    }

    fun getAll():List<Bin> {
        return template.findAll("Bins")
    }

    fun save(bin: Bin) {
        template.upsert<Bin>(bin)
    }

    fun delete(bin: Bin) {
        template.remove(bin, Bin::class.java)
    }

    fun updateBinPosition(id: Int, newPosition: Position) {
        byId(id)?.let {
            val bin = Bin(it.id, it.type, newPosition)
            template.upsert<Bin>(bin)
        }
    }

    fun findBinByDrawer(drawerId: Int): List<Bin> {
        val jxQuery = String.format("//position[drawer=%s]/..", drawerId)
        return template.find(jxQuery, Bin::class.java)
    }

}