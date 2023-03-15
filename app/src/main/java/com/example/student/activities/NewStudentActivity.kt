package com.example.student.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.student.R
import com.example.student.database.StudentDao
import com.example.student.database.StudentRoomDatabase
import com.example.student.model.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewStudentActivity : AppCompatActivity() {

    private var studentDB:StudentRoomDatabase ?= null
    private lateinit var student:StudentDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_student)
        val btnSave = findViewById<Button>(R.id.btnSave)

        studentDB = StudentRoomDatabase.getDatabase(this)

        btnSave.setOnClickListener {
            insertStudent()
        }


    }

    private fun insertStudent() {
        val editName = findViewById<EditText>(R.id.textName)
        val name = editName.text.toString()
        if(name.isNotEmpty()){
            val student = Student(null,name)
            GlobalScope.launch(Dispatchers.IO){
                studentDB?.studentDao()?.insertStudent(student)
        }
            editName.text.clear()
            Toast.makeText(this,"Add successfully",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show()
        }
    }
}