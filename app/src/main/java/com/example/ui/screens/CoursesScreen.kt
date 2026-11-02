package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.ui.theme.SkyBlueContainer
import com.example.ui.theme.SkyBlueDark
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.Skill2CareerViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CoursesScreen(
  viewModel: Skill2CareerViewModel,
  onBack: () -> Unit
) {
  val searchQuery by viewModel.courseSearchQuery.collectAsState()
  val selectedCategory by viewModel.selectedCourseCategory.collectAsState()
  val enrolledCourses by viewModel.enrolledCourses.collectAsState()
  val savedCourses by viewModel.savedCourses.collectAsState()
  val courses = viewModel.getCourses()

  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  var viewingCourse by remember { mutableStateOf<Course?>(null) }

  val categories = listOf("All", "Data Science", "AI and Machine Learning", "Cloud Computing", "Programming", "Web Development", "Cybersecurity", "Free", "Paid")

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .testTag("courses_screen")
  ) {
    // Header
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onBack) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
      }
      Spacer(modifier = Modifier.width(6.dp))
      Column {
        Text("Courses & Certifications", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextPrimary)
        Text("Learn from top providers and build your skills", fontSize = 12.sp, color = TextSecondary)
      }
    }

    // Search bar
    OutlinedTextField(
      value = searchQuery,
      onValueChange = { viewModel.setCourseSearch(it) },
      placeholder = { Text("Search courses, skills or providers...", fontSize = 13.sp, color = TextMuted) },
      leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = SkyBluePrimary) },
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 6.dp)
        .testTag("course_search_input"),
      shape = RoundedCornerShape(14.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = SkyBluePrimary,
        unfocusedBorderColor = Color(0xFFE2E8F0),
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
      ),
      singleLine = true
    )

    // Filter Chips
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      categories.forEach { cat ->
        val isSelected = selectedCategory.equals(cat, ignoreCase = true)
        Surface(
          shape = RoundedCornerShape(18.dp),
          color = if (isSelected) SkyBluePrimary else Color.White,
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) SkyBluePrimary else Color(0xFFE2E8F0)
          ),
          modifier = Modifier.clickable { viewModel.setCourseCategory(cat) }
        ) {
          Text(
            text = cat,
            color = if (isSelected) Color.White else TextSecondary,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
          )
        }
      }
    }

    // Courses List
    LazyColumn(
      modifier = Modifier
        .weight(1f)
        .padding(horizontal = 20.dp, vertical = 6.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      items(courses) { course ->
        val isEnrolled = enrolledCourses.any { it.courseId == course.id }
        val isSaved = savedCourses.any { it.courseId == course.id }

        CourseCard(
          course = course,
          isEnrolled = isEnrolled,
          isSaved = isSaved,
          onViewCourse = { viewingCourse = course },
          onEnroll = { viewModel.enrollInCourse(course) },
          onToggleSave = { viewModel.toggleSaveCourse(course.id, isSaved) }
        )
      }
    }

    // Course Detail Sheet
    if (viewingCourse != null) {
      val course = viewingCourse!!
      val isEnrolled = enrolledCourses.any { it.courseId == course.id }

      ModalBottomSheet(
        onDismissRequest = { viewingCourse = null },
        sheetState = sheetState,
        containerColor = Color.White
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
            .verticalScroll(rememberScrollState())
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(course.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
              Text("${course.provider} • ${course.partner}", fontSize = 13.sp, color = SkyBlueDark, fontWeight = FontWeight.SemiBold)
            }
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = if (course.isPaid) Color(0xFFEDE9FE) else AccentSuccessContainer
            ) {
              Text(
                text = course.priceTag,
                color = if (course.isPaid) Color(0xFF7C3AED) else AccentSuccess,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("${course.rating}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
            }
            Text("• ${course.difficulty}", fontSize = 13.sp, color = TextSecondary)
            Text("• ${course.duration}", fontSize = 13.sp, color = TextSecondary)
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.People, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("${course.enrolledCount} learners", fontSize = 13.sp, color = TextSecondary)
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Text(course.description, fontSize = 14.sp, color = TextSecondary, lineHeight = 20.sp)

          Spacer(modifier = Modifier.height(16.dp))

          Text("Syllabus & Modules", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
          Spacer(modifier = Modifier.height(8.dp))
          course.syllabus.forEachIndexed { idx, item ->
            Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
              Surface(shape = CircleShape, color = Color(0xFFE0F2FE), modifier = Modifier.size(24.dp)) {
                Box(contentAlignment = Alignment.Center) {
                  Text("${idx + 1}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SkyBlueDark)
                }
              }
              Spacer(modifier = Modifier.width(10.dp))
              Text(item, fontSize = 13.sp, color = TextPrimary)
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          Button(
            onClick = {
              viewModel.enrollInCourse(course)
              viewingCourse = null
            },
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if (isEnrolled) AccentSuccess else SkyBluePrimary)
          ) {
            Text(
              text = if (isEnrolled) "Already Enrolled ✓" else "Enroll in Course",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = Color.White
            )
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CourseCard(
  course: Course,
  isEnrolled: Boolean,
  isSaved: Boolean,
  onViewCourse: () -> Unit,
  onEnroll: () -> Unit,
  onToggleSave: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("course_card_${course.id}"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFE0F2FE),
            modifier = Modifier.size(46.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(Icons.Default.School, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(26.dp))
            }
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Text(course.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
            Text("${course.provider} • ${course.partner}", fontSize = 12.sp, color = TextSecondary)
          }
        }

        IconButton(onClick = onToggleSave) {
          Icon(
            imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = "Save Course",
            tint = if (isSaved) SkyBluePrimary else TextMuted
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Tags
      FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        course.tags.forEach { tag ->
          Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFFF1F5F9)) {
            Text(tag, fontSize = 11.sp, color = TextSecondary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
          Text(course.difficulty, fontSize = 12.sp, color = SkyBlueDark, fontWeight = FontWeight.Bold)
          Text("• ${course.duration}", fontSize = 12.sp, color = TextMuted)
          Text("• ${course.priceTag}", fontSize = 12.sp, color = if (course.isPaid) Color(0xFF7C3AED) else AccentSuccess, fontWeight = FontWeight.Bold)
        }

        Button(
          onClick = if (isEnrolled) onViewCourse else onEnroll,
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnrolled) Color(0xFFE0F2FE) else SkyBluePrimary
          ),
          modifier = Modifier.height(38.dp)
        ) {
          Text(
            text = if (isEnrolled) "Enrolled ✓" else "View Course",
            color = if (isEnrolled) SkyBlueDark else Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          )
        }
      }
    }
  }
}
