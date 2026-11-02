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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CareerPath
import com.example.ui.theme.AccentError
import com.example.ui.theme.AccentErrorContainer
import com.example.ui.theme.AccentSuccess
import com.example.ui.theme.AccentSuccessContainer
import com.example.ui.theme.SkyBlueDark
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.MainTab
import com.example.ui.viewmodel.Skill2CareerViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CareersScreen(
  viewModel: Skill2CareerViewModel,
  onNavigateToTab: (MainTab) -> Unit
) {
  val selectedCategory by viewModel.selectedCareerCategory.collectAsState()
  val selectedCareer by viewModel.selectedCareerPath.collectAsState()
  val careers = viewModel.getAllCareers()
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  val categories = listOf("All", "Tech", "Data", "Security", "Cloud", "AI/ML")

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .testTag("careers_screen")
  ) {
    // Header
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = Color(0xFFE0F2FE),
          modifier = Modifier.size(34.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(Icons.Default.School, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(20.dp))
          }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Skill2Career",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "Career Recommendations",
        fontSize = 22.sp,
        fontWeight = FontWeight.ExtraBold,
        color = TextPrimary
      )

      Text(
        text = "Based on your verified skills, education, and resume profile.",
        fontSize = 13.sp,
        color = TextSecondary
      )
    }

    // Category Tabs
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      categories.forEach { category ->
        val isSelected = selectedCategory.equals(category, ignoreCase = true)
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = if (isSelected) SkyBluePrimary else Color.White,
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) SkyBluePrimary else Color(0xFFE2E8F0)
          ),
          modifier = Modifier.clickable { viewModel.filterCareerCategory(category) }
        ) {
          Text(
            text = category,
            color = if (isSelected) Color.White else TextSecondary,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
          )
        }
      }
    }

    // Career List
    LazyColumn(
      modifier = Modifier
        .weight(1f)
        .padding(horizontal = 20.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      items(careers) { career ->
        CareerCard(
          career = career,
          onViewDetails = { viewModel.selectCareerPath(career) }
        )
      }
    }

    // Career Detail Sheet
    if (selectedCareer != null) {
      val career = selectedCareer!!
      ModalBottomSheet(
        onDismissRequest = { viewModel.selectCareerPath(null) },
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
              Text(career.title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
              Text(career.category, fontSize = 13.sp, color = SkyBlueDark, fontWeight = FontWeight.SemiBold)
            }
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = AccentSuccessContainer
            ) {
              Text(
                text = "${career.matchPercentage}% match",
                color = AccentSuccess,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Text(career.description, fontSize = 14.sp, color = TextSecondary, lineHeight = 20.sp)

          Spacer(modifier = Modifier.height(16.dp))

          // Why Recommended
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2FE))
          ) {
            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
              Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text("Why this is recommended for you", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SkyBlueDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(career.whyRecommended, fontSize = 12.sp, color = TextPrimary, lineHeight = 18.sp)
              }
            }
          }

          Spacer(modifier = Modifier.height(18.dp))

          // Salary Range
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = AccentSuccess, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Average Industry Compensation: ", fontSize = 13.sp, color = TextSecondary)
            Text(career.salaryRange, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
          }

          Spacer(modifier = Modifier.height(18.dp))

          // Learning Sequence
          Text("Beginner to Advanced Learning Sequence", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
          Spacer(modifier = Modifier.height(8.dp))
          career.learningSequence.forEach { step ->
            Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.Top) {
              Icon(Icons.Default.Check, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(step, fontSize = 13.sp, color = TextSecondary, lineHeight = 18.sp)
            }
          }

          Spacer(modifier = Modifier.height(18.dp))

          // Target Job Roles
          Text("Target Job Roles", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
          Spacer(modifier = Modifier.height(8.dp))
          FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            career.jobRoles.forEach { role ->
              Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFF1F5F9)) {
                Text(role, fontSize = 12.sp, color = TextPrimary, fontWeight = FontWeight.Medium, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
              }
            }
          }

          Spacer(modifier = Modifier.height(18.dp))

          // Suggested Projects
          Text("Portfolio Project Recommendations", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
          Spacer(modifier = Modifier.height(8.dp))
          career.suggestedProjects.forEach { proj ->
            Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Construction, contentDescription = null, tint = Color(0xFF7C3AED), modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(proj, fontSize = 12.sp, color = TextPrimary)
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          Button(
            onClick = {
              viewModel.selectCareerPath(null)
              onNavigateToTab(MainTab.ROADMAP)
            },
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
          ) {
            Text("Follow Learning Roadmap", fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White)
          }
        }
      }
    }
  }
}

@Composable
fun CareerCard(
  career: CareerPath,
  onViewDetails: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("career_card_${career.id}"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = CircleShape,
            color = Color(0xFFE0F2FE),
            modifier = Modifier.size(46.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              val icon = when (career.category) {
                "AI/ML" -> Icons.Default.Psychology
                "Cloud" -> Icons.Default.Cloud
                "Security" -> Icons.Default.Security
                else -> Icons.Default.Code
              }
              Icon(icon, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(24.dp))
            }
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Text(career.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
            Text(career.category, fontSize = 12.sp, color = TextSecondary)
          }
        }

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = AccentSuccessContainer
        ) {
          Text(
            text = "${career.matchPercentage}% match",
            color = AccentSuccess,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = career.description,
        fontSize = 13.sp,
        color = TextSecondary,
        lineHeight = 18.sp
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Matched Skills
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Matched: ", fontSize = 11.sp, color = TextMuted, fontWeight = FontWeight.Bold)
        Text(career.matchedSkills.joinToString(", "), fontSize = 11.sp, color = AccentSuccess, fontWeight = FontWeight.SemiBold)
      }

      Spacer(modifier = Modifier.height(4.dp))

      // Missing Skills
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Missing: ", fontSize = 11.sp, color = TextMuted, fontWeight = FontWeight.Bold)
        Text(career.missingSkills.joinToString(", "), fontSize = 11.sp, color = AccentError, fontWeight = FontWeight.SemiBold)
      }

      Spacer(modifier = Modifier.height(14.dp))

      OutlinedButton(
        onClick = onViewDetails,
        modifier = Modifier
          .fillMaxWidth()
          .height(44.dp)
          .testTag("view_details_${career.id}"),
        shape = RoundedCornerShape(12.dp)
      ) {
        Text("View Details", color = SkyBluePrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
      }
    }
  }
}
