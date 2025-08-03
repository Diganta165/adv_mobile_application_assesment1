package com.shafiur.bibliophase.ui.screens.book

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shafiur.bibliophase.data.model.Book
import com.shafiur.bibliophase.data.model.Category
import java.io.File
import java.io.FileOutputStream
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    categories: List<com.shafiur.bibliophase.data.model.Category>,
    userId: Int,
    onAddBook: (com.shafiur.bibliophase.data.model.Book) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<com.shafiur.bibliophase.data.model.Category?>(null) }
    var currentPage by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf("Not Started") }
    var showError by remember { mutableStateOf(false) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    fun resetForm() {
        title = ""
        author = ""
        selectedCategory = null
        currentPage = ""
        selectedStatus = "Not Started"
        imageUri = null
        showError = false
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
                resetForm()
            },
            title = { Text("Add New Book") },
            text = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 500.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        // Image Picker
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable { launcher.launch("image/*") },
                            contentAlignment = Alignment.Center
                        ) {
                            if (imageUri != null) {
                                AsyncImage(
                                    model = imageUri,
                                    contentDescription = "Book cover",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Default.AddPhotoAlternate,
                                        contentDescription = "Add cover",
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Text("Add Book Cover")
                                }
                            }
                        }

                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Title") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                capitalization = KeyboardCapitalization.Words
                            )
                        )

                        OutlinedTextField(
                            value = author,
                            onValueChange = { author = it },
                            label = { Text("Author") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                capitalization = KeyboardCapitalization.Words
                            )
                        )

                        OutlinedTextField(
                            value = currentPage,
                            onValueChange = { currentPage = it.filter { c -> c.isDigit() } },
                            label = { Text("Pages") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Status Dropdown
                        var statusExpanded by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(
                            expanded = statusExpanded,
                            onExpandedChange = { statusExpanded = !statusExpanded }
                        ) {
                            OutlinedTextField(
                                readOnly = true,
                                value = selectedStatus,
                                onValueChange = {},
                                label = { Text("Reading Status") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(statusExpanded)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
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
                                        }
                                    )
                                }
                            }
                        }

                        Text("Select Category:", style = MaterialTheme.typography.labelLarge)

                        Column(
                            modifier = Modifier
                                .heightIn(max = 200.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            categories.forEach { category ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedCategory = category }
                                        .padding(4.dp)
                                ) {
                                    RadioButton(
                                        selected = selectedCategory?.id == category.id,
                                        onClick = { selectedCategory = category }
                                    )
                                    Text(category.name, modifier = Modifier.padding(start = 8.dp))
                                }
                            }
                        }

                        if (showError) {
                            Text(
                                "Please fill all fields and select a category",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (title.isBlank() || author.isBlank() || currentPage.isBlank() || selectedCategory == null) {
                        showError = true
                    } else {
                        val savedPath = imageUri?.let { saveImageToInternalStorage(context, it) }

                        onAddBook(
                            com.shafiur.bibliophase.data.model.Book(
                                userId = userId,
                                title = title.trim(),
                                author = author.trim(),
                                coverImageUrl = savedPath,
                                categoryId = selectedCategory!!.id,
                                currentPage = currentPage.toIntOrNull() ?: 0,
                                status = selectedStatus
                            )
                        )
                        onDismiss()
                        resetForm()
                    }
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismiss()
                    resetForm()
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// Helper function to save image
fun saveImageToInternalStorage(context: Context, uri: Uri): String {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.filesDir, "book_cover_${UUID.randomUUID()}.jpg")
    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()
    return file.absolutePath
}
