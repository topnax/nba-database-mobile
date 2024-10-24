package com.github.topnax.nbadatabasemobile.ui.composables.player

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.topnax.nbadatabasemobile.data.Player

@Composable
fun PlayerItem(modifier: Modifier = Modifier, player: Player, onClick: ((Player) -> Unit)? = null) {

    ListItem(
        modifier = modifier
            .then(
                onClick?.let { Modifier.clickable { it(player) } } ?: Modifier
            ),
        headlineContent = { Text(text = player.fullName) },
        supportingContent = {
            Text(text = player.position.takeIf { it.isNotBlank() } ?: "---")
        },
        leadingContent = { Text(text = player.teamPreview?.abbreviation ?: "N/A") },
    )
}
