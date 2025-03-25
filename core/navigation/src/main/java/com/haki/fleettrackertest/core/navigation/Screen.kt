package com.haki.fleettrackertest.core.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Maps : Screen("maps")
}