package me.vavra.sportresults

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.vavra.sportresults.ui.NewSportResult
import me.vavra.sportresults.ui.SportResults
import me.vavra.sportresults.ui.theme.SportResultsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportResultsTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "sport-results") {
                    composable("sport-results") {
                        SportResults(onFabTapped = {
                            navController.navigate("new-sport-result")
                        })
                    }
                    composable("new-sport-result") {
                        NewSportResult(onUpTapped = {
                            navController.navigateUp()
                        })
                    }
                }
            }
        }
    }

}