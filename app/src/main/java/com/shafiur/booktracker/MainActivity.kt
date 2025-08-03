package com.shafiur.booktracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.booktracker.viewmodel.AuthViewModelFactory
//import com.shafiur.booktracker.navigation.getScreenTitle
//import com.shafiur.booktracker.navigation.AppNavGraph
//import com.shafiur.booktracker.navigation.DrawerItem
//import com.shafiur.booktracker.navigation.Screen
//import com.shafiur.booktracker.navigation.getScreenTitle
//import com.shafiur.booktracker.viewmodel.AuthViewModelFactory
import com.shafiur.booktracker.data.db.DatabaseProvider
import com.shafiur.booktracker.data.repository.UserRepository
import com.shafiur.booktracker.navigation.AppNavGraph
import com.shafiur.booktracker.navigation.DrawerItem
import com.shafiur.booktracker.navigation.Screen
import com.shafiur.booktracker.navigation.getScreenTitle
import com.shafiur.booktracker.ui.components.AppDrawerContent
import com.shafiur.booktracker.ui.theme.BookTrackerTheme
import com.shafiur.booktracker.viewmodel.AuthViewModel
//import com.shafiur.booktracker.viewmodel.AuthViewModelFactory
import com.shafiur.booktracker.viewmodel.SessionViewModel
import com.shafiur.booktracker.viewmodel.ThemeViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val currentTitle = getScreenTitle(currentRoute)

            // ViewModels (use lifecycle-aware viewModel(), not remember)
            val sessionViewModel: SessionViewModel = viewModel()
            val themeViewModel: ThemeViewModel = viewModel()

            val db = remember { DatabaseProvider.getDatabase(context) }
            val userRepo = remember { UserRepository(db.userDao()) }
            val authViewModel: AuthViewModel = viewModel(
                factory = AuthViewModelFactory(userRepo)
            )

            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
            val user by sessionViewModel.currentUser.collectAsState()

            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            var selectedItem by remember { mutableStateOf<DrawerItem>(DrawerItem.Dashboard) }

            LaunchedEffect(user) {
                println("👀 User state changed: $user")
                if (user == null) {
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
            BookTrackerTheme(darkTheme = isDarkTheme) {
                if (user == null) {
                    // No drawer during login/signup
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(currentTitle) },
                                actions = {
                                    IconButton(onClick = { themeViewModel.toggleTheme() }) {
                                        Icon(
                                            imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                            contentDescription = "Toggle Theme"
                                        )
                                    }
                                }
                            )
                        }
                    ) {
                        AppNavGraph(
                            navController = navController,
                            sessionViewModel = sessionViewModel,
                            authViewModel = authViewModel
                        )
                    }
                } else {
                    // Drawer layout after login
                    ModalNavigationDrawer(
                        drawerContent = {
                            AppDrawerContent(
                                items = listOf(
                                    DrawerItem.Dashboard,
                                    DrawerItem.Category,
                                    DrawerItem.Book,
                                    DrawerItem.Goals,
                                    DrawerItem.Notes,
                                    DrawerItem.Account
                                ),
                                selectedItem = selectedItem,
                                onItemClick = { item ->
                                    selectedItem = item
                                    navController.navigate(item.route) {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                scope = scope,
                                drawerState = drawerState,
                                sessionViewModel = sessionViewModel,
                                onLogout = {
                                    sessionViewModel.clearSession()
                                    authViewModel.clearAuthState()

                                    navController.apply {
                                        graph = navController.createGraph(startDestination = Screen.Auth.route) {
                                            composable(Screen.Auth.route) { /* ... */ }
                                        }
                                        navigate(Screen.Auth.route)
                                    }
                                    scope.launch { drawerState.close() }
                                }
                            )
                        },
                        drawerState = drawerState
                    ) {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = { Text(currentTitle) },
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            scope.launch { drawerState.open() }
                                        }) {
                                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                                        }
                                    },
                                    actions = {
                                        IconButton(onClick = { themeViewModel.toggleTheme() }) {
                                            Icon(
                                                imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                                contentDescription = "Toggle Theme"
                                            )
                                        }
                                    }
                                )
                            }
                        ) {
                            AppNavGraph(
                                navController = navController,
                                sessionViewModel = sessionViewModel,
                                authViewModel = authViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
