package me.vavra.sportresults.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import me.vavra.sportresults.network.RemoteRepository
import me.vavra.sportresults.ui.theme.SportResultsTheme
import me.vavra.sportresults.view.NewSportResultScreen
import me.vavra.sportresults.view.SportResultsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportResultsTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = SPORT_RESULTS_DESTINATION) {
                    composable(SPORT_RESULTS_DESTINATION) {
                        SportResultsScreen(onFabTapped = {
                            navController.navigate(NEW_SPORT_RESULT_DESTINATION)
                        })
                    }
                    composable(NEW_SPORT_RESULT_DESTINATION) {
                        NewSportResultScreen(onGoBack = {
                            navController.navigateUp()
                        })
                    }
                }
            }
        }
        if (savedInstanceState == null) {
            // Load data from network only for the first time
            lifecycleScope.launch {
                try {
                    RemoteRepository.load()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        private const val SPORT_RESULTS_DESTINATION = "sport-results"
        private const val NEW_SPORT_RESULT_DESTINATION = "new-sport-result"
    }

}