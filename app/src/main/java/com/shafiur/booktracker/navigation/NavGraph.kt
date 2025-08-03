package com.shafiur.booktracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shafiur.booktracker.viewmodel.AuthViewModel
import com.shafiur.booktracker.viewmodel.SessionViewModel
import com.shafiur.booktracker.data.db.DatabaseProvider
import com.shafiur.booktracker.ui.screens.account.AuthScreen
import com.shafiur.booktracker.ui.screens.dashboard.DashboardScreen
//import com.shafiur.booktracker.viewModel.AuthViewModel
//import com.shafiur.booktracker.viewmodel.SessionViewModel
//import com.shafiur.booktracker.data.model.Book
import com.shafiur.booktracker.data.repository.BookRepository
import com.shafiur.booktracker.data.repository.CategoryRepository
import com.shafiur.booktracker.data.repository.GoalRepository
import com.shafiur.booktracker.data.repository.NoteRepository
import com.shafiur.booktracker.ui.screens.account.AccountScreen
import com.shafiur.booktracker.ui.screens.book.BookScreen
import com.shafiur.booktracker.ui.screens.category.CategoryScreen
import com.shafiur.booktracker.ui.screens.goals.GoalsScreen
import com.shafiur.booktracker.ui.screens.notes.NotesScreen
import com.shafiur.booktracker.viewModel.GoalViewModelFactory
import com.shafiur.booktracker.viewmodel.BookViewModel
import com.shafiur.booktracker.viewmodel.BookViewModelFactory
import com.shafiur.booktracker.viewmodel.CategoryViewModel
import com.shafiur.booktracker.viewmodel.CategoryViewModelFactory
import com.shafiur.booktracker.viewmodel.GoalViewModel
import com.shafiur.booktracker.viewmodel.NoteViewModel
import com.shafiur.booktracker.viewmodel.NoteViewModelFactory

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
                factory = BookViewModelFactory(BookRepository(db.bookDao()))
            )

            val categoryViewModel: CategoryViewModel = viewModel(
                factory = CategoryViewModelFactory(
                    categoryRepository = CategoryRepository(db.categoryDao()),
                    bookRepository = BookRepository(db.bookDao())
                )
            )

            val goalViewModel: GoalViewModel = viewModel(
                factory = GoalViewModelFactory(GoalRepository(db.goalDao()))
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
                factory = BookViewModelFactory(BookRepository(db.bookDao()))
            )

            val categoryViewModel: CategoryViewModel = viewModel(
                factory = CategoryViewModelFactory(
                    categoryRepository = CategoryRepository(db.categoryDao()),
                    bookRepository = BookRepository(db.bookDao())
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
                factory = GoalViewModelFactory(GoalRepository(db.goalDao()))
            )

            val bookViewModel: BookViewModel = viewModel(
                factory = BookViewModelFactory(BookRepository(db.bookDao()))
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
                factory = BookViewModelFactory(BookRepository(db.bookDao()))
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
                    categoryRepository = CategoryRepository(db.categoryDao()),
                    bookRepository = BookRepository(db.bookDao())
                )
            )

            CategoryScreen(
                sessionViewModel = sessionViewModel,
                viewModel = categoryViewModel
            )
        }

    }
}
