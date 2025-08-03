package com.shafiur.booktracker.ui.screens.goals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shafiur.booktracker.data.model.Book
import com.shafiur.booktracker.data.model.ReadingGoal
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGoalDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAddGoal: (ReadingGoal) -> Unit,
    books: List<Book>,
    userId: Int
) {
    // State variables
    val goalTypes = listOf("Weekly", "Monthly", "Yearly")
    var selectedGoalType by remember { mutableStateOf(goalTypes.first()) }
    var selectedBooks by remember { mutableStateOf(setOf<Int>()) }

    // Date picker states
    val startDateState = rememberDatePickerState()
    val endDateState = rememberDatePickerState()
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    val dateFormatter = remember {
        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    }

    // Main dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    val startDate = startDateState.selectedDateMillis ?: System.currentTimeMillis()
                    val endDate = endDateState.selectedDateMillis ?: System.currentTimeMillis()
                    onAddGoal(
                        ReadingGoal(
                            userId = userId,
                            goalType = selectedGoalType,
                            targetBooks = selectedBooks.size,
                            startDate = startDate,
                            endDate = endDate,
                            selectedBookIds = selectedBooks.toList()
                        )
                    )
                    onDismiss()
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            },
            title = { Text("Add Reading Goal") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Goal Type Dropdown
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selectedGoalType,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Goal Type") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            goalTypes.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type) },
                                    onClick = {
                                        selectedGoalType = type
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Book selection
                    Text("Select Books", style = MaterialTheme.typography.labelMedium)
                    Column {
                        books.forEach { book ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Checkbox(
                                    checked = selectedBooks.contains(book.id),
                                    onCheckedChange = {
                                        selectedBooks = if (it) selectedBooks + book.id else selectedBooks - book.id
                                    }
                                )
                                Text(book.title, modifier = Modifier.padding(start = 8.dp))
                            }
                        }
                    }

                    // Start Date Picker Trigger
                    DatePickerButton(
                        label = "Start Date",
                        selectedDateMillis = startDateState.selectedDateMillis,
                        dateFormatter = dateFormatter,
                        onClick = { showStartDatePicker = true }
                    )

                    // End Date Picker Trigger
                    DatePickerButton(
                        label = "End Date",
                        selectedDateMillis = endDateState.selectedDateMillis,
                        dateFormatter = dateFormatter,
                        onClick = { showEndDatePicker = true }
                    )
                }
            }
        )
    }

    // Date picker dialogs (shown independently)
    if (showStartDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showStartDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = { showStartDatePicker = false },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = startDateState)
        }
    }

    if (showEndDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showEndDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = { showEndDatePicker = false },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = endDateState)
        }
    }
}

@Composable
private fun DatePickerButton(
    label: String,
    selectedDateMillis: Long?,
    dateFormatter: SimpleDateFormat,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = selectedDateMillis?.let { dateFormatter.format(it) } ?: "Select $label",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}