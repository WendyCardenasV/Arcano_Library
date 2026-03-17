package factory

import model.*

class ItemFactory {
    private var idCounter = 1
    fun create(type: String, name: String): MagicItem? {

        return when (type.trim().lowercase()) {
            "libro", "book" -> Book(id = idCounter++, name = name, pages = 200, genre = Genre.FANTASY)
            "pergamino", "scroll" -> Scroll(id = idCounter++, name = name)
            "artefacto", "artifact" -> Artifact(id = idCounter++, name = name, energyLevel = 100)
            else -> null
        }
    }
}