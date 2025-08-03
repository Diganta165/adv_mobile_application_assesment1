package com.shafiur.bibliophase.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shafiur.bibliophase.navigation.DrawerItem
import com.example.booktracker.viewmodel.SessionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppDrawerContent(
    items: List<DrawerItem>,
    selectedItem: DrawerItem,
    onItemClick: (DrawerItem) -> Unit,
    scope: CoroutineScope,
    drawerState: DrawerState,
    sessionViewModel: SessionViewModel,
    onLogout: () -> Unit
) {
    val user by sessionViewModel.currentUser.collectAsState()

    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = com.example.booktracker.R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(44.dp)
                            .padding(end = 4.dp)
                    )
                    Text(
                        text = "Bibliophase",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                user?.let {
                    Text(
                        text = "Welcome, ${it.email}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

                Spacer(modifier = Modifier.height(8.dp))

                items.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.title) },
                        selected = item == selectedItem,
                        onClick = {
                            scope.launch { drawerState.close() }
                            onItemClick(item)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }

            Column {
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                Spacer(modifier = Modifier.height(12.dp))

                NavigationDrawerItem(
                    label = { Text("🚪 Logout") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        sessionViewModel.clearSession()
                        onLogout()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    }
}
