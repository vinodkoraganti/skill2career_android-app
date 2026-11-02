package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.GeminiAiService
import com.example.data.local.EnrolledCourseEntity
import com.example.data.local.QuizAttemptEntity
import com.example.data.local.SavedCourseEntity
import com.example.data.local.Skill2CareerDatabase
import com.example.data.model.AccountType
import com.example.data.model.CareerPath
import com.example.data.model.Course
import com.example.data.model.JobOpportunity
import com.example.data.model.LearningRoadmap
import com.example.data.model.Quiz
import com.example.data.model.QuizResult
import com.example.data.model.ResumeAnalysisResult
import com.example.data.model.SkillGapData
import com.example.data.model.UserProfile
import com.example.data.remote.FirestoreManager
import com.example.data.repository.Skill2CareerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class ScreenDestination {
  object Splash : ScreenDestination()
  object Onboarding : ScreenDestination()
  object Login : ScreenDestination()
  object RegisterChoice : ScreenDestination()
  object RegisterWizard : ScreenDestination()
  object MainApp : ScreenDestination()
}

enum class MainTab {
  HOME,
  CAREERS,
  ROADMAP,
  JOBS,
  PROFILE
}

enum class SubScreen {
  NONE,
  SKILL_GAP,
  RESUME_ANALYSIS,
  COURSES_LIST,
  QUIZ_LIST,
  QUIZ_PLAYER,
  PROGRESS_DASHBOARD,
  ADMIN_PANEL
}

class Skill2CareerViewModel(application: Application) : AndroidViewModel(application) {

  private val database = Skill2CareerDatabase.getDatabase(application)
  val firestoreManager = FirestoreManager(application)
  private val repository = Skill2CareerRepository(database, firestoreManager)
  private val aiService = GeminiAiService()

  private val _firestoreCourses = MutableStateFlow<List<Course>>(emptyList())

  // Navigation states
  private val _currentScreen = MutableStateFlow<ScreenDestination>(ScreenDestination.Splash)
  val currentScreen: StateFlow<ScreenDestination> = _currentScreen.asStateFlow()

  private val _activeTab = MutableStateFlow(MainTab.HOME)
  val activeTab: StateFlow<MainTab> = _activeTab.asStateFlow()

  private val _activeSubScreen = MutableStateFlow(SubScreen.NONE)
  val activeSubScreen: StateFlow<SubScreen> = _activeSubScreen.asStateFlow()

  // User Profile
  val userProfile: StateFlow<UserProfile?> = repository.getUserProfileFlow()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

  // Registration state
  private val _registrationStep = MutableStateFlow(1)
  val registrationStep: StateFlow<Int> = _registrationStep.asStateFlow()

  private val _selectedAccountType = MutableStateFlow(AccountType.STUDENT)
  val selectedAccountType: StateFlow<AccountType> = _selectedAccountType.asStateFlow()

  // Careers & Filters
  private val _selectedCareerCategory = MutableStateFlow("All")
  val selectedCareerCategory: StateFlow<String> = _selectedCareerCategory.asStateFlow()

  private val _selectedCareerPath = MutableStateFlow<CareerPath?>(null)
  val selectedCareerPath: StateFlow<CareerPath?> = _selectedCareerPath.asStateFlow()

  // Skill Gap Role
  private val _selectedRoleForSkillGap = MutableStateFlow("AI/ML Engineer")
  val selectedRoleForSkillGap: StateFlow<String> = _selectedRoleForSkillGap.asStateFlow()

  // Completed roadmap tasks
  private val _completedTaskIds = MutableStateFlow<Set<String>>(setOf("t1_1", "t1_2", "t1_3", "t1_4", "t2_1", "t2_2", "t2_3"))
  val completedTaskIds: StateFlow<Set<String>> = _completedTaskIds.asStateFlow()

  // Courses
  val enrolledCourses: StateFlow<List<EnrolledCourseEntity>> = repository.getEnrolledCoursesFlow()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val savedCourses: StateFlow<List<SavedCourseEntity>> = repository.getSavedCoursesFlow()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  private val _courseSearchQuery = MutableStateFlow("")
  val courseSearchQuery: StateFlow<String> = _courseSearchQuery.asStateFlow()

  private val _selectedCourseCategory = MutableStateFlow("All")
  val selectedCourseCategory: StateFlow<String> = _selectedCourseCategory.asStateFlow()

  // Custom courses added by admin
  private val _adminCustomCourses = MutableStateFlow<List<Course>>(emptyList())

  // Quiz state
  private val _activeQuiz = MutableStateFlow<Quiz?>(null)
  val activeQuiz: StateFlow<Quiz?> = _activeQuiz.asStateFlow()

  private val _currentQuestionIndex = MutableStateFlow(0)
  val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

  private val _userSelectedAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
  val userSelectedAnswers: StateFlow<Map<Int, Int>> = _userSelectedAnswers.asStateFlow()

  private val _quizResult = MutableStateFlow<QuizResult?>(null)
  val quizResult: StateFlow<QuizResult?> = _quizResult.asStateFlow()

  val pastQuizAttempts: StateFlow<List<QuizAttemptEntity>> = repository.getQuizAttemptsFlow()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Resume analysis state
  private val _isAnalyzingResume = MutableStateFlow(false)
  val isAnalyzingResume: StateFlow<Boolean> = _isAnalyzingResume.asStateFlow()

  private val _resumeAnalysisResult = MutableStateFlow<ResumeAnalysisResult?>(null)
  val resumeAnalysisResult: StateFlow<ResumeAnalysisResult?> = _resumeAnalysisResult.asStateFlow()

  // Notifications popup
  private val _showNotifications = MutableStateFlow(false)
  val showNotifications: StateFlow<Boolean> = _showNotifications.asStateFlow()

  init {
    viewModelScope.launch {
      repository.ensureDefaultUserInitialized()
    }
    viewModelScope.launch {
      firestoreManager.getCoursesRealtimeFlow().collect { cloudCourses ->
        _firestoreCourses.value = cloudCourses
      }
    }
  }

  // Navigation handlers
  fun navigateTo(destination: ScreenDestination) {
    _currentScreen.value = destination
  }

  fun selectTab(tab: MainTab) {
    _activeTab.value = tab
    _activeSubScreen.value = SubScreen.NONE
  }

  fun openSubScreen(subScreen: SubScreen) {
    _activeSubScreen.value = subScreen
  }

  fun closeSubScreen() {
    _activeSubScreen.value = SubScreen.NONE
  }

  fun toggleNotifications() {
    _showNotifications.value = !_showNotifications.value
  }

  // Registration Wizard
  fun startRegistration(accountType: AccountType) {
    _selectedAccountType.value = accountType
    _registrationStep.value = 1
    _currentScreen.value = ScreenDestination.RegisterWizard
  }

  fun nextRegistrationStep() {
    if (_registrationStep.value < 4) {
      _registrationStep.value += 1
    } else {
      finishRegistration()
    }
  }

  fun prevRegistrationStep() {
    if (_registrationStep.value > 1) {
      _registrationStep.value -= 1
    } else {
      _currentScreen.value = ScreenDestination.RegisterChoice
    }
  }

  fun finishRegistration() {
    viewModelScope.launch {
      val isStudent = _selectedAccountType.value == AccountType.STUDENT
      val newUser = UserProfile(
        uid = "usr_" + System.currentTimeMillis(),
        fullName = if (isStudent) "Vinod Koraganti" else "Kumar",
        email = if (isStudent) "vinodkoraganti@gmail.com" else "kumar.pro@example.com",
        phone = "+91 98765 43210",
        accountType = _selectedAccountType.value,
        educationLevel = if (isStudent) "B.Tech" else "B.Tech & Working",
        college = if (isStudent) "Global Institute of Technology" else "NIT Surathkal",
        branch = "Computer Science",
        graduationYear = if (isStudent) "2024" else "2021",
        preferredLocation = "Bengaluru, India",
        currentRole = if (isStudent) "Student" else "Software Developer (3 yrs exp)",
        experienceYears = if (isStudent) "0-1 years" else "3 years",
        targetRole = if (isStudent) "AI/ML Engineer" else "Cloud Engineer",
        careerGoal = if (isStudent) "Learn advanced ML and build real-world AI projects" else "Advance into Cloud Architecture and DevOps",
        technicalSkills = if (isStudent) listOf("Python", "SQL", "Pandas", "NumPy", "Git") else listOf("Java", "Spring Boot", "AWS", "Git", "Docker"),
        careerInterests = if (isStudent) listOf("Artificial Intelligence", "Data Science") else listOf("Cloud Architecture", "DevOps", "Kubernetes"),
        profileCompletionPercentage = if (isStudent) 75 else 85,
        resumeFileName = if (isStudent) "Vinod_Koraganti_Resume.pdf" else "Kumar_CV.pdf",
        resumeSummary = if (isStudent) "Enthusiastic graduate with AI/ML foundations" else "Software Developer with 3 years of enterprise Java and Cloud experience",
        isAdmin = false
      )
      repository.updateUserProfile(newUser)
      _currentScreen.value = ScreenDestination.MainApp
    }
  }

  fun login(email: String, pass: String) {
    viewModelScope.launch {
      repository.ensureDefaultUserInitialized()
      _currentScreen.value = ScreenDestination.MainApp
    }
  }

  fun logout() {
    viewModelScope.launch {
      _currentScreen.value = ScreenDestination.Login
    }
  }

  // Career Management
  fun filterCareerCategory(cat: String) {
    _selectedCareerCategory.value = cat
  }

  fun selectCareerPath(path: CareerPath?) {
    _selectedCareerPath.value = path
  }

  fun getAllCareers(): List<CareerPath> {
    val all = repository.getCareerPaths()
    val filter = _selectedCareerCategory.value
    return if (filter == "All") all else all.filter { it.category.equals(filter, ignoreCase = true) || it.title.contains(filter, ignoreCase = true) }
  }

  // Skill Gap
  fun selectSkillGapRole(role: String) {
    _selectedRoleForSkillGap.value = role
  }

  fun getCurrentSkillGap(): SkillGapData {
    val user = userProfile.value
    val skills = user?.technicalSkills ?: emptyList()
    return repository.getSkillGapForRole(_selectedRoleForSkillGap.value, skills)
  }

  // Roadmap
  fun getRoadmap(): LearningRoadmap {
    val role = userProfile.value?.targetRole ?: "AI/ML Engineer"
    return repository.getRoadmapForRole(role, _completedTaskIds.value)
  }

  fun toggleRoadmapTask(taskId: String, completed: Boolean) {
    val current = _completedTaskIds.value.toMutableSet()
    if (completed) current.add(taskId) else current.remove(taskId)
    _completedTaskIds.value = current

    viewModelScope.launch {
      val role = userProfile.value?.targetRole ?: "AI/ML Engineer"
      repository.toggleTaskCompleted(taskId, role, completed)
    }
  }

  // Courses
  fun setCourseSearch(query: String) {
    _courseSearchQuery.value = query
  }

  fun setCourseCategory(category: String) {
    _selectedCourseCategory.value = category
  }

  fun getCourses(): List<Course> {
    val list = (repository.getAvailableCourses() + _firestoreCourses.value + _adminCustomCourses.value)
      .distinctBy { it.id }
    val query = _courseSearchQuery.value.trim().lowercase()
    val category = _selectedCourseCategory.value

    return list.filter { course ->
      val matchesCategory = category == "All" || course.category.equals(category, ignoreCase = true) || (category == "Free" && !course.isPaid) || (category == "Paid" && course.isPaid)
      val matchesQuery = query.isEmpty() || course.title.lowercase().contains(query) || course.tags.any { it.lowercase().contains(query) } || course.provider.lowercase().contains(query)
      matchesCategory && matchesQuery
    }
  }

  fun enrollInCourse(course: Course) {
    viewModelScope.launch {
      repository.enrollInCourse(course)
    }
  }

  fun toggleSaveCourse(courseId: String, isCurrentlySaved: Boolean) {
    viewModelScope.launch {
      repository.toggleSaveCourse(courseId, !isCurrentlySaved)
    }
  }

  fun addAdminCourse(course: Course) {
    _adminCustomCourses.value = _adminCustomCourses.value + course
    viewModelScope.launch {
      firestoreManager.addCourseToFirestore(course)
    }
  }

  // Quizzes
  fun getQuizzes(): List<Quiz> = repository.getAvailableQuizzes()

  fun startQuiz(quiz: Quiz) {
    _activeQuiz.value = quiz
    _currentQuestionIndex.value = 0
    _userSelectedAnswers.value = emptyMap()
    _quizResult.value = null
    _activeSubScreen.value = SubScreen.QUIZ_PLAYER
  }

  fun selectQuizAnswer(questionIndex: Int, optionIndex: Int) {
    val map = _userSelectedAnswers.value.toMutableMap()
    map[questionIndex] = optionIndex
    _userSelectedAnswers.value = map
  }

  fun nextQuizQuestion() {
    val quiz = _activeQuiz.value ?: return
    if (_currentQuestionIndex.value < quiz.questions.size - 1) {
      _currentQuestionIndex.value += 1
    }
  }

  fun prevQuizQuestion() {
    if (_currentQuestionIndex.value > 0) {
      _currentQuestionIndex.value -= 1
    }
  }

  fun submitQuiz() {
    val quiz = _activeQuiz.value ?: return
    val answers = _userSelectedAnswers.value
    var correctCount = 0

    quiz.questions.forEachIndexed { index, q ->
      val selected = answers[index]
      if (selected == q.correctOptionIndex) {
        correctCount++
      }
    }

    val percentage = if (quiz.questions.isNotEmpty()) (correctCount * 100) / quiz.questions.size else 0
    val result = QuizResult(
      quizId = quiz.id,
      quizTitle = quiz.title,
      score = correctCount,
      totalQuestions = quiz.questions.size,
      percentage = percentage
    )
    _quizResult.value = result

    viewModelScope.launch {
      repository.saveQuizResult(result)
    }
  }

  // Resume Analysis
  fun analyzeUploadedResume(customText: String? = null) {
    viewModelScope.launch {
      _isAnalyzingResume.value = true
      val resumeContent = customText ?: """
        Vinod Koraganti
        Email: vinodkoraganti@gmail.com | Phone: +91 98765 43210
        Education: B.Tech in Computer Science, Global Institute of Technology (2020-2024), CGPA: 8.7
        Technical Skills: Python, SQL, Pandas, NumPy, Machine Learning, Scikit-Learn, Git, TensorFlow basics.
        Experience: 2 years 3 months as Software Developer at ABC Tech Solutions.
        Projects: Built an end-to-end sentiment classification API and exploratory sales forecasting model.
      """.trimIndent()

      val result = aiService.analyzeResume(resumeContent)
      _resumeAnalysisResult.value = result
      _isAnalyzingResume.value = false
    }
  }

  // Jobs
  fun getJobOpportunities(): List<JobOpportunity> = repository.getJobOpportunities()

  // Profile Updates
  fun updateUserProfile(updated: UserProfile) {
    viewModelScope.launch {
      repository.updateUserProfile(updated)
    }
  }

  fun toggleAdminMode() {
    val user = userProfile.value ?: return
    val updated = user.copy(isAdmin = !user.isAdmin)
    updateUserProfile(updated)
  }
}
