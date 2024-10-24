package com.github.topnax.nbadatabasemobile.presentation.screen.teamdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
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
        if (state.team == null) {
            onEvent(Event.ReloadTeam)
        }
    }
    Scaffold(
        topBar = {
            // Top bar that gradually hides as you scroll
            CenterAlignedTopAppBar(
                title = {
                    Text(text = state.teamName, fontSize = 24.sp)
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
            label = "Full Name",
            value = team.fullName
        )
        BasicPropertyRow(
            label = "Abbreviation",
            value = team.abbreviation
        )
        team.conference?.let {
            BasicPropertyRow(
                label = "Conference",
                value = it
            )
        }
        team.division?.let {
            BasicPropertyRow(
                label = "Division",
                value = it
            )
        }
        team.city?.let {
            BasicPropertyRow(
                label = "City",
                value = it
            )
        }
    }
}


