package cn.yu.practica4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cn.yu.practica4.ui.theme.Practica4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var currentScreen by remember { mutableStateOf("home") }

            // 使用 DataProvider 加载问题
            val dataProvider = DataProvider(this)
            val preguntas = dataProvider.preguntas.shuffled()

            Practica4Theme {
                when (currentScreen) {
                    "home" -> Home(
                        onBack = { currentScreen = "preguntas" }
                    )
                    "preguntas" -> PreguntasApp(
                        preguntas = preguntas,
                    )
                }
            }
        }
    }

}

