package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.ui.viewmodel.Skill2CareerViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResumeScreen(
  viewModel: Skill2CareerViewModel,
  onBack: () -> Unit
) {
  val isAnalyzing by viewModel.isAnalyzingResume.collectAsState()
  val analysisResult by viewModel.resumeAnalysisResult.collectAsState()
  val user by viewModel.userProfile.collectAsState()

  var selectedFileName by remember { mutableStateOf(user?.resumeFileName ?: "Vinod_Koraganti_Resume_2024.pdf") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .padding(horizontal = 20.dp)
      .verticalScroll(rememberScrollState())
      .testTag("resume_screen")
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
      Text("Resume Analysis", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextPrimary)
    }

    Text(
      text = "Upload your resume to extract skills, experience, and get AI recommendations.",
      fontSize = 13.sp,
      color = TextSecondary,
      modifier = Modifier.padding(bottom = 16.dp)
    )

    // Upload Drop Area matching Screenshot 8
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("resume_upload_box"),
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Surface(
          shape = CircleShape,
          color = Color(0xFFE0F2FE),
          modifier = Modifier.size(64.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(Icons.Default.CloudUpload, contentDescription = "Upload", tint = SkyBluePrimary, modifier = Modifier.size(34.dp))
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text("Upload Resume", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = TextPrimary)
        Spacer(modifier = Modifier.height(4.dp))
        Text("Drag & drop your file here or browse", fontSize = 13.sp, color = TextSecondary)
        Text("PDF, DOCX, TXT (Max 5MB)", fontSize = 11.sp, color = TextMuted)

        Spacer(modifier = Modifier.height(16.dp))

        // Simulated file selector button
        Button(
          onClick = {
            selectedFileName = "Vinod_Koraganti_Updated_Resume.pdf"
          },
          colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.testTag("upload_file_button")
        ) {
          Text("Upload File", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
        }

        if (selectedFileName.isNotBlank()) {
          Spacer(modifier = Modifier.height(12.dp))
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFF1F5F9)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.Description, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(selectedFileName, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Analyze Resume Button
    Button(
      onClick = { viewModel.analyzeUploadedResume() },
      modifier = Modifier
        .fillMaxWidth()
        .height(52.dp)
        .testTag("analyze_resume_button"),
      shape = RoundedCornerShape(12.dp),
      colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary),
      enabled = !isAnalyzing
    ) {
      if (isAnalyzing) {
        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
        Spacer(modifier = Modifier.width(10.dp))
        Text("Analyzing Resume with AI...", fontWeight = FontWeight.Bold, color = Color.White)
      } else {
        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Analyze Resume", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Extracted Information Section (Screenshot 8)
    val result = analysisResult
    if (result != null) {
      Text("Extracted Information", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
      Spacer(modifier = Modifier.height(12.dp))

      // Technical Skills Chips
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text("Technical Skills (${result.technicalSkills.size})", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
          Spacer(modifier = Modifier.height(10.dp))
          FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            result.technicalSkills.forEach { skill ->
              Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFE0F2FE),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBAE6FD))
              ) {
                Text(
                  text = skill,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = SkyBlueDark,
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Experience & Education Cards
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          // Experience
          Row(verticalAlignment = Alignment.Top) {
            Surface(shape = CircleShape, color = Color(0xFFE0F2FE), modifier = Modifier.size(36.dp)) {
              Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Default.BusinessCenter, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(18.dp))
              }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text("Experience", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
              Text(result.extractedExperience, fontSize = 13.sp, color = TextSecondary)
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Education
          Row(verticalAlignment = Alignment.Top) {
            Surface(shape = CircleShape, color = Color(0xFFE0F2FE), modifier = Modifier.size(36.dp)) {
              Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Default.School, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(18.dp))
              }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text("Education", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
              Text(result.extractedEducation, fontSize = 13.sp, color = TextSecondary)
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // AI Suggestions
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = AccentWarning, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("AI Resume Suggestions", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF92400E))
          }
          Spacer(modifier = Modifier.height(8.dp))
          result.improvementSuggestions.forEach { suggestion ->
            Text("• $suggestion", fontSize = 12.sp, color = Color(0xFF78350F), modifier = Modifier.padding(vertical = 2.dp))
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
