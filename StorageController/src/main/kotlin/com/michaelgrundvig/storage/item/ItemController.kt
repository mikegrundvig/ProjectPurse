package com.michaelgrundvig.storage.item

import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.Context

class ItemController(private val itemService: ItemService) {

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