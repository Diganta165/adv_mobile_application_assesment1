package com.example.booktracker.ui.screens.book

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
//import com.example.booktracker.data.model.Book
import com.example.booktracker.data.model.BookWithCategory
import com.example.booktracker.data.model.Note
import com.example.booktracker.viewmodel.BookViewModel
import com.example.booktracker.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsDialog(
    bookWithCategory: BookWithCategory,
    onDismiss: () -> Unit,
    noteViewModel: NoteViewModel,
    bookViewModel: BookViewModel
) {
    val book = bookWithCategory.book
    val notes by noteViewModel.getNotesForBook(book.id).collectAsState(initial = emptyList())
    var newNote by remember { mutableStateOf("") }
//    var selectedStatus by remember { mutableStateOf(book.status ?: "Not Started") }
    var selectedStatus by remember { mutableStateOf(book.status) }
    var statusExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "Author: ${book.author}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Category: ${bookWithCategory.category?.name ?: "Uncategorized"}")
                Text(text = "Page: ${book.currentPage}")

                // Status dropdown
                ExposedDropdownMenuBox(
                    expanded = statusExpanded,
                    onExpandedChange = { statusExpanded = !statusExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedStatus,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Reading Status") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusExpanded)
                        },
//                        modifier = Modifier.fillMaxWidth().menuAnchor()
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = statusExpanded,
                        onDismissRequest = { statusExpanded = false }
                    ) {
                        listOf("Not Started", "Reading", "Read").forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status) },
                                onClick = {
                                    selectedStatus = status
                                    statusExpanded = false
                                    bookViewModel.updateBook(book.copy(status = status))
                                }
                            )
                        }
                    }
                }

                // Notes display
                if (notes.isNotEmpty()) {
                    Text("Notes:", style = MaterialTheme.typography.labelLarge)
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        notes.forEach { note ->
                            Text("- ${note.content}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }

                // Add Note input
                OutlinedTextField(
                    value = newNote,
                    onValueChange = { newNote = it },
                    label = { Text("Add Note") },
                    trailingIcon = {
                        IconButton(onClick = {
                            if (newNote.isNotBlank()) {
                                noteViewModel.addNote(
                                    Note(bookId = book.id, content = newNote.trim(), page = 0)
                                )
                                newNote = ""
                            }
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Add Note")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
