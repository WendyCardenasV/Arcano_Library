data class LibraryState(
    val items: List<MagicItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)