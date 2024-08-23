package com.example.lab07

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab07.ui.theme.Lab07Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab07Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    VisualProgram(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


// Define una lista de ejemplo para correos electrónicos
val emails = listOf(
    EmailData("Reunión", "Detalles de la reunión de mañana", "10:00 AM"),
    EmailData("Oferta", "Nueva oferta de trabajo", "9:30 AM"),
    EmailData("Evento", "Invitación a evento", "8:45 AM"),
    EmailData("Tarea Pendiente","Por favor entregar la tarea que hace falta","8:45 AM")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualProgram(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notificaciones") },
                navigationIcon = {
                    IconButton(onClick = { /* Acción de navegación */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Green // Color de fondo para TopAppBar
                )
            )
        },

        content = { padding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE9F0C1))
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(text = "Tipo de notificaciones")

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val selectedInformativas = remember { mutableStateOf(false) }
                        val selectedCapacitaciones = remember { mutableStateOf(false) }

                        FilterChip(
                            selected = selectedInformativas.value,
                            onClick = { selectedInformativas.value = !selectedInformativas.value },
                            label = { Text("Informativas", fontWeight = FontWeight.Bold) },
                            shape = RoundedCornerShape(16.dp)

                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        FilterChip(
                            selected = selectedCapacitaciones.value,
                            onClick = { selectedCapacitaciones.value = !selectedCapacitaciones.value },
                            label = { Text("Capacitaciones", fontWeight = FontWeight.Bold) },
                            shape = RoundedCornerShape(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    EmailList(emails = emails)
                }
            }
        }
    )
}

@Composable
fun EmailList(emails: List<EmailData>) {
    LazyColumn(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp)) // Esquinas redondeadas
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))


    ) {
        items(emails.size) { index ->
            val email = emails[index]
            EmailCard(
                icon = {
                    Icon(
                        Icons.Filled.Email,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = Color.Blue
                    )
                },
                title = email.title,
                subject = email.subject,
                timestamp = email.timestamp
            )
        }
    }
}

@Composable
fun EmailCard(
    icon: @Composable () -> Unit,
    title: String,
    subject: String,
    timestamp: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        //elevation = CardDefaults.cardElevation(4.dp),

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.Red,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Row {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = timestamp,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.End
                    )
                }
                Text(
                    text = subject,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

data class EmailData(val title: String, val subject: String, val timestamp: String)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab07Theme {
        VisualProgram()
    }
}