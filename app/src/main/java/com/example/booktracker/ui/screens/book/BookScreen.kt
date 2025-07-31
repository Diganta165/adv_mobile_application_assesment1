package com.example.booktracker.ui.screens.book

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.booktracker.data.model.BookWithCategory
import com.example.booktracker.data.model.Category
import com.example.booktracker.data.model.Note
import com.example.booktracker.viewmodel.BookViewModel
import com.example.booktracker.viewmodel.CategoryViewModel
import com.example.booktracker.viewmodel.NoteViewModel
import com.example.booktracker.viewmodel.SessionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookScreen(
    sessionViewModel: SessionViewModel,
    bookViewModel: BookViewModel,
    categoryViewModel: CategoryViewModel,
    noteViewModel: NoteViewModel
) {
    val user by sessionViewModel.currentUser.collectAsState()
    val categories by categoryViewModel.categories.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var filterCategory by remember { mutableStateOf<Category?>(null) }
    var expandedFilter by remember { mutableStateOf(false) }
    var selectedBookWithCategory by remember { mutableStateOf<BookWithCategory?>(null) }

    val books by produceState(initialValue = emptyList<BookWithCategory>(), user, filterCategory) {
        user?.let { currentUser ->
            if (filterCategory == null) {
                bookViewModel.getAllBooksWithCategory(currentUser.id).collect { value = it }
            } else {
                bookViewModel.getBooksByCategoryId(currentUser.id, filterCategory!!.id).collect { value = it }
            }
        }
    }

    LaunchedEffect(user) {
        user?.id?.let {
            categoryViewModel.loadCategories(it)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Books") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Book")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expandedFilter,
                onExpandedChange = { expandedFilter = !expandedFilter }
            ) {
                OutlinedTextField(
                    value = filterCategory?.name ?: "All Categories",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Filter by Category") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedFilter)
                    },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expandedFilter,
                    onDismissRequest = { expandedFilter = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("All Categories") },
                        onClick = {
                            filterCategory = null
                            expandedFilter = false
                        }
                    )
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                filterCategory = category
                                expandedFilter = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (books.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No books found.")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(books) { bookWithCategory ->
                        BookItem(
                            bookWithCategory = bookWithCategory,
                            onDelete = {
                                bookViewModel.deleteBook(bookWithCategory.book)
                            },
                            onClick = {
                                selectedBookWithCategory = bookWithCategory
                            }
                        )
                    }
                }
            }
        }
    }

    AddBookDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        categories = categories,
        userId = user?.id ?: 0,
        onAddBook = { book ->
            bookViewModel.addBook(book)
        }
    )

    selectedBookWithCategory?.let { bookWithCategory ->
        BookDetailsDialog(
            bookWithCategory = bookWithCategory,
            onDismiss = { selectedBookWithCategory = null },
            noteViewModel = noteViewModel,
            bookViewModel = bookViewModel
        )
    }
}

@Composable
private fun BookItem(
    bookWithCategory: BookWithCategory,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    val book = bookWithCategory.book
    val category = bookWithCategory.category

    val statusColor = when (book.status) {
        "Reading" -> MaterialTheme.colorScheme.primary
        "Read" -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.secondary
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = book.status,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(
                        color = statusColor,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    if (!book.coverImageUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = book.coverImageUrl,
                            contentDescription = "Book cover",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Book,
                            contentDescription = "Book placeholder",
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.Center),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "by ${book.author}",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    category?.let {
                        Text(
                            text = "Category: ${it.name}",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Text("Pages: ${book.currentPage}", style = MaterialTheme.typography.labelSmall)
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Book",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
