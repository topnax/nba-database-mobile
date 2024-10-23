package com.github.topnax.nbadatabasemobile.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.github.topnax.nbadatabasemobile.presentation.screen.player.detail.PlayerDetailScreenContent
import com.github.topnax.nbadatabasemobile.presentation.screen.player.detail.PlayerDetailScreenViewModel
import com.github.topnax.nbadatabasemobile.presentation.screen.teamdetail.TeamDetailScreenContent
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.viewmodel.resolveViewModel

@Composable
fun NbaDatabaseNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = PlayersListDestination
    ) {

        composable<PlayersListDestination> {
            PlayersListScreen(navController = navController)
        }
        composable<PlayerDetailDestination> {
            PlayerDetailScreen(
                playerId = it.toRoute<PlayerDetailDestination>().playerId
            )
        }
        composable<TeamDetailDestination> {
            TeamDetailScreen()
        }
    }
}

// Screens definitions:

@Serializable
object PlayersListDestination

@Composable
fun PlayersListScreen(navController: NavController) {
    // PlayersListScreenContent()
    Column {
        Text("Test 1")
        Button(
            onClick = {
                navController.navigate(PlayerDetailDestination(
                    playerId = "1"
                ))
            }
        ) {
            Text("Go to Detail")
        }
    }
}


@Serializable
data class PlayerDetailDestination(
    val playerId: String
)

@Composable
fun PlayerDetailScreen(playerId: String) {
    val viewModel: PlayerDetailScreenViewModel = koinViewModel() { parametersOf(playerId) }
    PlayerDetailScreenContent(
        state = viewModel.state.value,
        onEvent = viewModel::onEvent
    )
}


@Serializable
object TeamDetailDestination

@Composable
fun TeamDetailScreen() {
    TeamDetailScreenContent()
}
