@file:OptIn(ExperimentalMaterial3Api::class)

package me.vavra.sportresults.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vavra.sportresults.R
import me.vavra.sportresults.model.SportResult
import me.vavra.sportresults.ui.theme.SportResultsTheme
import kotlin.time.Duration.Companion.minutes

@Composable
fun SportResults(onFabTapped: () -> Unit) {
    Scaffold(
        topBar = {
            SmallTopAppBar(title = { Text(text = "Sport results") })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onFabTapped, icon = {
                Icon(Icons.Default.Add, null)
            }, text = {
                Text("New sport result")
            })
        },
        content = {
            SportResultsContent(it)
        }
    )
}

@Composable
private fun SportResultsContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
    ) {
        StorageFilter()
        SportResultsList(
            listOf(
                SportResult("Tenis", "Praha", 45, true),
                SportResult("Plavání", "Praha", 60, false),
                SportResult("Squash", "Brno", 20, true)
            )
        )
    }
}

@Composable
private fun StorageFilter() {
    Row {
        StorageFilterItem(titleText = "Online", iconRes = R.drawable.ic_cloud_on)
        Spacer(modifier = Modifier.width(8.dp))
        StorageFilterItem(titleText = "Local", iconRes = R.drawable.ic_cloud_off)
    }
}

@Composable
private fun StorageFilterItem(titleText: String, iconRes: Int) {
    var selected by remember { mutableStateOf(true) }
    FilterChip(
        selected = selected,
        onClick = { selected = !selected },
        label = { Text(titleText) },
        trailingIcon = { Icon(painterResource(iconRes), null) }
    )
}

@Composable
private fun SportResultsList(sportResults: List<SportResult>) {
    LazyColumn {
        items(sportResults) { sportResult ->
            SportResultItem(sportResult)
        }
    }
}

@Composable
private fun SportResultItem(sportResult: SportResult) {
    val durationFormatted = sportResult.duration.minutes.toString()
    val secondaryText = durationFormatted + " · " + sportResult.place
    val iconRes = if (sportResult.remote) R.drawable.ic_cloud_on else R.drawable.ic_cloud_off
    ListItem(
        headlineText = { Text(sportResult.name) },
        supportingText = { Text(secondaryText) },
        trailingContent = { Icon(painterResource(iconRes), null) }
    )
}


@Composable
@Preview(showBackground = true)
private fun SportResultsPreview() {
    SportResultsTheme {
        SportResults {}
    }
}