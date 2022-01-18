package com.example.zmice.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination


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

