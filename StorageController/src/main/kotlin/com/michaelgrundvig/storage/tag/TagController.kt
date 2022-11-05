package com.michaelgrundvig.storage.tag

import com.michaelgrundvig.storage.item.ItemService
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.Context
import io.javalin.http.bodyAsClass
import java.util.HashMap

class TagController(
    private val tagService: TagService,
    private val itemService: ItemService) {

    fun apiRoutes() {
        return path("tag") {
            post { createTag(it) }
            get { getTags(it) }
            path("{id}") {
                get { getTag(it) }
            }
        }
    }

    fun pageRoutes() {
        return path("tags") {
            get{ listPage(it) }
            post { handlePost(it) }
        }
    }

    private fun listPage(ctx: Context) {
        ctx.render("tags.jte",
            mapOf(
                "pageTitle" to "Tags",
                "tags" to tagService.getAll().sortedBy { it.name }.map { TagDTO(it) }
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
        tagService.save(
            Tag(
                tagService.findHighestId(Tag()),
                ctx.formParam("tag") ?: "ERROR!!"
            )
        )
    }
    private fun handleEdit(ctx: Context) {
        ctx.formParam("id")?.let { id ->
            tagService.byId(id.toInt())?.let { tag ->
                tag.name = ctx.formParam("tagName") ?: "ERROR"
                tagService.save(tag)
            }
        }
    }
    private fun handleDelete(ctx: Context) {
        ctx.formParam("id")?.let { id ->

            // Make sure we have a tag with this id
            tagService.byId(id.toInt())?.let { tag ->

                // Remove this tag from all items using it

                // TODO: get this function working properly to remove the cludge
//                itemService.findItemsByTags(tag.id).forEach { item ->
//                    if(item.tags.removeAll{ it == tag.id }) {
//                        itemService.save(item)
//                    }
//                }
                itemService.getAll().forEach {
                    if(it.tags.remove(tag.id)) {
                        itemService.save(it)
                    }
                }

                // Delete the tag itself
                tagService.delete(tag)
            }
        }
    }




    private fun createTag(ctx: Context) {
        val tagDTO = ctx.bodyAsClass<TagDTO>()
        val newTag = tagDTO.toTag(tagService.findHighestId(Tag()))
        tagService.save(newTag)
        ctx.status(200)
        ctx.json(newTag)
    }

    private fun getTags(ctx: Context) {
        ctx.status(200)
        ctx.json(tagService.getAll())
    }

    private fun getTag(ctx: Context) {
        val tag: Tag? = tagService.byId(ctx.pathParam("id").toInt())
        tag?.let {
            ctx.status(200)
            ctx.json(TagDTO(tag))
            return
        }
        ctx.status(404)
    }

}