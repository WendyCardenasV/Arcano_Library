package model

data class Book (
    override val name: String,
    override val id: Int,
    val pages: Int,
    val genre: Genre
    ):MagicItem()
