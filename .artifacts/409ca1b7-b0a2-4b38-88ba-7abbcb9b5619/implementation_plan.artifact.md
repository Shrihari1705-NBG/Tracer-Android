# Implementation Plan - StatusCard UI Component (Refined)

Create a reusable `StatusCard` composable to display the status of various system services (e.g., Bluetooth, Scanner, Location) following the Tracer design system.

## User Review Required

> [!IMPORTANT]
> The component will use an `ElevatedCard` with Level 2 elevation (3dp).
> It now includes accessibility semantics for the status indicator and support for multi-line status text.

## Proposed Changes

### UI Components

#### [MODIFY] [StatusCard.kt](file:///D:/ENGG/MajorProject/SmartCampusNavigator/app/src/main/java/com/shrihari/smartcampusnavigator/ui/components/StatusCard.kt)
- Replace the empty class with a `@Composable` function named `StatusCard`.
- **Parameters**:
    - `title: String`
    - `status: String`
    - `statusColor: Color`
    - `modifier: Modifier = Modifier`
- **Layout**:
    - Root: `ElevatedCard` with `fillMaxWidth()`, `shape = RoundedCornerShape(16.dp)`, and `elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)`.
    - Internal Padding: `20.dp`.
    - Content: `Column` containing:
        - `Text(title)` with `MaterialTheme.typography.titleMedium`.
        - `Spacer(height = 12.dp)`.
        - `Row(verticalAlignment = Alignment.Top)` (using `Top` alignment to handle multi-line status text gracefully).
            - `Box` (Status Indicator): `size(12.dp)`, `CircleShape`, `background(statusColor)`.
            - `Modifier.semantics { contentDescription = "Status indicator: $status" }`.
            - `Spacer(width = 12.dp)`.
            - `Text(status)` with `MaterialTheme.typography.bodyMedium`.
- **Previews**:
    - `Bluetooth Disabled`: `TracerError` (`#B3261E`).
    - `Scanner Ready`: `Color(0xFF4CAF50)` // TODO: Replace with theme success color when design system expands.
    - `Current Location`: `TracerPrimary` (`#00133A`).

## Design Decisions

- **Multi-line Support**: By using `Alignment.Top` in the `Row`, if the status text wraps to multiple lines, the status indicator remains aligned with the first line of text, maintaining a professional look.
- **Accessibility**: Added `semantics` to the status indicator `Box` so screen readers can associate the visual color cue with the textual status.
- **Visual Rhythm**: Increased the spacer between title and status to `12.dp` to match the internal padding and provide better breathing room.
- **Future Proofing**: Added a TODO for the success color to ensure the design system remains evolvable.

## Verification Plan

### Manual Verification
- Render the `@Preview` composables in Android Studio.
- Verify that long status text wraps correctly and the indicator remains at the top.
- Verify accessibility labels in the TalkBack preview (if available).
