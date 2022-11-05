package com.michaelgrundvig.storage.item

import com.michaelgrundvig.storage.tag.TagDTO
import com.michaelgrundvig.storage.tag.TagService
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.Context
import io.javalin.http.bodyAsClass

class ItemController(
    private val itemService: ItemService,
    private val tagService: TagService) {

    fun routes() {
        return path("item") {
            post { createItem(it) }
            get { getItems(it) }
            path("{id}") {
                get { getItem(it) }
                put { updateItem(it) }
//                ApiBuilder.post("position") { updateBinPosition(it) }
            }
        }
    }

    fun pageRoutes() {
        return path("items") {
            get{ listPage(it) }
            post { handlePost(it) }
        }
    }

    private fun listPage(ctx: Context) {
        ctx.render("items.jte",
            mapOf(
                "pageTitle" to "Items",
                "items" to itemService.getAll().sortedBy { it.name }.map { ItemDTO(it) },
                "tags" to tagService.getAll().sortedBy { it.name }.map { TagDTO(it) },
                "tagService" to tagService
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
        createOrEdit(ctx, itemService.findHighestId(Item()))
    }

    private fun handleEdit(ctx: Context) {
        ctx.formParam("itemId")?.let { id ->
            createOrEdit(ctx, id.toInt())
        }
    }

    private fun createOrEdit(ctx: Context, id: Int) {
        itemService.save(
            Item(
                id,
                ctx.formParam("name")?: "ERROR NAME",
                ctx.formParam("description")?: "ERROR Description",
                ctx.formParams("selectedBins").map { it.toInt() }.toMutableList(),
                ctx.formParams("selectedTags").map { it.toInt() }.toMutableList()
            )
        )
    }

    private fun handleDelete(ctx: Context) {
        ctx.formParam("itemId")?.let { id ->

            // Make sure we have an item with this id
            itemService.byId(id.toInt())?.let { item ->
                itemService.delete(item)
            }
        }
    }

    private fun createItem(ctx: Context) {
        val itemDTO = ctx.bodyAsClass<ItemDTO>()

        if(itemDTO.id == -1) {

            itemDTO.id = itemService.findHighestId(Item())

        } else {

            // Ensure we don't already have an item with this id
            val existingItem: Item? = itemService.byId(itemDTO.id)
            if (existingItem != null) {
                ctx.status(400)
                ctx.result("Item already exists")
                return
            }
        }

        // Save it out
        val item = itemDTO.toItem()
        itemService.save(item)
        ctx.status(200).json(item)
    }

    private fun updateItem(ctx: Context) {
        val itemDTO = ctx.bodyAsClass<ItemDTO>()

        // Save it out
        val item = itemDTO.toItem()
        item.id = ctx.pathParam("id").toInt()
        itemService.save(item)
        ctx.status(200).json(item)
    }

    private fun getItems(ctx: Context) {
        ctx.status(200)
        ctx.json(itemService.getAll())
    }

    private fun getItem(ctx: Context) {
        val item: Item? = itemService.byId(ctx.pathParam("id").toInt())
        item?.let {
            ctx.status(200)
            ctx.json(ItemDTO(item))
            return
        }
        ctx.status(404)
    }

}