package com.example.lab06

import android.os.Bundle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.HorizontalDivider
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.lab06.ui.theme.Lab06Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab06Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AdvancedCounter(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// asd@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvancedCounter(modifier: Modifier = Modifier) {
    var contador by remember { mutableIntStateOf(0) }
    var totalIncrementos by remember { mutableIntStateOf(0) }
    var totalDecrementos by remember { mutableIntStateOf(0) }
    var valorMaximo by remember { mutableIntStateOf(0) }
    var valorMinimo by remember { mutableIntStateOf(0) }
    var totalCambios by remember { mutableIntStateOf(0) }
    var historial = remember { mutableStateListOf<Pair<Int, Boolean>>() }

    Column(modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sebastian Garcia Bustamante",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Row(
                modifier = modifier
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        contador--
                        totalDecrementos++
                        totalCambios++
                        if (contador < valorMinimo) valorMinimo = contador
                        historial.add(Pair(contador, false))
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                }

                Text(
                    text = "$contador",
                    style = MaterialTheme.typography.displayLarge
                )

                IconButton(
                    onClick = {
                        contador++
                        totalIncrementos++
                        totalCambios++
                        if (contador > valorMaximo) valorMaximo = contador
                        historial.add(Pair(contador, true))
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            HorizontalDivider()
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Total incrementos: ")
                Text(text = "$totalIncrementos")
            }
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Total decrementos: ")
                Text(text = "$totalDecrementos")
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Valor máximo: ")
                Text(text = "$valorMaximo")
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Valor mínimo: ")
                Text(text = "$valorMinimo")
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Total cambios: ")
                Text(text = "$totalCambios")
            }
            Row {
                Text(text = "Historial: ")
            }
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                Button(
                    onClick = {
                        contador = 0
                        totalIncrementos = 0
                        totalDecrementos = 0
                        valorMaximo = 0
                        valorMinimo = 0
                        totalCambios = 0
                        historial.clear()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Reiniciar")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    items(historial.chunked(5)) { chunk ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            chunk.forEach { (numero, esIncremento) ->
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .padding(4.dp)
                                        .background(
                                            color = if (esIncremento) Color.Green else Color.Red,
                                            shape = RoundedCornerShape(16.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = numero.toString(),
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AdvancedCounterPreview() {
    Lab06Theme {
        AdvancedCounter()
    }
}
