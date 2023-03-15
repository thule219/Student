package com.example.student.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.student.model.Student

@Dao
interface StudentDao {
    @Query("SELECT * FROM student_table")
    fun getAllStudent():LiveData<List<Student>>

    @Query("SELECT * FROM student_table WHERE name LIKE :name")
    suspend fun findStudent(name:String):Student

    @Query("SELECT * FROM student_table WHERE id = :id")
    suspend fun findStudentById(id: Int):Student

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Update
    suspend fun updateStudent(student: Student)

}