package com.example

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testUserProfileDefaults() {
    val user = com.example.data.model.UserProfile()
    assertEquals("Vinod Koraganti", user.fullName)
    assertEquals("AI/ML Engineer", user.targetRole)
    assertEquals(85, user.profileCompletionPercentage)
  }

  @Test
  fun testQuizScoring() {
    val questions = listOf(
      com.example.data.model.QuizQuestion("1", "Q1", listOf("A", "B"), 0, "Exp1"),
      com.example.data.model.QuizQuestion("2", "Q2", listOf("A", "B"), 1, "Exp2")
    )
    val selected = mapOf(0 to 0, 1 to 1)
    val correct = questions.filterIndexed { index, q -> selected[index] == q.correctOptionIndex }.size
    val percentage = (correct * 100) / questions.size
    assertEquals(2, correct)
    assertEquals(100, percentage)
  }
}
