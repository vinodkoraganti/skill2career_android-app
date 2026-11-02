package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.data.model.AccountType
import com.example.data.model.UserProfile

@Entity(tableName = "user_profiles")
data class UserEntity(
  @PrimaryKey val uid: String,
  val fullName: String,
  val email: String,
  val phone: String,
  val accountType: String,
  val educationLevel: String,
  val college: String,
  val branch: String,
  val graduationYear: String,
  val preferredLocation: String,
  val currentRole: String,
  val experienceYears: String,
  val targetRole: String,
  val careerGoal: String,
  val technicalSkills: String, // comma-separated
  val careerInterests: String, // comma-separated
  val profileCompletionPercentage: Int,
  val resumeFileName: String?,
  val resumeSummary: String?,
  val isAdmin: Boolean
) {
  fun toDomain(): UserProfile {
    return UserProfile(
      uid = uid,
      fullName = fullName,
      email = email,
      phone = phone,
      accountType = try { AccountType.valueOf(accountType) } catch (e: Exception) { AccountType.STUDENT },
      educationLevel = educationLevel,
      college = college,
      branch = branch,
      graduationYear = graduationYear,
      preferredLocation = preferredLocation,
      currentRole = currentRole,
      experienceYears = experienceYears,
      targetRole = targetRole,
      careerGoal = careerGoal,
      technicalSkills = if (technicalSkills.isBlank()) emptyList() else technicalSkills.split(",").map { it.trim() },
      careerInterests = if (careerInterests.isBlank()) emptyList() else careerInterests.split(",").map { it.trim() },
      profileCompletionPercentage = profileCompletionPercentage,
      resumeFileName = resumeFileName,
      resumeSummary = resumeSummary,
      isAdmin = isAdmin
    )
  }

  companion object {
    fun fromDomain(user: UserProfile): UserEntity {
      return UserEntity(
        uid = user.uid,
        fullName = user.fullName,
        email = user.email,
        phone = user.phone,
        accountType = user.accountType.name,
        educationLevel = user.educationLevel,
        college = user.college,
        branch = user.branch,
        graduationYear = user.graduationYear,
        preferredLocation = user.preferredLocation,
        currentRole = user.currentRole,
        experienceYears = user.experienceYears,
        targetRole = user.targetRole,
        careerGoal = user.careerGoal,
        technicalSkills = user.technicalSkills.joinToString(","),
        careerInterests = user.careerInterests.joinToString(","),
        profileCompletionPercentage = user.profileCompletionPercentage,
        resumeFileName = user.resumeFileName,
        resumeSummary = user.resumeSummary,
        isAdmin = user.isAdmin
      )
    }
  }
}

@Entity(tableName = "enrolled_courses")
data class EnrolledCourseEntity(
  @PrimaryKey val courseId: String,
  val title: String,
  val provider: String,
  val progressPercentage: Int,
  val enrolledTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_courses")
data class SavedCourseEntity(
  @PrimaryKey val courseId: String,
  val savedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "quiz_attempts")
data class QuizAttemptEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val quizId: String,
  val quizTitle: String,
  val score: Int,
  val totalQuestions: Int,
  val percentage: Int,
  val timestamp: Long
)

@Entity(tableName = "completed_roadmap_tasks")
data class CompletedTaskEntity(
  @PrimaryKey val taskId: String,
  val targetRole: String,
  val completedTimestamp: Long = System.currentTimeMillis()
)
