package com.michaelgrundvig.storage

import com.michaelgrundvig.storage.bin.BinController
import com.michaelgrundvig.storage.bin.BinService
import com.michaelgrundvig.storage.drawer.DrawerController
import com.michaelgrundvig.storage.drawer.DrawerService
import com.michaelgrundvig.storage.item.ItemController
import com.michaelgrundvig.storage.item.ItemService
import com.michaelgrundvig.storage.tag.TagController
import com.michaelgrundvig.storage.tag.TagService
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.staticfiles.Location
import io.jsondb.JsonDBTemplate

fun main() {

    val app = Javalin.create { config ->
        config.addStaticFiles { staticFiles ->
            staticFiles.directory = "C:/Users/Michael/IdeaProjects/StorageController/data/web"
            staticFiles.location = Location.EXTERNAL
        }
        config.addSinglePageRoot("", "C:/Users/Michael/IdeaProjects/StorageController/data/web/index.html", Location.EXTERNAL)
    }

    // Set up the DB
    val dbFilesLocation = "data/db"
    val baseScanPackage = "com.michaelgrundvig.storage"
    val jsonDBTemplate = JsonDBTemplate(dbFilesLocation, baseScanPackage)

    val drawerService = DrawerService(jsonDBTemplate)
    val binService = BinService(jsonDBTemplate)
    val itemService = ItemService(jsonDBTemplate)
    val tagService = TagService(jsonDBTemplate)

    val drawerController = DrawerController(binService, drawerService)
    val binsController = BinController(binService, drawerService)
    val itemController = ItemController(itemService)
    val tagController = TagController(tagService)

    // Stand up Javalin and the routes
    app.routes {
        path("api") {
            binsController.routes()
            drawerController.routes()
            itemController.routes()
            tagController.routes()
        }
    }

    // Start the server
    app.start(8080)
}