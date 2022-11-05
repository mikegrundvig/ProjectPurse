package com.michaelgrundvig.storage.bin

import com.michaelgrundvig.storage.Storable
import io.jsondb.annotation.Document
import io.jsondb.annotation.Id

@Document(collection = "Bins", schemaVersion = "1.0")
data class Bin(
    @Id
    override var id: Int = 0,
    var type: Type = Type.OneByOne,
    val position: Position = Position(-1, -1, -1)
): Storable

class BinDTO(
    @JvmField val id: Int,
    @JvmField val type: Type,
    @JvmField val position: Position = Position(-1, -1, -1)
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


class Position(
    @JvmField var drawer:Int = -1,
    @JvmField var row:Int = -1,
    @JvmField var column:Int = 1
)

enum class Type {
    OneByOne, OneByTwo, OneByThree, OneByFour, TwoByTwo, TwoByFour
}