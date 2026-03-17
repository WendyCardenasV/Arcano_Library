package controller

import repository.LibraryRepository
import state.LibraryState
import model.*

class LibraryController(private val repository: LibraryRepository) {
    var state = LibraryState()
        private set

    suspend fun addItem(item: MagicItem) {
        state = state.copy(isLoading = true, errorMessage = null)
        repository.addItem(item)
        val updatedList = repository.getAll()
        state = state.copy(items = updatedList, isLoading = false)
    }

    suspend fun fetchInventory() {
        state = state.copy(isLoading = true, errorMessage = null)
        val items = repository.getAll()
        state = state.copy(items = items, isLoading = false)
    }

    suspend fun useItem(id: Int) {
        state = state.copy(isLoading = true, errorMessage = null)
        val item = repository.getItem(id)

        if (item != null) {
            when (item) {
                is Scroll -> {
                    // Scroll has only one use and is destroyed
                    repository.deleteItem(id)
                }
                is Artifact -> {
                    // A Artifact uses energy when it is used.
                    val newEnergy = (item.energyLevel - 20).coerceAtLeast(0)
                    repository.updateItem(item.copy(energyLevel = newEnergy))
                }
                is Book -> {
                    // Books are only read, not used up
                }
            }
        } else {
            state = state.copy(errorMessage = "Ítem no encontrado")
        }

        val updatedList = repository.getAll()
        state = state.copy(items = updatedList, isLoading = false)
    }
}