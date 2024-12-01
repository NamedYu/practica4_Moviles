package cn.yu.practica4

data class Pregunta(
    val text: String,
    val options : List<String>,
    val correctAnswerIndex: Int
)