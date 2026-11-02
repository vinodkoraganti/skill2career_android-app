package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.data.model.JobOpportunity
import com.example.ui.theme.AccentSuccess
import com.example.ui.theme.AccentSuccessContainer
import com.example.ui.theme.SkyBlueDark
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.Skill2CareerViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun JobsScreen(viewModel: Skill2CareerViewModel) {
  val jobs = viewModel.getJobOpportunities()
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  var applyingJob by remember { mutableStateOf<JobOpportunity?>(null) }
  var appliedSubmitted by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .padding(horizontal = 20.dp)
      .testTag("jobs_screen")
  ) {
    Spacer(modifier = Modifier.height(14.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {
      Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFE0F2FE), modifier = Modifier.size(32.dp)) {
        Box(contentAlignment = Alignment.Center) {
          Icon(Icons.Default.School, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(18.dp))
        }
      }
      Spacer(modifier = Modifier.width(8.dp))
      Text("Skill2Career", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = TextPrimary)
    }

    Spacer(modifier = Modifier.height(10.dp))

    Text(
      text = "Jobs & Internships",
      fontSize = 22.sp,
      fontWeight = FontWeight.ExtraBold,
      color = TextPrimary
    )

    Text(
      text = "Curated openings matching your skills, education, and career target.",
      fontSize = 13.sp,
      color = TextSecondary,
      modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
    )

    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(14.dp),
      modifier = Modifier.weight(1f)
    ) {
      items(jobs) { job ->
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth().testTag("job_card_${job.id}")
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(job.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                Text(job.company, fontSize = 13.sp, color = TextSecondary)
              }
              if (job.isVerified) {
                Surface(
                  shape = RoundedCornerShape(8.dp),
                  color = AccentSuccessContainer
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Icon(Icons.Default.Verified, contentDescription = "Verified", tint = AccentSuccess, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Verified", color = AccentSuccess, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.LocationOn, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(job.location, fontSize = 12.sp, color = TextSecondary)
              Spacer(modifier = Modifier.width(12.dp))
              Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = AccentSuccess, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(job.salaryRange, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            }

            Spacer(modifier = Modifier.height(10.dp))

            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
              job.requiredSkills.forEach { skill ->
                Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFFF1F5F9)) {
                  Text(skill, fontSize = 11.sp, color = TextSecondary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                }
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
              onClick = {
                applyingJob = job
                appliedSubmitted = false
              },
              modifier = Modifier.fillMaxWidth().height(42.dp),
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
            ) {
              Text("Easy Apply with Profile", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
            }
          }
        }
      }
    }

    // Apply BottomSheet
    if (applyingJob != null) {
      val job = applyingJob!!
      ModalBottomSheet(
        onDismissRequest = { applyingJob = null },
        sheetState = sheetState,
        containerColor = Color.White
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          if (appliedSubmitted) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AccentSuccess, modifier = Modifier.size(60.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text("Application Submitted!", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextPrimary)
            Spacer(modifier = Modifier.height(6.dp))
            Text("Your verified Skill2Career profile and resume have been shared with ${job.company}.", fontSize = 13.sp, color = TextSecondary, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
              onClick = { applyingJob = null },
              modifier = Modifier.fillMaxWidth().height(48.dp),
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
            ) {
              Text("Done", fontWeight = FontWeight.Bold, color = Color.White)
            }
          } else {
            Text("Apply for ${job.title}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextPrimary)
            Text(job.company, fontSize = 13.sp, color = SkyBlueDark, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(14.dp))
            Text("We will submit your verified skills, education details, and parsed resume automatically.", fontSize = 13.sp, color = TextSecondary, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
              onClick = { appliedSubmitted = true },
              modifier = Modifier.fillMaxWidth().height(48.dp),
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
            ) {
              Text("Confirm & Submit Application", fontWeight = FontWeight.Bold, color = Color.White)
            }
          }
        }
      }
    }
  }
}
