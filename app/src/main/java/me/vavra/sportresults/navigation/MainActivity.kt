package me.vavra.sportresults.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.vavra.sportresults.ui.theme.SportResultsTheme
import me.vavra.sportresults.view.NewSportResultScreen
import me.vavra.sportresults.view.SportResultsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportResultsTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "sport-results") {
                    composable("sport-results") {
                        SportResultsScreen(onFabTapped = {
                            navController.navigate("new-sport-result")
                        })
                    }
                    composable("new-sport-result") {
                        NewSportResultScreen(onGoBack = {
                            navController.navigateUp()
                        })
                    }
                }
            }
        }
    }

}