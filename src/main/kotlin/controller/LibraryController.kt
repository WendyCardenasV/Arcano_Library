package controller

import repository.LibraryRepository
import state.LibraryState
import model.MagicItem

class LibraryController(private val repository: LibraryRepository) {
    // Estado observable desde el Main
    var state = LibraryState()
        private set

    suspend fun addItem(item: MagicItem) {
        // 1. Mostrar carga
        state = state.copy(isLoading = true, errorMessage = null)

        // 2. Operación asíncrona
        repository.addItem(item)

        // 3. Actualizar estado final
        val updatedList = repository.getAll()
        state = state.copy(items = updatedList, isLoading = false)
    }

    suspend fun fetchInventory() {
        state = state.copy(isLoading = true)
        val items = repository.getAll()
        state = state.copy(items = items, isLoading = false)
    }
}