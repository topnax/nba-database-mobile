package com.github.topnax.nbadatabasemobile.presentation.screen.player.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.topnax.nbadatabasemobile.data.Player
import com.github.topnax.nbadatabasemobile.presentation.screen.player.detail.PlayerDetailScreenContract.*

@Composable
fun PlayerDetailScreenContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    Column {
        Text("Player Detail Screen")
        Text("Player ID: ${state.playerId}")
    }
}