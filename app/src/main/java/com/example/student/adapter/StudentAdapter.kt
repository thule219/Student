package com.example.student.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.student.R
import com.example.student.activities.UpdateActivity
import com.example.student.database.StudentRoomDatabase
import com.example.student.model.Student
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable.start
import kotlinx.coroutines.launch

class StudentAdapter(private val context: Context, private var listStudent: List<Student>)
    : RecyclerView.Adapter<StudentAdapter.ViewHolder>(){

    private var studentDB = StudentRoomDatabase.getDatabase(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: StudentAdapter.ViewHolder, position: Int) {
        val student = listStudent[position]
        holder.name.text = student.name
        holder.update.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("studentId", student.id.toString())
            context.startActivity(intent)
        }
        holder.delete.setOnClickListener{
            GlobalScope.launch {
                studentDB.studentDao().deleteStudent(student)
            }
        }

    }
    override fun getItemCount(): Int {
        return listStudent.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.nameStudent)
        val update:ImageButton = itemView.findViewById(R.id.btnEdit)
        val delete: ImageButton = itemView.findViewById(R.id.btnDelete)

    }

}