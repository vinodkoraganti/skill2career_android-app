package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CastForEducation
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.SkyBlueDark
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
  val scale = remember { Animatable(0.85f) }

  LaunchedEffect(Unit) {
    scale.animateTo(
      targetValue = 1.05f,
      animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
    )
    delay(1200)
    onSplashFinished()
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(
        brush = Brush.verticalGradient(
          colors = listOf(
            Color(0xFFE0F2FE),
            Color(0xFFBAE6FD),
            Color(0xFF0284C7)
          )
        )
      )
      .testTag("splash_screen"),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier
        .scale(scale.value)
        .padding(32.dp)
    ) {
      Surface(
        modifier = Modifier.size(110.dp),
        shape = RoundedCornerShape(28.dp),
        color = Color.White,
        shadowElevation = 12.dp
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(
            imageVector = Icons.Default.School,
            contentDescription = "Skill2Career Logo",
            tint = SkyBluePrimary,
            modifier = Modifier.size(68.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      Text(
        text = "Skill2Career",
        fontSize = 34.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color(0xFF0F172A),
        letterSpacing = (-0.5).sp
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Your Career Starts with the Right Skills.",
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF0369A1),
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(48.dp))

      CircularProgressIndicator(
        modifier = Modifier.size(36.dp),
        color = Color.White,
        strokeWidth = 3.dp
      )
    }
  }
}

data class OnboardingPage(
  val title: String,
  val description: String,
  val icon: ImageVector,
  val tag: String
)

@Composable
fun OnboardingScreen(
  onFinishOnboarding: () -> Unit,
  onLoginClick: () -> Unit
) {
  val pages = listOf(
    OnboardingPage(
      title = "Discover Your Career Path",
      description = "Explore high-demand tech careers in AI/ML, Cloud, Data Science, and Software Development tailored to your interests.",
      icon = Icons.Default.Psychology,
      tag = "AI-Powered Guidance"
    ),
    OnboardingPage(
      title = "Identify Your Skill Gaps",
      description = "Compare your current skillset with real industry job requirements to know exactly what you need to master next.",
      icon = Icons.Default.CheckCircle,
      tag = "Skill-Gap Analysis"
    ),
    OnboardingPage(
      title = "Personalized Roadmaps",
      description = "Follow step-by-step learning stages from Foundations to Career-Ready projects with curated tutorials.",
      icon = Icons.Default.Timeline,
      tag = "Structured Learning"
    ),
    OnboardingPage(
      title = "Job-Ready with Quizzes & Certs",
      description = "Test your proficiency with interactive technical assessments and enroll in verified industry courses.",
      icon = Icons.Default.Work,
      tag = "Assess & Achieve"
    )
  )

  val pagerState = rememberPagerState(pageCount = { pages.size })
  val scope = rememberCoroutineScope()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .statusBarsPadding()
      .navigationBarsPadding()
      .testTag("onboarding_screen")
  ) {
    // Top Bar with Skip
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 12.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.School,
          contentDescription = null,
          tint = SkyBluePrimary,
          modifier = Modifier.size(26.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Skill2Career",
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp,
          color = TextPrimary
        )
      }

      TextButton(
        onClick = onFinishOnboarding,
        modifier = Modifier.testTag("skip_onboarding_button")
      ) {
        Text("Skip", color = TextSecondary, fontWeight = FontWeight.SemiBold)
      }
    }

    // Pager Content
    HorizontalPager(
      state = pagerState,
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
    ) { pageIndex ->
      val page = pages[pageIndex]
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Surface(
          modifier = Modifier.size(150.dp),
          shape = CircleShape,
          color = Color(0xFFE0F2FE),
          shadowElevation = 4.dp
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = page.icon,
              contentDescription = null,
              tint = SkyBluePrimary,
              modifier = Modifier.size(72.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Surface(
          shape = RoundedCornerShape(16.dp),
          color = Color(0xFFBAE6FD).copy(alpha = 0.5f)
        ) {
          Text(
            text = page.tag,
            color = SkyBlueDark,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = page.title,
          fontSize = 24.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = page.description,
          fontSize = 14.sp,
          color = TextSecondary,
          textAlign = TextAlign.Center,
          lineHeight = 22.sp
        )
      }
    }

    // Page Indicator Dots
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp),
      horizontalArrangement = Arrangement.Center
    ) {
      repeat(pages.size) { index ->
        val isSelected = pagerState.currentPage == index
        Box(
          modifier = Modifier
            .padding(horizontal = 4.dp)
            .height(8.dp)
            .width(if (isSelected) 24.dp else 8.dp)
            .clip(CircleShape)
            .background(if (isSelected) SkyBluePrimary else Color(0xFFCBD5E1))
        )
      }
    }

    // Bottom Action Buttons
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
      Button(
        onClick = {
          if (pagerState.currentPage < pages.size - 1) {
            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
          } else {
            onFinishOnboarding()
          }
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .testTag("onboarding_primary_button"),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
      ) {
        Text(
          text = if (pagerState.currentPage == pages.size - 1) "Get Started" else "Next",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowForward,
          contentDescription = null,
          tint = Color.White
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      OutlinedButton(
        onClick = onLoginClick,
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("onboarding_login_button"),
        shape = RoundedCornerShape(14.dp)
      ) {
        Text(
          text = "Already have an account? Login",
          color = SkyBluePrimary,
          fontWeight = FontWeight.SemiBold,
          fontSize = 14.sp
        )
      }
    }
  }
}
