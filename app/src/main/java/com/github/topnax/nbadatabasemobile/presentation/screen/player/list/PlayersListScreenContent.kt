package com.github.topnax.nbadatabasemobile.presentation.screen.player.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.github.topnax.nbadatabasemobile.R
import com.github.topnax.nbadatabasemobile.data.Player
import com.github.topnax.nbadatabasemobile.presentation.navigation.PlayerDetailDestination
import com.github.topnax.nbadatabasemobile.ui.composables.ErrorSection
import com.github.topnax.nbadatabasemobile.ui.composables.LoadingIndicator
import com.github.topnax.nbadatabasemobile.ui.composables.player.PlayerItem
import com.github.topnax.nbadatabasemobile.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersListScreenContent(
    navController: NavController,
    state: PlayersListViewModelScreenContract.State,
    onEvent: (PlayersListViewModelScreenContract.Event) -> Unit,
) {
    LaunchedEffect(true) {
        if (state.players.isNullOrEmpty()) {
            onEvent(PlayersListViewModelScreenContract.Event.LoadNextItems)
        }
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            // Top bar that gradually hides as you scroll
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.player_list_title), style = MaterialTheme.typography.titleLarge)
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                when {
                    state.isLoading && state.players.isNullOrEmpty() ->
                        LoadingIndicator(modifier = Modifier.align(Alignment.Center))

                    state.players != null -> {
                        PlayerListSection(
                            players = state.players,
                            onPlayerClick = { player ->
                                navController.navigate(PlayerDetailDestination(
                                    playerId = player.id,
                                    playerFullName = player.fullName
                                ))
                            },
                            scrollBehavior = scrollBehavior,
                            endReached = state.endReached,
                            isLoading = state.isLoading,
                            isError = state.isError,
                            onLoadNextItems = {
                                onEvent(PlayersListViewModelScreenContract.Event.LoadNextItems)
                            }
                        )
                    }

                    state.isError -> {
                        ErrorSection(
                            modifier = Modifier.fillMaxSize(),
                            onReload = {
                                onEvent(PlayersListViewModelScreenContract.Event.Reload)
                            },
                            label = stringResource(R.string.player_list_error)
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerListSection(
    players: List<Player>,
    endReached: Boolean,
    isLoading: Boolean,
    isError: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    onPlayerClick: (Player) -> Unit,
    onLoadNextItems: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection), // used to make the top bar hide when scrolling
        contentPadding = PaddingValues(MaterialTheme.spacing.medium)
    ) {
        items(players.size) { index ->
            val player = players[index]
            PlayerItem(player = player, onClick = onPlayerClick)
            if (index != players.size - 1) {
                HorizontalDivider()
            }

            // check whether to load next items
            val shouldLoadNextItems =
                (index >= players.size - 1)
                        && !endReached && !isLoading && !isError

            LaunchedEffect(shouldLoadNextItems) {
                if (shouldLoadNextItems) {
                    onLoadNextItems()
                }
            }
        }

        item {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    LoadingIndicator(
                        modifier = Modifier
                            .padding(top = MaterialTheme.spacing.medium)
                            .align(Alignment.Center)
                    )
                }
            }
        }

        item {
            if (isError) {
                ErrorSection(
                    modifier = Modifier
                        .padding(top = MaterialTheme.spacing.medium)
                        .fillMaxWidth(),
                    label = stringResource(R.string.player_list_error),
                    onReload = onLoadNextItems
                )
            }
        }

        item {
            Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.large))
        }
    }
}


