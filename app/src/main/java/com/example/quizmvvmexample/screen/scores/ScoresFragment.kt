package com.example.quizmvvmexample.screen.scores


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.quizmvvmexample.R
import com.example.quizmvvmexample.data.SQLiteOpenHelperImpl

/**
 * A simple [Fragment] subclass.
 */
class ScoresFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_scores, container, false)
        val rview = rootView.findViewById(R.id.rview) as RecyclerView
        rview.layoutManager = LinearLayoutManager(activity)
        val sql = SQLiteOpenHelperImpl(inflater.context)
        val listOfUserScores = sql.allScores
        rview.adapter = Adapter(sql, listOfUserScores)


        return rootView
    }



}
