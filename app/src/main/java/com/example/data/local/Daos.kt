package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
  @Query("SELECT * FROM user_profiles WHERE uid = :uid LIMIT 1")
  suspend fun getUserById(uid: String): UserEntity?

  @Query("SELECT * FROM user_profiles LIMIT 1")
  fun getActiveUserFlow(): Flow<UserEntity?>

  @Query("SELECT * FROM user_profiles LIMIT 1")
  suspend fun getActiveUser(): UserEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertUser(user: UserEntity)

  @Query("DELETE FROM user_profiles")
  suspend fun clearUsers()
}

@Dao
interface CourseDao {
  @Query("SELECT * FROM enrolled_courses ORDER BY enrolledTimestamp DESC")
  fun getEnrolledCourses(): Flow<List<EnrolledCourseEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun enrollCourse(course: EnrolledCourseEntity)

  @Query("UPDATE enrolled_courses SET progressPercentage = :progress WHERE courseId = :courseId")
  suspend fun updateCourseProgress(courseId: String, progress: Int)

  @Query("SELECT * FROM saved_courses")
  fun getSavedCourses(): Flow<List<SavedCourseEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveCourse(course: SavedCourseEntity)

  @Query("DELETE FROM saved_courses WHERE courseId = :courseId")
  suspend fun removeSavedCourse(courseId: String)
}

@Dao
interface QuizDao {
  @Query("SELECT * FROM quiz_attempts ORDER BY timestamp DESC")
  fun getAllQuizAttempts(): Flow<List<QuizAttemptEntity>>

  @Insert
  suspend fun insertQuizAttempt(attempt: QuizAttemptEntity)
}

@Dao
interface RoadmapDao {
  @Query("SELECT taskId FROM completed_roadmap_tasks WHERE targetRole = :targetRole")
  fun getCompletedTasks(targetRole: String): Flow<List<String>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun markTaskCompleted(task: CompletedTaskEntity)

  @Query("DELETE FROM completed_roadmap_tasks WHERE taskId = :taskId")
  suspend fun unmarkTask(taskId: String)
}
