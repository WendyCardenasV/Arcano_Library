import controller.LibraryController
import factory.ItemFactory
import repository.InMemoryLibraryRepository
import kotlinx.coroutines.runBlocking
import model.MagicItem

fun main() = runBlocking {
    val repository = InMemoryLibraryRepository()
    val controller = LibraryController(repository)
    val factory = ItemFactory()

    println("--- BIENVENIDA AL ARCHIVO ARCANO, WENDY ---")

    while (true) {
        println("\n1. Agregar Ítem | 2. Ver Inventario | 3. Salir")
        print("> Seleccione: ")

        when (readlnOrNull()) {
            "1" -> {
                print("Tipo (Libro/Pergamino/Artefacto): ")
                val type = readlnOrNull() ?: ""
                print("Nombre: ")
                val name = readlnOrNull() ?: ""

                val newItem = factory.create(type, name)
                if (newItem != null) {
                    println("[SISTEMA]: Procesando acción 'ADD_ITEM'...")
                    controller.addItem(newItem)
                    printState(controller.state)
                } else {
                    println("❌ Tipo no reconocido.")
                }
            }
            "2" -> {
                controller.fetchInventory()
                printState(controller.state)
            }
            "3" -> break
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
        println("  \"items\": ${state.items.map { "{\"id\": ${it.id}, \"nombre\": \"${it.name}\"}" }},")
        println("  \"mensaje\": \"Operación exitosa\"")
        println("}")
    }
}