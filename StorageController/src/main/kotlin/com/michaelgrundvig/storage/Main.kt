package com.michaelgrundvig.storage

import com.michaelgrundvig.storage.bin.BinController
import com.michaelgrundvig.storage.bin.BinService
import com.michaelgrundvig.storage.drawer.DrawerController
import com.michaelgrundvig.storage.drawer.DrawerService
import com.michaelgrundvig.storage.item.ItemController
import com.michaelgrundvig.storage.item.ItemService
import com.michaelgrundvig.storage.tag.Tag
import com.michaelgrundvig.storage.tag.TagController
import com.michaelgrundvig.storage.tag.TagDTO
import com.michaelgrundvig.storage.tag.TagService
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.rendering.template.JavalinJte
import io.jsondb.JsonDBTemplate
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor


fun main() {

    val app = Javalin.create { config ->
        //config.staticFiles.add(directory = "../data/web", Location.EXTERNAL)
        //config.staticFiles.add(directory = "C:/Users/Michael/Documents/GitHub/ProjectPurse/StorageController/src/main/resources", Location.EXTERNAL)
        //config.spaRoot.addFile("", "../data/web/tags.jte", Location.EXTERNAL)
        config.staticFiles.add("/public")
    }
    JavalinJte.init()

    // Set up the DB
    val dbFilesLocation = "../data/db"
    val baseScanPackage = "com.michaelgrundvig.storage"
    val jsonDBTemplate = JsonDBTemplate(dbFilesLocation, baseScanPackage)

    val drawerService = DrawerService(jsonDBTemplate)
    val binService = BinService(jsonDBTemplate)
    val itemService = ItemService(jsonDBTemplate)
    val tagService = TagService(jsonDBTemplate)

    val drawerController = DrawerController(binService, drawerService)
    val binsController = BinController(binService, itemService, drawerService)
    val itemController = ItemController(itemService, tagService)
    val tagController = TagController(tagService, itemService)

    // Stand up Javalin and the routes
    app.routes {
        path("api") {
            binsController.routes()
            drawerController.routes()
            itemController.routes()
            tagController.apiRoutes()
        }
        tagController.pageRoutes()
        itemController.pageRoutes()
        binsController.pageRoutes()
    }


    // Start the server
    app.start(8080)

    //val arduino = ArduinoService("COM6")

    val executor = Executors.newFixedThreadPool(2) as ThreadPoolExecutor

//    println(arduino.sendCommand(MoveStepper(arduino.nextMessageNumber(), 1, 10000)))
//    println(arduino.sendCommand(HomeStepper(arduino.nextMessageNumber(),1)))

}