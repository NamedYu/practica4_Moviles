package cn.yu.practica4

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Home(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
){
    val context = LocalContext.current
    var puntaje by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            puntaje = false
            onBack()
        }) {
            Text(
                text = "Empezar Juego"
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Button(onClick = { puntaje = true }) {
            Text(
                text = "Ver Puntajes"
            )
        }
    }

    if(puntaje){
        HasDialog(
            title = "Puntajes",
            description = readScoresFromFile(context = context),
            textP = "Cerrar",
            funP = { puntaje = false },
            textN = "Limpiar Puntajes",
            funN = {
                clearFile(context = context)
                puntaje = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home(onBack = { /* 处理返回操作 */ })
}