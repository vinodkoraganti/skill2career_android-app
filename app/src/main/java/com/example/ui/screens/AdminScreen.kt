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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.ui.theme.AccentSuccess
import com.example.ui.theme.AccentSuccessContainer
import com.example.ui.theme.SkyBlueDark
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.Skill2CareerViewModel

@Composable
fun AdminScreen(
  viewModel: Skill2CareerViewModel,
  onBack: () -> Unit
) {
  var newTitle by remember { mutableStateOf("") }
  var newCategory by remember { mutableStateOf("AI and Machine Learning") }
  var newProvider by remember { mutableStateOf("Skill2Career Pro") }
  var newDuration by remember { mutableStateOf("6 weeks") }
  var isPaid by remember { mutableStateOf(false) }
  var courseAddedToast by remember { mutableStateOf(false) }

  val courses = viewModel.getCourses()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .padding(horizontal = 20.dp)
      .verticalScroll(rememberScrollState())
      .testTag("admin_screen")
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
      Text("Admin Management Portal", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextPrimary)
    }

    Text(
      text = "Manage platform courses, learning content, and monitor analytics.",
      fontSize = 13.sp,
      color = TextSecondary,
      modifier = Modifier.padding(bottom = 16.dp)
    )

    // Platform Analytics Row
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // Metric 1: Total Users
      Card(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Icon(Icons.Default.Group, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(24.dp))
          Spacer(modifier = Modifier.height(6.dp))
          Text("14,820", fontWeight = FontWeight.ExtraBold, fontSize = 17.sp, color = TextPrimary)
          Text("Total Students", fontSize = 11.sp, color = TextSecondary)
        }
      }

      // Metric 2: Active Courses
      Card(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Icon(Icons.Default.Book, contentDescription = null, tint = Color(0xFF7C3AED), modifier = Modifier.size(24.dp))
          Spacer(modifier = Modifier.height(6.dp))
          Text("${courses.size}", fontWeight = FontWeight.ExtraBold, fontSize = 17.sp, color = TextPrimary)
          Text("Live Courses", fontSize = 11.sp, color = TextSecondary)
        }
      }

      // Metric 3: Quizzes Taken
      Card(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Icon(Icons.Default.School, contentDescription = null, tint = AccentSuccess, modifier = Modifier.size(24.dp))
          Spacer(modifier = Modifier.height(6.dp))
          Text("8,340", fontWeight = FontWeight.ExtraBold, fontSize = 17.sp, color = TextPrimary)
          Text("Assessments", fontSize = 11.sp, color = TextSecondary)
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Add Course Form
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth().testTag("add_course_card")
    ) {
      Column(modifier = Modifier.padding(18.dp)) {
        Text("Add New Course", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = newTitle,
          onValueChange = { newTitle = it },
          label = { Text("Course Title") },
          modifier = Modifier.fillMaxWidth().testTag("admin_course_title"),
          shape = RoundedCornerShape(10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
          value = newCategory,
          onValueChange = { newCategory = it },
          label = { Text("Category") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
          value = newProvider,
          onValueChange = { newProvider = it },
          label = { Text("Provider / Academy") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
          value = newDuration,
          onValueChange = { newDuration = it },
          label = { Text("Duration (e.g. 6 weeks)") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("Course Pricing (Free / Paid)", fontSize = 13.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
          Switch(checked = isPaid, onCheckedChange = { isPaid = it })
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
          onClick = {
            if (newTitle.isNotBlank()) {
              val newCourse = Course(
                id = "crs_" + System.currentTimeMillis(),
                title = newTitle,
                provider = newProvider,
                partner = "Verified Partner",
                category = newCategory,
                difficulty = "Beginner",
                duration = newDuration,
                isPaid = isPaid,
                priceTag = if (isPaid) "Paid" else "Free",
                tags = listOf(newCategory, "Industry Skill"),
                description = "Comprehensive masterclass curriculum created via the Skill2Career Admin Console.",
                rating = 5.0f,
                enrolledCount = 1
              )
              viewModel.addAdminCourse(newCourse)
              newTitle = ""
              courseAddedToast = true
            }
          },
          modifier = Modifier.fillMaxWidth().height(48.dp).testTag("admin_publish_course_button"),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
        ) {
          Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
          Spacer(modifier = Modifier.width(6.dp))
          Text(if (courseAddedToast) "Course Published Live ✓" else "Publish Course", fontWeight = FontWeight.Bold, color = Color.White)
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}
