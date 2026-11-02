package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.ui.theme.AccentError
import com.example.ui.theme.AccentErrorContainer
import com.example.ui.theme.AccentSuccess
import com.example.ui.theme.AccentSuccessContainer
import com.example.ui.theme.AccentWarning
import com.example.ui.theme.AccentWarningContainer
import com.example.ui.theme.SkyBlueDark
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.MainTab
import com.example.ui.viewmodel.Skill2CareerViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillGapScreen(
  viewModel: Skill2CareerViewModel,
  onBack: () -> Unit,
  onNavigateToRoadmap: () -> Unit
) {
  val selectedRole by viewModel.selectedRoleForSkillGap.collectAsState()
  val skillGap = viewModel.getCurrentSkillGap()
  var dropdownExpanded by remember { mutableStateOf(false) }
  var isAnalyzing by remember { mutableStateOf(false) }

  val availableRoles = listOf("AI/ML Engineer", "Software Developer", "Cloud Engineer", "Data Scientist", "Cybersecurity Analyst")

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .verticalScroll(rememberScrollState())
      .testTag("skill_gap_screen")
  ) {
    // Header
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onBack) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
      }
      Spacer(modifier = Modifier.width(8.dp))
      Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFE0F2FE), modifier = Modifier.size(32.dp)) {
          Box(contentAlignment = Alignment.Center) {
            Icon(Icons.Default.School, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(18.dp))
          }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text("Skill2Career", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = TextPrimary)
      }
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
      Text(
        text = "Skill-Gap Analysis",
        fontSize = 22.sp,
        fontWeight = FontWeight.ExtraBold,
        color = TextPrimary
      )

      Text(
        text = "Compare your current skills with the required skills for your target career.",
        fontSize = 13.sp,
        color = TextSecondary,
        modifier = Modifier.padding(top = 4.dp)
      )

      Spacer(modifier = Modifier.height(18.dp))

      // Target Role Dropdown
      Text("Target Role", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
      Spacer(modifier = Modifier.height(6.dp))

      Box {
        Surface(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { dropdownExpanded = true }
            .testTag("target_role_dropdown"),
          shape = RoundedCornerShape(14.dp),
          color = Color.White,
          border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFE2E8F0))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Surface(shape = CircleShape, color = Color(0xFFE0F2FE), modifier = Modifier.size(30.dp)) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(Icons.Default.School, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(16.dp))
                }
              }
              Spacer(modifier = Modifier.width(10.dp))
              Text(selectedRole, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
            }
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Select", tint = TextMuted)
          }
        }

        DropdownMenu(
          expanded = dropdownExpanded,
          onDismissRequest = { dropdownExpanded = false }
        ) {
          availableRoles.forEach { role ->
            DropdownMenuItem(
              text = { Text(role, fontWeight = if (role == selectedRole) FontWeight.Bold else FontWeight.Normal) },
              onClick = {
                viewModel.selectSkillGapRole(role)
                dropdownExpanded = false
              }
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Match Progress Bar
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
            Text("Skill Readiness Score", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
            Text("${skillGap.completionPercentage}%", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = SkyBluePrimary)
          }
          Spacer(modifier = Modifier.height(8.dp))
          LinearProgressIndicator(
            progress = { skillGap.completionPercentage / 100f },
            modifier = Modifier
              .fillMaxWidth()
              .height(10.dp),
            color = SkyBluePrimary,
            trackColor = Color(0xFFE2E8F0)
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // 1. Skills You Have
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AccentSuccess, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Skills You Have (${skillGap.skillsHave.size})",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = TextPrimary
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            skillGap.skillsHave.forEach { skill ->
              Surface(
                shape = RoundedCornerShape(20.dp),
                color = AccentSuccessContainer,
                border = androidx.compose.foundation.BorderStroke(1.dp, AccentSuccess.copy(alpha = 0.4f))
              ) {
                Text(
                  text = "✓ $skill",
                  color = AccentSuccess,
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 2. Skills to Improve
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.TrendingUp, contentDescription = null, tint = AccentWarning, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Skills to Improve (${skillGap.skillsToImprove.size})",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = TextPrimary
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            skillGap.skillsToImprove.forEach { skill ->
              Surface(
                shape = RoundedCornerShape(20.dp),
                color = AccentWarningContainer,
                border = androidx.compose.foundation.BorderStroke(1.dp, AccentWarning.copy(alpha = 0.4f))
              ) {
                Text(
                  text = "↑ $skill",
                  color = AccentWarning,
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 3. Skills to Learn
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = AccentError, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Skills to Learn (${skillGap.skillsToLearn.size})",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = TextPrimary
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            skillGap.skillsToLearn.forEach { skill ->
              Surface(
                shape = RoundedCornerShape(20.dp),
                color = AccentErrorContainer,
                border = androidx.compose.foundation.BorderStroke(1.dp, AccentError.copy(alpha = 0.4f))
              ) {
                Text(
                  text = "+ $skill",
                  color = AccentError,
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      Button(
        onClick = onNavigateToRoadmap,
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .testTag("analyze_skills_roadmap_button"),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
      ) {
        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Follow Learning Roadmap", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
      }

      Spacer(modifier = Modifier.height(28.dp))
    }
  }
}
