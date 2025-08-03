package com.shafiur.bibliophase.navigation

sealed class DrawerItem(val title: String, val route: String) {

    object Dashboard : DrawerItem("Dashboard", Screen.Dashboard.route)

    object Account : DrawerItem("Account", Screen.Account.route)
    object Book : DrawerItem("Book", Screen.Book.route)
    object Goals : DrawerItem("Goals", Screen.Goals.route)
    object Notes : DrawerItem("Notes", Screen.Notes.route)
    object Category : DrawerItem("Category", Screen.Category.route)
}
