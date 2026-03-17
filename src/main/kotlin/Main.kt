package main

import LibraryState


    fun main() = runBlocking {
        // 1. Inicializamos las piezas
        val repository = InMemoryLibraryRepository() // O FileStorage cuando lo termines
        val controller = LibraryController(repository)
        val factory = ItemFactory()

        var running = true

        while (running) {
            println("\n--- GESTIÓN DE LA BIBLIOTECA ARCANO ---")
            println("1. Agregar Ítem Mágico")
            println("2. Ver Inventario")
            println("3. Salir")
            print("> Seleccione una opción: ")

            when (readlnOrNull()) {
                "1" -> {
                    println("Tipo (Libro/Pergamino/Artefacto): ")
                    val type = readlnOrNull() ?: ""
                    println("Nombre del objeto: ")
                    val name = readlnOrNull() ?: ""

                    // Usamos el Factory para crear el objeto
                    val newItem = factory.create(type, name)

                    if (newItem != null) {
                        controller.addItem(newItem)
                        renderState(controller.state)
                    } else {
                        println("[ERROR]: Tipo no válido.")
                    }
                }

                "2" -> {
                    controller.loadInventory()
                    renderState(controller.state)
                }

                "3" -> running = false
            }
        }
    }

    fun renderState(state: LibraryState) {
        if (state.isLoading) {
            println("\n[SISTEMA]: Procesando acción... (delay de 1s)")
        } else {
            println("\n[ESTADO ACTUALIZADO]:")
            println("Items: ${state.items.map { "${it.name} (ID: ${it.id})" }}")
            state.errorMessage?.let { println("Error: $it") }
        }
    }
