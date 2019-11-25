package com.example.quizmvvmexample.screen.quiz.finish


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.quizmvvmexample.R
import com.example.quizmvvmexample.data.dao.PersonScore
import com.example.quizmvvmexample.data.SQLiteOpenHelperImpl
import com.example.quizmvvmexample.databinding.QuizFinishFragmentBinding

/**
 * A simple [Fragment] subclass.
 */
class QuizFinishFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<QuizFinishFragmentBinding>(
            inflater,
            R.layout.quiz_finish_fragment,
            container,
            false
        )

        binding.buttonFinish.setOnClickListener{
            activity?.onBackPressed()
        }

        val args = QuizFinishFragmentArgs.fromBundle(arguments!!)

        binding.score.text = args.correctAnswers.toString()

        val sql = SQLiteOpenHelperImpl(inflater.context)

        val personScore =
            PersonScore(args.personName, args.correctAnswers)
        sql.addScore(personScore)

        return binding.root
    }


}
