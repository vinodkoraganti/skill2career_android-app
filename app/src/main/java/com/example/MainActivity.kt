package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MainScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.RegisterChoiceScreen
import com.example.ui.screens.RegistrationWizardScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.Skill2CareerTheme
import com.example.ui.viewmodel.ScreenDestination
import com.example.ui.viewmodel.Skill2CareerViewModel

class MainActivity : ComponentActivity() {
  private val viewModel: Skill2CareerViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Skill2CareerTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF8FAFC)) {
          Skill2CareerApp(viewModel)
        }
      }
    }
  }
}

@Composable
fun Skill2CareerApp(viewModel: Skill2CareerViewModel) {
  val currentScreen by viewModel.currentScreen.collectAsState()
  val regStep by viewModel.registrationStep.collectAsState()
  val selectedAccountType by viewModel.selectedAccountType.collectAsState()

  AnimatedContent(
    targetState = currentScreen,
    transitionSpec = { fadeIn() togetherWith fadeOut() },
    label = "ScreenNavigation"
  ) { screen ->
    when (screen) {
      is ScreenDestination.Splash -> {
        SplashScreen(
          onSplashFinished = {
            viewModel.navigateTo(ScreenDestination.Onboarding)
          }
        )
      }
      is ScreenDestination.Onboarding -> {
        OnboardingScreen(
          onFinishOnboarding = {
            viewModel.navigateTo(ScreenDestination.RegisterChoice)
          },
          onLoginClick = {
            viewModel.navigateTo(ScreenDestination.Login)
          }
        )
      }
      is ScreenDestination.Login -> {
        LoginScreen(
          onLoginSuccess = { email, password ->
            viewModel.login(email, password)
          },
          onNavigateToRegisterChoice = {
            viewModel.navigateTo(ScreenDestination.RegisterChoice)
          }
        )
      }
      is ScreenDestination.RegisterChoice -> {
        RegisterChoiceScreen(
          onSelectAccountType = { accountType ->
            viewModel.startRegistration(accountType)
          },
          onBackToLogin = {
            viewModel.navigateTo(ScreenDestination.Login)
          }
        )
      }
      is ScreenDestination.RegisterWizard -> {
        RegistrationWizardScreen(
          currentStep = regStep,
          accountType = selectedAccountType,
          onNextStep = { viewModel.nextRegistrationStep() },
          onPrevStep = { viewModel.prevRegistrationStep() },
          onFinish = { viewModel.finishRegistration() }
        )
      }
      is ScreenDestination.MainApp -> {
        MainScreen(viewModel = viewModel)
      }
    }
  }
}
