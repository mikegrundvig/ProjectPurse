package com.michaelgrundvig.storage.bin

import com.michaelgrundvig.storage.drawer.DrawerService
import com.michaelgrundvig.storage.item.ItemService
import com.michaelgrundvig.storage.label.LabelMaker
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.Context
import io.javalin.http.bodyAsClass

class BinController(
    private val binService: BinService,
    private val itemService: ItemService,
    private val drawerService: DrawerService) {

    private val labels = LabelMaker()

    fun routes() {
        return path("bin") {
            get("label") { generateLabels(it) }
            post { createBin(it) }
            path("{id}") {
                get { getBin(it) }
                post("position") { updateBinPosition(it) }
            }
        }
    }

    fun pageRoutes() {
        return path("bins") {
            get{ listPage(it) }
            post { handlePost(it) }
        }
    }

    private fun listPage(ctx: Context) {
        ctx.render("bins.jte",
            mapOf(
                "pageTitle" to "Bins",
                "bins" to binService.getAll().sortedBy { it.id }.map { BinDTO(it) }
            )
        )
    }

    private fun handlePost(ctx: Context) {

        when(ctx.formParam("action")) {
            "c" -> handleCreate(ctx)
            "e" -> handleEdit(ctx)
            "d" -> handleDelete(ctx)
        }

        listPage(ctx)
    }

    private fun handleCreate(ctx: Context) {
        binService.save(
            Bin(
                (ctx.formParam("binId")?:"-1").toInt(),
                Type.valueOf(ctx.formParam("type") ?: "OneByOne")
            )
        )
    }
    private fun handleEdit(ctx: Context) {
        ctx.formParam("binId")?.let { id ->
            binService.byId(id.toInt())?.let { bin ->
                bin.type = Type.valueOf(ctx.formParam("type") ?: "OneByOne")
                binService.save(bin)
            }
        }
    }
    private fun handleDelete(ctx: Context) {
        ctx.formParam("binId")?.let { id ->

            // Make sure we have a tag with this id
            binService.byId(id.toInt())?.let { bin ->

                // TODO: figure out how to search properly for this instead of brute-forcing it
                itemService.getAll().forEach { item ->
                    item.bins.remove(bin.id)
                    itemService.save(item)
                }

                // Delete the bin itself
                binService.delete(bin)
            }
        }
    }

    private fun getBin(ctx: Context) {
        val bin: Bin? = binService.byId(ctx.pathParam("id").toInt())
        bin?.let {
            ctx.status(200)
            ctx.json(BinDTO(bin))
            return
        }
        ctx.status(404)
    }

    private fun updateBinPosition(ctx: Context) {
        val bin: Bin? = binService.byId(ctx.pathParam("id").toInt())
        bin?.let {
            val newPosition = ctx.bodyAsClass<Position>()

            if(!isPositionValid(newPosition)) {
                ctx.status(400)
                ctx.result("Position is invalid")
                return
            }

            binService.updateBinPosition(bin.id, newPosition)
            ctx.status(204)
            return
        }
        ctx.status(404)
    }

    private fun createBin(ctx: Context) {

        val binDTO = ctx.bodyAsClass<BinDTO>()

        // Ensure we don't already have a bin with this id
        val existingBin: Bin? = binService.byId(binDTO.id)
        if(existingBin != null) {
            ctx.status(400)
            ctx.result("Bin already exists")
            return
        }

        val bin = binDTO.toBin()

        if(!isPositionValid(bin.position)) {
            ctx.status(400)
            ctx.result("Position is invalid")
            return
        }

        // Save it out
        binService.save(bin)
        ctx.status(204)
    }

    private fun generateLabels(ctx: Context) {
        ctx.contentType("image/jpeg")
        ctx.result(labels.generateLabelSheet(binService.findHighestId(Bin())))
    }

    private fun isPositionValid(position: Position): Boolean {
        drawerService.byId(position.drawer)?.let {

            // If the coordinates are outside the drawer dimensions, fail
            if(position.column > it.columns ||
                        position.row > it.rows) {
                return false
            }

            return true
        }
        return false
    }
}