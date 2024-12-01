package cn.yu.practica4


import org.json.JSONArray
import android.content.Context

class DataProvider(context: Context?) {
    var preguntas: List<Pregunta>

    init {
        var jsonString = ""
        if (context != null) {
            jsonString = loadJSONFromAsset(context, "kotlin_questions.json")
        }

        val jsonArray = JSONArray(jsonString)
        val temp = mutableListOf<Pregunta>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val text = jsonObject.getString("text")
            val optionsArray = jsonObject.getJSONArray("options")
            val correctAnswerIndex = jsonObject.getInt("correctAnswerIndex")

            val options = mutableListOf<String>()
            for (k in 0 until optionsArray.length()) {
                options.add(optionsArray.getString(k))
            }

            val question = Pregunta(
                text = text,
                options = options,
                correctAnswerIndex = correctAnswerIndex
            )
            temp.add(question)
        }

        preguntas = temp
    }

    private fun loadJSONFromAsset(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun shuffle() {
        preguntas = preguntas.shuffled()
    }
}
