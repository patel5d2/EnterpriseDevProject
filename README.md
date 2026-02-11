# Enterprise Dev Group Project - Design Document

## 1. Introduction

The application created in this problem is a University Student Directory program intended for higher learning institutions within the tri-state region. These include institutions such as Xavier University, the University of Cincinnati (UC), as well as Cincinnati State. The program solves the problem of the lack of centralization of information concerning student enrollment within the institutions within the specified regions

- Users: University administrators and students within the tri-state network.
- Main Purpose: To maintain a searchable record of students’ first and last names, their associated IDs, and their respective universities
---

## 2. Storyboard
The project uses GitHub Projects to maintain a storyboard. Each pull request will address the specific feature by incorporating it from the storyboard, ensuring it aligns with the overall project goals and is subject to change as the feature developments.

---

## 3. Functional Requirements
- **Student Management**: The system must allow for the creation and storage of student records containing first names, last names, unique IDs, and university affiliations.
- **Regional Filtering**: Users should be able to filter or identify students based on their specific tri-state institution.
- **Technical Compliance**:
  - Implementation of business logic for data processing. 
  - Data persistence via a database. 
  - Integration and production of JSON data. 
  - Use of interfaces to support collaborative development

---

## 4. Class Diagram

[Insert your UML class diagram here - you can use tools like:]
- draw.io
- Lucidchart
- PlantUML
- Visual Paradigm

```
[Insert image or PlantUML code here]
```

### Class Diagram Description

- **User**: Represents application users with authentication credentials and profile information. Implements UserDetails interface for Spring Security.
- **Task**: Main entity representing a work item with title, description, status, priority, and due date. Associated with User (assignee).
- **Project**: Groups related tasks together. Contains multiple tasks and team members.
- **TaskRepository**: JPA repository interface for CRUD operations on Task entities. Extends JpaRepository.
- **UserRepository**: JPA repository interface for user data access. Provides custom query methods for finding users by email and username.
- **TaskService**: Business logic layer for task management. Handles task creation, assignment, and status updates.
- **TaskController**: REST controller exposing task-related endpoints. Maps HTTP requests to service methods.
- **TaskDTO**: Data Transfer Object for task information sent to/from the API. Separates internal model from API representation.



- [ Student: Represents the core entity with attributes for firstName, lastName, studentID, and universityName.
  • University: Represents the tri-state institutions (e.g., Xavier, UC).
  • StudentRepository: JPA repository interface for CRUD operations on student records.
  • StudentService: Business logic layer handling directory searches and data validation.
  • StudentController: REST controller managing JSON-based API requests for student data]
---

## 5. JSON Schema


---

## 6. Scrum Roles

- **Scrum Master/Product Owner/GitHub admin**: Ian Kellenberger & Dharmin Patel - Responsible for defining features and prioritizing backlog
- **UI/UX**: Matthew Brown - Facilitates scrum ceremonies and removes impediments
- **Backend Developer**: Jonathan Soriano 


---

## 7. GitHub Repository

**Repository Link**: ```https://github.com/jonathansoriano/EnterpriseDevGroupProject```

---

## 8. Project Board & Milestones

**Project Board Link**: [https://github.com/YOUR-USERNAME/enterprisedevgroupproject/projects/1](https://github.com/YOUR-USERNAME/enterprisedevgroupproject/projects/1)


---

## 9. Weekly Standup Meeting

**Meeting Time**: Every Monday at 05:00PM EST  
**Platform**: Microsoft Teams  
**Meeting Link**: ``` https://teams.microsoft.com/meet/2727813537852?p=8FKd43Pa4OstTky9WE```

**Meeting Agenda**:
- What did you accomplish this week?
- What are you working on next?
- Are there any blockers or issues?

---

## Technology Stack

- **Backend**: Spring Boot, Spring Data JPA
- **Database**: PostgreSQL / H2 (for development)
- **Frontend**: Thymeleaf
- **Testing**: JUnit, Mockito, Spring Test
- **Build Tool**: Maven
- **Version Control**: Git/GitHub
- **CI/CD**: GitHub Actions
- **Deployment**: [AWS / Heroku / Azure / Other]

---

## Development Guidelines
- All methods must include JavaDocs to explain their functionality. 
- Pull Requests must document major breaking changes and confirm unit testing. 
- A README markdown file will be maintained to track test results and collective progress

### Branching Strategy
- `main` - Production-ready code
- `feature/*` - Individual feature branches
- `bugfix/*` - Bug fix branches

---

## Helps Links

- [JDK Setup and Installation]()
