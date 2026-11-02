package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentSuccess
import com.example.ui.theme.AccentSuccessContainer
import com.example.ui.theme.SkyBlueDark
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.Skill2CareerViewModel

@Composable
fun ProgressScreen(
  viewModel: Skill2CareerViewModel,
  onBack: () -> Unit
) {
  val enrolledCourses by viewModel.enrolledCourses.collectAsState()
  val pastQuizAttempts by viewModel.pastQuizAttempts.collectAsState()
  val completedTasks by viewModel.completedTaskIds.collectAsState()
  val roadmap = viewModel.getRoadmap()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .padding(horizontal = 20.dp)
      .verticalScroll(rememberScrollState())
      .testTag("progress_screen")
  ) {
    // Header
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
      Text("Learning Progress", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextPrimary)
    }

    Text(
      text = "Track your learning streaks, course completions, and assessment milestones.",
      fontSize = 13.sp,
      color = TextSecondary,
      modifier = Modifier.padding(bottom = 16.dp)
    )

    // Top Stats Row
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Streak Card
      Card(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
          Surface(shape = CircleShape, color = Color(0xFFFEF3C7), modifier = Modifier.size(42.dp)) {
            Box(contentAlignment = Alignment.Center) {
              Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(24.dp))
            }
          }
          Spacer(modifier = Modifier.height(8.dp))
          Text("5 Days", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = TextPrimary)
          Text("Learning Streak", fontSize = 11.sp, color = TextSecondary)
        }
      }

      // Tasks Completed
      Card(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
          Surface(shape = CircleShape, color = Color(0xFFE0F2FE), modifier = Modifier.size(42.dp)) {
            Box(contentAlignment = Alignment.Center) {
              Icon(Icons.Default.Timeline, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(24.dp))
            }
          }
          Spacer(modifier = Modifier.height(8.dp))
          Text("${completedTasks.size}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = TextPrimary)
          Text("Roadmap Tasks", fontSize = 11.sp, color = TextSecondary)
        }
      }

      // Quizzes Taken
      Card(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
          Surface(shape = CircleShape, color = AccentSuccessContainer, modifier = Modifier.size(42.dp)) {
            Box(contentAlignment = Alignment.Center) {
              Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = AccentSuccess, modifier = Modifier.size(24.dp))
            }
          }
          Spacer(modifier = Modifier.height(8.dp))
          Text("${pastQuizAttempts.size}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = TextPrimary)
          Text("Quizzes Passed", fontSize = 11.sp, color = TextSecondary)
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Roadmap Progress
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("Target: ${roadmap.targetRole}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
          Text("${roadmap.overallProgress}%", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = SkyBluePrimary)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
          progress = { roadmap.overallProgress / 100f },
          modifier = Modifier.fillMaxWidth().height(8.dp),
          color = SkyBluePrimary,
          trackColor = Color(0xFFE2E8F0)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("${roadmap.completedTasksCount} of ${roadmap.totalTasksCount} lessons completed", fontSize = 12.sp, color = TextSecondary)
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Enrolled Courses
    Text("Enrolled Courses", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
    Spacer(modifier = Modifier.height(8.dp))

    if (enrolledCourses.isEmpty()) {
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(Icons.Default.School, contentDescription = null, tint = TextMuted, modifier = Modifier.size(40.dp))
          Spacer(modifier = Modifier.height(8.dp))
          Text("No active course enrollments yet.", color = TextSecondary, fontSize = 13.sp)
        }
      }
    } else {
      enrolledCourses.forEach { course ->
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(course.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
            Text(course.provider, fontSize = 12.sp, color = TextSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
              progress = { course.progressPercentage / 100f },
              modifier = Modifier.fillMaxWidth().height(6.dp),
              color = AccentSuccess,
              trackColor = Color(0xFFE2E8F0)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("${course.progressPercentage}% completed", fontSize = 11.sp, color = TextMuted)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}
