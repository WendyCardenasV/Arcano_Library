data class Artifact(
    override val id: Int,
    override val name: String,
    val energy: Int
):MagicItem()