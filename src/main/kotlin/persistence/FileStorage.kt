package persistence

import model.*
import repository.LibraryRepository
import kotlinx.coroutines.delay
import java.io.File

class FileStorage(private val filePath: String = "biblioteca.txt") : LibraryRepository {
    private val file = File(filePath)

    init {
        // Crea el archivo físico si no existe en tu computadora
        if (!file.exists()) file.createNewFile()
    }

    override suspend fun addItem(item: MagicItem) {
        delay(1000) // Simulación asíncrona exigida por el taller
        val currentItems = getAll().toMutableList()
        currentItems.add(item)
        saveAll(currentItems)
    }

    override suspend fun getItem(id: Int): MagicItem? {
        return getAll().find { it.id == id }
    }

    override suspend fun deleteItem(id: Int) {
        delay(500)
        val currentItems = getAll().filterNot { it.id == id }
        saveAll(currentItems)
    }

    override suspend fun updateItem(item: MagicItem) {
        delay(500)
        val currentItems = getAll().toMutableList()
        val index = currentItems.indexOfFirst { it.id == item.id }
        if (index != -1) {
            currentItems[index] = item
            saveAll(currentItems)
        }
    }

    override suspend fun getAll(): List<MagicItem> {
        delay(1000)
        if (!file.exists()) return emptyList()

        // Lee el archivo de texto y reconstruye los objetos mágicos
        return file.readLines().mapNotNull { line ->
            val parts = line.split("|")
            if (parts.size < 3) return@mapNotNull null

            val type = parts[0]
            val id = parts[1].toInt()
            val name = parts[2]

            when (type) {
                "BOOK" -> Book(name, id, parts[3].toInt(), Genre.valueOf(parts[4]))
                "SCROLL" -> Scroll(id, name, parts[3].toBoolean())
                "ARTIFACT" -> Artifact(id, name, parts[3].toInt())
                else -> null
            }
        }
    }

    // Función privada que convierte la lista en texto para guardarla
    private fun saveAll(items: List<MagicItem>) {
        val lines = items.map {
            when (it) {
                is Book -> "BOOK|${it.id}|${it.name}|${it.pages}|${it.genre.name}"
                is Scroll -> "SCROLL|${it.id}|${it.name}|${it.isUsed}"
                is Artifact -> "ARTIFACT|${it.id}|${it.name}|${it.energyLevel}"
            }
        }
        file.writeText(lines.joinToString("\n"))
    }
}