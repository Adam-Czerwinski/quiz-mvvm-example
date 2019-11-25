package com.example.quizmvvmexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.quizmvvmexample.screen.quiz.QuizFragment
import com.example.quizmvvmexample.screen.welcome.WelcomeFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment)
        val x = navHostFragment?.childFragmentManager?.fragments?.get(0)

        if (x is QuizFragment) {
            val alertBuilder = android.app.AlertDialog.Builder(this)

            alertBuilder.setMessage("Czy na pewno chcesz wrócić?")
            alertBuilder.setPositiveButton("Tak") { _, _ ->
                super.onBackPressed()
            }
            alertBuilder.setNegativeButton("Anuluj", null)

            alertBuilder.show()
        } else
            super.onBackPressed()


    }



}
