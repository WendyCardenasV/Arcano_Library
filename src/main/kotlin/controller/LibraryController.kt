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

    // NUEVO: La lógica de uso solicitada en el documento
    suspend fun useItem(id: Int) {
        state = state.copy(isLoading = true, errorMessage = null)
        val item = repository.getItem(id)

        if (item != null) {
            when (item) {
                is Scroll -> {
                    // Requisito cumplido: El pergamino tiene un solo uso y se destruye
                    repository.deleteItem(id)
                }
                is Artifact -> {
                    // Un artefacto gasta energía al usarse
                    val newEnergy = (item.energyLevel - 20).coerceAtLeast(0)
                    repository.updateItem(item.copy(energyLevel = newEnergy))
                }
                is Book -> {
                    // Los libros solo se leen, no se gastan
                }
            }
        } else {
            state = state.copy(errorMessage = "Ítem no encontrado")
        }

        val updatedList = repository.getAll()
        state = state.copy(items = updatedList, isLoading = false)
    }
}