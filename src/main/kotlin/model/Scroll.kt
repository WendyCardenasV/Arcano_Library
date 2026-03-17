package model

data class Scroll (
    override val id: Int,
    override val name: String,
    val isUsed: Boolean = false
):MagicItem()
