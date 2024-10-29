package com.github.topnax.nbadatabasemobile.presentation.screen.player.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.github.topnax.nbadatabasemobile.R
import com.github.topnax.nbadatabasemobile.data.Player
import com.github.topnax.nbadatabasemobile.presentation.navigation.TeamDetailDestination
import com.github.topnax.nbadatabasemobile.presentation.screen.player.detail.PlayerDetailScreenContract.Event
import com.github.topnax.nbadatabasemobile.presentation.screen.player.detail.PlayerDetailScreenContract.State
import com.github.topnax.nbadatabasemobile.ui.composables.BasicPropertyRow
import com.github.topnax.nbadatabasemobile.ui.composables.ErrorSection
import com.github.topnax.nbadatabasemobile.ui.composables.LoadingIndicator
import com.github.topnax.nbadatabasemobile.ui.composables.SlideInAnimatedVisibility


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerDetailScreenContent(
    navController: NavController,
    state: State,
    onEvent: (Event) -> Unit
) {
    LaunchedEffect(state.player) {
        // reload on recomposition only if player is null
        if (state.player == null) {
            onEvent(Event.ReloadPlayer)
        }
    }
    Scaffold(
        topBar = {
            // Top bar that gradually hides as you scroll
            CenterAlignedTopAppBar(
                title = {
                    Text(text = state.playerFullName, style = MaterialTheme.typography.titleLarge)
                },
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                when {
                    state.isLoading ->
                        LoadingIndicator(modifier = Modifier.align(Alignment.Center))

                    state.isError -> {
                        ErrorSection(
                            modifier = Modifier.fillMaxSize(),
                            onReload = {
                                onEvent(Event.ReloadPlayer)
                            },
                            label = stringResource(R.string.player_detai_error_loading_player)
                        )
                    }
                }
                SlideInAnimatedVisibility(
                    isVisible = !state.isLoading
                ) {
                    if (state.player != null) {
                        PlayerDetail(
                            player = state.player,
                            // setup navigation to team detail screen only if teamPreview is not null
                            onTeamClicked = state.player.teamPreview?.let { teamPreview ->
                                {
                                    navController.navigate(
                                        TeamDetailDestination(
                                            teamId = teamPreview.id,
                                            teamName = teamPreview.name
                                        )
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun PlayerDetail(player: Player, onTeamClicked: (() -> Unit)?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        player.teamPreview?.also {
            BasicPropertyRow(label = stringResource(R.string.team), value = it.name, onClick = onTeamClicked)
        }
        BasicPropertyRow(label = stringResource(R.string.position), value = player.position)
        player.height?.also {
            BasicPropertyRow(label = stringResource(R.string.height), value = it)
        }
        player.weight?.also {
            BasicPropertyRow(label = stringResource(R.string.weight), value = it.toString())
        }
        player.jerseyNumber?.also { BasicPropertyRow(label = stringResource(R.string.jersey_number), value = it) }
        player.college?.also {
            BasicPropertyRow(label = stringResource(R.string.college), value = it)
        }
        player.country?.also { BasicPropertyRow(label = stringResource(R.string.country), value = it) }
        player.draftYear?.also {
            BasicPropertyRow(label = stringResource(R.string.draft_year), value = it.toString())
        }
        player.draftRound?.also {
            BasicPropertyRow(label = stringResource(R.string.draft_round), value = it.toString())
        }
        player.draftNumber?.also {
            BasicPropertyRow(label = stringResource(R.string.draft_number), value = it.toString())
        }


    }

}

