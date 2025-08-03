package com.shafiur.booktracker.navigation

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Dashboard : Screen("dashboard")
    object Account : Screen("account")
    object Book : Screen("book")
    object Goals : Screen("goals")
    object Notes : Screen("notes")
    object Category : Screen("category")
}

