# Implementation Plan - Navigation Compose (Final)

Implement Navigation Compose using a type-safe `sealed class` for routing, following a clean package structure and Material 3 design principles.

## User Review Required

> [!IMPORTANT]
> I will use the latest stable Navigation Compose (2.8.5 or higher as per version lookup).
> The `HomeScreen` will be implemented using a Material 3 `Scaffold` with a `TopAppBar`.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///D:/ENGG/MajorProject/SmartCampusNavigator/gradle/libs.versions.toml)
- Add `navigationCompose = "2.8.5"` (latest stable) to `[versions]`.
- Add `androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }` to `[libraries]`.

#### [MODIFY] [build.gradle.kts](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/build.gradle.kts)
- Add `implementation(libs.androidx.navigation.compose)` to the dependencies.

### Navigation

#### [NEW] [Screen.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/ui/navigation/Screen.kt)
- Define `sealed class Screen(val route: String)` with `Splash` and `Home` objects.

#### [NEW] [AppNavigation.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/ui/navigation/AppNavigation.kt)
- Implement `AppNavigation` composable with `rememberNavController()` and `NavHost`.

### Screens

#### [NEW] [SplashScreen.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/ui/screens/splash/SplashScreen.kt)
- Minimal M3 Splash UI (e.g., a centered icon or text).
- `LaunchedEffect` delay of 2000ms to navigate to `Screen.Home.route`, popping the Splash screen.

#### [NEW] [HomeScreen.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/ui/screens/home/HomeScreen.kt)
- **Scaffold**: Material 3 Scaffold implementation.
- **TopAppBar**: Title "Smart Campus Navigator".
- **Content**: Centered welcome message with modern layout and M3 spacing.

### Main Entry Point

#### [MODIFY] [MainActivity.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/MainActivity.kt)
- Replace default setup with `AppNavigation()`.

## Verification Plan

### Automated Tests
- Build project using `./gradlew assembleDebug`.

### Manual Verification
- Deploy and verify 2s Splash delay.
- Verify Home Screen TopAppBar and centered message.
- Verify back button behavior (exits app from Home).
