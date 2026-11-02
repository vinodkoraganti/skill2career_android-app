package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.model.UserProfile
import com.example.ui.theme.AccentSuccess
import com.example.ui.theme.AccentSuccessContainer
import com.example.ui.theme.SkyBlueDark
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.Skill2CareerViewModel
import com.example.ui.viewmodel.SubScreen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
  viewModel: Skill2CareerViewModel,
  onOpenSubScreen: (SubScreen) -> Unit
) {
  val user by viewModel.userProfile.collectAsState()
  var activeTab by remember { mutableIntStateOf(0) } // 0: Personal, 1: Education/Work, 2: Skills & Interests

  var fullName by remember(user) { mutableStateOf(user?.fullName ?: "Vinod Koraganti") }
  var email by remember(user) { mutableStateOf(user?.email ?: "vinodkoraganti@gmail.com") }
  var phone by remember(user) { mutableStateOf(user?.phone ?: "+91 98765 43210") }
  var location by remember(user) { mutableStateOf(user?.preferredLocation ?: "Bangalore, India") }
  var targetRole by remember(user) { mutableStateOf(user?.targetRole ?: "AI/ML Engineer") }
  var careerGoal by remember(user) { mutableStateOf(user?.careerGoal ?: "Learn advanced ML and build real-world projects") }

  var college by remember(user) { mutableStateOf(user?.college ?: "Global Institute of Technology") }
  var branch by remember(user) { mutableStateOf(user?.branch ?: "Computer Science") }
  var gradYear by remember(user) { mutableStateOf(user?.graduationYear ?: "2024") }
  var experience by remember(user) { mutableStateOf(user?.experienceYears ?: "2 years 3 months") }

  var savedToast by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .padding(horizontal = 20.dp)
      .verticalScroll(rememberScrollState())
      .testTag("profile_screen")
  ) {
    Spacer(modifier = Modifier.height(14.dp))

    // Profile Top Header
    Text("Profile Management", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextPrimary)
    Text("Keep your profile up to date for better recommendations.", fontSize = 13.sp, color = TextSecondary)

    Spacer(modifier = Modifier.height(18.dp))

    // Avatar Card matching Screenshot 7
    Card(
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box {
          Surface(
            shape = CircleShape,
            color = Color(0xFFE0F2FE),
            modifier = Modifier.size(68.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = fullName.take(2).uppercase(),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SkyBluePrimary
              )
            }
          }

          Surface(
            shape = CircleShape,
            color = SkyBluePrimary,
            modifier = Modifier
              .size(24.dp)
              .align(Alignment.BottomEnd)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(Icons.Default.CameraAlt, contentDescription = "Edit Avatar", tint = Color.White, modifier = Modifier.size(14.dp))
            }
          }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
          Text(fullName, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = TextPrimary)
          Text(email, fontSize = 13.sp, color = TextSecondary)
          Spacer(modifier = Modifier.height(6.dp))
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = AccentSuccessContainer
          ) {
            Text(
              text = "Profile ${user?.profileCompletionPercentage ?: 85}% Complete",
              color = AccentSuccess,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Profile Sub Tabs
    val tabs = listOf("Personal Details", "Education / Work", "Skills & Interests")
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFFE2E8F0), shape = RoundedCornerShape(14.dp))
        .padding(4.dp)
    ) {
      tabs.forEachIndexed { index, title ->
        val isSelected = activeTab == index
        Box(
          modifier = Modifier
            .weight(1f)
            .clickable { activeTab = index }
            .background(if (isSelected) Color.White else Color.Transparent, shape = RoundedCornerShape(10.dp))
            .padding(vertical = 10.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) SkyBluePrimary else TextSecondary,
            maxLines = 1
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    when (activeTab) {
      0 -> {
        // Personal Details
        Text("Personal Information", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = fullName,
          onValueChange = { fullName = it },
          label = { Text("Full Name") },
          modifier = Modifier.fillMaxWidth().testTag("profile_full_name"),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          label = { Text("Email Address") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
          value = phone,
          onValueChange = { phone = it },
          label = { Text("Phone Number") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
          value = location,
          onValueChange = { location = it },
          label = { Text("Preferred Location") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(14.dp))

        Text("Career Goals", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
          value = targetRole,
          onValueChange = { targetRole = it },
          label = { Text("Target Role") },
          modifier = Modifier.fillMaxWidth().testTag("profile_target_role"),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
          value = careerGoal,
          onValueChange = { careerGoal = it },
          label = { Text("Career Goal") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          minLines = 2
        )
      }
      1 -> {
        // Education / Work Details
        Text("Academic & Professional Details", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = college,
          onValueChange = { college = it },
          label = { Text("College / University") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
          value = branch,
          onValueChange = { branch = it },
          label = { Text("Branch / Specialization") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
          value = gradYear,
          onValueChange = { gradYear = it },
          label = { Text("Graduation Year") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
          value = experience,
          onValueChange = { experience = it },
          label = { Text("Total Work Experience") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp)
        )
      }
      2 -> {
        // Skills & Interests
        Text("Technical Skills", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
        Spacer(modifier = Modifier.height(8.dp))
        val currentSkills = user?.technicalSkills ?: listOf("Python", "SQL", "Machine Learning", "Git")
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          currentSkills.forEach { skill ->
            Surface(
              shape = RoundedCornerShape(14.dp),
              color = Color(0xFFE0F2FE),
              border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBAE6FD))
            ) {
              Text(
                text = "✓ $skill",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = SkyBlueDark,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text("Career Interests", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
        Spacer(modifier = Modifier.height(8.dp))
        val interests = user?.careerInterests ?: listOf("Artificial Intelligence", "Data Science")
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          interests.forEach { interest ->
            Surface(shape = RoundedCornerShape(14.dp), color = Color(0xFFF1F5F9)) {
              Text(
                text = interest,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Save Button
    Button(
      onClick = {
        val updated = (user ?: UserProfile()).copy(
          fullName = fullName,
          email = email,
          phone = phone,
          preferredLocation = location,
          targetRole = targetRole,
          careerGoal = careerGoal,
          college = college,
          branch = branch,
          graduationYear = gradYear,
          experienceYears = experience
        )
        viewModel.updateUserProfile(updated)
        savedToast = true
      },
      modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .testTag("save_profile_button"),
      shape = RoundedCornerShape(12.dp),
      colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
    ) {
      Text(if (savedToast) "Saved Successfully ✓" else "Save Changes", fontWeight = FontWeight.Bold, color = Color.White)
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Admin & Management section
    Card(
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = SkyBluePrimary)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text("Admin Management Mode", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
              Text("Course creation & platform stats", fontSize = 11.sp, color = TextSecondary)
            }
          }

          Switch(
            checked = user?.isAdmin ?: false,
            onCheckedChange = { viewModel.toggleAdminMode() },
            modifier = Modifier.testTag("admin_mode_switch")
          )
        }

        if (user?.isAdmin == true) {
          Spacer(modifier = Modifier.height(10.dp))
          Button(
            onClick = { onOpenSubScreen(SubScreen.ADMIN_PANEL) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0369A1))
          ) {
            Text("Open Admin Dashboard", fontWeight = FontWeight.Bold, color = Color.White)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Logout
    OutlinedButton(
      onClick = { viewModel.logout() },
      modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .testTag("logout_button"),
      shape = RoundedCornerShape(12.dp)
    ) {
      Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
      Spacer(modifier = Modifier.width(8.dp))
      Text("Logout", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}
