import controller.LibraryController
import factory.ItemFactory
import persistence.FileStorage
import kotlinx.coroutines.runBlocking
import model.Genre

fun main() = runBlocking {
    // We use FileStorage to save
    val repository = FileStorage()
    val controller = LibraryController(repository)
    val factory = ItemFactory()

    println("--- WELCOME TO THE ARCANO LIBRARY ---")

    while (true) {
        println("\n1. add object | 2. See inventory | 3. use an object | 4. Exit")
        print("> Select: ")

        when (readlnOrNull()) {
            "1" -> {
                print("Type (Book/Scroll/Artifact): ")
                val type = readlnOrNull()?.trim() ?: ""
                var name = ""
                var pages = 0
                var genre = Genre.FANTASY
                var energy = 0

                when (type.lowercase()) {
                    "libro", "book" -> {
                        print("Name: ")
                        name = readlnOrNull()?.trim() ?: ""
                        print("Pages: ")
                        pages = readlnOrNull()?.toIntOrNull() ?: 100
                        print("genre (FANTASY, DARK_ARTS, HISTORY, ALCHEMY): ")
                        val genreInput = readlnOrNull()?.trim()?.uppercase() ?: "FANTASY"
                        genre = try { Genre.valueOf(genreInput) } catch (e: Exception) { Genre.FANTASY }
                    }
                    "artefacto", "artifact" -> {
                        print("energy level (0-100): ")
                        energy = readlnOrNull()?.toIntOrNull() ?: 100
                    }
                }

                val newItem = factory.create(type, name, pages, genre, energy)
                if (newItem != null) {
                    println("\n[SYSTEM]: loading action 'ADD_ITEM'...")
                    controller.addItem(newItem)
                    printState(controller.state)
                } else {
                    println("not recognized type.")
                }
            }
            "2" -> {
                println("\n[SYSTEM]: loading action 'FETCH_INVENTORY'...")
                controller.fetchInventory()
                printState(controller.state)
            }
            "3" -> {
                print("write the ID of the object you want to use: ")
                val id = readlnOrNull()?.toIntOrNull()
                if (id != null) {
                    println("\n[SYSTEM]:loading action  'USE_ITEM'...")
                    controller.useItem(id)
                    printState(controller.state)
                } else {
                    println("Invalid ID.")
                }
            }
            "4" -> break
            else -> println("Not valid option.")
        }
    }
}

fun printState(state: state.LibraryState) {
    if (state.isLoading) {
        println("Loading data...")
    } else {
        println("\n[State UPDATED]:")
        println("{")
        println("  \"isLoading\": ${state.isLoading},")
        println("  \"items\": [")


        state.items.forEach { item ->
            val details = when (item) {
                is model.Book -> "\"type\": \"Libro\", \"Pages\": ${item.pages}, \"\"genre: \"${item.genre}\""
                is model.Scroll -> "\"type\": \"Pergamino\", \"is Used\": ${item.isUsed}"
                is model.Artifact -> "\"type\": \"Artefacto\", \"energy\": ${item.energyLevel}%"
            }
            println("    {\"id\": ${item.id}, \"name\": \"${item.name}\", $details},")
        }

        println("  ],")
        val finalMessage = state.errorMessage ?: "SUCCESSFUL "
        println("  \"message\": \"$finalMessage\"")
        println("}")
    }
}