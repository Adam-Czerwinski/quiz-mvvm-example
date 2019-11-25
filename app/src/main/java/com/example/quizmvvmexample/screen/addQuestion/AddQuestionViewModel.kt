package com.example.quizmvvmexample.screen.addQuestion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.quizmvvmexample.data.SQLiteOpenHelperImpl
import com.example.quizmvvmexample.data.dao.Question

class AddQuestionViewModel(application: Application) : AndroidViewModel(application) {

    private var sql: SQLiteOpenHelperImpl =
        SQLiteOpenHelperImpl(getApplication<Application>().applicationContext)

    var question = MutableLiveData<String>()

    var answerA = MutableLiveData<String>()
    var answerB = MutableLiveData<String>()
    var answerC = MutableLiveData<String>()
    var answerD = MutableLiveData<String>()

    var answer: Int = 0

    fun addQuestion() {
        val q: Question = Question(
            question.value!!,
            listOf(answerA.value!!, answerB.value!!, answerC.value!!, answerD.value!!),
            answer.toByte()
        )
        sql.addQuestion(q)
    }

    fun reset() {
        question.value = ""
        answerA.value = ""
        answerB.value = ""
        answerC.value = ""
        answerD.value = ""
        answer = -1
    }
}