package com.example.quizmvvmexample.data.dao

import java.io.Serializable

class Question(val question: String, val answers: List<String>, val correctAnswer: Byte, var id: Int? = null) :
    Serializable