package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AccountType
import com.example.data.model.CareerPath
import com.example.data.model.Course
import com.example.data.model.UserProfile
import com.example.ui.theme.AccentError
import com.example.ui.theme.AccentErrorContainer
import com.example.ui.theme.AccentSuccess
import com.example.ui.theme.AccentSuccessContainer
import com.example.ui.theme.AccentWarning
import com.example.ui.theme.AccentWarningContainer
import com.example.ui.theme.SkyBlueContainer
import com.example.ui.theme.SkyBlueDark
import com.example.ui.theme.SkyBlueLight
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.MainTab
import com.example.ui.viewmodel.Skill2CareerViewModel
import com.example.ui.viewmodel.SubScreen

@Composable
fun HomeScreen(
  viewModel: Skill2CareerViewModel,
  onNavigateToTab: (MainTab) -> Unit,
  onOpenSubScreen: (SubScreen) -> Unit,
  onSelectCareer: (CareerPath) -> Unit
) {
  val user by viewModel.userProfile.collectAsState()
  val showNotifications by viewModel.showNotifications.collectAsState()
  var searchQuery by remember { mutableStateOf("") }

  val careers = viewModel.getAllCareers()
  val courses = viewModel.getCourses()
  val skillGap = viewModel.getCurrentSkillGap()
  val roadmap = viewModel.getRoadmap()
  val isStudent = (user?.accountType ?: AccountType.STUDENT) == AccountType.STUDENT

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .verticalScroll(rememberScrollState())
      .testTag("home_screen")
  ) {
    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 12.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = Color(0xFFE0F2FE),
          modifier = Modifier.size(38.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(Icons.Default.School, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(24.dp))
          }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = "Skill2Career",
          fontSize = 20.sp,
          fontWeight = FontWeight.ExtraBold,
          color = TextPrimary
        )
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        BadgedBox(
          badge = {
            Badge(containerColor = Color(0xFFEF4444)) { Text("2", color = Color.White) }
          }
        ) {
          IconButton(
            onClick = { viewModel.toggleNotifications() },
            modifier = Modifier.testTag("notifications_button")
          ) {
            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = TextPrimary)
          }
        }

        Spacer(modifier = Modifier.width(6.dp))

        Surface(
          shape = CircleShape,
          color = SkyBluePrimary,
          modifier = Modifier
            .size(38.dp)
            .clickable { onNavigateToTab(MainTab.PROFILE) }
            .testTag("home_profile_avatar")
        ) {
          Box(contentAlignment = Alignment.Center) {
            Text(
              text = user?.fullName?.take(2)?.uppercase() ?: "VK",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }
        }
      }
    }

    // Notifications Dialog / Dropdown
    AnimatedVisibility(visible = showNotifications) {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2FE)),
        shape = RoundedCornerShape(14.dp)
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text("🔔 Notifications", fontWeight = FontWeight.Bold, color = SkyBlueDark, fontSize = 14.sp)
          Spacer(modifier = Modifier.height(6.dp))
          Text("• Complete your resume analysis to unlock 96% AI career match.", fontSize = 12.sp, color = TextPrimary)
          Spacer(modifier = Modifier.height(4.dp))
          Text("• New course available: AWS Cloud Practitioner (Free).", fontSize = 12.sp, color = TextPrimary)
        }
      }
    }

    // Personalized Greeting Banner
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 8.dp),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            brush = Brush.horizontalGradient(
              colors = listOf(
                Color(0xFFE0F2FE),
                Color(0xFFBAE6FD),
                Color(0xFF7DD3FC)
              )
            )
          )
          .padding(20.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = if (isStudent) "Good Morning, ${user?.fullName ?: "Vinod Koraganti"} 👋" else "Good Morning, ${user?.fullName ?: "Kumar"} 👋",
              fontWeight = FontWeight.Bold,
              fontSize = 18.sp,
              color = TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = if (isStudent) "Your Career Starts with the Right Skills. Discover your path and follow a personalized roadmap." else "Advance your career with targeted learning and curated opportunities.",
              fontSize = 13.sp,
              color = Color(0xFF0369A1),
              lineHeight = 18.sp
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Surface(
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.9f),
            modifier = Modifier.size(54.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = if (isStudent) Icons.Default.School else Icons.Default.BusinessCenter,
                contentDescription = null,
                tint = SkyBluePrimary,
                modifier = Modifier.size(32.dp)
              )
            }
          }
        }
      }
    }

    // Search Bar
    OutlinedTextField(
      value = searchQuery,
      onValueChange = { searchQuery = it },
      placeholder = { Text("Search careers, skills, courses...", fontSize = 14.sp, color = TextMuted) },
      leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = SkyBluePrimary) },
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 8.dp)
        .testTag("home_search_bar"),
      shape = RoundedCornerShape(14.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = SkyBluePrimary,
        unfocusedBorderColor = Color(0xFFE2E8F0),
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
      ),
      singleLine = true
    )

    // Profile Completion Card
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 8.dp)
        .clickable { onNavigateToTab(MainTab.PROFILE) },
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(50.dp)) {
          CircularProgressIndicator(
            progress = { (user?.profileCompletionPercentage ?: 75) / 100f },
            modifier = Modifier.fillMaxSize(),
            color = SkyBluePrimary,
            trackColor = Color(0xFFE2E8F0),
            strokeWidth = 5.dp
          )
          Text(
            text = "${user?.profileCompletionPercentage ?: 75}%",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Profile Completion",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = TextPrimary
          )
          Text(
            text = "Complete your profile for better recommendations.",
            fontSize = 12.sp,
            color = TextSecondary
          )
        }

        Icon(
          Icons.AutoMirrored.Filled.ArrowForward,
          contentDescription = "View Profile",
          tint = SkyBluePrimary,
          modifier = Modifier.size(18.dp)
        )
      }
    }

    // Recommended Career Path highlight (AI/ML or Cloud)
    val topCareer = careers.firstOrNull()
    if (topCareer != null) {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 8.dp)
          .clickable { onSelectCareer(topCareer) }
          .testTag("recommended_career_card"),
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
            Row(verticalAlignment = Alignment.CenterVertically) {
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFE0F2FE),
                modifier = Modifier.size(40.dp)
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(Icons.Default.Psychology, contentDescription = null, tint = SkyBluePrimary)
                }
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(topCareer.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                Text(topCareer.category, fontSize = 12.sp, color = TextSecondary)
              }
            }

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = AccentSuccessContainer
            ) {
              Text(
                text = "${topCareer.matchPercentage}% match",
                color = AccentSuccess,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = topCareer.description,
            fontSize = 13.sp,
            color = TextSecondary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
          )

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            topCareer.matchedSkills.take(3).forEach { skill ->
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFF1F5F9)
              ) {
                Text(
                  text = skill,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = TextSecondary,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }
        }
      }
    }

    // Skill-Gap Summary Card
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 8.dp)
        .clickable { onOpenSubScreen(SubScreen.SKILL_GAP) }
        .testTag("skill_gap_summary_card"),
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
          Column {
            Text("Skill-Gap Summary", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
            Text("Target: ${skillGap.targetRole}", fontSize = 12.sp, color = SkyBlueDark, fontWeight = FontWeight.SemiBold)
          }
          TextButton(onClick = { onOpenSubScreen(SubScreen.SKILL_GAP) }) {
            Text("View Details ->", fontSize = 13.sp, color = SkyBluePrimary, fontWeight = FontWeight.Bold)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Have
          Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            color = AccentSuccessContainer
          ) {
            Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
              Text("${skillGap.skillsHave.size}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = AccentSuccess)
              Text("Have", fontSize = 11.sp, color = AccentSuccess, fontWeight = FontWeight.Bold)
            }
          }

          // Improve
          Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            color = AccentWarningContainer
          ) {
            Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
              Text("${skillGap.skillsToImprove.size}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = AccentWarning)
              Text("Improve", fontSize = 11.sp, color = AccentWarning, fontWeight = FontWeight.Bold)
            }
          }

          // Learn
          Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            color = AccentErrorContainer
          ) {
            Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
              Text("${skillGap.skillsToLearn.size}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = AccentError)
              Text("Learn", fontSize = 11.sp, color = AccentError, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // Quick Actions
    Text(
      text = "Quick Actions",
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold,
      color = TextPrimary,
      modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
    )

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      QuickActionButton(
        icon = Icons.Default.Description,
        label = "Analyze Resume",
        onClick = { onOpenSubScreen(SubScreen.RESUME_ANALYSIS) }
      )
      QuickActionButton(
        icon = Icons.Default.Timeline,
        label = "View Roadmap",
        onClick = { onNavigateToTab(MainTab.ROADMAP) }
      )
      QuickActionButton(
        icon = Icons.Default.CheckCircle,
        label = "Take Quiz",
        onClick = { onOpenSubScreen(SubScreen.QUIZ_LIST) }
      )
      QuickActionButton(
        icon = Icons.AutoMirrored.Filled.TrendingUp,
        label = "Progress",
        onClick = { onOpenSubScreen(SubScreen.PROGRESS_DASHBOARD) }
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Popular Skills Chips
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text("Popular Skills", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
      Text("See All ->", fontSize = 13.sp, color = SkyBluePrimary, fontWeight = FontWeight.SemiBold)
    }

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      val skills = listOf("Python", "Java", "C++", "SQL", "React", "Machine Learning", "Docker", "AWS")
      skills.forEach { skill ->
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = Color.White,
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
          modifier = Modifier.clickable { }
        ) {
          Text(
            text = skill,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
          )
        }
      }
    }

    // Latest Courses Preview
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text("Latest Courses", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
      TextButton(onClick = { onOpenSubScreen(SubScreen.COURSES_LIST) }) {
        Text("See All ->", fontSize = 13.sp, color = SkyBluePrimary, fontWeight = FontWeight.SemiBold)
      }
    }

    courses.take(2).forEach { course ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 6.dp)
          .clickable { onOpenSubScreen(SubScreen.COURSES_LIST) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFE0F2FE),
            modifier = Modifier.size(48.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(Icons.Default.Code, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(26.dp))
            }
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(course.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
            Text("${course.provider} • ${course.partner}", fontSize = 12.sp, color = TextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Text(course.difficulty, fontSize = 11.sp, color = SkyBlueDark, fontWeight = FontWeight.SemiBold)
              Text("• ${course.duration}", fontSize = 11.sp, color = TextMuted)
              Text("• ${course.priceTag}", fontSize = 11.sp, color = if (course.isPaid) Color(0xFF7C3AED) else AccentSuccess, fontWeight = FontWeight.Bold)
            }
          }

          Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = TextMuted)
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}

@Composable
fun QuickActionButton(
  icon: ImageVector,
  label: String,
  onClick: () -> Unit
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .clickable { onClick() }
      .width(76.dp)
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = Color.White,
      shadowElevation = 2.dp,
      modifier = Modifier.size(54.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(icon, contentDescription = label, tint = SkyBluePrimary, modifier = Modifier.size(26.dp))
      }
    }
    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = label,
      fontSize = 11.sp,
      fontWeight = FontWeight.Medium,
      color = TextPrimary,
      textAlign = androidx.compose.ui.text.style.TextAlign.Center,
      maxLines = 2,
      lineHeight = 14.sp
    )
  }
}
