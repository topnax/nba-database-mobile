package com.github.topnax.nbadatabasemobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.topnax.nbadatabasemobile.presentation.navigation.NbaDatabaseNavigation
import com.github.topnax.nbadatabasemobile.ui.theme.NBADatabaseMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NBADatabaseMobileTheme {
                NbaDatabaseNavigation()
            }
        }
    }
}