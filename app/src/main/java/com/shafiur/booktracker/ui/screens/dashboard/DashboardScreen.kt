package com.shafiur.booktracker.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shafiur.booktracker.data.model.*
import com.shafiur.booktracker.viewmodel.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DashboardScreen(
    sessionViewModel: SessionViewModel,
    bookViewModel: BookViewModel,
    categoryViewModel: CategoryViewModel,
    goalViewModel: GoalViewModel,
    noteViewModel: NoteViewModel
) {
    val user by sessionViewModel.currentUser.collectAsState()
    val userId = user?.id ?: return

    val books by bookViewModel.getAllBooks(userId).collectAsState(initial = emptyList())
    val categories by categoryViewModel.categories.collectAsState()
    val goals by goalViewModel.getGoalsForUser(userId).collectAsState(initial = emptyList())
    val notes by noteViewModel.getAllNotesWithBooks().collectAsState(initial = emptyList())

    LaunchedEffect(userId) {
        categoryViewModel.loadCategories(userId)
    }

    val currentlyReading = books.filter { it.status == "Reading" }
    val expiringGoals = goals.filter {
        val daysLeft = (it.endDate - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)
        daysLeft in 0..7
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Spacer(modifier = Modifier.height(50.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard("Total Books", books.size, Modifier.weight(1f))
                SummaryCard("Total Categories", categories.size, Modifier.weight(1f))
            }
        }

        item {
            SectionTitle("Currently Reading")
        }

        items(currentlyReading.take(3)) { book ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(book.title, style = MaterialTheme.typography.titleMedium)
                    Text("Author: ${book.author}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        item {
            SectionTitle("Latest Notes")
        }

        items(notes.take(2)) { noteWithBook ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(noteWithBook.book.title, style = MaterialTheme.typography.titleMedium)
                    Text(noteWithBook.note.content, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        item {
            SectionTitle("Expiring Goals")
        }

        items(expiringGoals) { goal ->
            val sdf = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
            val progress = goal.booksCompleted.coerceAtMost(goal.targetBooks).toFloat() /
                    goal.targetBooks.coerceAtLeast(1)

            val progressColor = when {
                progress >= 1f -> MaterialTheme.colorScheme.primary
                progress >= 0.5f -> MaterialTheme.colorScheme.tertiary
                else -> MaterialTheme.colorScheme.error
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Type: ${goal.goalType}", style = MaterialTheme.typography.titleMedium)
                    Text("Ends: ${sdf.format(Date(goal.endDate))}")
                    Spacer(Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp),
                        color = progressColor,
                        trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Progress: ${goal.booksCompleted} / ${goal.targetBooks}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryCard(label: String, count: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.titleSmall)
            Text(count.toString(), style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}
