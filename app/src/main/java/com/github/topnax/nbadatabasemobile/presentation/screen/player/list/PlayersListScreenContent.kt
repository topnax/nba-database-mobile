package com.github.topnax.nbadatabasemobile.presentation.screen.player.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.github.topnax.nbadatabasemobile.presentation.navigation.PlayerDetailDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersListScreenContent(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            // Top bar that gradually hides as you scroll
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "NBA Player List", fontSize = 24.sp)
                },

                scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            // LazyColumn to display the list content
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .nestedScroll(scrollBehavior.nestedScrollConnection), // Attach nested scroll behavior
                contentPadding = PaddingValues(16.dp)
            ) {
                items((1..30).toList()) { item ->
                    ListItem(
                        modifier = Modifier.clickable {
                            navController.navigate(PlayerDetailDestination(playerId = item.toString()))
                        },
                        headlineContent = { Text("Item $item") },
                    )
                    HorizontalDivider()
                }
            }
        }
    )
}