package com.example.jetbankapp.ui.navigation

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.SPLASH_SCREEN.name
    ) {
        composable(
            route = AppScreen.SPLASH_SCREEN.name
        ) {
            SplashScreen(navController = navController)
        }
        composable(
            route = AppScreen.HOME_SCREEN.name,
            enterTransition = {
                // https://developer.android.com/develop/ui/compose/animation/customize
                slideInHorizontally(
                    initialOffsetX = { -1000 },
                    animationSpec = tween(500, easing = LinearOutSlowInEasing)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                )
            }
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = "${AppScreen.DETAIL_SCREEN.name}/{bankDataJson}",
            arguments = listOf(navArgument("bankDataJson") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val bankDataJson = backStackEntry.arguments?.getString("bankDataJson")
            if (bankDataJson != null) {
                DetailScreen(bankDataJson)
            }
        }
    }
}