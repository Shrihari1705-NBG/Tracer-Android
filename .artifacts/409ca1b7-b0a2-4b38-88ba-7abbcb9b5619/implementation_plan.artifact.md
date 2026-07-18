# Implementation Plan - MVVM Home Feature (Final with Lifecycle Support)

Implement the first MVVM-based feature for the Home screen using Dagger Hilt, Repository pattern with `suspend` functions, and `StateFlow` observed via lifecycle-aware collectors.

## User Review Required

> [!IMPORTANT]
> I will add `androidx.lifecycle:lifecycle-runtime-compose` and `androidx.lifecycle:lifecycle-viewmodel-compose` dependencies.
> This allows using `collectAsStateWithLifecycle()`, which is a best practice for battery-efficient state collection in Compose.

## Architecture Details

- **Clean Architecture**: Strict separation between Data, Domain, and UI layers.
- **Hilt**: Dependency injection for all layers.
- **MVVM**: UI state managed by ViewModel and observed by Compose using `collectAsStateWithLifecycle()`.
- **Coroutines**: Asynchronous data fetching in the Repository and ViewModel.

---

## Proposed Changes

### 1. Build Configuration

#### [MODIFY] [libs.versions.toml](file:///D:/ENGG/MajorProject/SmartCampusNavigator/gradle/libs.versions.toml)
- Add `lifecycle = "2.8.7"` to `[versions]`.
- Add `androidx-lifecycle-runtime-compose` and `androidx-lifecycle-viewmodel-compose` to `[libraries]`.

#### [MODIFY] [build.gradle.kts (app)](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/build.gradle.kts)
- Add the new lifecycle dependencies.

### 2. Domain Layer

#### [NEW] [HomeRepository.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/domain/repository/HomeRepository.kt)
- Define `interface HomeRepository` with `suspend fun getWelcomeMessage(): String`.

### 3. Data Layer

#### [NEW] [HomeRepositoryImpl.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/data/repository/HomeRepositoryImpl.kt)
- Implement `HomeRepository` using `@Inject constructor()`.

### 4. Dependency Injection

#### [NEW] [RepositoryModule.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/di/RepositoryModule.kt)
- Create an `abstract class RepositoryModule` with `@Module` and `@InstallIn(SingletonComponent::class)`.
- Use `@Binds` to provide the implementation.

### 5. ViewModel Layer

#### [NEW] [HomeViewModel.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/ui/viewmodel/HomeViewModel.kt)
- Create `@HiltViewModel class HomeViewModel`.
- Inject `HomeRepository`.
- Expose `StateFlow<String>` using `MutableStateFlow`.
- Fetch data in `init` block using `viewModelScope.launch`.

### 6. UI Layer

#### [MODIFY] [HomeScreen.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/ui/screens/home/HomeScreen.kt)
- Modify signature to accept `HomeViewModel`.
- Observe state via `viewModel.welcomeMessage.collectAsStateWithLifecycle()`.

#### [MODIFY] [AppNavigation.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/ui/navigation/AppNavigation.kt)
- Inject `HomeViewModel` using `hiltViewModel()`.

---

## Verification Plan

### Automated Tests
- Build project using `./gradlew app:assembleDebug` to verify Hilt generation and dependencies.

### Manual Verification
- Deploy the app.
- Verify transition from Splash to Home.
- Verify the message "Welcome from Repository via MVVM!" is displayed correctly.
