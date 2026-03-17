package repository

import model.MagicItem

interface LibraryRepository {
    suspend fun addItem(item: MagicItem)
    suspend fun getItem(id: Int): MagicItem?
    suspend fun getAll(): List<MagicItem>
    suspend fun deleteItem(id: Int)
    suspend fun updateItem(item: MagicItem)
}