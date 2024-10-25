package com.github.topnax.nbadatabasemobile.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.github.topnax.nbadatabasemobile.presentation.screen.player.detail.PlayerDetailScreenContent
import com.github.topnax.nbadatabasemobile.presentation.screen.player.detail.PlayerDetailScreenViewModel
import com.github.topnax.nbadatabasemobile.presentation.screen.player.list.PlayersListScreenContent
import com.github.topnax.nbadatabasemobile.presentation.screen.player.list.PlayersListViewScreenModel
import com.github.topnax.nbadatabasemobile.presentation.screen.teamdetail.TeamDetailScreenContent
import com.github.topnax.nbadatabasemobile.presentation.screen.teamdetail.TeamDetailScreenViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NbaDatabaseNavigation() {
    val navController = rememberNavController()

    // Type-safe routing
    NavHost(
        navController = navController,
        startDestination = PlayersListDestination
    ) {
        composable<PlayersListDestination> {
            PlayersListScreen(navController = navController)
        }
        composable<PlayerDetailDestination> {
            PlayerDetailScreen(
                navController = navController,
                playerId = it.toRoute<PlayerDetailDestination>().playerId,
                playerFullName = it.toRoute<PlayerDetailDestination>().playerFullName
            )
        }
        composable<TeamDetailDestination> {
            TeamDetailScreen(
                teamId = it.toRoute<TeamDetailDestination>().teamId,
                teamName = it.toRoute<TeamDetailDestination>().teamName
            )
        }
    }
}

// Screens definitions:

@Serializable
object PlayersListDestination

@Composable
fun PlayersListScreen(navController: NavController) {

    val viewModel: PlayersListViewScreenModel = koinViewModel()
    PlayersListScreenContent(
        state = viewModel.state.value,
        onEvent = viewModel::onEvent,
        navController = navController
    )
}


@Serializable
data class PlayerDetailDestination(
    val playerId: String,
    val playerFullName: String
)

@Composable
fun PlayerDetailScreen(navController: NavController, playerId: String, playerFullName: String) {
    val viewModel: PlayerDetailScreenViewModel =
        koinViewModel() { parametersOf(playerId, playerFullName) }
    PlayerDetailScreenContent(
        state = viewModel.state.value,
        onEvent = viewModel::onEvent,
        navController = navController
    )
}


@Serializable
data class TeamDetailDestination(
    val teamId: String,
    val teamName: String
)

@Composable
fun TeamDetailScreen(teamId: String, teamName: String) {
    val viewModel = koinViewModel<TeamDetailScreenViewModel>(
    ) {
        parametersOf(
            teamId,
            teamName
        )
    }
    TeamDetailScreenContent(
        state = viewModel.state.value,
        onEvent = viewModel::onEvent
    )
}
