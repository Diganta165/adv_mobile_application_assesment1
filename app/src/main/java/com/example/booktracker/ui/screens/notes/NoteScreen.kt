package com.example.booktracker.ui.screens.notes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.booktracker.data.model.Book
import com.example.booktracker.data.model.BookWithCategory
import com.example.booktracker.data.model.NoteWithBook
import com.example.booktracker.viewmodel.BookViewModel
import com.example.booktracker.viewmodel.NoteViewModel
import com.example.booktracker.viewmodel.SessionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    noteViewModel: NoteViewModel,
    bookViewModel: BookViewModel,
    sessionViewModel: SessionViewModel
) {
    val notes by noteViewModel.getAllNotesWithBooks().collectAsState(initial = emptyList())
    val user by sessionViewModel.currentUser.collectAsState()
    val books by produceState(initialValue = emptyList<BookWithCategory>(), user) {
        user?.let {
            bookViewModel.getAllBooksWithCategory(it.id).collect { value = it }
        }
    }

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Your Notes") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(notes) { noteWithBook ->
                NoteItem(noteWithBook, noteViewModel)
            }
        }
    }

    if (showDialog) {
        AddNoteDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            books = books.map { it.book },
            noteViewModel = noteViewModel
        )
    }
}

@Composable
fun NoteItem(noteWithBook: NoteWithBook, noteViewModel: NoteViewModel) {
    var isEditing by remember { mutableStateOf(false) }
    var updatedContent by remember { mutableStateOf(noteWithBook.note.content) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Book: ${noteWithBook.book.title}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))

            if (isEditing) {
                OutlinedTextField(
                    value = updatedContent,
                    onValueChange = { updatedContent = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    maxLines = 3
                )
                Row {
                    TextButton(onClick = {
                        isEditing = false
                        updatedContent = noteWithBook.note.content
                    }) {
                        Text("Cancel")
                    }
                    TextButton(onClick = {
                        noteViewModel.updateNote(noteWithBook.note.copy(content = updatedContent))
                        isEditing = false
                    }) {
                        Text("Save")
                    }
                }
            } else {
                Text(noteWithBook.note.content)
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = { isEditing = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = {
                        noteViewModel.deleteNote(noteWithBook.note)
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    books: List<Book>,
    noteViewModel: NoteViewModel
) {
    var selectedBook by remember { mutableStateOf<Book?>(null) }
    var content by remember { mutableStateOf("") }
    var page by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    selectedBook?.let {
                        noteViewModel.addNote(
                            com.example.booktracker.data.model.Note(
                                bookId = it.id,
                                content = content,
                                page = page.toIntOrNull() ?: 0
                            )
                        )
                        onDismiss()
                    }
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) { Text("Cancel") }
            },
            title = { Text("Add Note") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selectedBook?.title ?: "Select Book",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Book") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            books.forEach { book ->
                                DropdownMenuItem(
                                    text = { Text(book.title) },
                                    onClick = {
                                        selectedBook = book
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

//                    OutlinedTextField(
//                        value = page,
//                        onValueChange = { page = it },
//                        label = { Text("Page") },
//                        singleLine = true,
//                        modifier = Modifier.fillMaxWidth()
//                    )

                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Note Content") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false,
                        maxLines = 4
                    )
                }
            }
        )
    }
}
