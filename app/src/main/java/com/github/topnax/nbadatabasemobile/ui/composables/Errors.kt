package com.github.topnax.nbadatabasemobile.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.topnax.nbadatabasemobile.R

@Composable
fun ErrorSection(
    modifier: Modifier = Modifier,
    label: String,
    onReload: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label)
        TextButton(onClick = onReload) {
            Text(text = stringResource(R.string.reload))
        }
    }
}
