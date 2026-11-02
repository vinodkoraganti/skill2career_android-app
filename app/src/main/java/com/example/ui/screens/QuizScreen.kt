package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Quiz
import com.example.ui.theme.AccentError
import com.example.ui.theme.AccentSuccess
import com.example.ui.theme.AccentSuccessContainer
import com.example.ui.theme.SkyBlueDark
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.Skill2CareerViewModel
import kotlinx.coroutines.delay

@Composable
fun QuizListScreen(
  viewModel: Skill2CareerViewModel,
  onBack: () -> Unit
) {
  val quizzes = viewModel.getQuizzes()
  val pastAttempts by viewModel.pastQuizAttempts.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .padding(horizontal = 20.dp)
      .verticalScroll(rememberScrollState())
      .testTag("quiz_list_screen")
  ) {
    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onBack) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
      }
      Spacer(modifier = Modifier.width(8.dp))
      Text("Assessments & Quizzes", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextPrimary)
    }

    Text(
      text = "Test your skills with timed industry challenges and track your score history.",
      fontSize = 13.sp,
      color = TextSecondary,
      modifier = Modifier.padding(bottom = 16.dp)
    )

    // Quiz Cards
    quizzes.forEach { quiz ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp)
          .testTag("quiz_card_${quiz.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(quiz.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
              Text(quiz.category, fontSize = 12.sp, color = SkyBlueDark, fontWeight = FontWeight.SemiBold)
            }
            Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFE0F2FE)) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.Timer, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("${quiz.durationMinutes} min", fontSize = 11.sp, color = SkyBlueDark, fontWeight = FontWeight.Bold)
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text("${quiz.questions.size} Multiple-Choice Questions with detailed solution explanations.", fontSize = 13.sp, color = TextSecondary)

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = { viewModel.startQuiz(quiz) },
            modifier = Modifier.fillMaxWidth().height(44.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
          ) {
            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Start Assessment", fontWeight = FontWeight.Bold, color = Color.White)
          }
        }
      }
    }

    // Past Results Section
    if (pastAttempts.isNotEmpty()) {
      Spacer(modifier = Modifier.height(20.dp))
      Text("Recent Quiz Attempts", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
      Spacer(modifier = Modifier.height(10.dp))

      pastAttempts.take(3).forEach { attempt ->
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(attempt.quizTitle, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
              Text("Score: ${attempt.score}/${attempt.totalQuestions} questions", fontSize = 12.sp, color = TextSecondary)
            }
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (attempt.percentage >= 60) AccentSuccessContainer else Color(0xFFFEE2E2)
            ) {
              Text(
                text = "${attempt.percentage}%",
                color = if (attempt.percentage >= 60) AccentSuccess else AccentError,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}

@Composable
fun QuizPlayerScreen(
  viewModel: Skill2CareerViewModel,
  onClose: () -> Unit
) {
  val quiz by viewModel.activeQuiz.collectAsState()
  val questionIndex by viewModel.currentQuestionIndex.collectAsState()
  val selectedAnswers by viewModel.userSelectedAnswers.collectAsState()
  val result by viewModel.quizResult.collectAsState()

  var timeLeftSeconds by remember { mutableIntStateOf(600) }

  LaunchedEffect(quiz) {
    timeLeftSeconds = (quiz?.durationMinutes ?: 10) * 60
    while (timeLeftSeconds > 0 && result == null) {
      delay(1000)
      timeLeftSeconds--
    }
    if (timeLeftSeconds == 0 && result == null) {
      viewModel.submitQuiz()
    }
  }

  if (quiz == null) {
    onClose()
    return
  }

  val activeQuiz = quiz!!
  val currentQuestion = activeQuiz.questions.getOrNull(questionIndex)

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .padding(horizontal = 20.dp)
      .verticalScroll(rememberScrollState())
      .testTag("quiz_player_screen")
  ) {
    // Quiz Player Header
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onClose) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Exit Quiz", tint = TextPrimary)
      }

      Text(
        text = activeQuiz.title,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = TextPrimary
      )

      Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFE0F2FE)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.Timer, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          val mins = timeLeftSeconds / 60
          val secs = timeLeftSeconds % 60
          Text(String.format("%02d:%02d", mins, secs), fontWeight = FontWeight.Bold, color = SkyBlueDark, fontSize = 12.sp)
        }
      }
    }

    // Result Screen if finished
    if (result != null) {
      val res = result!!
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
      ) {
        Column(
          modifier = Modifier.padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(64.dp))
          Spacer(modifier = Modifier.height(12.dp))
          Text("Assessment Complete!", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Your Score: ${res.score} / ${res.totalQuestions} (${res.percentage}%)",
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold,
            color = if (res.percentage >= 60) AccentSuccess else AccentError
          )

          Spacer(modifier = Modifier.height(18.dp))

          Text(
            text = if (res.percentage >= 60) "Great job! You have demonstrated strong grasp of this topic." else "Review the solutions below and retake to strengthen your knowledge.",
            fontSize = 13.sp,
            color = TextSecondary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
          )

          Spacer(modifier = Modifier.height(20.dp))

          // Detailed Explanations
          activeQuiz.questions.forEachIndexed { i, q ->
            Card(
              colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Text("Q${i+1}: ${q.question}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Correct Answer: ${q.options[q.correctOptionIndex]}", fontSize = 12.sp, color = AccentSuccess, fontWeight = FontWeight.SemiBold)
                Text("Explanation: ${q.explanation}", fontSize = 11.sp, color = TextSecondary)
              }
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          Button(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
          ) {
            Text("Done & Save Result", fontWeight = FontWeight.Bold, color = Color.White)
          }
        }
      }
      return
    }

    // Progress Bar
    if (currentQuestion != null) {
      Spacer(modifier = Modifier.height(8.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text("Question ${questionIndex + 1} of ${activeQuiz.questions.size}", fontSize = 13.sp, color = TextSecondary, fontWeight = FontWeight.SemiBold)
        Text("${((questionIndex + 1) * 100) / activeQuiz.questions.size}%", fontSize = 13.sp, color = SkyBlueDark, fontWeight = FontWeight.Bold)
      }
      Spacer(modifier = Modifier.height(6.dp))
      LinearProgressIndicator(
        progress = { (questionIndex + 1f) / activeQuiz.questions.size },
        modifier = Modifier.fillMaxWidth().height(6.dp),
        color = SkyBluePrimary,
        trackColor = Color(0xFFE2E8F0)
      )

      Spacer(modifier = Modifier.height(24.dp))

      // Question Card
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(20.dp)) {
          Text(
            text = currentQuestion.question,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            lineHeight = 22.sp
          )

          Spacer(modifier = Modifier.height(20.dp))

          // 4 Options
          currentQuestion.options.forEachIndexed { optIndex, optionText ->
            val isSelected = selectedAnswers[questionIndex] == optIndex

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = if (isSelected) Color(0xFFE0F2FE) else Color(0xFFF8FAFC),
              border = androidx.compose.foundation.BorderStroke(
                1.5.dp,
                if (isSelected) SkyBluePrimary else Color(0xFFE2E8F0)
              ),
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .clickable { viewModel.selectQuizAnswer(questionIndex, optIndex) }
                .testTag("option_${optIndex}")
            ) {
              Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = if (isSelected) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                  contentDescription = null,
                  tint = if (isSelected) SkyBluePrimary else Color(0xFF94A3B8),
                  modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                  text = optionText,
                  fontSize = 14.sp,
                  color = if (isSelected) TextPrimary else TextSecondary,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(28.dp))

      // Next / Previous / Submit Buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        OutlinedButton(
          onClick = { viewModel.prevQuizQuestion() },
          enabled = questionIndex > 0,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.weight(1f).height(48.dp)
        ) {
          Text("Previous", fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.width(12.dp))

        if (questionIndex == activeQuiz.questions.size - 1) {
          Button(
            onClick = { viewModel.submitQuiz() },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentSuccess),
            modifier = Modifier.weight(1f).height(48.dp).testTag("submit_quiz_button")
          ) {
            Text("Submit", fontWeight = FontWeight.Bold, color = Color.White)
          }
        } else {
          Button(
            onClick = { viewModel.nextQuizQuestion() },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary),
            modifier = Modifier.weight(1f).height(48.dp).testTag("next_question_button")
          ) {
            Text("Next", fontWeight = FontWeight.Bold, color = Color.White)
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
