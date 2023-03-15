package com.example.student.database

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.student.model.Student

@Database(
    entities = [Student::class],
    version = 1,
    exportSchema = true
)
abstract class StudentRoomDatabase: RoomDatabase() {
    abstract fun studentDao(): StudentDao
    companion object {
        private var INSTANCE: StudentRoomDatabase?= null
        private val DB_NAME = "student_db"
        fun getDatabase(context: Context): StudentRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentRoomDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}