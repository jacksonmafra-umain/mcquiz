package com.mcdonalds.mcquizapp.domain.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizData(
    @SerialName("quiz")
    val quiz: List<Quiz?>? = null
) {
    @Serializable
    data class Quiz(
        @SerialName("answer")
        val answer: String? = null,
        @SerialName("options")
        val options: List<String?>? = null,
        @SerialName("question")
        val question: String? = null
    )
}