package factory

import model.*

class ItemFactory {
    private var idCounter = 1


    fun create(
        type: String,
        name: String,
        pages: Int = 0,
        genre: Genre = Genre.FANTASY,
        energy: Int = 0
    ): MagicItem? {
        return when (type.trim().lowercase()) {
            "libro", "book" -> Book(id = idCounter++, name = name, pages = pages, genre = genre)
            "pergamino", "scroll" -> Scroll(id = idCounter++, name = name)
            "artefacto", "artifact" -> Artifact(id = idCounter++, name = name, energyLevel = energy)
            else -> null
        }
    }
}