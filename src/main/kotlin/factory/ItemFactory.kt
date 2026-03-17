package factory

import model.*

class ItemFactory {
    private var idCounter = 1

    fun create(type: String, name: String): MagicItem? {
        return when (type.lowercase()) {
            "libro" -> Book(idCounter++, name, 200, Genre.FANTASY)
            "pergamino" -> Scroll(idCounter++, name)
            "artefacto" -> Artifact(idCounter++, name, 100)
            else -> null
        }
    }
}