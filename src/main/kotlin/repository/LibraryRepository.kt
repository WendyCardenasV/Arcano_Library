package repository

import model.MagicItem
import kotlinx.coroutines.delay

interface LibraryRepository {
    suspend fun addItem(item: MagicItem)
    suspend fun getAll(): List<MagicItem>
}

class InMemoryLibraryRepository : LibraryRepository {
    private val items = mutableListOf<MagicItem>()

    override suspend fun addItem(item: MagicItem) {
        delay(1000) // Simulación de latencia asíncrona
        items.add(item)
    }

    override suspend fun getAll(): List<MagicItem> {
        delay(500)
        return items.toList()
    }
}