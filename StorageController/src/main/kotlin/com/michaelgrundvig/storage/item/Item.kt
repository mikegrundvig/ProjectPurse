package com.michaelgrundvig.storage.item

import com.michaelgrundvig.storage.Storable
import io.jsondb.annotation.Document
import io.jsondb.annotation.Id

@Document(collection = "Items", schemaVersion = "1.0")
data class Item(
    @Id
    override var id: Int = 0,
    val name: String = "",
    val bins:List<Int> = mutableListOf(),
    val tags: List<Int> = mutableListOf()
) : Storable

data class ItemDTO(
    var id: Int = -1,
    val name: String = "",
    val bins:List<Int> = mutableListOf(),
    val tags: List<Int> = mutableListOf()
) {

    constructor(item: Item): this(
        id = item.id,
        name = item.name,
        bins = item.bins,
        tags = item.tags
    )

    fun toItem(): Item {
        return Item(
            id = this.id,
            name = this.name,
            bins = this.bins,
            tags = this.tags
        )
    }
}