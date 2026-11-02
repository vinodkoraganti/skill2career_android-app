package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.BusinessCenter
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SkyBlueContainer
import com.example.ui.theme.SkyBlueDark
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.TextMuted
import com.example.ui.viewmodel.MainTab
import com.example.ui.viewmodel.Skill2CareerViewModel
import com.example.ui.viewmodel.SubScreen

@Composable
fun MainScreen(viewModel: Skill2CareerViewModel) {
  val activeTab by viewModel.activeTab.collectAsState()
  val activeSubScreen by viewModel.activeSubScreen.collectAsState()

  // Handle Android Back Button: return to previous sub-screen or home tab
  BackHandler(enabled = activeSubScreen != SubScreen.NONE || activeTab != MainTab.HOME) {
    if (activeSubScreen != SubScreen.NONE) {
      viewModel.closeSubScreen()
    } else {
      viewModel.selectTab(MainTab.HOME)
    }
  }

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .statusBarsPadding(),
    bottomBar = {
      // Bottom Navigation Bar matching Screenshot 1 to 12
      if (activeSubScreen == SubScreen.NONE || activeSubScreen == SubScreen.COURSES_LIST) {
        NavigationBar(
          containerColor = Color.White,
          tonalElevation = 8.dp,
          modifier = Modifier.testTag("main_bottom_nav_bar")
        ) {
          NavigationBarItem(
            selected = activeTab == MainTab.HOME && activeSubScreen == SubScreen.NONE,
            onClick = { viewModel.selectTab(MainTab.HOME) },
            icon = {
              Icon(
                if (activeTab == MainTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                contentDescription = "Home"
              )
            },
            label = { Text("Home", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = SkyBluePrimary,
              selectedTextColor = SkyBluePrimary,
              indicatorColor = Color(0xFFE0F2FE),
              unselectedIconColor = TextMuted,
              unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("nav_home")
          )

          NavigationBarItem(
            selected = activeTab == MainTab.CAREERS && activeSubScreen == SubScreen.NONE,
            onClick = { viewModel.selectTab(MainTab.CAREERS) },
            icon = {
              Icon(
                if (activeTab == MainTab.CAREERS) Icons.Filled.Psychology else Icons.Outlined.Psychology,
                contentDescription = "Careers"
              )
            },
            label = { Text("Careers", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = SkyBluePrimary,
              selectedTextColor = SkyBluePrimary,
              indicatorColor = Color(0xFFE0F2FE),
              unselectedIconColor = TextMuted,
              unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("nav_careers")
          )

          NavigationBarItem(
            selected = activeTab == MainTab.ROADMAP && activeSubScreen == SubScreen.NONE,
            onClick = { viewModel.selectTab(MainTab.ROADMAP) },
            icon = {
              Icon(
                if (activeTab == MainTab.ROADMAP) Icons.Filled.Timeline else Icons.Outlined.Timeline,
                contentDescription = "Roadmap"
              )
            },
            label = { Text("Roadmap", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = SkyBluePrimary,
              selectedTextColor = SkyBluePrimary,
              indicatorColor = Color(0xFFE0F2FE),
              unselectedIconColor = TextMuted,
              unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("nav_roadmap")
          )

          NavigationBarItem(
            selected = activeTab == MainTab.JOBS && activeSubScreen == SubScreen.NONE,
            onClick = { viewModel.selectTab(MainTab.JOBS) },
            icon = {
              Icon(
                if (activeTab == MainTab.JOBS) Icons.Filled.BusinessCenter else Icons.Outlined.BusinessCenter,
                contentDescription = "Jobs"
              )
            },
            label = { Text("Jobs", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = SkyBluePrimary,
              selectedTextColor = SkyBluePrimary,
              indicatorColor = Color(0xFFE0F2FE),
              unselectedIconColor = TextMuted,
              unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("nav_jobs")
          )

          NavigationBarItem(
            selected = activeTab == MainTab.PROFILE && activeSubScreen == SubScreen.NONE,
            onClick = { viewModel.selectTab(MainTab.PROFILE) },
            icon = {
              Icon(
                if (activeTab == MainTab.PROFILE) Icons.Filled.Person else Icons.Outlined.Person,
                contentDescription = "Profile"
              )
            },
            label = { Text("Profile", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = SkyBluePrimary,
              selectedTextColor = SkyBluePrimary,
              indicatorColor = Color(0xFFE0F2FE),
              unselectedIconColor = TextMuted,
              unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("nav_profile")
          )
        }
      }
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      AnimatedContent(
        targetState = activeSubScreen,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "SubScreenTransition"
      ) { sub ->
        when (sub) {
          SubScreen.SKILL_GAP -> {
            SkillGapScreen(
              viewModel = viewModel,
              onBack = { viewModel.closeSubScreen() },
              onNavigateToRoadmap = {
                viewModel.closeSubScreen()
                viewModel.selectTab(MainTab.ROADMAP)
              }
            )
          }
          SubScreen.RESUME_ANALYSIS -> {
            ResumeScreen(
              viewModel = viewModel,
              onBack = { viewModel.closeSubScreen() }
            )
          }
          SubScreen.COURSES_LIST -> {
            CoursesScreen(
              viewModel = viewModel,
              onBack = { viewModel.closeSubScreen() }
            )
          }
          SubScreen.QUIZ_LIST -> {
            QuizListScreen(
              viewModel = viewModel,
              onBack = { viewModel.closeSubScreen() }
            )
          }
          SubScreen.QUIZ_PLAYER -> {
            QuizPlayerScreen(
              viewModel = viewModel,
              onClose = { viewModel.closeSubScreen() }
            )
          }
          SubScreen.PROGRESS_DASHBOARD -> {
            ProgressScreen(
              viewModel = viewModel,
              onBack = { viewModel.closeSubScreen() }
            )
          }
          SubScreen.ADMIN_PANEL -> {
            AdminScreen(
              viewModel = viewModel,
              onBack = { viewModel.closeSubScreen() }
            )
          }
          SubScreen.NONE -> {
            // Main Tabs
            when (activeTab) {
              MainTab.HOME -> HomeScreen(
                viewModel = viewModel,
                onNavigateToTab = { viewModel.selectTab(it) },
                onOpenSubScreen = { viewModel.openSubScreen(it) },
                onSelectCareer = {
                  viewModel.selectCareerPath(it)
                  viewModel.selectTab(MainTab.CAREERS)
                }
              )
              MainTab.CAREERS -> CareersScreen(
                viewModel = viewModel,
                onNavigateToTab = { viewModel.selectTab(it) }
              )
              MainTab.ROADMAP -> RoadmapScreen(
                viewModel = viewModel
              )
              MainTab.JOBS -> JobsScreen(
                viewModel = viewModel
              )
              MainTab.PROFILE -> ProfileScreen(
                viewModel = viewModel,
                onOpenSubScreen = { viewModel.openSubScreen(it) }
              )
            }
          }
        }
      }
    }
  }
}
