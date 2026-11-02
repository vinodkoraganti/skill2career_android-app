package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.School
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
import com.example.data.model.RoadmapStage
import com.example.data.model.RoadmapTask
import com.example.data.model.StageStatus
import com.example.ui.theme.AccentSuccess
import com.example.ui.theme.AccentSuccessContainer
import com.example.ui.theme.AccentWarning
import com.example.ui.theme.AccentWarningContainer
import com.example.ui.theme.SkyBlueContainer
import com.example.ui.theme.SkyBlueDark
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.Skill2CareerViewModel

@Composable
fun RoadmapScreen(viewModel: Skill2CareerViewModel) {
  val user by viewModel.userProfile.collectAsState()
  val completedTasks by viewModel.completedTaskIds.collectAsState()
  val roadmap = viewModel.getRoadmap()
  var expandedStage by remember { mutableStateOf<Int?>(2) } // Stage 2 expanded by default

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .verticalScroll(rememberScrollState())
      .testTag("roadmap_screen")
  ) {
    // Header
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)) {
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
        text = "Personalized Learning Roadmap",
        fontSize = 22.sp,
        fontWeight = FontWeight.ExtraBold,
        color = TextPrimary
      )

      Text(
        text = "Follow a structured path to achieve your career goals.",
        fontSize = 13.sp,
        color = TextSecondary,
        modifier = Modifier.padding(top = 4.dp)
      )
    }

    // Overall Progress Card
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 8.dp),
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Row(
        modifier = Modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(80.dp)) {
          CircularProgressIndicator(
            progress = { roadmap.overallProgress / 100f },
            modifier = Modifier.fillMaxSize(),
            color = AccentSuccess,
            trackColor = Color(0xFFE2E8F0),
            strokeWidth = 7.dp
          )
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "${roadmap.overallProgress}%",
              fontSize = 20.sp,
              fontWeight = FontWeight.ExtraBold,
              color = TextPrimary
            )
          }
        }

        Spacer(modifier = Modifier.width(20.dp))

        Column {
          Text(
            text = "Overall Progress",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "${roadmap.completedTasksCount} of ${roadmap.totalTasksCount} tasks completed",
            fontSize = 13.sp,
            color = TextSecondary
          )
          Spacer(modifier = Modifier.height(6.dp))
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFE0F2FE)
          ) {
            Text(
              text = "Target: ${roadmap.targetRole}",
              color = SkyBlueDark,
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
          }
        }
      }
    }

    // Roadmap Stages
    Text(
      text = "Roadmap Stages",
      fontSize = 17.sp,
      fontWeight = FontWeight.Bold,
      color = TextPrimary,
      modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
    )

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      roadmap.stages.forEach { stage ->
        StageCard(
          stage = stage,
          isExpanded = expandedStage == stage.stageNumber,
          onToggleExpand = {
            expandedStage = if (expandedStage == stage.stageNumber) null else stage.stageNumber
          },
          onToggleTask = { taskId, completed ->
            viewModel.toggleRoadmapTask(taskId, completed)
          }
        )
      }
    }

    // Next Milestone Card
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 16.dp),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2FE))
    ) {
      Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(shape = RoundedCornerShape(12.dp), color = SkyBluePrimary, modifier = Modifier.size(44.dp)) {
          Box(contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Flag, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
          }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
          Text("Next Milestone", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = SkyBlueDark)
          Text(roadmap.nextMilestone, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = TextPrimary)
          Text("${roadmap.remainingMilestoneTasks} tasks remaining", fontSize = 12.sp, color = TextSecondary)
        }

        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = SkyBluePrimary)
      }
    }

    Spacer(modifier = Modifier.height(20.dp))
  }
}

@Composable
fun StageCard(
  stage: RoadmapStage,
  isExpanded: Boolean,
  onToggleExpand: () -> Unit,
  onToggleTask: (taskId: String, completed: Boolean) -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("stage_card_${stage.stageNumber}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onToggleExpand() },
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = CircleShape,
          color = when (stage.status) {
            StageStatus.COMPLETED -> AccentSuccess
            StageStatus.IN_PROGRESS -> SkyBluePrimary
            StageStatus.PENDING -> Color(0xFF94A3B8)
          },
          modifier = Modifier.size(36.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            if (stage.status == StageStatus.COMPLETED) {
              Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            } else {
              Text("${stage.stageNumber}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
          }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
          Text(stage.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
          Text(stage.subtitle, fontSize = 12.sp, color = TextSecondary)
        }

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = when (stage.status) {
            StageStatus.COMPLETED -> AccentSuccessContainer
            StageStatus.IN_PROGRESS -> Color(0xFFE0F2FE)
            StageStatus.PENDING -> Color(0xFFF1F5F9)
          }
        ) {
          Text(
            text = when (stage.status) {
              StageStatus.COMPLETED -> "✓ Completed"
              StageStatus.IN_PROGRESS -> "● In Progress"
              StageStatus.PENDING -> "Pending"
            },
            color = when (stage.status) {
              StageStatus.COMPLETED -> AccentSuccess
              StageStatus.IN_PROGRESS -> SkyBluePrimary
              StageStatus.PENDING -> TextMuted
            },
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
          )
        }

        Spacer(modifier = Modifier.width(6.dp))

        Icon(
          imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
          contentDescription = null,
          tint = TextSecondary
        )
      }

      // Expandable Tasks list
      AnimatedVisibility(visible = isExpanded) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          stage.tasks.forEach { task ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleTask(task.id, !task.isCompleted) }
                .padding(vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = if (task.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                tint = if (task.isCompleted) AccentSuccess else Color(0xFFCBD5E1),
                modifier = Modifier.size(22.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = task.title,
                fontSize = 13.sp,
                color = if (task.isCompleted) TextMuted else TextPrimary,
                fontWeight = if (task.isCompleted) FontWeight.Normal else FontWeight.Medium,
                modifier = Modifier.weight(1f)
              )
              Text("${task.durationMinutes}m", fontSize = 11.sp, color = TextMuted)
            }
          }
        }
      }
    }
  }
}
