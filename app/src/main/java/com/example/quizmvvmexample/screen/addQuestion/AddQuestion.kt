package com.example.quizmvvmexample.screen.addQuestion


import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.example.quizmvvmexample.R
import com.example.quizmvvmexample.databinding.FragmentAddQuestionBinding
import com.example.quizmvvmexample.databinding.QuizFragmentBinding
import com.example.quizmvvmexample.screen.quiz.QuizViewModel

/**
 * A simple [Fragment] subclass.
 */
class AddQuestion : Fragment() {

    private lateinit var addQuestionViewModel: AddQuestionViewModel
    private lateinit var addQuestionFragmentBinding: FragmentAddQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addQuestionViewModel = ViewModelProviders.of(this).get(AddQuestionViewModel::class.java)

        addQuestionFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_question,
            container,
            false
        )

        addQuestionFragmentBinding.addQuestionViewModel = addQuestionViewModel


        addQuestionFragmentBinding.addButton.setOnClickListener {
            view?.hideKeyboard()

            if (addQuestionFragmentBinding.question.text.isEmpty() ||
                addQuestionFragmentBinding.answerAText.text.isEmpty() ||
                addQuestionFragmentBinding.answerBText.text.isEmpty() ||
                addQuestionFragmentBinding.answerCText.text.isEmpty() ||
                addQuestionFragmentBinding.answerDText.text.isEmpty() ||
                addQuestionFragmentBinding.groupAnswers.checkedRadioButtonId == -1
            )
                Toast.makeText(inflater.context, "Pola muszą być wypełnione!", Toast.LENGTH_LONG).show()
            else {
                var answer = -1
                when (addQuestionFragmentBinding.groupAnswers.checkedRadioButtonId) {
                    addQuestionFragmentBinding.answerA.id -> answer = 0
                    addQuestionFragmentBinding.answerB.id -> answer = 1
                    addQuestionFragmentBinding.answerC.id -> answer = 2
                    addQuestionFragmentBinding.answerD.id -> answer = 3
                }
                addQuestionViewModel.answer = answer

                addQuestionViewModel.addQuestion()
                Toast.makeText(context, "Pytanie zostało dodane!", Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    activity?.onBackPressed()
                }, 1000)
            }


        }

        addQuestionFragmentBinding.lifecycleOwner = this
        return addQuestionFragmentBinding.root
    }

    override fun onResume() {
        super.onResume()
        addQuestionViewModel.reset()
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}
