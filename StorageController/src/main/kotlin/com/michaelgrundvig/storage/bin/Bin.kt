package com.michaelgrundvig.storage.bin

import com.michaelgrundvig.storage.Storable
import io.jsondb.annotation.Document
import io.jsondb.annotation.Id

@Document(collection = "Bins", schemaVersion = "1.0")
data class Bin(
    @Id
    override var id: Int = 0,
    val type: Type = Type.OneByOne,
    val position: Position = Position(-1, -1, -1)
): Storable

data class BinDTO(
    val id: Int,
    val type: Type,
    val position: Position = Position(-1, -1, -1)
) {
    constructor(bin:Bin): this(
        id = bin.id,
        type = bin.type,
        position = bin.position
    )

    fun toBin(): Bin {
        return Bin(
            id = this.id,
            type = this.type,
            position = this.position
        )
    }
}


data class Position(
    var drawer:Int = -1,
    var row:Int = -1,
    var column:Int = 1
)

enum class Type {
    OneByOne, OneByTwo, OneByThree, OneByFour, TwoByTwo, TwoByFour
}