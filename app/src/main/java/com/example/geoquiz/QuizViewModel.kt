package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
const val IS_ANSWERED_KEY = "IS_ANSWERED_KEY"
const val MAX_CHEAT_COUNT = 3

class QuizViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)
    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    val isCheatedCurrentQuestion: Boolean
        get() = cheatBank[currentIndex]
    var isAnswered: Boolean
        get() = savedStateHandle.get(IS_ANSWERED_KEY) ?: false
        private set(value) = savedStateHandle.set(IS_ANSWERED_KEY, value)

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans,true),
        Question(R.string.question_mideast,false),
        Question(R.string.question_africa,false),
        Question(R.string.question_americas,true),
        Question(R.string.question_asia,true)
    )

    private val cheatBank: MutableList<Boolean> = ArrayList(Collections.nCopies(questionBank.size, false))

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    fun moveToNext(){
        if(isCheater){
            cheatBank[currentIndex] = true
            isCheater = false
        }
        isAnswered = false
        if(currentIndex != questionBank.size-1)
            currentIndex++
    }

    fun answeredQuestion() {
        isAnswered = true
    }
}