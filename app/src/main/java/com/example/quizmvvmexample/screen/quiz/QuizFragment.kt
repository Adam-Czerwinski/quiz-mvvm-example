package com.example.quizmvvmexample.screen.quiz


import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.quizmvvmexample.R
import com.example.quizmvvmexample.databinding.QuizFragmentBinding
import com.example.quizmvvmexample.screen.quiz.finish.QuizFinishFragmentArgs

/**
 * A simple [Fragment] subclass.
 */
class QuizFragment : Fragment() {

    private lateinit var quizViewModel: QuizViewModel
    private lateinit var quizFragmentBinding: QuizFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args = QuizFragmentArgs.fromBundle(arguments!!)
        quizViewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)

        quizFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.quiz_fragment,
            container,
            false
        )

        quizFragmentBinding.quizViewModel = quizViewModel

        quizViewModel.lastQuestion.observe(this, Observer {
            if (it) {
                quizFragmentBinding.buttonNextQuestion.visibility = View.GONE
                quizFragmentBinding.buttonFinish.visibility = View.VISIBLE
            } else {
                quizFragmentBinding.buttonFinish.visibility = View.GONE
                quizFragmentBinding.buttonNextQuestion.visibility = View.VISIBLE
            }

        })

        quizFragmentBinding.radioGroupAnswers.setOnCheckedChangeListener { group, checkedId ->

                when (checkedId) {
                    quizFragmentBinding.questionAnswerA.id -> quizViewModel.currentAnswer = 0
                    quizFragmentBinding.questionAnswerB.id -> quizViewModel.currentAnswer = 1
                    quizFragmentBinding.questionAnswerC.id -> quizViewModel.currentAnswer = 2
                    quizFragmentBinding.questionAnswerD.id -> quizViewModel.currentAnswer = 3
                }
        }

        quizFragmentBinding.buttonFinish.setOnClickListener {
            val score = quizViewModel.finishQuiz()
            it.findNavController().navigate(

                QuizFragmentDirections.actionQuizFragmentToQuizFinishFragment(
                    score,
                    quizViewModel.numberOfQuestions.value!!,
                    args.personName
                )
            )
        }

        quizFragmentBinding.buttonNextQuestion.setOnClickListener{
            quizViewModel.nextQuestion()
            quizFragmentBinding.radioGroupAnswers.clearCheck()
        }

        quizFragmentBinding.lifecycleOwner = this
        return quizFragmentBinding.root
    }


}

