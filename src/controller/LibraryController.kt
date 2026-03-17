class LibraryController(private val repository: LibraryRepository) {
    var state = LibraryState()
        private set

    suspend fun addItem(item: MagicItem) {
        state = state.copy(isLoading = true) // Primero mostramos que está cargando

        repository.addItem(item) // Operación pesada

        val updatedList = repository.getAll()
        state = LibraryState(items = updatedList, isLoading = false) // Actualizamos con la lista nueva
    }
}