<p align="center">
  <img src="branding/logo/tracer-logo.png" alt="Tracer Logo" width="160"/>
</p>

<h1 align="center">Tracer</h1>

<p align="center">
  <b>Smart Campus Indoor Navigation</b><br/>
  AI-Based BLE RSSI Fingerprinting Indoor Localization and Navigation System
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green" alt="Android"/>
  <img src="https://img.shields.io/badge/Language-Kotlin-blue" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-blue" alt="Jetpack Compose"/>
  <img src="https://img.shields.io/badge/Architecture-MVVM-orange" alt="MVVM"/>
  <img src="https://img.shields.io/badge/Status-Project%20Complete-success" alt="Project Status"/>
</p>

## About

**Tracer** is an Android-based indoor navigation system developed for smart-campus environments.

The project combines **Bluetooth Low Energy (BLE) RSSI fingerprinting**, **machine learning-based indoor localization**, and **A* pathfinding** to estimate the user's location and generate routes to selected destinations inside the Electronics & Communication Engineering (ECE) Department.

Tracer was developed as a **Final Year Engineering Major Project**.

---

## Features

- 📡 BLE beacon scanning and RSSI collection
- 📊 Session-based RSSI fingerprinting
- 🤖 ML-based indoor localization using KNN, Random Forest, and SVM
- 🗺 Indoor map with zoom and pan support
- 🧭 A* based indoor route planning
- 📍 Current-location-aware navigation
- 🛣 Visual and animated route guidance
- 📌 Destination selection and recent destinations
- 💾 Persistent data using Android DataStore
- 🎓 First-launch interactive tutorial
- 🔗 QR/deep-link handoff from the Tracer Kiosk
- 🌙 Material 3 UI with theme support

---

## How It Works

```text
BLE Beacons
    │
    ▼
RSSI Scanning
    │
    ▼
RSSI Fingerprint
    │
    ▼
Machine Learning Localization
    │
    ▼
Current Node
    │
    ▼
Destination Selection
    │
    ▼
A* Pathfinding
    │
    ▼
Route Guidance
```

### Localization

RSSI values from multiple BLE beacons are collected at known reference nodes. These fingerprints are used to train supervised machine learning models:

- **K-Nearest Neighbors (KNN)**
- **Random Forest**
- **Support Vector Machine (SVM)**

The selected localization model is intended to predict the most likely reference node from a new RSSI fingerprint. The Android application contains the localization/inference pipeline required for on-device operation.

### Navigation

Once the current reference node and destination are known, Tracer uses an indoor navigation graph and the **A\*** pathfinding algorithm to calculate a route.

---

## Application Flow

```text
Launch
  │
  ▼
First-Launch Tutorial
  │
  ▼
Home
  │
  ├── Current Location
  │
  └── Select Destination
          │
          ▼
      A* Route
          │
          ▼
     Navigation
```

The Tracer Kiosk can also hand off a destination to the Android application through a custom `tracer://navigate` deep link.

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Kotlin | Application development |
| Jetpack Compose | User interface |
| Material 3 | UI design system |
| MVVM | Application architecture |
| Hilt | Dependency injection |
| Bluetooth Low Energy | RSSI acquisition |
| RSSI Fingerprinting | Indoor localization |
| KNN / Random Forest / SVM | ML localization |
| ONNX Runtime | ML model inference |
| A* | Indoor pathfinding |
| Android DataStore | Persistent storage |
| QR / Deep Linking | Kiosk-to-mobile handoff |
| Android Studio | Development |

---

## Project Structure

```text
SmartCampusNavigator/
├── app/
│   └── src/
│       └── main/
│           └── java/com/shrihari/smartcampusnavigator/
│               ├── data/
│               ├── domain/
│               ├── ui/
│               └── di/
├── branding/
├── gradle/
├── LICENSE
├── README.md
├── build.gradle.kts
└── settings.gradle.kts
```

The package structure may evolve as the project is maintained.

---

## Running the Project

### Requirements

- Android Studio
- Android SDK
- A physical Android device with Bluetooth Low Energy support
- BLE beacons configured for the Tracer environment

A physical device is recommended because the core localization workflow depends on BLE RSSI measurements.

### Setup

1. Clone the repository.

```bash
git clone https://github.com/Shrihari1705-NBG/Tracer-Android.git
```

2. Open the project in **Android Studio**.

3. Allow Gradle to sync and install the required dependencies.

4. Connect a compatible Android device or use an appropriate emulator for UI-only testing.

5. Build and run the `app` module.

6. Grant the requested Bluetooth and camera permissions when required.

> The navigation map, BLE beacon configuration, and localization model are specific to the Tracer deployment environment. Full localization/navigation behavior therefore requires the corresponding project setup and BLE infrastructure.

---

## Android Permissions

Tracer uses Bluetooth permissions required by modern Android versions and camera access for QR scanning.

### Android 12+

```text
BLUETOOTH_SCAN
BLUETOOTH_CONNECT
```

### Android 11 and below

```text
BLUETOOTH
BLUETOOTH_ADMIN
ACCESS_FINE_LOCATION
```

### QR Scanning

```text
CAMERA
```

---

## Research

The project is based on research and experimentation in:

- BLE indoor localization
- RSSI fingerprinting
- Machine learning for indoor positioning
- Indoor navigation
- A* path planning
- Mobile robot path planning

The project includes session-based RSSI data collection to account for variations in wireless signal strength across different collection sessions.

---

## Project Status

### Completed

- [x] Android application
- [x] MVVM + Hilt architecture
- [x] Tracer branding and Material 3 UI
- [x] BLE scanning
- [x] RSSI fingerprint data collection
- [x] Session-based dataset workflow
- [x] ML localization pipeline
- [x] KNN, Random Forest, and SVM experimentation
- [x] Indoor map
- [x] A* navigation
- [x] Route visualization and guidance
- [x] Destination selection
- [x] Recent destinations
- [x] First-launch tutorial
- [x] Tracer Kiosk QR/deep-link handoff
- [x] Integrated mobile navigation workflow

### Future Scope

- Larger campus deployment
- Additional floors and wings
- Multi-floor routing
- Improved real-time localization
- Voice guidance
- Dynamic route updates
- Augmented Reality (AR) navigation

---

## Team

**Shrihari N B G**  
**Shreesha M Tembe**  
**Shubhangi S Naik**  
**Vageesh I Gaonkar**

Final Year Engineering Students

---

## License

Tracer is released under the **Apache License 2.0**.

Copyright © 2026 Shrihari N B G, Shreesha M Tembe, Shubhangi S Naik, and Vageesh I Gaonkar.

See the [`LICENSE`](LICENSE) file for the complete license terms.

This project was developed as a Final Year Engineering Major Project for academic and research purposes.

---

<p align="center">
  <b>Tracer — Making Indoor Campus Navigation Smarter.</b>
</p>
