package com.example.data.repository

import com.example.data.local.CompletedTaskEntity
import com.example.data.local.EnrolledCourseEntity
import com.example.data.local.QuizAttemptEntity
import com.example.data.local.SavedCourseEntity
import com.example.data.local.Skill2CareerDatabase
import com.example.data.local.UserEntity
import com.example.data.model.AccountType
import com.example.data.model.CareerPath
import com.example.data.model.Course
import com.example.data.model.JobOpportunity
import com.example.data.model.LearningRoadmap
import com.example.data.model.Quiz
import com.example.data.model.QuizQuestion
import com.example.data.model.QuizResult
import com.example.data.model.RoadmapStage
import com.example.data.model.RoadmapTask
import com.example.data.model.SkillGapData
import com.example.data.model.StageStatus
import com.example.data.model.UserProfile
import com.example.data.remote.FirestoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class Skill2CareerRepository(
  private val database: Skill2CareerDatabase,
  private val firestoreManager: FirestoreManager? = null
) {

  // Default initial user
  suspend fun ensureDefaultUserInitialized() {
    val existing = database.userDao().getActiveUser()
    if (existing == null) {
      val defaultUser = UserProfile(
        uid = "usr_101",
        fullName = "Vinod Koraganti",
        email = "vinodkoraganti@gmail.com",
        phone = "+91 98765 43210",
        accountType = AccountType.STUDENT,
        educationLevel = "B.Tech",
        college = "Global Institute of Technology",
        branch = "Computer Science",
        graduationYear = "2024",
        preferredLocation = "Bangalore, India",
        currentRole = "Student / Aspiring Engineer",
        experienceYears = "0-1 years",
        targetRole = "AI/ML Engineer",
        careerGoal = "Learn advanced ML and build real-world AI projects",
        technicalSkills = listOf("Python", "SQL", "Pandas", "NumPy", "Git", "Machine Learning"),
        careerInterests = listOf("Artificial Intelligence", "Data Science", "Software Engineering"),
        profileCompletionPercentage = 85,
        resumeFileName = "Vinod_Koraganti_Resume_2024.pdf",
        resumeSummary = "B.Tech CSE graduate with hands-on skills in Python, ML algorithms, and data structures.",
        isAdmin = false
      )
      database.userDao().insertUser(UserEntity.fromDomain(defaultUser))
      firestoreManager?.saveUserProfileToFirestore(defaultUser)
    }
  }

  fun getUserProfileFlow(): Flow<UserProfile?> {
    return database.userDao().getActiveUserFlow().map { it?.toDomain() }
  }

  suspend fun updateUserProfile(profile: UserProfile) {
    database.userDao().insertUser(UserEntity.fromDomain(profile))
    firestoreManager?.saveUserProfileToFirestore(profile)
  }

  suspend fun clearSession() {
    database.userDao().clearUsers()
  }

  // Career Paths
  fun getCareerPaths(): List<CareerPath> {
    return listOf(
      CareerPath(
        id = "career_aiml",
        title = "AI/ML Engineer",
        category = "AI/ML",
        description = "Build intelligent systems and neural networks using machine learning and AI.",
        matchPercentage = 96,
        matchedSkills = listOf("Python", "Machine Learning", "SQL", "Pandas", "NumPy"),
        missingSkills = listOf("Statistics", "Deep Learning", "TensorFlow", "MLOps"),
        requiredSkills = listOf("Python", "Mathematics", "Statistics", "NumPy", "Pandas", "Scikit-Learn", "Deep Learning", "TensorFlow / PyTorch", "MLOps"),
        whyRecommended = "Your background in Computer Science and proficiency in Python, NumPy, and SQL provides a direct foundation for Machine Learning engineering.",
        learningSequence = listOf(
          "1. Python & Linear Algebra foundations",
          "2. Exploratory Data Analysis with Pandas & Seaborn",
          "3. Classical Machine Learning with Scikit-Learn",
          "4. Deep Learning architectures (CNNs, Transformers) with PyTorch",
          "5. MLOps, Model Deployment & API Serving (FastAPI, Docker)"
        ),
        jobRoles = listOf("Machine Learning Engineer", "AI Developer", "Computer Vision Specialist", "NLP Engineer"),
        certifications = listOf("TensorFlow Developer Certificate", "AWS Certified Machine Learning - Specialty", "Google Cloud Professional ML Engineer"),
        suggestedProjects = listOf("End-to-end Sentiment Analysis Pipeline", "Object Detection with YOLOv8", "RAG-powered Knowledge Assistant with LLMs"),
        salaryRange = "₹8,00,000 - ₹24,00,000 / yr"
      ),
      CareerPath(
        id = "career_swe",
        title = "Software Developer",
        category = "Tech",
        description = "Design and build scalable software applications, backend services, and APIs.",
        matchPercentage = 88,
        matchedSkills = listOf("Java", "Python", "SQL", "Git", "Data Structures"),
        missingSkills = listOf("System Design", "Cloud Infrastructure", "Spring Boot"),
        requiredSkills = listOf("Data Structures & Algorithms", "Java / Kotlin / Go", "REST APIs", "System Design", "Microservices", "Docker", "Git"),
        whyRecommended = "Strong core algorithm skills and object-oriented programming fluency align well with high-demand software engineering roles.",
        learningSequence = listOf(
          "1. Advanced Data Structures & Algorithm Optimization",
          "2. Enterprise Backend Development (Spring Boot / Node / Go)",
          "3. Relational & NoSQL Database Design",
          "4. Distributed Systems & Microservices",
          "5. CI/CD & Cloud Native Deployment"
        ),
        jobRoles = listOf("Software Development Engineer (SDE I/II)", "Backend Engineer", "Systems Engineer"),
        certifications = listOf("Oracle Certified Professional Java", "AWS Certified Developer - Associate"),
        suggestedProjects = listOf("High-Throughput E-commerce API", "Distributed Message Queue Client", "Real-time Chat with WebSockets"),
        salaryRange = "₹6,50,000 - ₹20,00,000 / yr"
      ),
      CareerPath(
        id = "career_cloud",
        title = "Cloud Engineer",
        category = "Cloud",
        description = "Manage cloud infrastructure, automation, serverless systems, and cloud security.",
        matchPercentage = 82,
        matchedSkills = listOf("Linux", "Python", "Networking Basics", "Git"),
        missingSkills = listOf("AWS", "Docker", "Kubernetes", "Terraform"),
        requiredSkills = listOf("Linux OS", "AWS / Azure / GCP", "Docker & Kubernetes", "Terraform (IaC)", "CI/CD Pipelines", "Networking & Security"),
        whyRecommended = "Every tech enterprise requires robust cloud operations. Your Linux and scripting capabilities offer a rapid transition to DevOps and Cloud.",
        learningSequence = listOf(
          "1. Linux Administration & Shell Scripting",
          "2. Core Cloud Architecture (AWS / GCP)",
          "3. Containerization with Docker & Podman",
          "4. Orchestration with Kubernetes (EKS / GKE)",
          "5. Infrastructure as Code (Terraform) and GitOps"
        ),
        jobRoles = listOf("Cloud Engineer", "Cloud Architect", "Site Reliability Engineer (SRE)"),
        certifications = listOf("AWS Solutions Architect Associate", "CKA: Certified Kubernetes Administrator", "HashiCorp Certified: Terraform Associate"),
        suggestedProjects = listOf("Multi-tier AWS VPC Architecture", "Kubernetes Auto-scaling Cluster", "Automated Terraform Provisioning"),
        salaryRange = "₹7,00,000 - ₹22,00,000 / yr"
      ),
      CareerPath(
        id = "career_datasci",
        title = "Data Scientist",
        category = "Data",
        description = "Analyze complex data to find business insights and build predictive statistical models.",
        matchPercentage = 76,
        matchedSkills = listOf("Python", "SQL", "Pandas", "NumPy"),
        missingSkills = listOf("Machine Learning", "Statistics", "Tableau / PowerBI", "A/B Testing"),
        requiredSkills = listOf("Statistical Modeling", "Python / R", "SQL & Data Warehousing", "Tableau / PowerBI", "Feature Engineering", "Storytelling"),
        whyRecommended = "Your data analysis and querying experience are ideal for interpreting enterprise metrics and forecasting trends.",
        learningSequence = listOf(
          "1. Inferential Statistics & Probability",
          "2. Advanced SQL & BigQuery",
          "3. Feature Selection & Predictive Modeling",
          "4. Business Intelligence Dashboards",
          "5. Model Interpretability (SHAP, LIME)"
        ),
        jobRoles = listOf("Data Scientist", "Business Intelligence Analyst", "Quantitative Analyst"),
        certifications = listOf("IBM Data Science Professional Certificate", "Google Data Analytics Professional"),
        suggestedProjects = listOf("Customer Churn Prediction Model", "Interactive Sales Dashboard in Streamlit", "Market Basket Analysis"),
        salaryRange = "₹7,50,000 - ₹21,00,000 / yr"
      ),
      CareerPath(
        id = "career_cyber",
        title = "Cybersecurity Analyst",
        category = "Security",
        description = "Protect organizational systems, networks, and databases against cyber threats and vulnerabilities.",
        matchPercentage = 72,
        matchedSkills = listOf("Networking", "Linux", "Python"),
        missingSkills = listOf("Penetration Testing", "SIEM Tools", "Cryptography", "Security Compliance"),
        requiredSkills = listOf("Network Security", "Ethical Hacking", "SIEM (Splunk)", "Vulnerability Assessment", "Incident Response", "OWASP Top 10"),
        whyRecommended = "Crucial industry sector with high job security. Networking and Linux knowledge provide a seamless entry point.",
        learningSequence = listOf(
          "1. TCP/IP Protocols & Network Defense",
          "2. Linux / Windows Security Hardening",
          "3. Vulnerability Scanning & Penetration Testing",
          "4. SOC Monitoring & Incident Response with SIEM",
          "5. Cryptography & Cloud Security Frameworks"
        ),
        jobRoles = listOf("SOC Analyst", "Information Security Specialist", "Penetration Tester"),
        certifications = listOf("CompTIA Security+", "Certified Ethical Hacker (CEH)", "Cisco Certified CyberOps Associate"),
        suggestedProjects = listOf("Home Lab SIEM Implementation", "Web App Vulnerability Audit (DVWA)", "Network Traffic Analyzer in Python"),
        salaryRange = "₹6,00,000 - ₹18,00,000 / yr"
      ),
      CareerPath(
        id = "career_android",
        title = "Android Developer",
        category = "Tech",
        description = "Build cutting-edge native mobile experiences for billions of Android users worldwide.",
        matchPercentage = 84,
        matchedSkills = listOf("Java", "Kotlin", "Git", "OOP Concepts"),
        missingSkills = listOf("Jetpack Compose", "Coroutines", "Room Database", "Material 3"),
        requiredSkills = listOf("Kotlin", "Jetpack Compose", "Coroutines & Flow", "MVVM / Clean Architecture", "Room Database", "Retrofit", "Material 3"),
        whyRecommended = "Modern Android development with Jetpack Compose is highly productive and opens doors to top global mobile engineering teams.",
        learningSequence = listOf(
          "1. Kotlin Core, Generics & Coroutines",
          "2. Declarative UI with Jetpack Compose & Material 3",
          "3. State Management, ViewModel & Navigation",
          "4. Offline Persistence with Room & DataStore",
          "5. Network APIs with Retrofit & Play Store Publishing"
        ),
        jobRoles = listOf("Android Developer", "Mobile Software Engineer", "Android Architect"),
        certifications = listOf("Associate Android Developer (Google)", "Meta Android Developer Professional"),
        suggestedProjects = listOf("Offline-First Habit Tracker", "E-Commerce App with Compose", "Crypto Portfolio Tracker"),
        salaryRange = "₹6,00,000 - ₹19,00,000 / yr"
      )
    )
  }

  // Skill Gap Analysis for a given role
  fun getSkillGapForRole(role: String, userSkills: List<String>): SkillGapData {
    return when (role) {
      "AI/ML Engineer" -> SkillGapData(
        targetRole = "AI/ML Engineer",
        skillsHave = listOf("Python", "SQL", "Pandas", "NumPy", "Git"),
        skillsToImprove = listOf("Machine Learning", "Statistics", "Data Visualization", "Problem Solving"),
        skillsToLearn = listOf("Deep Learning", "Model Evaluation", "System Design", "TensorFlow / PyTorch"),
        completionPercentage = 65
      )
      "Software Developer" -> SkillGapData(
        targetRole = "Software Developer",
        skillsHave = listOf("Java", "Python", "SQL", "Git", "DSA Basics"),
        skillsToImprove = listOf("System Design", "Object-Oriented Design", "Unit Testing", "Debugging"),
        skillsToLearn = listOf("Microservices", "Docker", "CI/CD", "Redis / Caching"),
        completionPercentage = 70
      )
      "Cloud Engineer" -> SkillGapData(
        targetRole = "Cloud Engineer",
        skillsHave = listOf("Linux", "Python", "Git", "Networking Basics"),
        skillsToImprove = listOf("Bash Scripting", "AWS Core Services", "Security Groups"),
        skillsToLearn = listOf("Docker", "Kubernetes", "Terraform", "CloudFormation"),
        completionPercentage = 55
      )
      else -> SkillGapData(
        targetRole = role,
        skillsHave = listOf("Python", "SQL", "Git"),
        skillsToImprove = listOf("Algorithms", "Data Modeling"),
        skillsToLearn = listOf("Cloud Deployment", "Production Monitoring"),
        completionPercentage = 60
      )
    }
  }

  // Roadmap Stages
  fun getRoadmapForRole(role: String, completedTaskIds: Set<String>): LearningRoadmap {
    val tasks1 = listOf(
      RoadmapTask("t1_1", "Python Syntax & Object-Oriented Programming", 120, completedTaskIds.contains("t1_1")),
      RoadmapTask("t1_2", "Linear Algebra & Vectors Essentials", 90, completedTaskIds.contains("t1_2")),
      RoadmapTask("t1_3", "Git & GitHub Version Control Workflow", 60, completedTaskIds.contains("t1_3")),
      RoadmapTask("t1_4", "Data Cleaning with Pandas and NumPy", 120, completedTaskIds.contains("t1_4"))
    )
    val stage1Complete = tasks1.all { it.isCompleted }

    val tasks2 = listOf(
      RoadmapTask("t2_1", "Supervised Learning: Regression & Classification", 180, completedTaskIds.contains("t2_1")),
      RoadmapTask("t2_2", "Model Evaluation Metrics (Precision, Recall, ROC-AUC)", 120, completedTaskIds.contains("t2_2")),
      RoadmapTask("t2_3", "Unsupervised Learning: K-Means & PCA Clustering", 150, completedTaskIds.contains("t2_3")),
      RoadmapTask("t2_4", "Feature Engineering & Cross-Validation Strategies", 120, completedTaskIds.contains("t2_4")),
      RoadmapTask("t2_5", "Hyperparameter Tuning with Optuna & GridSearch", 90, completedTaskIds.contains("t2_5")),
      RoadmapTask("t2_6", "Building REST Prediction Endpoints with FastAPI", 120, completedTaskIds.contains("t2_6"))
    )
    val stage2Complete = tasks2.all { it.isCompleted }

    val tasks3 = listOf(
      RoadmapTask("t3_1", "Deep Learning Foundations: Artificial Neural Networks", 180, completedTaskIds.contains("t3_1")),
      RoadmapTask("t3_2", "Convolutional Neural Networks (CNNs) for Computer Vision", 240, completedTaskIds.contains("t3_2")),
      RoadmapTask("t3_3", "Transformer Architectures & Self-Attention Mechanisms", 240, completedTaskIds.contains("t3_3")),
      RoadmapTask("t3_4", "Fine-Tuning Open Source LLMs with LoRA / QLoRA", 200, completedTaskIds.contains("t3_4")),
      RoadmapTask("t3_5", "Retrieval-Augmented Generation (RAG) Systems", 180, completedTaskIds.contains("t3_5")),
      RoadmapTask("t3_6", "Vector Databases (Pinecone, ChromaDB, Milvus)", 120, completedTaskIds.contains("t3_6"))
    )
    val stage3Complete = tasks3.all { it.isCompleted }

    val tasks4 = listOf(
      RoadmapTask("t4_1", "Docker Containerization for Machine Learning Models", 150, completedTaskIds.contains("t4_1")),
      RoadmapTask("t4_2", "Deploying Models to AWS ECS & Lambda Serverless", 180, completedTaskIds.contains("t4_2")),
      RoadmapTask("t4_3", "CI/CD Automation Pipelines with GitHub Actions", 120, completedTaskIds.contains("t4_3")),
      RoadmapTask("t4_4", "End-to-End Capstone Project & Portfolio Presentation", 240, completedTaskIds.contains("t4_4"))
    )
    val stage4Complete = tasks4.all { it.isCompleted }

    val allTasks = tasks1 + tasks2 + tasks3 + tasks4
    val completedCount = allTasks.count { it.isCompleted }
    val totalCount = allTasks.size
    val progress = if (totalCount > 0) (completedCount * 100) / totalCount else 0

    val stages = listOf(
      RoadmapStage(
        stageNumber = 1,
        title = "Foundations",
        subtitle = "4 tasks • 8 hours",
        durationHours = 8,
        taskCount = 4,
        status = if (stage1Complete) StageStatus.COMPLETED else StageStatus.IN_PROGRESS,
        tasks = tasks1
      ),
      RoadmapStage(
        stageNumber = 2,
        title = "Core Skills",
        subtitle = "6 tasks • 18 hours",
        durationHours = 18,
        taskCount = 6,
        status = if (stage2Complete) StageStatus.COMPLETED else if (stage1Complete) StageStatus.IN_PROGRESS else StageStatus.PENDING,
        tasks = tasks2
      ),
      RoadmapStage(
        stageNumber = 3,
        title = "Advanced Topics",
        subtitle = "6 tasks • 20 hours",
        durationHours = 20,
        taskCount = 6,
        status = if (stage3Complete) StageStatus.COMPLETED else if (stage2Complete) StageStatus.IN_PROGRESS else StageStatus.PENDING,
        tasks = tasks3
      ),
      RoadmapStage(
        stageNumber = 4,
        title = "Career Ready",
        subtitle = "4 tasks • 12 hours",
        durationHours = 12,
        taskCount = 4,
        status = if (stage4Complete) StageStatus.COMPLETED else if (stage3Complete) StageStatus.IN_PROGRESS else StageStatus.PENDING,
        tasks = tasks4
      )
    )

    return LearningRoadmap(
      targetRole = role,
      overallProgress = progress,
      completedTasksCount = completedCount,
      totalTasksCount = totalCount,
      nextMilestone = if (!stage1Complete) "Complete Foundations" else if (!stage2Complete) "Complete Machine Learning Basics" else if (!stage3Complete) "Master Deep Learning & LLMs" else "Deploy Capstone Project",
      remainingMilestoneTasks = if (!stage1Complete) tasks1.count { !it.isCompleted } else if (!stage2Complete) tasks2.count { !it.isCompleted } else if (!stage3Complete) tasks3.count { !it.isCompleted } else tasks4.count { !it.isCompleted },
      stages = stages
    )
  }

  suspend fun toggleTaskCompleted(taskId: String, targetRole: String, isCompleted: Boolean) {
    if (isCompleted) {
      database.roadmapDao().markTaskCompleted(CompletedTaskEntity(taskId, targetRole))
    } else {
      database.roadmapDao().unmarkTask(taskId)
    }
  }

  fun getCompletedTasksFlow(targetRole: String): Flow<List<String>> {
    return database.roadmapDao().getCompletedTasks(targetRole)
  }

  // Courses
  fun getAvailableCourses(): List<Course> {
    return listOf(
      Course(
        id = "crs_python_ds",
        title = "Python for Data Science",
        provider = "Coursera",
        partner = "Google",
        category = "Data Science",
        difficulty = "Beginner",
        duration = "4 weeks",
        isPaid = true,
        priceTag = "Paid",
        tags = listOf("Python", "Data Analysis", "Pandas", "NumPy"),
        description = "Learn the fundamentals of data analysis using Python, Pandas dataframes, and data visualization.",
        rating = 4.8f,
        enrolledCount = 28400,
        syllabus = listOf("Python syntax & data structures", "Working with Pandas & NumPy", "Data visualization with Seaborn", "Real-world dataset projects")
      ),
      Course(
        id = "crs_ml_spec",
        title = "Machine Learning Specialization",
        provider = "Coursera",
        partner = "Stanford Online",
        category = "AI and Machine Learning",
        difficulty = "Intermediate",
        duration = "5 months",
        isPaid = true,
        priceTag = "Paid",
        tags = listOf("Machine Learning", "Python", "Statistics", "Scikit-Learn"),
        description = "Master machine learning fundamentals and how to apply them to build real-world AI applications with Andrew Ng.",
        rating = 4.9f,
        enrolledCount = 45200,
        syllabus = listOf("Supervised Machine Learning", "Advanced Learning Algorithms", "Unsupervised Learning, Recommenders, RL")
      ),
      Course(
        id = "crs_aws_cloud",
        title = "AWS Cloud Practitioner",
        provider = "AWS",
        partner = "Amazon Web Services",
        category = "Cloud Computing",
        difficulty = "Beginner",
        duration = "6 weeks",
        isPaid = false,
        priceTag = "Free",
        tags = listOf("Cloud Computing", "AWS", "DevOps", "Infrastructure"),
        description = "Gain an overall understanding of AWS Cloud concepts, security, architecture, pricing, and support.",
        rating = 4.7f,
        enrolledCount = 18900,
        syllabus = listOf("Cloud concepts & value proposition", "AWS core services (EC2, S3, RDS)", "Security & compliance", "Pricing & billing models")
      ),
      Course(
        id = "crs_android_compose",
        title = "Android Jetpack Compose Bootcamp",
        provider = "Skill2Career",
        partner = "Google Developers",
        category = "Programming",
        difficulty = "Intermediate",
        duration = "8 weeks",
        isPaid = false,
        priceTag = "Free",
        tags = listOf("Kotlin", "Android", "Jetpack Compose", "Material 3"),
        description = "Build modern, declarative, reactive Android mobile apps using Kotlin and Jetpack Compose.",
        rating = 4.9f,
        enrolledCount = 14300,
        syllabus = listOf("Kotlin Coroutines & Flow", "Compose State & Layouts", "Architecture: MVVM & Room", "Animations & Material Design 3")
      ),
      Course(
        id = "crs_fullstack_react",
        title = "Full Stack Web Development",
        provider = "Skill2Career",
        partner = "Tech Academy",
        category = "Web Development",
        difficulty = "Beginner",
        duration = "10 weeks",
        isPaid = true,
        priceTag = "Paid",
        tags = listOf("React", "TypeScript", "Node.js", "Tailwind CSS"),
        description = "Master modern web development from frontend React and TypeScript to backend Node.js and MongoDB.",
        rating = 4.8f,
        enrolledCount = 31200,
        syllabus = listOf("HTML5, Modern CSS & Tailwind", "React.js & State Management", "Node.js REST APIs", "Full Stack Deployment")
      ),
      Course(
        id = "crs_cyber_sec",
        title = "Cybersecurity Fundamentals",
        provider = "Cisco Networking",
        partner = "Cisco",
        category = "Cybersecurity",
        difficulty = "Beginner",
        duration = "5 weeks",
        isPaid = false,
        priceTag = "Free",
        tags = listOf("Cybersecurity", "Networking", "Linux", "Ethical Hacking"),
        description = "Learn how to defend networks, identify vulnerabilities, and understand cybersecurity threat vectors.",
        rating = 4.7f,
        enrolledCount = 12600,
        syllabus = listOf("Networking fundamentals", "Threat detection & malware", "Cryptography principles", "Security policy & incident response")
      )
    )
  }

  fun getEnrolledCoursesFlow(): Flow<List<EnrolledCourseEntity>> {
    return database.courseDao().getEnrolledCourses()
  }

  suspend fun enrollInCourse(course: Course) {
    database.courseDao().enrollCourse(
      EnrolledCourseEntity(
        courseId = course.id,
        title = course.title,
        provider = course.provider,
        progressPercentage = 10
      )
    )
    val user = database.userDao().getActiveUser()
    if (user != null) {
      firestoreManager?.recordCourseEnrollment(user.uid, course)
    }
  }

  suspend fun updateCourseProgress(courseId: String, progress: Int) {
    database.courseDao().updateCourseProgress(courseId, progress)
  }

  fun getSavedCoursesFlow(): Flow<List<SavedCourseEntity>> {
    return database.courseDao().getSavedCourses()
  }

  suspend fun toggleSaveCourse(courseId: String, isSaved: Boolean) {
    if (isSaved) {
      database.courseDao().saveCourse(SavedCourseEntity(courseId))
    } else {
      database.courseDao().removeSavedCourse(courseId)
    }
  }

  // Quizzes & Assessments
  fun getAvailableQuizzes(): List<Quiz> {
    return listOf(
      Quiz(
        id = "quiz_python",
        title = "Python Programming Assessment",
        category = "Programming",
        durationMinutes = 10,
        questions = listOf(
          QuizQuestion(
            id = "py_1",
            question = "Which of the following data types in Python is immutable?",
            options = listOf("List", "Dictionary", "Tuple", "Set"),
            correctOptionIndex = 2,
            explanation = "Tuples in Python are immutable sequence data types; once created, their elements cannot be changed or reassigned."
          ),
          QuizQuestion(
            id = "py_2",
            question = "What is the output of `bool([])` in Python?",
            options = listOf("True", "False", "None", "Error"),
            correctOptionIndex = 1,
            explanation = "Empty collections (lists, tuples, dicts, strings) evaluate to False in boolean contexts."
          ),
          QuizQuestion(
            id = "py_3",
            question = "Which Python library is primarily used for multi-dimensional array operations?",
            options = listOf("Matplotlib", "NumPy", "Flask", "Requests"),
            correctOptionIndex = 1,
            explanation = "NumPy provides high-performance N-dimensional array objects and mathematical operations."
          ),
          QuizQuestion(
            id = "py_4",
            question = "What does the `*args` syntax in a function definition represent?",
            options = listOf("Keyword arguments", "Variable number of positional arguments", "Pointer reference", "Default arguments"),
            correctOptionIndex = 1,
            explanation = "`*args` allows a function to accept any number of positional arguments passed as a tuple."
          )
        )
      ),
      Quiz(
        id = "quiz_ml",
        title = "Machine Learning & AI Evaluation",
        category = "AI/ML",
        durationMinutes = 15,
        questions = listOf(
          QuizQuestion(
            id = "ml_1",
            question = "Which metric is best suited for evaluating a classifier on an imbalanced dataset?",
            options = listOf("Accuracy", "F1-Score / ROC-AUC", "Mean Squared Error", "R-Squared"),
            correctOptionIndex = 1,
            explanation = "F1-Score and ROC-AUC balance precision and recall, preventing deceptive high accuracy caused by class imbalance."
          ),
          QuizQuestion(
            id = "ml_2",
            question = "What is the primary purpose of Dropout in deep neural networks?",
            options = listOf("Speed up inference", "Prevent overfitting during training", "Normalize gradients", "Increase model parameters"),
            correctOptionIndex = 1,
            explanation = "Dropout randomly deactivates neurons during training, which prevents co-adaptation and reduces overfitting."
          ),
          QuizQuestion(
            id = "ml_3",
            question = "Which algorithm is an example of an unsupervised learning method?",
            options = listOf("Random Forest", "Linear Regression", "K-Means Clustering", "Support Vector Machines"),
            correctOptionIndex = 2,
            explanation = "K-Means groups unlabeled data points based on feature similarity without ground-truth target labels."
          )
        )
      ),
      Quiz(
        id = "quiz_dsa",
        title = "Data Structures & Algorithms Challenge",
        category = "Tech",
        durationMinutes = 12,
        questions = listOf(
          QuizQuestion(
            id = "dsa_1",
            question = "What is the average time complexity of searching in a balanced Binary Search Tree (BST)?",
            options = listOf("O(1)", "O(log n)", "O(n)", "O(n log n)"),
            correctOptionIndex = 1,
            explanation = "In a balanced BST, each comparison halves the search space, giving an average search time of O(log n)."
          ),
          QuizQuestion(
            id = "dsa_2",
            question = "Which data structure follows the Last-In-First-Out (LIFO) principle?",
            options = listOf("Queue", "Stack", "Linked List", "Tree"),
            correctOptionIndex = 1,
            explanation = "A Stack operates on the LIFO principle, where elements are inserted and removed from the top."
          )
        )
      )
    )
  }

  suspend fun saveQuizResult(result: QuizResult) {
    database.quizDao().insertQuizAttempt(
      QuizAttemptEntity(
        quizId = result.quizId,
        quizTitle = result.quizTitle,
        score = result.score,
        totalQuestions = result.totalQuestions,
        percentage = result.percentage,
        timestamp = result.timestamp
      )
    )
    val user = database.userDao().getActiveUser()
    if (user != null) {
      firestoreManager?.recordQuizAttempt(user.uid, result)
    }
  }

  fun getQuizAttemptsFlow(): Flow<List<QuizAttemptEntity>> {
    return database.quizDao().getAllQuizAttempts()
  }

  // Job Opportunities
  fun getJobOpportunities(): List<JobOpportunity> {
    return listOf(
      JobOpportunity(
        id = "job_1",
        title = "Senior Software Engineer",
        company = "TechCorp Solutions",
        location = "Bengaluru • Remote",
        isRemote = true,
        experienceLevel = "2-4 years",
        salaryRange = "₹14 - 22 LPA",
        requiredSkills = listOf("Java", "Spring Boot", "AWS", "Microservices"),
        isVerified = true
      ),
      JobOpportunity(
        id = "job_2",
        title = "Associate Machine Learning Engineer",
        company = "Cognitive Labs",
        location = "Hyderabad • Hybrid",
        isRemote = false,
        experienceLevel = "0-2 years",
        salaryRange = "₹9 - 15 LPA",
        requiredSkills = listOf("Python", "PyTorch", "NumPy", "FastAPI"),
        isVerified = true
      ),
      JobOpportunity(
        id = "job_3",
        title = "Cloud Infrastructure Engineer",
        company = "Skyline Cloud Services",
        location = "Pune • Remote",
        isRemote = true,
        experienceLevel = "1-3 years",
        salaryRange = "₹10 - 18 LPA",
        requiredSkills = listOf("AWS", "Docker", "Kubernetes", "Linux"),
        isVerified = true
      ),
      JobOpportunity(
        id = "job_4",
        title = "Junior Data Analyst / Scientist",
        company = "Global Insight Analytics",
        location = "Bangalore • On-site",
        isRemote = false,
        experienceLevel = "Fresher / 0-1 yr",
        salaryRange = "₹7 - 11 LPA",
        requiredSkills = listOf("Python", "SQL", "Tableau", "Pandas"),
        isVerified = true
      )
    )
  }
}
