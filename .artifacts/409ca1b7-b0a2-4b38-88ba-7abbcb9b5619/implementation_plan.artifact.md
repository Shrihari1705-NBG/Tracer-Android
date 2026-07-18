# Implementation Plan - BottomNavBar UI Component (Refined)

Create a reusable and stateless `BottomNavBar` using Material 3 for the Tracer application, incorporating specific refinements for architectural cleanliness and future-proofing.

## User Review Required

> [!IMPORTANT]
> The `BottomNavItem` model now includes a `route` property to prepare for future Navigation Compose integration.
> The UI uses explicit Material 3 color tokens to lock in the Tracer brand identity.

## Proposed Changes

### UI Components

#### [MODIFY] [BottomNavBar.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/ui/components/BottomNavBar.kt)
- **Model**: `sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector)`
    - `object Home : BottomNavItem("home", "Home", Icons.Rounded.Home)`
    - `object Scan : BottomNavItem("scan", "Scan", Icons.Rounded.BluetoothSearching)`
    - `object Navigate : BottomNavItem("navigate", "Navigate", Icons.Rounded.Explore)`
    - `object Settings : BottomNavItem("settings", "Settings", Icons.Rounded.Settings)`
    - `companion object { val items = listOf(...) }`
- **Composable**: `BottomNavBar`
    - Uses `BottomNavItem.items` to generate `NavigationBarItem`s.
    - Explicitly configures colors using `NavigationBarItemDefaults.colors()`.
    - Includes architectural KDoc notes.
- **Previews**:
    - `private fun BottomNavBarHomePreview()`
    - `private fun BottomNavBarScanPreview()`
    - `private fun BottomNavBarNavigatePreview()`
    - `private fun BottomNavBarSettingsPreview()`

## Design Decisions

- **Architectural Separation**: The component is strictly a "dumb" UI element. It emits selection events but does not handle navigation logic, maintaining a clear separation of concerns.
- **Future Ready**: Inclusion of the `route` property ensures that when Navigation Compose is added, the model won't need breaking changes.
- **Maintenance**: The `companion object` list centralizes the navigation structure, making it trivial to add or remove items in one place.
- **Theme Lock**: Using `primaryContainer` for the selection indicator and `primary` for active state ensures the bottom navigation bar is a prominent part of the Tracer visual experience.

## Verification Plan

### Manual Verification
- Verify that all four previews render correctly in Android Studio.
- Ensure the selection indicator uses `primaryContainer` and icons/text use `primary`.
- Verify that clicking an item in interactive mode correctly triggers the `onItemSelected` callback.
