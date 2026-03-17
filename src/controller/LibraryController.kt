
class LibraryController(private val repository: LibraryRepository) {
    var state = LibraryState()
        private set // Solo el controlador puede modificar el estado

    suspend fun addItem(item: MagicItem) {
        state = state.copy(isLoading = true, errorMessage = null)

        try {
            repository.addItem(item)
            val currentItems = repository.getAll()
            state = state.copy(items = currentItems, isLoading = false)
        } catch (e: Exception) {
            state = state.copy(isLoading = false, errorMessage = "Error: ${e.message}")
        }
    }

    suspend fun loadInventory() {
        state = state.copy(isLoading = true)
        val currentItems = repository.getAll()
        state = state.copy(items = currentItems, isLoading = false)
    }
}