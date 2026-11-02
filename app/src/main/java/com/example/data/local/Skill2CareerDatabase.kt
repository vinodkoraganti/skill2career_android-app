package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
  entities = [
    UserEntity::class,
    EnrolledCourseEntity::class,
    SavedCourseEntity::class,
    QuizAttemptEntity::class,
    CompletedTaskEntity::class
  ],
  version = 1,
  exportSchema = false
)
abstract class Skill2CareerDatabase : RoomDatabase() {
  abstract fun userDao(): UserDao
  abstract fun courseDao(): CourseDao
  abstract fun quizDao(): QuizDao
  abstract fun roadmapDao(): RoadmapDao

  companion object {
    @Volatile
    private var INSTANCE: Skill2CareerDatabase? = null

    fun getDatabase(context: Context): Skill2CareerDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          Skill2CareerDatabase::class.java,
          "skill2career_database"
        )
        .fallbackToDestructiveMigration()
        .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
