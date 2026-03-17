import controller.LibraryController
import factory.ItemFactory
import persistence.FileStorage
import kotlinx.coroutines.runBlocking
import model.Genre

fun main() = runBlocking {
    // REQUISITO EVOLUCIÓN: Usamos FileStorage para guardar en archivo físico (.txt)
    val repository = FileStorage()
    val controller = LibraryController(repository)
    val factory = ItemFactory()

    println("--- GESTIÓN DE LA BIBLIOTECA ARCANO ---")

    while (true) {
        println("\n1. Agregar Ítem | 2. Ver Inventario | 3. Usar Ítem | 4. Salir")
        print("> Seleccione una opción: ")

        when (readlnOrNull()) {
            "1" -> {
                print("Tipo (Book/Scroll/Artifact): ")
                val type = readlnOrNull()?.trim() ?: ""
                print("Nombre: ")
                val name = readlnOrNull()?.trim() ?: ""

                var pages = 0
                var genre = Genre.FANTASY
                var energy = 0

                when (type.lowercase()) {
                    "libro", "book" -> {
                        print("Páginas: ")
                        pages = readlnOrNull()?.toIntOrNull() ?: 100
                        print("Género (FANTASY, DARK_ARTS, HISTORY, ALCHEMY): ")
                        val genreInput = readlnOrNull()?.trim()?.uppercase() ?: "FANTASY"
                        genre = try { Genre.valueOf(genreInput) } catch (e: Exception) { Genre.FANTASY }
                    }
                    "artefacto", "artifact" -> {
                        print("Nivel de Energía (0-100): ")
                        energy = readlnOrNull()?.toIntOrNull() ?: 100
                    }
                }

                val newItem = factory.create(type, name, pages, genre, energy)
                if (newItem != null) {
                    println("\n[SISTEMA]: Procesando acción 'ADD_ITEM'...")
                    controller.addItem(newItem)
                    printState(controller.state)
                } else {
                    println("❌ Tipo no reconocido.")
                }
            }
            "2" -> {
                println("\n[SISTEMA]: Procesando acción 'FETCH_INVENTORY'...")
                controller.fetchInventory()
                printState(controller.state)
            }
            "3" -> {
                print("Ingrese el ID del ítem que desea usar: ")
                val id = readlnOrNull()?.toIntOrNull()
                if (id != null) {
                    println("\n[SISTEMA]: Procesando acción 'USE_ITEM'...")
                    controller.useItem(id)
                    printState(controller.state)
                } else {
                    println("❌ ID inválido.")
                }
            }
            "4" -> break
            else -> println("Opción no válida.")
        }
    }
}

fun printState(state: state.LibraryState) {
    if (state.isLoading) {
        println("⏳ Cargando datos mágicos...")
    } else {
        println("\n[ESTADO ACTUALIZADO]:")
        println("{")
        println("  \"isLoading\": ${state.isLoading},")
        println("  \"items\": [")

        // REQUISITO CUMPLIDO: Cast inteligente para mostrar propiedades específicas de cada hijo
        state.items.forEach { item ->
            val details = when (item) {
                is model.Book -> "\"tipo\": \"Libro\", \"páginas\": ${item.pages}, \"género\": \"${item.genre}\""
                is model.Scroll -> "\"tipo\": \"Pergamino\", \"usado\": ${item.isUsed}"
                is model.Artifact -> "\"tipo\": \"Artefacto\", \"energía\": ${item.energyLevel}%"
            }
            println("    {\"id\": ${item.id}, \"nombre\": \"${item.name}\", $details},")
        }

        println("  ],")
        val mensajeFinal = state.errorMessage ?: "Operación exitosa"
        println("  \"mensaje\": \"$mensajeFinal\"")
        println("}")
    }
}