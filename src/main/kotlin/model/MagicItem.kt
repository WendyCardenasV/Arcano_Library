package model


sealed class MagicItem {
     abstract  val name: String
     abstract val id: Int
}


data class Scroll (
     override val id: Int,
     override val name: String,
     val isUsed: Boolean = false
):MagicItem()

data class Book (
     override val name: String,
     override val id: Int,
     val pages: Int,
     val genre: Genre
):MagicItem()

data class Artifact(
     override val id: Int,
     override val name: String,
     val energyLevel: Int
):MagicItem()

