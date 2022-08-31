package com.michaelgrundvig.storage.drawer

import io.jsondb.annotation.Document
import io.jsondb.annotation.Id

data class DrawerDTO(
    val id: Int = 0,
    val rows: Int = 0,
    val columns: Int = 0
) {

    constructor(drawer: Drawer) : this(
        id = drawer.id,
        rows = drawer.rows,
        columns = drawer.columns
    )

    fun toDrawer():Drawer {
        return Drawer(id, rows, columns)
    }

}

@Document(collection = "Drawers", schemaVersion = "1.0")
data class Drawer(
    @Id
    val id: Int = 0,
    val rows: Int = 0,
    val columns: Int = 0,

    // what column is the camera over currently
    var cameraPosition: Int = 0,

    // what row is the drawer at currently
    var slidePosition: Int = 0
)