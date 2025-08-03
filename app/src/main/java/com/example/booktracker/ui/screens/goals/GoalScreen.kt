package com.shafiur.bibliophase.ui.screens.goals

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shafiur.bibliophase.data.model.Book
import com.shafiur.bibliophase.data.model.ReadingGoal
import com.example.booktracker.viewmodel.BookViewModel
import com.example.booktracker.viewmodel.GoalViewModel
import com.example.booktracker.viewmodel.SessionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(
    sessionViewModel: SessionViewModel,
    goalViewModel: GoalViewModel,
    bookViewModel: BookViewModel
) {
    val user by sessionViewModel.currentUser.collectAsState()
    val goals by goalViewModel.getGoalsForUser(user?.id ?: 0).collectAsState(initial = emptyList())
    val books by bookViewModel.getAllBooks(user?.id ?: 0).collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Reading Goals") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Goal")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(goals) { goal ->
                GoalCard(
                    goal = goal,
                    allBooks = books,
                    onUpdate = { goalViewModel.updateGoal(it) },
                    onDelete = { goalViewModel.deleteGoal(it) }
                )

            }
        }
    }

    if (showDialog) {
        AddGoalDialog(
            showDialog = showDialog, // ✅ FIXED
            onDismiss = { showDialog = false },
            onAddGoal = { goalViewModel.addGoal(it) },
            userId = user?.id ?: 0,
            books = books
        )

    }
}
@Composable
fun GoalCard(
    goal: com.shafiur.bibliophase.data.model.ReadingGoal,
    allBooks: List<com.shafiur.bibliophase.data.model.Book>,
    onUpdate: (com.shafiur.bibliophase.data.model.ReadingGoal) -> Unit,
    onDelete: (com.shafiur.bibliophase.data.model.ReadingGoal) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var completed by remember { mutableStateOf(goal.booksCompleted.toString()) }

    val selectedBooks = allBooks.filter { it.id in goal.selectedBookIds }

    val startDateFormatted = remember(goal.startDate) {
        java.text.SimpleDateFormat("MMM dd, yyyy").format(java.util.Date(goal.startDate))
    }

    val endDateFormatted = remember(goal.endDate) {
        java.text.SimpleDateFormat("MMM dd, yyyy").format(java.util.Date(goal.endDate))
    }

    val progress = goal.booksCompleted.coerceAtMost(goal.targetBooks).toFloat() / goal.targetBooks.coerceAtLeast(1)
    val progressPercent = (progress * 100).toInt()

    // Color based on progress
    val progressColor = when {
        progress >= 1f -> MaterialTheme.colorScheme.primary
        progress >= 0.5f -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.error
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Goal Type: ${goal.goalType}", style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = { onDelete(goal) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Goal")
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text("Target Books: ${goal.targetBooks}")
            Text("Books Completed: ${goal.booksCompleted} ($progressPercent%)")
            Spacer(modifier = Modifier.height(6.dp))

            // Thicker, color-coded progress bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                color = progressColor,
                trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text("Duration: $startDateFormatted - $endDateFormatted", style = MaterialTheme.typography.bodySmall)

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = completed,
                    onValueChange = {
                        if (it.toIntOrNull() != null && it.toInt() <= goal.targetBooks) {
                            completed = it
                            onUpdate(goal.copy(booksCompleted = it.toInt()))
                        }
                    },
                    label = { Text("Update Completed") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (selectedBooks.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text("Books in this goal:", style = MaterialTheme.typography.labelMedium)
                selectedBooks.forEach {
                    Text("• ${it.title}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
