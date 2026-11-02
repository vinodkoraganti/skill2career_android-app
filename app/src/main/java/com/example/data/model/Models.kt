package com.example.data.model

data class UserProfile(
  val uid: String = "user_default_1",
  val fullName: String = "Vinod Koraganti",
  val email: String = "vinodkoraganti@gmail.com",
  val phone: String = "+91 98765 43210",
  val accountType: AccountType = AccountType.STUDENT,
  val educationLevel: String = "B.Tech",
  val college: String = "Global Institute of Technology",
  val branch: String = "Computer Science",
  val graduationYear: String = "2024",
  val preferredLocation: String = "Bangalore, India",
  val currentRole: String = "Student / Aspiring Engineer",
  val experienceYears: String = "0 - 1 years",
  val targetRole: String = "AI/ML Engineer",
  val careerGoal: String = "Learn advanced ML and build real-world AI projects",
  val technicalSkills: List<String> = listOf("Python", "SQL", "Pandas", "NumPy", "Git", "Machine Learning"),
  val careerInterests: List<String> = listOf("Artificial Intelligence", "Data Science", "Software Engineering"),
  val profileCompletionPercentage: Int = 85,
  val resumeFileName: String? = "Vinod_Koraganti_Resume_2024.pdf",
  val resumeSummary: String? = "B.Tech in Computer Science with focus on Machine Learning and Python backend development.",
  val isAdmin: Boolean = false
)

enum class AccountType {
  STUDENT,
  PROFESSIONAL
}

data class CareerPath(
  val id: String,
  val title: String,
  val category: String, // Tech, Data, Security, Cloud, AI/ML
  val description: String,
  val matchPercentage: Int,
  val matchedSkills: List<String>,
  val missingSkills: List<String>,
  val requiredSkills: List<String>,
  val whyRecommended: String,
  val learningSequence: List<String>,
  val jobRoles: List<String>,
  val certifications: List<String>,
  val suggestedProjects: List<String>,
  val salaryRange: String
)

data class SkillGapData(
  val targetRole: String,
  val skillsHave: List<String>,
  val skillsToImprove: List<String>,
  val skillsToLearn: List<String>,
  val completionPercentage: Int = 65
)

data class RoadmapStage(
  val stageNumber: Int,
  val title: String,
  val subtitle: String,
  val durationHours: Int,
  val taskCount: Int,
  val status: StageStatus,
  val tasks: List<RoadmapTask>
)

enum class StageStatus {
  COMPLETED,
  IN_PROGRESS,
  PENDING
}

data class RoadmapTask(
  val id: String,
  val title: String,
  val durationMinutes: Int,
  val isCompleted: Boolean,
  val resourceUrl: String? = null
)

data class LearningRoadmap(
  val targetRole: String,
  val overallProgress: Int, // e.g. 35%
  val completedTasksCount: Int,
  val totalTasksCount: Int,
  val nextMilestone: String,
  val remainingMilestoneTasks: Int,
  val stages: List<RoadmapStage>
)

data class Course(
  val id: String,
  val title: String,
  val provider: String,
  val partner: String,
  val category: String,
  val difficulty: String, // Beginner, Intermediate, Advanced
  val duration: String, // e.g. "4 weeks"
  val isPaid: Boolean,
  val priceTag: String,
  val tags: List<String>,
  val description: String,
  val rating: Float = 4.8f,
  val enrolledCount: Int = 12400,
  val isEnrolled: Boolean = false,
  val progressPercentage: Int = 0,
  val syllabus: List<String> = emptyList(),
  val isPublished: Boolean = true
)

data class QuizQuestion(
  val id: String,
  val question: String,
  val options: List<String>,
  val correctOptionIndex: Int,
  val explanation: String
)

data class Quiz(
  val id: String,
  val title: String,
  val category: String,
  val durationMinutes: Int,
  val questions: List<QuizQuestion>
)

data class QuizResult(
  val quizId: String,
  val quizTitle: String,
  val score: Int,
  val totalQuestions: Int,
  val percentage: Int,
  val timestamp: Long = System.currentTimeMillis()
)

data class JobOpportunity(
  val id: String,
  val title: String,
  val company: String,
  val location: String,
  val isRemote: Boolean,
  val experienceLevel: String,
  val salaryRange: String,
  val requiredSkills: List<String>,
  val isVerified: Boolean = true
)

data class ResumeAnalysisResult(
  val extractedName: String,
  val extractedEmail: String,
  val extractedEducation: String,
  val extractedExperience: String,
  val technicalSkills: List<String>,
  val programmingLanguages: List<String>,
  val strengths: List<String>,
  val missingOrWeakSkills: List<String>,
  val recommendedCareerPaths: List<String>,
  val improvementSuggestions: List<String>
)
