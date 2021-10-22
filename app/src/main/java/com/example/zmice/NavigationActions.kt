package com.example.zmice

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zmice.models.Score
import com.example.zmice.ui.views.Home
import com.example.zmice.ui.views.SolashScreen
import com.example.zmice.ui.views.Zmijce
import com.example.zmice.ui.views.settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NavigationActions(private val navController: NavController) {

    fun navigateTo(
        destination: String,
        saveState: Boolean = true,
        launchSingleTop: Boolean = false,
        restoreState: Boolean = false,
        popUp:Boolean = true
    ) {
        navController.navigate(destination) {
            if (popUp) {
                popUpTo(navController.graph.findStartDestination().id) {
                    this.saveState = saveState
                }
            }
            this.launchSingleTop = launchSingleTop
            this.restoreState = restoreState
        }
    }
}

