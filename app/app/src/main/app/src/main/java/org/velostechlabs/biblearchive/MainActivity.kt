
package org.velostechlabs.biblearchive

import androidx.compose.material3.ExperimentalMaterial3Api
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                BibleArchiveApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibleArchiveApp() {
    var tab by remember { mutableIntStateOf(0) }
    var character by remember { mutableStateOf("Jesus Christ") }
    var engine by remember { mutableStateOf("THEO") }
    var question by remember { mutableStateOf("") }
    var answer by remember {
        mutableStateOf("Welcome to BIBLE ARCHIVE V10.")
    }

    val engines = listOf(
        "THEO", "EXEGETE", "HEBRA", "HELLEN",
        "HISTOR", "CRITIC", "VERITAS"
    )

    val characters = listOf(
        "Jesus Christ", "Moses", "Abraham", "David",
        "Paul", "Peter", "Mary Magdalene", "Isaiah",
        "Jeremiah", "Daniel", "Esther", "Solomon"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📖 BIBLE ARCHIVE V10") }
            )
        },
        bottomBar = {
            NavigationBar {
                listOf("Chat", "Characters", "Research").forEachIndexed {
                    index, label ->
                    NavigationBarItem(
                        selected = tab == index,
                        onClick = { tab = index },
                        icon = {},
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            when (tab) {
                0 -> {
                    Text("Active character: $character")
                    Spacer(Modifier.height(12.dp))

                    Text("Cognitive workflow")
                    var expanded by remember { mutableStateOf(false) }

                    Box {
                        Button(onClick = { expanded = true }) {
                            Text(engine)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            engines.forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        engine = it
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = question,
                        onValueChange = { question = it },
                        label = { Text("Ask a Bible question") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )

                    Button(
                        onClick = {
                            answer = "Backend connection is not configured yet."
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Send Query")
                    }

                    Spacer(Modifier.height(16.dp))
                    Text("Response", style = MaterialTheme.typography.titleMedium)
                    Text(answer)
                }

                1 -> {
                    Text(
                        "Character Explorer",
                        style = MaterialTheme.typography.titleLarge
                    )
                    LazyColumn {
                        items(characters) { name ->
                            Button(
                                onClick = {
                                    character = name
                                    tab = 0
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp)
                            ) {
                                Text(name)
                            }
                        }
                    }
                }

                2 -> {
                    Text(
                        "Research & Memory Vault",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        "Your research workspace for Bible study, " +
                        "historical investigation and saved notes."
                    )
                }
            }
        }
    }
}
