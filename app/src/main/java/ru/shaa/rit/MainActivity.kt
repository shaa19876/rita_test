package ru.shaa.rit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.shaa.main.presentation.MainScreen
import ru.shaa.main.presentation.MainViewModel
import ru.shaa.rit.ui.theme.RitTheme
import ru.shaa.settings.presentation.SettingsScreen
import ru.shaa.settings.presentation.SettingsViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            RitTheme {
                NavHost(navController = navController, startDestination = Routes.MainScreen.route) {
                    composable(Routes.MainScreen.route) {
                        MainScreen(viewModel = mainViewModel) { navController.navigate(Routes.SettingsScreen.route) }
                    }
                    composable(Routes.SettingsScreen.route) {
                        SettingsScreen(viewModel = settingsViewModel) {
                            navController.navigate(
                                Routes.MainScreen.route
                            ) { popUpTo(Routes.MainScreen.route) { inclusive = true } }
                        }
                    }
                }
            }
        }
    }

    sealed class Routes(val route: String) {
        data object MainScreen : Routes("main")
        data object SettingsScreen : Routes("settings")
    }
}
