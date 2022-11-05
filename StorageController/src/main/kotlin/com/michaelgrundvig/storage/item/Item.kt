package com.michaelgrundvig.storage.item

import com.michaelgrundvig.storage.Storable
import io.jsondb.annotation.Document
import io.jsondb.annotation.Id

@Document(collection = "Items", schemaVersion = "1.0")
data class Item(
    @Id
    override var id: Int = 0,
    val name: String = "",
    val description: String = "",
    val bins: MutableList<Int> = mutableListOf(),
    val tags: MutableList<Int> = mutableListOf()
) : Storable

class ItemDTO(
    @JvmField var id: Int = -1,
    @JvmField val name: String = "",
    @JvmField val description: String = "",
    @JvmField val bins: MutableList<Int> = mutableListOf(),
    @JvmField val tags: MutableList<Int> = mutableListOf()
) {

    constructor(item: Item): this(
        id = item.id,
        name = item.name,
        description = item.description,
        bins = item.bins,
        tags = item.tags
    )

    fun toItem(): Item {
        return Item(
            id = this.id,
            name = this.name,
            description = this.description,
            bins = this.bins,
            tags = this.tags
        )
    }
}