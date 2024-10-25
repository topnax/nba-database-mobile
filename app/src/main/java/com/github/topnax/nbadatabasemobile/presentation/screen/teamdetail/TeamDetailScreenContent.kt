package com.github.topnax.nbadatabasemobile.presentation.screen.teamdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.github.topnax.nbadatabasemobile.R
import com.github.topnax.nbadatabasemobile.data.Team
import com.github.topnax.nbadatabasemobile.presentation.screen.teamdetail.TeamDetailScreenContract.Event
import com.github.topnax.nbadatabasemobile.presentation.screen.teamdetail.TeamDetailScreenContract.State
import com.github.topnax.nbadatabasemobile.ui.composables.BasicPropertyRow
import com.github.topnax.nbadatabasemobile.ui.composables.ErrorSection
import com.github.topnax.nbadatabasemobile.ui.composables.LoadingIndicator
import com.github.topnax.nbadatabasemobile.ui.composables.SlideInAnimatedVisibility


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDetailScreenContent(
    state: State,
    onEvent: (Event) -> Unit
) {

    LaunchedEffect(state.team) {
        // reload on recomposition only if team is null
        if (state.team == null) {
            onEvent(Event.ReloadTeam)
        }
    }
    Scaffold(
        topBar = {
            // Top bar that gradually hides as you scroll
            CenterAlignedTopAppBar(
                title = {
                    Text(text = state.teamName, style = MaterialTheme.typography.titleLarge)
                },
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                when {
                    state.isLoading ->
                        LoadingIndicator(modifier = Modifier.align(Alignment.Center))

                    state.isError -> {
                        ErrorSection(
                            modifier = Modifier.fillMaxSize(),
                            onReload = {
                                onEvent(Event.ReloadTeam)
                            },
                            label = stringResource(R.string.team_detail_error)
                        )
                    }
                }
                SlideInAnimatedVisibility(
                    isVisible = !state.isLoading
                ) {
                    if (state.team != null) {
                        TeamDetail(
                            team = state.team
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun TeamDetail(team: Team) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        BasicPropertyRow(
            label = stringResource(R.string.full_name),
            value = team.fullName
        )
        BasicPropertyRow(
            label = stringResource(R.string.abbreviation),
            value = team.abbreviation
        )
        team.conference?.let {
            BasicPropertyRow(
                label = stringResource(R.string.conference),
                value = it
            )
        }
        team.division?.let {
            BasicPropertyRow(
                label = stringResource(R.string.division),
                value = it
            )
        }
        team.city?.let {
            BasicPropertyRow(
                label = stringResource(R.string.city),
                value = it
            )
        }
    }
}
