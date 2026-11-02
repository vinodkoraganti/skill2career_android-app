package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AccountType
import com.example.ui.theme.SkyBlueContainer
import com.example.ui.theme.SkyBlueDark
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun LoginScreen(
  onLoginSuccess: (email: String, pass: String) -> Unit,
  onNavigateToRegisterChoice: () -> Unit
) {
  var email by remember { mutableStateOf("vinodkoraganti@gmail.com") }
  var password by remember { mutableStateOf("password123") }
  var passwordVisible by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .statusBarsPadding()
      .navigationBarsPadding()
      .padding(horizontal = 24.dp)
      .verticalScroll(rememberScrollState())
      .testTag("login_screen"),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(modifier = Modifier.height(36.dp))

    // Skill2Career Logo & Header
    Surface(
      modifier = Modifier.size(76.dp),
      shape = RoundedCornerShape(20.dp),
      color = Color(0xFFE0F2FE)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          imageVector = Icons.Default.School,
          contentDescription = "Logo",
          tint = SkyBluePrimary,
          modifier = Modifier.size(46.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = "Skill2Career",
      fontSize = 28.sp,
      fontWeight = FontWeight.ExtraBold,
      color = TextPrimary
    )

    Text(
      text = "Your Career Starts with the Right Skills",
      fontSize = 13.sp,
      fontWeight = FontWeight.Medium,
      color = SkyBlueDark
    )

    Spacer(modifier = Modifier.height(32.dp))

    Text(
      text = "Welcome Back!",
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      color = TextPrimary
    )

    Text(
      text = "Login to continue your personalized dashboard",
      fontSize = 14.sp,
      color = TextSecondary,
      modifier = Modifier.padding(top = 4.dp)
    )

    Spacer(modifier = Modifier.height(28.dp))

    // Email Input
    OutlinedTextField(
      value = email,
      onValueChange = { email = it },
      label = { Text("Email Address") },
      leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = SkyBluePrimary) },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("login_email_input"),
      shape = RoundedCornerShape(12.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = SkyBluePrimary,
        unfocusedBorderColor = Color(0xFFCBD5E1),
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
      ),
      singleLine = true
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Password Input
    OutlinedTextField(
      value = password,
      onValueChange = { password = it },
      label = { Text("Password") },
      leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = SkyBluePrimary) },
      trailingIcon = {
        IconButton(onClick = { passwordVisible = !passwordVisible }) {
          Icon(
            if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
            contentDescription = "Toggle password",
            tint = TextSecondary
          )
        }
      },
      visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("login_password_input"),
      shape = RoundedCornerShape(12.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = SkyBluePrimary,
        unfocusedBorderColor = Color(0xFFCBD5E1),
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
      ),
      singleLine = true
    )

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.End
    ) {
      TextButton(onClick = { /* Password reset simulated */ }) {
        Text("Forgot Password?", color = SkyBluePrimary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Login Button
    Button(
      onClick = { onLoginSuccess(email, password) },
      modifier = Modifier
        .fillMaxWidth()
        .height(52.dp)
        .testTag("login_submit_button"),
      shape = RoundedCornerShape(12.dp),
      colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
    ) {
      Text("Login", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }

    Spacer(modifier = Modifier.height(24.dp))

    // Social Divider
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE2E8F0))
      Text("  OR  ", color = TextMuted, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
      HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE2E8F0))
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Google Sign In Button
    OutlinedButton(
      onClick = { onLoginSuccess(email, password) },
      modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .testTag("google_login_button"),
      shape = RoundedCornerShape(12.dp),
      colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
    ) {
      Text("G  ", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = Color(0xFFEA4335))
      Text("Continue with Google", fontWeight = FontWeight.SemiBold, color = TextPrimary)
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Apple / Demo Sign In Button
    OutlinedButton(
      onClick = { onLoginSuccess(email, password) },
      modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .testTag("apple_login_button"),
      shape = RoundedCornerShape(12.dp),
      colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
    ) {
      Text("  ", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
      Text("Continue with Apple", fontWeight = FontWeight.SemiBold, color = TextPrimary)
    }

    Spacer(modifier = Modifier.height(24.dp))

    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      Text("Don't have an account?", color = TextSecondary, fontSize = 14.sp)
      TextButton(
        onClick = onNavigateToRegisterChoice,
        modifier = Modifier.testTag("go_to_register_button")
      ) {
        Text("Register", color = SkyBluePrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}

@Composable
fun RegisterChoiceScreen(
  onSelectAccountType: (AccountType) -> Unit,
  onBackToLogin: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .statusBarsPadding()
      .navigationBarsPadding()
      .padding(horizontal = 24.dp)
      .testTag("register_choice_screen")
  ) {
    IconButton(
      onClick = onBackToLogin,
      modifier = Modifier.padding(top = 8.dp)
    ) {
      Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {
      Surface(
        modifier = Modifier.size(44.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFE0F2FE)
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(Icons.Default.School, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(28.dp))
        }
      }
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        Text("Skill2Career", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextPrimary)
        Text("Your Career Starts with the Right Skills", fontSize = 11.sp, color = SkyBlueDark)
      }
    }

    Spacer(modifier = Modifier.height(28.dp))

    Text(
      text = "Create Your Account",
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      color = TextPrimary
    )

    Text(
      text = "Choose how you want to use Skill2Career",
      fontSize = 14.sp,
      color = TextSecondary,
      modifier = Modifier.padding(top = 4.dp)
    )

    Spacer(modifier = Modifier.height(28.dp))

    // Student Card
    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .clickable { onSelectAccountType(AccountType.STUDENT) }
        .testTag("register_student_card"),
      shape = RoundedCornerShape(18.dp),
      color = Color.White,
      shadowElevation = 3.dp,
      border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFBAE6FD))
    ) {
      Row(
        modifier = Modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          modifier = Modifier.size(56.dp),
          shape = CircleShape,
          color = Color(0xFFE0F2FE)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(Icons.Default.School, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(32.dp))
          }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Register as a Student",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Explore careers, build skills, prepare for internships, and plan your future.",
            fontSize = 13.sp,
            color = TextSecondary,
            lineHeight = 18.sp
          )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Surface(
          shape = CircleShape,
          color = SkyBluePrimary,
          modifier = Modifier.size(36.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Professional Card
    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .clickable { onSelectAccountType(AccountType.PROFESSIONAL) }
        .testTag("register_professional_card"),
      shape = RoundedCornerShape(18.dp),
      color = Color.White,
      shadowElevation = 3.dp,
      border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFE2E8F0))
    ) {
      Row(
        modifier = Modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          modifier = Modifier.size(56.dp),
          shape = CircleShape,
          color = Color(0xFFEDE9FE)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(Icons.Default.BusinessCenter, contentDescription = null, tint = Color(0xFF7C3AED), modifier = Modifier.size(32.dp))
          }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Register as a Professional",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Upgrade your skills, explore new opportunities, and advance your career.",
            fontSize = 13.sp,
            color = TextSecondary,
            lineHeight = 18.sp
          )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Surface(
          shape = CircleShape,
          color = Color(0xFF7C3AED),
          modifier = Modifier.size(36.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
          }
        }
      }
    }

    Spacer(modifier = Modifier.weight(1f))

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 20.dp),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text("Already have an account?", color = TextSecondary, fontSize = 14.sp)
      TextButton(onClick = onBackToLogin) {
        Text("Login", color = SkyBluePrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
      }
    }
  }
}

@Composable
fun RegistrationWizardScreen(
  currentStep: Int,
  accountType: AccountType,
  onNextStep: () -> Unit,
  onPrevStep: () -> Unit,
  onFinish: () -> Unit
) {
  var fullName by remember { mutableStateOf("Vinod Koraganti") }
  var email by remember { mutableStateOf("vinodkoraganti@gmail.com") }
  var password by remember { mutableStateOf("password123") }
  var college by remember { mutableStateOf("Global Institute of Technology") }
  var branch by remember { mutableStateOf("Computer Science Engineering") }
  var gradYear by remember { mutableStateOf("2024") }
  var targetRole by remember { mutableStateOf("AI/ML Engineer") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .statusBarsPadding()
      .navigationBarsPadding()
      .padding(horizontal = 24.dp)
      .verticalScroll(rememberScrollState())
      .testTag("register_wizard_screen")
  ) {
    // Header
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onPrevStep) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
      }
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = if (accountType == AccountType.STUDENT) "Student Registration" else "Professional Registration",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Step indicators 1, 2, 3, 4
    val steps = listOf("Basic Info", "Education", "Skills & Goals", "Resume")
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      steps.forEachIndexed { index, name ->
        val stepNum = index + 1
        val isActive = stepNum == currentStep
        val isDone = stepNum < currentStep

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Surface(
            shape = CircleShape,
            color = when {
              isDone -> Color(0xFF10B981)
              isActive -> SkyBluePrimary
              else -> Color(0xFFE2E8F0)
            },
            modifier = Modifier.size(34.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              if (isDone) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
              } else {
                Text(
                  text = "$stepNum",
                  color = if (isActive) Color.White else TextSecondary,
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp
                )
              }
            }
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = name,
            fontSize = 11.sp,
            color = if (isActive) SkyBluePrimary else TextMuted,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
          )
        }

        if (index < steps.size - 1) {
          Box(
            modifier = Modifier
              .weight(1f)
              .height(2.dp)
              .background(if (stepNum < currentStep) Color(0xFF10B981) else Color(0xFFE2E8F0))
              .padding(horizontal = 4.dp)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(28.dp))

    // Step Contents
    when (currentStep) {
      1 -> {
        Text("Basic Information", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Text("Please enter your legal credentials to setup your account", fontSize = 13.sp, color = TextSecondary)
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
          value = fullName,
          onValueChange = { fullName = it },
          label = { Text("Full Name *") },
          modifier = Modifier.fillMaxWidth().testTag("reg_full_name"),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          label = { Text("Email Address *") },
          modifier = Modifier.fillMaxWidth().testTag("reg_email"),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
          value = password,
          onValueChange = { password = it },
          label = { Text("Password *") },
          visualTransformation = PasswordVisualTransformation(),
          modifier = Modifier.fillMaxWidth().testTag("reg_password"),
          shape = RoundedCornerShape(12.dp)
        )
      }
      2 -> {
        Text("Academic Background", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Text("Help us recommend learning paths aligned with your academic degree", fontSize = 13.sp, color = TextSecondary)
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
          value = college,
          onValueChange = { college = it },
          label = { Text("College or University *") },
          modifier = Modifier.fillMaxWidth().testTag("reg_college"),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
          value = branch,
          onValueChange = { branch = it },
          label = { Text("Branch / Specialization *") },
          modifier = Modifier.fillMaxWidth().testTag("reg_branch"),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
          value = gradYear,
          onValueChange = { gradYear = it },
          label = { Text("Graduation Year *") },
          modifier = Modifier.fillMaxWidth().testTag("reg_grad_year"),
          shape = RoundedCornerShape(12.dp)
        )
      }
      3 -> {
        Text("Skills & Career Goals", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Text("Choose your dream job target and select technical competencies", fontSize = 13.sp, color = TextSecondary)
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
          value = targetRole,
          onValueChange = { targetRole = it },
          label = { Text("Target Career Role *") },
          modifier = Modifier.fillMaxWidth().testTag("reg_target_role"),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Select your current skills:", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextPrimary)
        Spacer(modifier = Modifier.height(8.dp))
        val sampleSkills = listOf("Python", "SQL", "Java", "C++", "Pandas", "NumPy", "Git", "React")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          sampleSkills.take(4).forEach { skill ->
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = Color(0xFFE0F2FE),
              border = androidx.compose.foundation.BorderStroke(1.dp, SkyBluePrimary)
            ) {
              Text(
                text = "✓ $skill",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = SkyBlueDark,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
              )
            }
          }
        }
      }
      4 -> {
        Text("Resume & Finalize", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Text("Upload your resume to instantly extract technical skills with AI", fontSize = 13.sp, color = TextSecondary)
        Spacer(modifier = Modifier.height(20.dp))

        Surface(
          shape = RoundedCornerShape(16.dp),
          color = Color(0xFFF1F5F9),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFCBD5E1)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(Icons.Default.UploadFile, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(52.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Vinod_Koraganti_Resume_2024.pdf", fontWeight = FontWeight.Bold, color = TextPrimary)
            Text("Ready for AI skill-gap parsing", fontSize = 12.sp, color = TextSecondary)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(32.dp))

    // Navigation Buttons
    Button(
      onClick = {
        if (currentStep < 4) onNextStep() else onFinish()
      },
      modifier = Modifier
        .fillMaxWidth()
        .height(52.dp)
        .testTag("wizard_next_button"),
      shape = RoundedCornerShape(12.dp),
      colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
    ) {
      Text(
        text = if (currentStep == 4) "Complete Registration" else "Next Step",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
      )
    }

    Spacer(modifier = Modifier.height(20.dp))
  }
}
