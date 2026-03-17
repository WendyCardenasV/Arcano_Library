interface LibraryRepository {
    suspend fun addItem(item: MagicItem)
    suspend fun getAll(): List<MagicItem>
}