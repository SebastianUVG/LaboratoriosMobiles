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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab07.ui.theme.Lab07Theme
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualProgram(modifier: Modifier = Modifier) {
    var selectedNotificationType by remember { mutableStateOf<NotificationType?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notificaciones", color = Color.Black) }, // Color fijo para el título
                navigationIcon = {
                    IconButton(onClick = { /* Acción de navegación */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Green
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
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Tipo de notificaciones", color = Color.Black) // Color fijo para el texto

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = selectedNotificationType == NotificationType.GENERAL,
                                onClick = {
                                    selectedNotificationType =
                                        if (selectedNotificationType == NotificationType.GENERAL) null
                                        else NotificationType.GENERAL
                                },
                                label = { Text("General", fontWeight = FontWeight.Bold, color = Color.Black) } // Color fijo
                            )
                        }
                        item {
                            FilterChip(
                                selected = selectedNotificationType == NotificationType.NEW_POST,
                                onClick = {
                                    selectedNotificationType =
                                        if (selectedNotificationType == NotificationType.NEW_POST) null
                                        else NotificationType.NEW_POST
                                },
                                label = { Text("New Post", fontWeight = FontWeight.Bold, color = Color.Black) } // Color fijo
                            )
                        }
                        item {
                            FilterChip(
                                selected = selectedNotificationType == NotificationType.NEW_MESSAGE,
                                onClick = {
                                    selectedNotificationType =
                                        if (selectedNotificationType == NotificationType.NEW_MESSAGE) null
                                        else NotificationType.NEW_MESSAGE
                                },
                                label = { Text("New Message", fontWeight = FontWeight.Bold, color = Color.Black) } // Color fijo
                            )
                        }
                        item {
                            FilterChip(
                                selected = selectedNotificationType == NotificationType.NEW_LIKE,
                                onClick = {
                                    selectedNotificationType =
                                        if (selectedNotificationType == NotificationType.NEW_LIKE) null
                                        else NotificationType.NEW_LIKE
                                },
                                label = { Text("New Like", fontWeight = FontWeight.Bold, color = Color.Black) } // Color fijo
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    val notifications = generateFakeNotifications()
                    val filteredNotifications = selectedNotificationType?.let { type ->
                        notifications.filter { it.type == type }
                    } ?: notifications

                    NotificationList(notifications = filteredNotifications)
                }
            }
        }
    )
}

@Composable
fun NotificationList(notifications: List<Notification>) {
    LazyColumn(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp)) // Esquinas redondeadas
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
    ) {
        items(notifications.size) { index ->
            val notification = notifications[index]
            NotificationCard(
                notification = notification,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp) // Espaciado entre las tarjetas
            )
        }
    }
}
@Composable
fun getIconColor(): Color {
    val isLightTheme = MaterialTheme.colorScheme.background == Color.White
    return if (isLightTheme) Color.Black else Color.White
}

@Composable
fun NotificationCard(
    notification: Notification,
    modifier: Modifier = Modifier
) {
    // Determina el color del ícono
    val iconColor = getIconColor()
    // Define el color de fondo basado en el tipo de notificación
    val backgroundColor = when (notification.type) {
        NotificationType.GENERAL -> Color.LightGray
        NotificationType.NEW_POST -> Color.Blue
        NotificationType.NEW_MESSAGE -> Color.Green
        NotificationType.NEW_LIKE -> Color.Red
    }

    val icon = when (notification.type) {
        NotificationType.GENERAL -> Icons.Filled.Email
        NotificationType.NEW_POST -> Icons.Filled.PostAdd
        NotificationType.NEW_MESSAGE -> Icons.Filled.Message
        NotificationType.NEW_LIKE -> Icons.Filled.Favorite
    }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = backgroundColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = iconColor
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Row {
                    Text(
                        text = notification.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black // Color fijo para el texto
                    )
                    Text(
                        text = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault()).format(notification.sendAt),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray, // Color fijo para la fecha
                        textAlign = TextAlign.End
                    )
                }
                Text(
                    text = notification.body,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black // Color fijo para el texto
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab07Theme {
        VisualProgram()
    }
}
