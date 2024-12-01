package cn.yu.practica4

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
fun PreguntasApp(
    modifier : Modifier = Modifier,
    preguntas : List<Pregunta>
){
    PreguntasScreen(preguntaList = preguntas)
}

@Composable
fun PreguntasScreen(
    modifier : Modifier = Modifier,
    preguntaList: List<Pregunta>
){
    var preguntas = preguntaList
    val context = LocalContext.current
    var round by remember { mutableStateOf(1) }
    var answer by remember { mutableStateOf(-1) }
    var preguntaCurrentIndex by remember { mutableStateOf(0) }
    var correctAnswers by remember { mutableStateOf(0) }
    var errorAnswers by remember { mutableStateOf(0) }
    var rondaFinish by remember { mutableStateOf(false) }
    var puntaje by remember { mutableStateOf(false) }
    var preguntaCurrent = preguntas[preguntaCurrentIndex]
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Round: $round",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = preguntaCurrent.text,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(30.dp))
        
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {answer = 0}
        ) {
            Text(
                text = preguntaCurrent.options[0]
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {answer = 1}
        ) {
            Text(
                text = preguntaCurrent.options[1]
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {answer = 2}
        ) {
            Text(
                text = preguntaCurrent.options[2]
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {answer = 3}
        ) {
            Text(
                text = preguntaCurrent.options[3]
            )
        }
    }
    // functions
    fun reiniciar(){
        round++
        answer = -1
        (preguntas as MutableList).shuffle()
        preguntaCurrentIndex = 0
        preguntaCurrent = preguntas[preguntaCurrentIndex]
        correctAnswers = 0
        errorAnswers = 0
        rondaFinish = false
        puntaje = false
    }

    //logics
    if(answer!=-1){
        if(checkAnswer(answer,preguntaCurrent.correctAnswerIndex))
            correctAnswers++
        else
            errorAnswers++
        answer = -1
        preguntaCurrentIndex++
        if(preguntaCurrentIndex<6){
            preguntaCurrent = preguntas[preguntaCurrentIndex]
        }
        else{
            writeScoreToFile(context,round,correctAnswers,errorAnswers)
            rondaFinish = true
        }

    }

    if(puntaje){
        HasDialog(
            title = "Puntajes",
            description = readScoresFromFile(context = context),
            textP = "Reiniciar",
            funP = { reiniciar() },
            textN = "Limpiar Puntajes",
            funN = {
                clearFile(context = context)
                puntaje = false
            }
        )
    }
    else if(rondaFinish){
        HasDialog(
            title = "Terminado la ronda",
            description = "Respuestas correctos: $correctAnswers\n" +
                    "Resupuestas incorrectos: $errorAnswers",
            textP = "Reiniciar",
            funP = { reiniciar() },
            textN = "Ver Puntajes",
            funN = {
                puntaje = true
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreguntasAppPreview() {
    val sampleQuestions = listOf(
        Pregunta(
            text = "¿Qué palabra clave se usa en Kotlin para declarar una variable inmutable?",
            options = listOf("var", "val", "const", "let"),
            correctAnswerIndex = 1
        ),
        Pregunta(
            text = "¿Cómo se representa un rango de 1 a 5 en Kotlin?",
            options = listOf("1..5", "1-5", "range(1, 5)", "[1, 5]"),
            correctAnswerIndex = 0
        )
    )

    PreguntasApp(
        preguntas = sampleQuestions,

    )
}