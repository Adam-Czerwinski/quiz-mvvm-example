package com.example.quizmvvmexample.screen.scores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizmvvmexample.R
import com.example.quizmvvmexample.data.dao.PersonScore
import com.example.quizmvvmexample.data.SQLiteOpenHelperImpl


class Adapter(
    private val sql: SQLiteOpenHelperImpl,
    private val listOfScores: MutableList<PersonScore>
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_scores,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfScores.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.namescore)
        var score: TextView = view.findViewById(R.id.scorescore)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = listOfScores[position].name
        holder.score.text = listOfScores[position].score.toString()
    }
}