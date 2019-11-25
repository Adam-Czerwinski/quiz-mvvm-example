package com.example.quizmvvmexample.data

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.quizmvvmexample.data.dao.Person
import com.example.quizmvvmexample.data.dao.PersonScore
import com.example.quizmvvmexample.data.dao.Question

class SQLiteOpenHelperImpl(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {


    companion object {
        internal val DB_NAME: String = "quiz.db"
        internal val DB_VERSION: Int = 3
        internal val DB_TABLE_NAME_USERS = "users"
        internal val DB_TABLE_NAME_QUESTIONS = "questions"
        internal val DB_TABLE_NAME_SCORES = "scores"
        internal val DB_TABLE_USERS_COLUMNS = listOf(
            "id", "name"
        )

        internal val DB_TABLE_QUESTIONS_COLUMNS = listOf(
            "id", "question", "answerA", "answerB", "answerC", "answerD", "correctAnswer"
        )

        internal val DB_TABLE_SCORES_COLUMNS = listOf(
            "id", "score", "id_user"
        )
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            "CREATE TABLE IF NOT EXISTS $DB_TABLE_NAME_USERS (" +
                    " ${DB_TABLE_USERS_COLUMNS[0]} INTEGER PRIMARY KEY," +
                    " ${DB_TABLE_USERS_COLUMNS[1]} TEXT NOT NULL)"
        )
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS $DB_TABLE_NAME_QUESTIONS (" +
                    " ${DB_TABLE_QUESTIONS_COLUMNS[0]} INTEGER PRIMARY KEY," +
                    " ${DB_TABLE_QUESTIONS_COLUMNS[1]} TEXT NOT NULL," +
                    " ${DB_TABLE_QUESTIONS_COLUMNS[2]} TEXT NOT NULL," +
                    " ${DB_TABLE_QUESTIONS_COLUMNS[3]} TEXT NOT NULL," +
                    " ${DB_TABLE_QUESTIONS_COLUMNS[4]} TEXT NOT NULL," +
                    " ${DB_TABLE_QUESTIONS_COLUMNS[5]} TEXT NOT NULL, " +
                    " ${DB_TABLE_QUESTIONS_COLUMNS[6]} INTEGER NOT NULL)"
        )
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS $DB_TABLE_NAME_SCORES (" +
                    " ${DB_TABLE_SCORES_COLUMNS[0]} INTEGER PRIMARY KEY," +
                    " ${DB_TABLE_SCORES_COLUMNS[1]} TEXT NOT NULL," +
                    " ${DB_TABLE_SCORES_COLUMNS[2]} INTEGER)"
        )

        val questions = listOf(
            Question(
                "Ile to 2 + 2?",
                listOf("2", "3", "4", "5"),
                2
            ),
            Question(
                "Ile to 2 + 2 * 2?",
                listOf("2", "4", "5", "6"),
                3
            )
        )

        questions.forEach { t-> addQuestion(t,db) }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $DB_TABLE_NAME_QUESTIONS")
        db!!.execSQL("DROP TABLE IF EXISTS $DB_TABLE_NAME_SCORES")
        db!!.execSQL("DROP TABLE IF EXISTS $DB_TABLE_NAME_USERS")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    val allUsers: MutableList<Person>
        get() {
            val query = "SELECT * FROM $DB_TABLE_NAME_USERS"
            val db = this.writableDatabase
            val cursor = db.rawQuery(query, null)
            val persons = mutableListOf<Person>()
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(DB_TABLE_USERS_COLUMNS[0]))
                    val name = cursor.getString(cursor.getColumnIndex(DB_TABLE_USERS_COLUMNS[1]))

                    val person = Person(name, id)

                    persons.add(person)
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return persons
        }


//    ################# QUESTIONS

    @Throws(SQLException::class)
    fun addQuestion(questionToAdd: Question, dbInc: SQLiteDatabase? = null): Long {

        var db = dbInc
        if(db==null) db = this.writableDatabase
        val values = ContentValues(6)
        values.put(DB_TABLE_QUESTIONS_COLUMNS[1], questionToAdd.question)
        values.put(DB_TABLE_QUESTIONS_COLUMNS[2], questionToAdd.answers[0])
        values.put(DB_TABLE_QUESTIONS_COLUMNS[3], questionToAdd.answers[1])
        values.put(DB_TABLE_QUESTIONS_COLUMNS[4], questionToAdd.answers[2])
        values.put(DB_TABLE_QUESTIONS_COLUMNS[5], questionToAdd.answers[3])
        values.put(DB_TABLE_QUESTIONS_COLUMNS[6], questionToAdd.correctAnswer)
        val returnId = db!!.insert(DB_TABLE_NAME_QUESTIONS, null, values)

        if (returnId != -1L)
            return returnId
        else
            throw SQLException("Could not add question to database")
    }

    val allQuestions: MutableList<Question>
        get() {
            val query = "SELECT * FROM $DB_TABLE_NAME_QUESTIONS"
            val db = this.writableDatabase
            val cursor = db.rawQuery(query, null)
            val questions = mutableListOf<Question>()
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(DB_TABLE_QUESTIONS_COLUMNS[0]))
                    val q = cursor.getString(cursor.getColumnIndex(DB_TABLE_QUESTIONS_COLUMNS[1]))
                    val a = cursor.getString(cursor.getColumnIndex(DB_TABLE_QUESTIONS_COLUMNS[2]))
                    val b = cursor.getString(cursor.getColumnIndex(DB_TABLE_QUESTIONS_COLUMNS[3]))
                    val c = cursor.getString(cursor.getColumnIndex(DB_TABLE_QUESTIONS_COLUMNS[4]))
                    val d = cursor.getString(cursor.getColumnIndex(DB_TABLE_QUESTIONS_COLUMNS[5]))
                    val correctAnswer =
                        cursor.getInt(cursor.getColumnIndex(DB_TABLE_QUESTIONS_COLUMNS[6]))

                    val question = Question(
                        q,
                        listOf(a, b, c, d),
                        correctAnswer.toByte(),
                        id
                    )

                    questions.add(question)
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return questions
        }

    val allScores: MutableList<PersonScore>
        get() {
//            s INNER JOIN $DB_TABLE_NAME_USERS u ON s.${DB_TABLE_SCORES_COLUMNS[2]}=u.${DB_TABLE_USERS_COLUMNS[0]}
            val query = "SELECT * FROM $DB_TABLE_NAME_SCORES "
            val db = this.writableDatabase
            val cursor = db.rawQuery(query, null)
            val personscore = mutableListOf<PersonScore>()
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex(DB_TABLE_SCORES_COLUMNS[1]))
                    val score = cursor.getInt(cursor.getColumnIndex(DB_TABLE_SCORES_COLUMNS[2]))
                    val person = PersonScore(name, score)

                    personscore.add(person)
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return personscore
        }

    fun addScore(personScore: PersonScore) : Long
    {
        var db = this.writableDatabase
        val values = ContentValues(6)
        values.put(DB_TABLE_SCORES_COLUMNS[1], personScore.name)
        values.put(DB_TABLE_SCORES_COLUMNS[2], personScore.score)
        val returnId = db!!.insert(DB_TABLE_NAME_SCORES, null, values)

        if (returnId != -1L)
            return returnId
        else
            throw SQLException("Could not add question to database")
    }

}