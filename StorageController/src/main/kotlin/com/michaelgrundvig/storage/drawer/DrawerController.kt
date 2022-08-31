package com.michaelgrundvig.storage.drawer

import com.michaelgrundvig.storage.bin.BinService
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.util.FileUtil
import io.javalin.http.Context

class DrawerController(
    private val binService: BinService,
    private val drawerService:DrawerService) {

    fun routes() {
        return path("drawer") {
            path("{id}") {
                get { getDrawer(it) }
                post { createDrawer(it) }
                post("position/{row}/{column}/upload") { handleUpload(it) }
                get("bins") { findBinsInDrawer(it) }
            }
        }
    }

    private fun getDrawer(ctx: Context) {
        val drawer:Drawer? = drawerService.byId(ctx.pathParam("id").toInt())
        drawer?.let {
            ctx.status(200)
            ctx.json(DrawerDTO(drawer))
            return
        }
        ctx.status(404)
    }

    private fun createDrawer(ctx: Context) {

        // Ensure we don't already have a drawer
        val drawer:Drawer? = drawerService.byId(ctx.pathParam("id").toInt())
        if(drawer != null) {
            ctx.status(400)
            ctx.result("Drawer already exists")
            return
        }

        // Get the request and save it out
        val drawerDTO = ctx.bodyAsClass<DrawerDTO>()
        drawerService.create(drawerDTO.toDrawer())

        ctx.status(200)
    }

    private fun findBinsInDrawer(ctx: Context) {
        ctx.status(200)
        ctx.json(binService.findBinByDrawer(ctx.pathParam("id").toInt()))
    }

    private fun handleUpload(ctx: Context) {
        val id = ctx.pathParam("id")
        val row = ctx.pathParam("row")
        val column = ctx.pathParam("column")

        // Get the file and validate it
        val uploadedFile = ctx.uploadedFile("image")
        if(uploadedFile == null) {
            ctx.status(400)
            ctx.result("No 'image' parameter")
            return;
        }

        // Write the file to disk, we'll want to keep this for debugging even if it fails processing
        FileUtil.streamToFile(
            uploadedFile.content,
            "data/upload/drawer_${id}_row_${row}_column_${column}.jpg"
        )


    }

}