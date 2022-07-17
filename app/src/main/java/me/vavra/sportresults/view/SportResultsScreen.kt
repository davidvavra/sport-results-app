@file:OptIn(ExperimentalMaterial3Api::class)

package me.vavra.sportresults.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.vavra.sportresults.R
import me.vavra.sportresults.model.SportResult
import me.vavra.sportresults.model.SportResultsState
import me.vavra.sportresults.ui.theme.SportResultsTheme
import me.vavra.sportresults.viewmodel.SportResultsViewModel
import kotlin.time.Duration.Companion.minutes

@Composable
fun SportResultsScreen(viewModel: SportResultsViewModel = hiltViewModel(), onFabTapped: () -> Unit) {
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
        content = { padding ->
            SportResultsContent(
                state = viewModel.state,
                padding = padding,
                onShowRemoteTapped = {
                    viewModel.changeShowRemote()
                }, onShowLocalTapped = {
                    viewModel.changeShowLocal()
                }
            )
        }
    )
}

@Composable
private fun SportResultsContent(
    state: SportResultsState,
    padding: PaddingValues,
    onShowRemoteTapped: () -> Unit,
    onShowLocalTapped: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 16.dp)
    ) {
        StorageFilter(state.showRemote, state.showLocal, onShowRemoteTapped, onShowLocalTapped)
        SportResultsList(state.sportResults)
    }
}

@Composable
private fun StorageFilter(
    showRemote: Boolean,
    showLocal: Boolean,
    onShowRemoteTapped: () -> Unit,
    onShowLocalTapped: () -> Unit
) {
    Row {
        StorageFilterItem(
            selected = showRemote,
            titleText = "Online",
            iconRes = R.drawable.ic_cloud_on,
            onTapped = onShowRemoteTapped
        )
        Spacer(modifier = Modifier.width(8.dp))
        StorageFilterItem(
            selected = showLocal,
            titleText = "Local",
            iconRes = R.drawable.ic_cloud_off,
            onTapped = onShowLocalTapped
        )
    }
}

@Composable
private fun StorageFilterItem(
    selected: Boolean,
    titleText: String,
    iconRes: Int,
    onTapped: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onTapped,
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
    val durationFormatted = sportResult.durationMinutes.minutes.toString()
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
        SportResultsScreen {}
    }
}