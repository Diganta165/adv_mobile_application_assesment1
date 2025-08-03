package com.shafiur.booktracker.navigation

fun getScreenTitle(route: String?): String = when {
    route == null -> "Bibliophase"
    route.startsWith(Screen.Dashboard.route) -> "Dashboard"
    route.startsWith(Screen.Category.route) -> "Categories"
    route.startsWith(Screen.Book.route) -> "Books"
    route.startsWith(Screen.Goals.route) -> "Goals"
    route.startsWith(Screen.Notes.route) -> "Notes"
    route.startsWith(Screen.Account.route) -> "Account"
    route.startsWith(Screen.Auth.route) -> "Login / Register"
    else -> "Bibliophase"
}
