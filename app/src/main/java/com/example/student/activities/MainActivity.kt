package com.example.student.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.student.R
import com.example.student.adapter.StudentAdapter
import com.example.student.model.Student
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_student.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.student.database.StudentRoomDatabase as StudentRoomDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var studentListAdapter: ArrayList<Student>
    private  var adapter: StudentAdapter ?= null
    private  var studentDB: StudentRoomDatabase?= null
    private lateinit var liveDataStudent:LiveData<List<Student>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //add activity
        btnAdd.setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            startActivity(intent)
            finish()
        }

        //display in recycler view

        studentDB = StudentRoomDatabase.getDatabase(this) // Khởi tạo database để dùng các method
        studentListAdapter = ArrayList()

        // Khởi tạo layout cho recycleView
        listrecyclerStudent.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL, false)
        // Lấy tất cả sinh viên r đưa vào recyecleView
        // Hàm này luôn chạy lại khi cái UI có thay đổi
        getAllStudent()

    }

    private fun getAllStudent() {
        var studentListLiveData: LiveData<List<Student>>? = studentDB?.studentDao()?.getAllStudent()

        studentListLiveData?.observe(this, Observer { studentList ->
            studentListAdapter.clear()
            for (student in studentList) {
                studentListAdapter.add(student)
            }
            adapter = StudentAdapter(this@MainActivity, studentListAdapter)
            listrecyclerStudent.adapter = adapter
            listrecyclerStudent.smoothScrollToPosition(studentListAdapter.size) // target vào cuối danh sách khi thêm sinh viên
        })
    }

}
