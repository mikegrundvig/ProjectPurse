package com.michaelgrundvig.storage.tag

import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.Context

class TagController(private val tagService: TagService) {

    fun routes() {
        return path("tag") {
            post { createTag(it) }
            get { getTags(it) }
            path("{id}") {
                get { getTag(it) }
            }
        }
    }

    private fun createTag(ctx: Context) {
        val tagDTO = ctx.bodyAsClass<TagDTO>()
        val newTag = tagDTO.toTag(tagService.findHighestId(Tag()))
        tagService.create(newTag)
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