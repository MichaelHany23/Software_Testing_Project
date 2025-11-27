# Software Testing Project - Movie Recommender System

## Project Overview

The **Software Testing Project** is a Java-based movie recommendation system designed with comprehensive unit testing and integration testing. The application reads user profiles and movie data from text files, validates the information, and generates personalized movie recommendations based on genres that users have previously liked.

### Key Features
- **File-based Data Input**: Reads movies and user data from text files
- **Data Validation**: Validates movie and user information with detailed error reporting
- **Recommendation Engine**: Generates movie recommendations based on user preferences and genre matching
- **File Output**: Writes recommendations and validation errors to output files
- **Comprehensive Testing**: Includes JUnit 5 unit tests and integration tests for all major components

### Technology Stack
- **Language**: Java 21
- **Build Tool**: Maven
- **Testing Framework**: JUnit 5 (Jupiter)
- **Project Structure**: Maven standard project structure

---

## Class Hierarchy & Architecture

### Data Model Classes

#### **Movie**
- **Purpose**: Represents a movie entity with title, ID, and genres
- **Key Attributes**:
  - `movieTitle`: The title of the movie
  - `movieId`: Unique identifier for the movie
  - `genres`: Array of genre tags associated with the movie
- **Methods**: Getters and setters for all attributes

#### **User**
- **Purpose**: Represents a user entity with profile and movie preferences
- **Key Attributes**:
  - `name`: User's name
  - `userId`: Unique identifier for the user
  - `likedMovieIds`: Array of movie IDs that the user has liked
- **Methods**: Getters and setters for all attributes

---

### File I/O Classes

#### **MovieFilereader**
- **Purpose**: Reads movie data from text files and creates Movie objects
- **Key Methods**:
  - `ReadMovies(String filename)`: Parses movie file and returns ArrayList of Movie objects
- **Input Format**: 
  - Each movie has 2 lines: `MovieTitle,MovieId` and `genre1,genre2,...`
- **Error Handling**: Throws exceptions for malformed data or missing genres

#### **UserFilereader**
- **Purpose**: Reads user data from text files and creates User objects
- **Key Methods**:
  - `ReadUsers(String filename)`: Parses user file and returns ArrayList of User objects
- **Input Format**:
  - Each user has 2 lines: `UserName,UserId` and `likedMovieId1,likedMovieId2,...`
- **Error Handling**: Throws exceptions for malformed data or missing liked movies

#### **OutputFileWriter**
- **Purpose**: Writes validation errors and recommendation results to output files
- **Key Methods**:
  - `setOutputPath(String outputPath)`: Sets the output file path
  - `WriteFirstError(ArrayList<String> results)`: Writes the first validation error encountered
  - `WriteRecommendations(ArrayList<String> results)`: Writes recommendation results with user info
- **Features**: Supports both append mode and file cleaning, formats output with proper spacing

---

### Validation Classes

#### **MovieValidator**
- **Purpose**: Validates movie data for correctness and consistency
- **Key Attributes**:
  - `movies`: ArrayList of Movie objects to validate
  - `Movie_errors`: ArrayList storing validation error messages
- **Key Methods**:
  - `Validate()`: Executes all validation checks
  - `getMovie_errors()`: Returns list of validation errors
  - `ErrorIsEmpty()`: Checks if validation passed
  - `getMovies()`: Returns movies if valid, null otherwise
- **Validation Rules**: 
  - Movie titles must have capitalized words
  - Movie IDs must follow specific format requirements

#### **UserValidator**
- **Purpose**: Validates user data for correctness and consistency
- **Key Attributes**:
  - `users`: ArrayList of User objects to validate
  - `User_errors`: ArrayList storing validation error messages
- **Key Methods**:
  - `Validate()`: Executes all validation checks
  - `getUser_errors()`: Returns list of validation errors
  - `ErrorIsEmpty()`: Checks if validation passed
  - `getUsers()`: Returns users if valid, null otherwise
- **Validation Rules**:
  - User names must contain only alphabetic characters and spaces
  - Names cannot start with spaces
  - User IDs must follow specific format requirements

---

### Recommendation & Business Logic Classes

#### **Recommender**
- **Purpose**: Core recommendation engine that generates personalized movie suggestions
- **Key Attributes**:
  - `movies`: ArrayList of all available movies
  - `users`: ArrayList of all users
  - `recommendationsResults`: ArrayList storing recommendation results
- **Key Methods**:
  - `FindAllRecommendations()`: Generates recommendations for all users
  - `GetRecommendations_OnUser(User U)`: Gets recommendations for a specific user
  - `recommendMovies(User user)`: Algorithm to generate movie recommendations
  - `GetRecommendationsInFile(OutputFileWriter O)`: Writes recommendations to file
  - `getRecommendationsResults()`: Returns the recommendation results
- **Algorithm**:
  1. Identifies genres from movies the user has already liked
  2. Recommends movies in those genres
  3. Excludes movies the user has already liked
  4. Returns formatted list of recommended movie titles

#### **Application**
- **Purpose**: Main orchestrator that integrates all system components
- **Key Attributes**:
  - `frUser`: UserFilereader instance
  - `frMovie`: MovieFilereader instance
  - `rcmnd`: Recommender instance
  - `fw`: OutputFileWriter instance
- **Key Methods**:
  - `RecommenderApp(String MoviePath, String UserPath, String OutputPath)`: Main orchestration method
- **Workflow**:
  1. Reads users and movies from files
  2. Validates both datasets
  3. Returns with first error if validation fails
  4. Generates recommendations for all users if validation passes
  5. Writes results to output file

#### **SwTProject** (Main Entry Point)
- **Purpose**: Application entry point and driver class
- **Key Methods**:
  - `main(String[] args)`: Initializes file readers, creates Application instance, and starts the RecommenderApp
- **Responsibilities**:
  - Sets up file paths for movies, users, and output
  - Instantiates the Application class
  - Triggers the recommendation process

---

## Class Interaction Diagram

```
SwTProject (main)
    ↓
Application (orchestrator)
    ├── MovieFilereader ─→ ReadMovies() → ArrayList<Movie>
    ├── UserFilereader ─→ ReadUsers() → ArrayList<User>
    ├── MovieValidator ─→ Validate() → movie_errors
    ├── UserValidator ─→ Validate() → user_errors
    ├── Recommender ─→ FindAllRecommendations() → recommendationsResults
    └── OutputFileWriter ─→ WriteRecommendations() or WriteFirstError()
```

---

## Data Flow

```
Input Files (Movies & Users)
    ↓
MovieFilereader & UserFilereader (Parse & Create Objects)
    ↓
Movie & User Objects (Populated)
    ↓
MovieValidator & UserValidator (Validate Data)
    ↓
[Error Check]
├─ Yes → OutputFileWriter.WriteFirstError()
└─ No → Recommender.FindAllRecommendations()
         ↓
    Generate Recommendations
         ↓
    OutputFileWriter.WriteRecommendations()
         ↓
Output File (Recommendations or Error)
```

---

## Project Structure

```
SwTProject/
├── src/
│   ├── main/java/com/mycompany/swtproject/
│   │   ├── SwTProject.java (Entry Point)
│   │   ├── Application.java (Orchestrator)
│   │   ├── Movie.java (Data Model)
│   │   ├── User.java (Data Model)
│   │   ├── MovieFilereader.java (I/O)
│   │   ├── UserFilereader.java (I/O)
│   │   ├── MovieValidator.java (Validation)
│   │   ├── UserValidator.java (Validation)
│   │   ├── Recommender.java (Business Logic)
│   │   └── OutputFileWriter.java (I/O)
│   └── test/java/com/mycompany/swtproject/
│       ├── MovieFilereaderTest.java
│       ├── MovieValidatorTest.java
│       ├── UserValidatorTest.java
│       ├── MovieReader_Validator_integrationTest.java
│       ├── RecommenderTest.java
│       ├── OutputFileWriterTest.java
│       └── ApplicationTest.java
├── pom.xml (Maven Configuration)
└── target/ (Build Output)
```

---

## Testing Strategy

The project uses **JUnit 5** with comprehensive test coverage:

- **Unit Tests**: Individual class functionality validation
  - MovieFilereaderTest
  - UserFilereaderTest (implied)
  - MovieValidatorTest
  - UserValidatorTest
  - RecommenderTest
  - OutputFileWriterTest

- **Integration Tests**:
  - MovieReader_Validator_integrationTest (Tests file reading + validation pipeline)
  - ApplicationTest (Tests full application workflow)

- **Test Data**: Located in TextFile_integrationTest folder with various input scenarios (valid data, error cases, edge cases)

---

## Key Design Patterns

1. **Separation of Concerns**: Each class has a single responsibility (file reading, validation, recommendation, output)
2. **Loose Coupling**: Recommender and OutputFileWriter are loosely coupled through the Application class
3. **Data Validation Pipeline**: Validators are applied before processing recommendations
4. **Composition**: Application class composes all necessary dependencies

---

## Dependencies

- **JUnit Jupiter API**: 5.6.0 (Unit testing)
- **JUnit Jupiter Engine**: 5.6.0 (Test execution)
- **JUnit Jupiter Params**: 5.6.0 (Parameterized tests)
- **Java**: Version 21