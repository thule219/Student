package com.example.student.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.student.R
import com.example.student.adapter.StudentAdapter
import com.example.student.database.StudentRoomDatabase
import com.example.student.model.Student
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.item_student.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateActivity : AppCompatActivity() {
    private lateinit var student: Student
    private  var adapter: StudentAdapter?= null
    private  var studentDB: StudentRoomDatabase?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        studentDB = StudentRoomDatabase.getDatabase(this) // Khởi tạo database để dùng các method
        val studentId = intent.getStringExtra("studentId")
        GlobalScope.launch {
            student = studentDB!!.studentDao().findStudentById(studentId!!.toInt())
            editName.setText(student.name)
        }

        btnUpdate.setOnClickListener {
            val studentNam = editName.text.toString() // Lấy dữ liệu mới nhập vào
            student.name = studentNam.toString() // Gán lại cho student mới get từu Id khi nảy
            GlobalScope.launch {
                studentDB!!.studentDao().updateStudent(student) // Update lại student
                val intent = Intent(this@UpdateActivity, MainActivity::class.java) // Chuyển qua trang read data
                startActivity(intent)
                finish()
            }
        }
    }
}