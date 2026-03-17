# The Arcane Library - Magic Inventory Management System

This project is the logical engine for an inventory management system of a magical objects library, developed entirely in **Kotlin**. 

The system allows adding, viewing, and using different types of magical items (Books, Scrolls, and Artifacts). It is designed by applying Software Engineering best practices, including **SOLID Principles**, **Asynchrony** management with Coroutines, and a **Clean Architecture**.

---
## System Architecture

The project utilizes a **State-Driven Architecture** combined with a **Unidirectional Data Flow (UDF)**. 

This is the exact same architectural foundation used by modern frameworks like Jetpack Compose (Android) or React. It operates under the following cycle:
1. **Input (Intent):** The user interacts with the console (`Main.kt`).
2. **Controller:** The `LibraryController` receives the command, processes the business logic, and communicates asynchronously with the data layer.
3. **State:** The controller generates an immutable copy of the `LibraryState` (e.g., setting `isLoading = true` or updating the item list).
4. **Output (UI):** The console reacts to the new state and "redraws" itself showing the changes, completely stripped of any business logic.

---

##  Applied Design Patterns

To ensure the codebase remains scalable and maintainable, the following design patterns were implemented:

### 1. Repository Pattern
Separates the application's core logic from the data persistence layer. 
* A strict contract was defined through the `LibraryRepository` interface.
* The system features two interchangeable implementations: 
  * `InMemoryLibraryRepository`: Useful for quick unit testing and RAM-based execution.
  * `FileStorage`: Implements real persistence by saving and reading objects from a local `.txt` file.
* **SOLID Benefit (Dependency Inversion):** The controller is entirely unaware of how or where data is saved; it only interacts with the interface abstraction. This allows swapping the underlying database in the future without altering a single line of the controller's logic.

### 2. Factory Pattern
Delegates the responsibility of object creation to the `ItemFactory` class.
* Centralizes the generation and auto-incrementing of unique IDs.
* Encapsulates the complexity of instantiating each specific item type (`Book`, `Scroll`, `Artifact`) based on raw user text inputs.
* Keeps the `Main.kt` entry point clean and focused solely on UI routing.

---

##  Key Concepts: Sealed Class vs. Enum Class

To accurately model the domain of magical objects, both `enum class` and `sealed class` were utilized. Understanding the architectural distinction between them is vital:

### Enum Class
Used to represent a **fixed set of constant values**. 
* **In this project:** `enum class Genre { FANTASY, DARK_ARTS, HISTORY, ALCHEMY }`.
* **Limitation:** All elements within an Enum share the exact same structure and behavior. Only their inherent value changes.

### Sealed Class
Used to represent a **restricted, closed-type hierarchy**. They act as "supercharged Enums".
* **In this project:** `sealed class MagicItem`.
* **Main Advantage:** It allows each subtype (child class) to hold **entirely different properties and behaviors**. 
  * A `Book` requires storing `pages` and a `genre`.
  * An `Artifact` does not have pages, but requires an `energyLevel`.
  * A `Scroll` requires a boolean state `isUsed`.
* A standard `enum` could never support such varying data structures. Furthermore, because the class is "sealed," the compiler knows exactly how many subclasses exist, forcing `when` expressions to be exhaustive and safe (eliminating the need for a generic `else` branch).

---

##  Technologies and Tools

* **Language:** Kotlin (JVM)
* **Asynchrony:** Kotlin Coroutines (`suspend functions`, `runBlocking`, `delay` for simulating network/disk latency).
* **Persistence:** File I/O (Native Kotlin file reading and writing).

##  How to run the project
1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Ensure Gradle is synced to download the Coroutines dependencies.
4. Run the `src/main/kotlin/Main.kt` file.
