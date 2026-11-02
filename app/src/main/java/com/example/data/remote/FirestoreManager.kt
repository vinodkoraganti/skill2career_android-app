package com.example.data.remote

import android.content.Context
import android.util.Log
import com.example.R
import com.example.data.model.Course
import com.example.data.model.QuizResult
import com.example.data.model.UserProfile
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreManager(private val context: Context) {
  private val tag = "FirestoreManager"

  private val firestore: FirebaseFirestore by lazy {
    try {
      val databaseId = try {
        context.getString(R.string.firestore_database_id)
      } catch (e: Exception) {
        "(default)"
      }
      if (databaseId.isNotBlank() && databaseId != "(default)") {
        FirebaseFirestore.getInstance(FirebaseApp.getInstance(), databaseId)
      } else {
        Firebase.firestore
      }
    } catch (e: Exception) {
      Log.w(tag, "Falling back to default Firestore: ${e.message}")
      Firebase.firestore
    }
  }

  // 1. Sync User Profile to Firestore
  suspend fun saveUserProfileToFirestore(user: UserProfile): Boolean {
    return try {
      val data = hashMapOf(
        "uid" to user.uid,
        "fullName" to user.fullName,
        "email" to user.email,
        "phone" to user.phone,
        "accountType" to user.accountType.name,
        "educationLevel" to user.educationLevel,
        "college" to user.college,
        "branch" to user.branch,
        "graduationYear" to user.graduationYear,
        "preferredLocation" to user.preferredLocation,
        "currentRole" to user.currentRole,
        "experienceYears" to user.experienceYears,
        "targetRole" to user.targetRole,
        "careerGoal" to user.careerGoal,
        "technicalSkills" to user.technicalSkills,
        "careerInterests" to user.careerInterests,
        "profileCompletionPercentage" to user.profileCompletionPercentage,
        "resumeFileName" to (user.resumeFileName ?: ""),
        "isAdmin" to user.isAdmin,
        "updatedAt" to System.currentTimeMillis()
      )
      firestore.collection("users").document(user.uid).set(data).await()
      Log.d(tag, "Successfully saved user ${user.fullName} to Firestore")
      true
    } catch (e: Exception) {
      Log.e(tag, "Failed to save user to Firestore: ${e.message}")
      false
    }
  }

  // 2. Real-time Courses Flow from Firestore
  fun getCoursesRealtimeFlow(): Flow<List<Course>> = callbackFlow {
    val registration = firestore.collection("courses")
      .addSnapshotListener { snapshot, error ->
        if (error != null) {
          Log.e(tag, "Error listening to courses: ${error.message}")
          trySend(emptyList())
          return@addSnapshotListener
        }
        val courses = snapshot?.documents?.mapNotNull { doc ->
          try {
            val id = doc.getString("id") ?: doc.id
            val title = doc.getString("title") ?: return@mapNotNull null
            val provider = doc.getString("provider") ?: "Skill2Career"
            val partner = doc.getString("partner") ?: "Verified Partner"
            val category = doc.getString("category") ?: "Technology"
            val difficulty = doc.getString("difficulty") ?: "Beginner"
            val duration = doc.getString("duration") ?: "4 weeks"
            val isPaid = doc.getBoolean("isPaid") ?: false
            val priceTag = doc.getString("priceTag") ?: if (isPaid) "Paid" else "Free"
            val desc = doc.getString("description") ?: ""
            val tags = (doc.get("tags") as? List<*>)?.mapNotNull { it?.toString() } ?: listOf(category)
            val rating = (doc.getDouble("rating") ?: 4.8).toFloat()
            val enrolled = (doc.getLong("enrolledCount") ?: 100).toInt()

            Course(
              id = id,
              title = title,
              provider = provider,
              partner = partner,
              category = category,
              difficulty = difficulty,
              duration = duration,
              isPaid = isPaid,
              priceTag = priceTag,
              tags = tags,
              description = desc,
              rating = rating,
              enrolledCount = enrolled
            )
          } catch (e: Exception) {
            null
          }
        } ?: emptyList()

        trySend(courses)
      }

    awaitClose { registration.remove() }
  }

  // 3. Add Course to Firestore (Admin & User feature)
  suspend fun addCourseToFirestore(course: Course): Boolean {
    return try {
      val data = hashMapOf(
        "id" to course.id,
        "title" to course.title,
        "provider" to course.provider,
        "partner" to course.partner,
        "category" to course.category,
        "difficulty" to course.difficulty,
        "duration" to course.duration,
        "isPaid" to course.isPaid,
        "priceTag" to course.priceTag,
        "tags" to course.tags,
        "description" to course.description,
        "rating" to course.rating.toDouble(),
        "enrolledCount" to course.enrolledCount,
        "createdAt" to System.currentTimeMillis()
      )
      firestore.collection("courses").document(course.id).set(data).await()
      Log.d(tag, "Successfully published course ${course.title} to Firestore")
      true
    } catch (e: Exception) {
      Log.e(tag, "Failed to publish course to Firestore: ${e.message}")
      false
    }
  }

  // 4. Record Course Enrollment in Firestore
  suspend fun recordCourseEnrollment(userId: String, course: Course): Boolean {
    return try {
      val data = hashMapOf(
        "courseId" to course.id,
        "title" to course.title,
        "provider" to course.provider,
        "progressPercentage" to 10,
        "enrolledTimestamp" to System.currentTimeMillis()
      )
      firestore.collection("users").document(userId)
        .collection("enrollments").document(course.id).set(data).await()
      true
    } catch (e: Exception) {
      Log.e(tag, "Failed to record enrollment in Firestore: ${e.message}")
      false
    }
  }

  // 5. Record Quiz Attempt in Firestore
  suspend fun recordQuizAttempt(userId: String, result: QuizResult): Boolean {
    return try {
      val data = hashMapOf(
        "quizId" to result.quizId,
        "quizTitle" to result.quizTitle,
        "score" to result.score,
        "totalQuestions" to result.totalQuestions,
        "percentage" to result.percentage,
        "timestamp" to result.timestamp
      )
      firestore.collection("users").document(userId)
        .collection("quizAttempts").add(data).await()
      true
    } catch (e: Exception) {
      Log.e(tag, "Failed to save quiz attempt to Firestore: ${e.message}")
      false
    }
  }
}
