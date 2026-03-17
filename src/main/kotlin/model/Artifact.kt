package model

data class Artifact(
    override val id: Int,
    override val name: String,
    val energyLevel: Int
):MagicItem()