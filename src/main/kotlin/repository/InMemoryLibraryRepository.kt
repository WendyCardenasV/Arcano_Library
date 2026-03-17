package repository

import model.MagicItem
import kotlinx.coroutines.delay

class InMemoryLibraryRepository : LibraryRepository {
    private val items = mutableListOf<MagicItem>()

    override suspend fun addItem(item: MagicItem) {
        delay(1000) // Simulamos carga de red o disco
        items.add(item)
    }

    override suspend fun getAll(): List<MagicItem> = items
}