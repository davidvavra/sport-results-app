@file:OptIn(ExperimentalMaterial3Api::class)

package me.vavra.sportresults.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vavra.sportresults.ui.theme.SportResultsTheme

@Composable
fun NewSportResult(onUpTapped: () -> Unit) {
    Scaffold(
        topBar = {
            SmallTopAppBar(title = { Text(text = "New sport result") }, navigationIcon = {
                IconButton(onClick = onUpTapped) {
                    Icon(Icons.Default.ArrowBack, null)
                }
            })
        },
        content = { NewSportResultContent(it) }
    )
}

@Composable
private fun NewSportResultContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Place") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Duration") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = { Text("minutes", modifier = Modifier.padding(horizontal = 16.dp)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        var checked by remember { mutableStateOf(true) }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Online storage",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Switch(checked = checked, onCheckedChange = { checked = !checked })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
            Text("Save")
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun NewSportResultPreview() {
    SportResultsTheme {
        NewSportResult {}
    }
}