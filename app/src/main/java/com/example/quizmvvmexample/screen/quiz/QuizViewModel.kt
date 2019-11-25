package com.example.quizmvvmexample.screen.quiz

import android.app.Application
import androidx.lifecycle.*
import com.example.quizmvvmexample.data.dao.Question
import com.example.quizmvvmexample.data.SQLiteOpenHelperImpl

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private var sql: SQLiteOpenHelperImpl = SQLiteOpenHelperImpl(getApplication<Application>().applicationContext)

    private var _lastQuestion = MutableLiveData<Boolean>()
    val lastQuestion: LiveData<Boolean>
        get() = _lastQuestion

    private var _currentQuestion = MutableLiveData<Question>()
    val currentQuestion: LiveData<Question>
        get() = _currentQuestion

    private var _currentIndex = MutableLiveData<Int>()
    val currentIndex: LiveData<Int>
        get() = _currentIndex

    private var _numberOfQuestions = MutableLiveData<Int>()
    val numberOfQuestions: LiveData<Int>
        get() = _numberOfQuestions

    var currentAnswer: Byte = -1
    private var score = 0
    private lateinit var questions: List<Question>

    init {
        initializeQuiz()
    }

    fun nextQuestion() {
        checkAnswer()

        if (!_lastQuestion.value!!) {
            _currentIndex.value = _currentIndex.value!!.plus(1)
            changeQuestion()
        }

        if (_currentIndex.value == _numberOfQuestions.value?.minus(1))
            _lastQuestion.value = true

    }

    private fun checkAnswer() {
        if (currentAnswer == _currentQuestion.value!!.correctAnswer)
            score++

        currentAnswer = -1
    }

    fun finishQuiz():Int {
        checkAnswer()

        val scoreToReturn = score

        resetQuiz()

        return scoreToReturn
    }

    private fun resetQuiz() {
        _currentIndex.value = 0
        _lastQuestion.value = questions.size == 1

        score = 0

        _numberOfQuestions.value = questions.size
        _currentQuestion.value = questions[0]
    }

    private fun changeQuestion() {
        _currentQuestion.value = questions[_currentIndex.value!!]
    }

    private fun initializeQuiz() {
        questions = sql.allQuestions
        resetQuiz()
    }
}