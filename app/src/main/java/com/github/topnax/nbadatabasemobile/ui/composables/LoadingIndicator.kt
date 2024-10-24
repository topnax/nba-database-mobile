package com.github.topnax.nbadatabasemobile.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.topnax.nbadatabasemobile.R
import com.github.topnax.nbadatabasemobile.ui.theme.spacing

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.loading)
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator()
        Spacer(
            modifier = Modifier.height(
                MaterialTheme.spacing.medium
            )
        )
        Text(text = text)
    }
}
