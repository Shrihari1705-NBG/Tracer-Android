# Implementation Plan - HomeScreen Refactoring

Refactor the `HomeScreen` to use a centralized UI state, updated ViewModel, and reusable Tracer branding components.

## User Review Required

> [!IMPORTANT]
> The `HomeScreen` will now strictly use custom Tracer components (`TracerTopBar`, `WelcomeBanner`, etc.) and a centralized `HomeUiState`.
> The `HomeViewModel` will transition from exposing a single string to a full `HomeUiState` object.

## Proposed Changes

### 1. View Model & State

#### [NEW] [HomeUiState.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/ui/viewmodel/HomeUiState.kt)
- Define `data class HomeUiState` with properties for Bluetooth, Scanner, Location status, and Selected Nav Item.

#### [MODIFY] [HomeViewModel.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/ui/viewmodel/HomeViewModel.kt)
- Replace `_welcomeMessage` with `_uiState: MutableStateFlow<HomeUiState>`.
- Update state on initialization (using placeholder values for now).

### 2. UI Screens

#### [MODIFY] [HomeScreen.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/ui/screens/home/HomeScreen.kt)
- Split into `HomeScreen` (Stateful) and `HomeScreenContent` (Stateless).
- **HomeScreen**:
    - Inject `HomeViewModel` via Hilt.
    - Collect `uiState` with `collectAsStateWithLifecycle()`.
- **HomeScreenContent**:
    - Use `Scaffold` with custom `BottomNavBar`.
    - Content: `Column` with `verticalScroll`.
    - Assemble components: `TracerTopBar`, `WelcomeBanner`, `StatusCard` (x3), `PrimaryButton`.
- **Preview**: Add `HomeScreenContentPreview` with sample data.

## Design Decisions

- **Single Source of Truth**: `HomeUiState` prevents state fragmentation and race conditions.
- **Component Reusability**: Hard-coding is removed in favor of `TracerTopBar`, `WelcomeBanner`, etc., ensuring brand consistency.
- **Scrollable Layout**: A vertically scrollable column is used to handle various screen sizes and future content growth.
- **Separation of Concerns**: `HomeScreen` handles the "How" (Hilt, Lifecycle), while `HomeScreenContent` handles the "What" (UI layout).

## Verification Plan

### Manual Verification
- Render the `HomeScreenContentPreview` in Android Studio.
- Verify the components appear in the requested order: TopBar -> Welcome -> Status Cards -> Button.
- Verify that clicking navigation items or the "Start Scan" button (simulated via `println` or similar) works in interactive mode.
- Deploy the app and verify the Splash screen transitions to the new Home layout.
