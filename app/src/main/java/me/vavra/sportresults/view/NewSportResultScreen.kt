@file:OptIn(ExperimentalMaterial3Api::class)

package me.vavra.sportresults.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.vavra.sportresults.model.NewSportResultState
import me.vavra.sportresults.model.SavingState
import me.vavra.sportresults.model.SportResult.Companion.EMPTY_DURATION
import me.vavra.sportresults.ui.theme.SportResultsTheme
import me.vavra.sportresults.viewmodel.NewSportResultViewModel

@Composable
fun NewSportResultScreen(viewModel: NewSportResultViewModel = viewModel(), onGoBack: () -> Unit) {
    Scaffold(
        topBar = {
            SmallTopAppBar(title = { Text(text = "New sport result") }, navigationIcon = {
                IconButton(onClick = onGoBack) {
                    Icon(Icons.Default.ArrowBack, null)
                }
            })
        },
        content = { padding ->
            val state = viewModel.state
            if (state.savingState == null) {
                NewSportResultContent(
                    state,
                    padding,
                    onNameChanged = {
                        viewModel.nameChanged(it)
                    },
                    onPlaceChanged = {
                        viewModel.placeChanged(it)
                    },
                    onDurationChanged = {
                        viewModel.durationChanged(it)
                    },
                    onRemoteChanged = {
                        viewModel.remoteChanged(it)
                    },
                    onSaveTapped = {
                        viewModel.save()
                    })
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (state.savingState) {
                        SavingState.IN_PROGRESS -> CircularProgressIndicator()
                        SavingState.SUCCESS -> onGoBack()
                        SavingState.ERROR -> Text("Something went wrong, try again later.")
                    }
                }
            }
        }
    )
}

@Composable
private fun NewSportResultContent(
    state: NewSportResultState,
    padding: PaddingValues,
    onNameChanged: (String) -> Unit,
    onPlaceChanged: (String) -> Unit,
    onDurationChanged: (String) -> Unit,
    onRemoteChanged: (Boolean) -> Unit,
    onSaveTapped: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 16.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        TextField(
            value = state.sportResult.name,
            onValueChange = onNameChanged,
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = !state.nameValid
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.sportResult.place,
            onValueChange = onPlaceChanged,
            label = { Text("Place") },
            modifier = Modifier.fillMaxWidth(),
            isError = !state.placeValid
        )
        Spacer(modifier = Modifier.height(16.dp))
        val duration =
            if (state.sportResult.durationMinutes == EMPTY_DURATION) "" else state.sportResult.durationMinutes.toString()
        TextField(
            value = duration,
            onValueChange = onDurationChanged,
            label = { Text("Duration") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = { Text("minutes", modifier = Modifier.padding(horizontal = 16.dp)) },
            modifier = Modifier.fillMaxWidth(),
            isError = !state.durationValid
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Online storage",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Switch(checked = state.sportResult.remote, onCheckedChange = onRemoteChanged)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onSaveTapped, modifier = Modifier.fillMaxWidth()) {
            Text("Save")
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun NewSportResultPreview() {
    SportResultsTheme {
        NewSportResultScreen {}
    }
}