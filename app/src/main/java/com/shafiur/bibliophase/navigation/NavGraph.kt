package com.shafiur.bibliophase.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.booktracker.data.db.DatabaseProvider
import com.example.booktracker.ui.screens.account.AuthScreen
import com.example.booktracker.ui.screens.dashboard.DashboardScreen
import com.example.booktracker.viewmodel.AuthViewModel
import com.example.booktracker.viewmodel.SessionViewModel
import com.shafiur.bibliophase.data.model.Book
import com.shafiur.bibliophase.data.repository.BookRepository
import com.shafiur.bibliophase.data.repository.CategoryRepository
import com.shafiur.bibliophase.data.repository.GoalRepository
import com.example.booktracker.data.repository.NoteRepository
import com.example.booktracker.ui.screens.account.AccountScreen
import com.example.booktracker.ui.screens.book.BookScreen
import com.example.booktracker.ui.screens.category.CategoryScreen
import com.example.booktracker.ui.screens.goals.GoalsScreen
import com.example.booktracker.ui.screens.notes.NotesScreen
import com.shafiur.bibliophase.viewModel.GoalViewModelFactory
import com.example.booktracker.viewmodel.BookViewModel
import com.example.booktracker.viewmodel.BookViewModelFactory
import com.example.booktracker.viewmodel.CategoryViewModel
import com.example.booktracker.viewmodel.CategoryViewModelFactory
import com.example.booktracker.viewmodel.GoalViewModel
import com.example.booktracker.viewmodel.NoteViewModel
import com.example.booktracker.viewmodel.NoteViewModelFactory

@Composable
fun AppNavGraph(
    navController: NavHostController,
    sessionViewModel: SessionViewModel,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Auth.route
    ) {
        composable(Screen.Auth.route) {
            AuthScreen(
                viewModel = authViewModel,
                onLoginSuccess = { user ->
                    sessionViewModel.setUser(user)
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Dashboard.route) {
            val context = LocalContext.current
            val db = remember { DatabaseProvider.getDatabase(context) }

            val bookViewModel: BookViewModel = viewModel(
                factory = BookViewModelFactory(
                    com.shafiur.bibliophase.data.repository.BookRepository(
                        db.bookDao()
                    )
                )
            )

            val categoryViewModel: CategoryViewModel = viewModel(
                factory = CategoryViewModelFactory(
                    categoryRepository = com.shafiur.bibliophase.data.repository.CategoryRepository(
                        db.categoryDao()
                    ),
                    bookRepository = com.shafiur.bibliophase.data.repository.BookRepository(db.bookDao())
                )
            )

            val goalViewModel: GoalViewModel = viewModel(
                factory = GoalViewModelFactory(
                    GoalRepository(
                        db.goalDao()
                    )
                )
            )

            val noteViewModel: NoteViewModel = viewModel(
                factory = NoteViewModelFactory(NoteRepository(db.noteDao()))
            )

            DashboardScreen(
                sessionViewModel = sessionViewModel,
                bookViewModel = bookViewModel,
                categoryViewModel = categoryViewModel,
                goalViewModel = goalViewModel,
                noteViewModel = noteViewModel
            )
        }
        composable(Screen.Account.route) {
            AccountScreen(authViewModel = authViewModel)
        }
        composable(Screen.Book.route) {
            val context = LocalContext.current
            val db = DatabaseProvider.getDatabase(context)

            val bookViewModel: BookViewModel = viewModel(
                factory = BookViewModelFactory(
                    com.shafiur.bibliophase.data.repository.BookRepository(
                        db.bookDao()
                    )
                )
            )

            val categoryViewModel: CategoryViewModel = viewModel(
                factory = CategoryViewModelFactory(
                    categoryRepository = com.shafiur.bibliophase.data.repository.CategoryRepository(
                        db.categoryDao()
                    ),
                    bookRepository = com.shafiur.bibliophase.data.repository.BookRepository(db.bookDao())
                )
            )

            val noteViewModel: NoteViewModel = viewModel(
                factory = NoteViewModelFactory(NoteRepository(db.noteDao()))
            )

            BookScreen(
                sessionViewModel = sessionViewModel,
                bookViewModel = bookViewModel,
                categoryViewModel = categoryViewModel,
                noteViewModel = noteViewModel // ✅ pass it here
            )
        }

        composable(Screen.Goals.route) {
            val context = LocalContext.current
            val db = remember { DatabaseProvider.getDatabase(context) }

            val goalViewModel: GoalViewModel = viewModel(
                factory = GoalViewModelFactory(
                    GoalRepository(
                        db.goalDao()
                    )
                )
            )

            val bookViewModel: BookViewModel = viewModel(
                factory = BookViewModelFactory(
                    com.shafiur.bibliophase.data.repository.BookRepository(
                        db.bookDao()
                    )
                )
            )

            GoalsScreen(
                sessionViewModel = sessionViewModel,
                goalViewModel = goalViewModel,
                bookViewModel = bookViewModel
            )
        }

        composable(Screen.Notes.route) {
            val context = LocalContext.current
            val db = remember { DatabaseProvider.getDatabase(context) }

            val noteViewModel: NoteViewModel = viewModel(
                factory = NoteViewModelFactory(NoteRepository(db.noteDao()))
            )

            val bookViewModel: BookViewModel = viewModel(
                factory = BookViewModelFactory(
                    com.shafiur.bibliophase.data.repository.BookRepository(
                        db.bookDao()
                    )
                )
            )

            NotesScreen(
                noteViewModel = noteViewModel,
                bookViewModel = bookViewModel,
                sessionViewModel = sessionViewModel
            )
        }

        composable(Screen.Category.route) {
            val context = LocalContext.current
            val db = remember { DatabaseProvider.getDatabase(context) }

            val categoryViewModel: CategoryViewModel = viewModel(
                factory = CategoryViewModelFactory(
                    categoryRepository = com.shafiur.bibliophase.data.repository.CategoryRepository(
                        db.categoryDao()
                    ),
                    bookRepository = com.shafiur.bibliophase.data.repository.BookRepository(db.bookDao())
                )
            )

            CategoryScreen(
                sessionViewModel = sessionViewModel,
                viewModel = categoryViewModel
            )
        }

    }
}
