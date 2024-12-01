package cn.yu.practica4

import android.content.Context
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Función para revisar las respuestas
fun checkAnswer(answer:Int,correctAnswerIndex:Int):Boolean{
    return answer==correctAnswerIndex
}

fun writeScoreToFile(context: Context,ronda:Int, correctAnswers:Int, errorAnswers:Int) {
    val fileName = "puntajes.txt"
    val file = File(context.filesDir, fileName)
    val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
    val entry = "-------------------------\n" +
            "Fecha: $date, \nRonda: $ronda, \nRespuestas Correctos: $correctAnswers, \nRespuestas Incorrectos: $errorAnswers\n"

    file.appendText(entry)
}

fun clearFile(context: Context) {
    val fileName = "puntajes.txt"
    val file = File(context.filesDir, fileName)
    file.writeText("")
}


// Función para leer el archivo de puntajes
fun readScoresFromFile(context: Context): String {
    val fileName = "puntajes.txt"
    val file = File(context.filesDir, fileName)
    return if (file.exists()) {
        file.readText()
    } else {
        "No hay puntajes registrados aún."
    }
}



@Composable
fun HasDialog(
    title: String,
    description: String,
    textP: String,
    funP: () -> Unit,
    textN: String,
    funN: () -> Unit
) {

    val scrollState = rememberScrollState()
    AlertDialog(

        onDismissRequest = { },
        title = {
            Text(text = title)
        },
        text = {
            Text(
                text = description,
                modifier = Modifier.verticalScroll(scrollState)
            )
        },
        confirmButton = {
            Button(onClick = {
                funP()
            }) {
                Text(text = textP)
            }
        },
        dismissButton = {
            Button(onClick = {
                funN()
            }) {
                Text(text = textN)
            }
        }
    )
}