import controller.LibraryController
import factory.ItemFactory
import repository.InMemoryLibraryRepository
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    val repository = InMemoryLibraryRepository()
    val controller = LibraryController(repository)
    val factory = ItemFactory()

    println("--- WELCOME TO THE ARCANO LIBRARY---")

    while (true) {
        println("\n1. Add item | 2. see inventory | 3. Exit")
        print(" Select: ")

        when (readlnOrNull()) {
            "1" -> {
                print("Type (Book/Scroll/Artifact): ")
                val type = readlnOrNull() ?: ""
                print("Name: ")
                val name = readlnOrNull() ?: ""

                val newItem = factory.create(type, name)
                if (newItem != null) {
                    println("[SYSTEM]: loading action 'ADD_ITEM'...")
                    controller.addItem(newItem)
                    printState(controller.state)
                } else {
                    println(" Type not recognized.")
                }
            }
            "2" -> {
                controller.fetchInventory()
                printState(controller.state)
            }
            "3" -> break
            else -> println("Not valid Option.")
        }
    }
}

fun printState(state: state.LibraryState) {
    if (state.isLoading) {
        println("Loading magic data...")
    } else {
        println("\n[STATE UPDATED]:")
        println("{")
        println("  \"isLoading\": ${state.isLoading},")
        println("  \"items\": ${state.items.map { "{\"id\": ${it.id}, \"name\": \"${it.name}\"}" }},")
        println("  \"message\": \"SUCCESSFUL\"")
        println("}")
    }
}