package repository

import model.MagicItem
import kotlinx.coroutines.delay

class InMemoryLibraryRepository : LibraryRepository {
    private val items = mutableListOf<MagicItem>()

    override suspend fun addItem(item: MagicItem) {
        delay(1000)
        items.add(item)
    }

    // NUEVO: Busca un ítem en la lista por su ID
    override suspend fun getItem(id: Int): MagicItem? {
        return items.find { it.id == id }
    }

    override suspend fun getAll(): List<MagicItem> {
        delay(500)
        return items.toList()
    }


    override suspend fun deleteItem(id: Int) {
        delay(500)
        items.removeIf { it.id == id }
    }


    override suspend fun updateItem(item: MagicItem) {
        delay(500)
        val index = items.indexOfFirst { it.id == item.id }
        if (index != -1) {
            items[index] = item // Reemplaza el ítem viejo por el nuevo
        }
    }
}