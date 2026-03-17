package repository

import model.MagicItem

interface LibraryRepository {
    suspend fun addItem(item: MagicItem)
    suspend fun getAll(): List<MagicItem>
}