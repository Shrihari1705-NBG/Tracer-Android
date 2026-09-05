<p align="center">
    <img src="branding/logo/tracer-logo.png"
         alt="Tracer Logo"
         width="180"/>
</p>

<h1 align="center">Tracer</h1>

<p align="center">
  <b>Smart Campus Indoor Navigation</b>
</p>

<p align="center">
AI-Based BLE RSSI Fingerprinting Indoor Localization and Navigation System
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green" alt="Android"/>
  <img src="https://img.shields.io/badge/Kotlin-2.2-blue" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4" alt="Jetpack Compose"/>
  <img src="https://img.shields.io/badge/Architecture-MVVM-orange" alt="MVVM"/>
  <img src="https://img.shields.io/badge/Status-Project%20Complete-success" alt="Project Status"/>
</p>

# Tracer – Smart Campus Indoor Navigation

Tracer is an AI-powered Android application designed for **BLE RSSI Fingerprinting-based Indoor Localization and Navigation** in smart campus environments.

The system combines **Bluetooth Low Energy (BLE) beacon scanning**, **RSSI fingerprinting**, **machine learning-based localization**, and **A* pathfinding** to provide indoor navigation inside the Electronics & Communication Engineering (ECE) Department.

Tracer is developed as a **Final Year Engineering Major Project** using modern Android development practices. The project integrates the complete pipeline from RSSI data collection and fingerprint-based localization to destination selection and route guidance.

---

## 🚀 Features

* 📡 BLE Beacon Scanning
* 📊 RSSI Fingerprint Data Collection
* 📍 RSSI Fingerprinting-based Indoor Localization
* 🤖 Machine Learning-based Location Prediction
* 🧠 KNN, Random Forest and SVM Model Training
* ⚡ ONNX Runtime Model Integration
* 🗺 A* Indoor Pathfinding
* 🧭 Interactive Zoomable Department Map
* 🛣 Route Visualization
* 🎞 Animated Route Guidance
* 📌 Current Location Display
* 📍 Destination Selection
* 💾 Persistent Recent Destinations using DataStore
* 📱 Modern Material 3 UI
* 🎓 First-launch Interactive Tutorial
* 🔗 Tracer Kiosk QR Handoff / Deep Linking
* 🏫 Smart Campus Indoor Navigation
* 📱 Android-based Navigation Interface

---

## 🧠 Localization System

Tracer uses **BLE RSSI fingerprinting** for indoor localization.

During data collection, RSSI values from multiple BLE beacons are recorded at known reference nodes. These measurements form a fingerprint database representing the signal characteristics of different locations.

The collected fingerprint data is used to train supervised machine learning models.

### Machine Learning Models

The project explores and trains multiple supervised learning algorithms:

* **K-Nearest Neighbors (KNN)**
* **Random Forest**
* **Support Vector Machine (SVM)**

The trained model can then receive a new RSSI fingerprint and predict the most likely indoor reference node.

The trained localization model is integrated into the Android application using **ONNX Runtime** for on-device inference.

---

## 📡 BLE RSSI Fingerprinting Pipeline

The localization pipeline follows this general workflow:

```text
BLE Beacons
     │
     ▼
RSSI Scanning
     │
     ▼
RSSI Fingerprint Collection
     │
     ▼
Reference Node Dataset
     │
     ▼
Data Preprocessing
     │
     ▼
Supervised ML Training
     │
     ├── KNN
     ├── Random Forest
     └── SVM
     │
     ▼
Trained Localization Model
     │
     ▼
ONNX Model
     │
     ▼
Android On-Device Inference
     │
     ▼
Predicted Current Node
```

---

## 🧪 Data Collection

RSSI fingerprints are collected at predefined reference nodes within the ECE Department.

Each fingerprint sample contains:

* Timestamp
* Reference Node
* Collection Session
* RSSI value from Beacon 1
* RSSI value from Beacon 2
* RSSI value from Beacon 3
* ...
* RSSI value from Beacon 15

The dataset supports **session-based data collection**, allowing measurements from different collection sessions to be combined for improved robustness.

### Session-Based Dataset

Multiple collection sessions are used to capture natural variations in BLE RSSI caused by factors such as:

* User movement
* Device orientation
* Environmental changes
* Wireless interference
* Signal fluctuations

The final dataset can therefore contain measurements from multiple sessions while preserving the session information.

---

## 🏗 System Architecture

Tracer follows an **MVVM architecture** with separation between the user interface, application logic, data layer, localization system, and navigation engine.

```text
┌───────────────────────────────┐
│       Jetpack Compose UI      │
│                               │
│ Home / Scan / Navigation /    │
│ Tutorial / Destination UI     │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│          ViewModel            │
│                               │
│ UI State / User Actions /     │
│ Scan & Navigation Control     │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│        Repository Layer       │
└───────────────┬───────────────┘
                │
       ┌────────┴─────────┐
       ▼                  ▼
┌───────────────┐  ┌────────────────┐
│ BLE Scanner   │  │ Localization   │
│               │  │ Engine         │
└───────┬───────┘  └───────┬────────┘
        │                  │
        ▼                  ▼
   RSSI Values       ML / ONNX Model
                           │
                           ▼
                    Current Node
                           │
                           ▼
                 ┌─────────────────┐
                 │ Navigation      │
                 │ Engine          │
                 │                 │
                 │ A* + Graph      │
                 └────────┬────────┘
                          │
                          ▼
                   Route Guidance
```

---

## 🗺 Navigation System

After determining the user's current node, Tracer uses an indoor navigation graph to calculate a route to the selected destination.

The project uses the **A* pathfinding algorithm** to determine an efficient path through the mapped indoor environment.

```text
Current Location
       │
       ▼
   Node Graph
       │
       ▼
 A* Pathfinding
       │
       ▼
 Optimal / Efficient Path
       │
       ▼
 Route Visualization
       │
       ▼
   User Guidance
```

The navigation system supports destination-based route generation and visual route guidance on the department map.

---

## 📱 Application Flow

The general application flow is:

```text
Launch Tracer
      │
      ▼
First-Launch Tutorial
      │
      ▼
Home Screen
      │
      ├───────────────┐
      ▼               ▼
Current Location   Destination
      │               │
      └───────┬───────┘
              ▼
        Route Generation
              │
              ▼
        A* Pathfinding
              │
              ▼
        Route Guidance
```

For localization, the application continuously obtains BLE RSSI information and uses the localization pipeline to determine the current reference node.

---

## 🎓 Tutorial

Tracer includes a first-launch walkthrough to introduce users to the major features of the application.

The tutorial highlights important Home screen controls and provides:

* Step-by-step guidance
* Spotlight highlighting
* Skip option
* Back navigation
* Next navigation
* Completion state

The tutorial is designed to help first-time users understand the Tracer interface before using indoor navigation.

---

## 🔗 Tracer Kiosk Integration

Tracer also supports a **QR-based handoff from the Tracer Kiosk system**.

The Android application can receive a custom deep link using the `tracer://navigate` URI scheme.

Example structure:

```text
tracer://navigate?destination=<destination>&start=<start>&floor=<floor>&version=<version>
```

The application reads the navigation parameters and can use the destination information to continue the indoor navigation workflow.

---

## 🎨 User Interface

The application uses:

* **Jetpack Compose**
* **Material 3**
* Custom Tracer branding
* Responsive Compose layouts
* Interactive map components
* Animated navigation elements
* Dark/light theme support

The interface is designed specifically for indoor navigation in a campus environment.

---

## 🛠 Tech Stack

| Technology | Usage |
|---|---|
| Kotlin | Programming Language |
| Jetpack Compose | UI Toolkit |
| Material 3 | Design System |
| MVVM | Application Architecture |
| Hilt | Dependency Injection |
| BLE | Indoor Positioning |
| RSSI Fingerprinting | Localization |
| KNN | Supervised ML Localization |
| Random Forest | Supervised ML Localization |
| SVM | Supervised ML Localization |
| ONNX Runtime | On-device ML Inference |
| A* Algorithm | Indoor Pathfinding |
| Android DataStore | Persistent Storage |
| QR / Deep Linking | Kiosk-to-Mobile Handoff |
| Android Studio | Development Environment |

---

## 📂 Project Structure

```text
Tracer-Android/
│
├── app/
│   ├── data/
│   │   ├── ble/
│   │   ├── localization/
│   │   ├── ml/
│   │   ├── navigation/
│   │   └── datastore/
│   │
│   ├── domain/
│   │
│   ├── ui/
│   │   ├── screens/
│   │   ├── components/
│   │   ├── navigation/
│   │   └── tutorial/
│   │
│   └── di/
│
├── branding/
│   ├── logo/
│   ├── brand-guide/
│   ├── screenshots/
│   └── mockups/
│
├── docs/
│
├── gradle/
│
└── README.md
```

> The exact package and directory structure may evolve as development continues.

---

## 🏫 Current Navigation Coverage

The completed prototype focuses on indoor navigation within the **ECE Department**.

The mapped environment includes the currently implemented department areas and navigation nodes used by the project.

The navigation architecture is designed so that additional wings, floors, and locations can be incorporated as the campus map is expanded.

---

## 🧭 Navigation Components

The navigation system consists of:

### Current Location

The localization system estimates the user's current reference node from the observed BLE RSSI fingerprint.

### Destination

Users can select an available destination from the application.

### Route Calculation

The navigation engine calculates a path between the current node and destination using A*.

### Route Guidance

The calculated path is rendered on the interactive department map and presented to the user through the navigation interface.

---

## 💾 Persistent Data

Tracer uses **Android DataStore** for persistent application data such as recent destinations.

This allows frequently selected destinations to remain available between application sessions.

---

## 🔐 Permissions

Tracer uses Android permissions required for its core functionality.

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

The application handles Bluetooth and camera capabilities according to the Android platform requirements.

---

## 🧪 Research Foundation

The project is based on research and experimentation in:

* BLE Indoor Localization
* RSSI Fingerprinting
* Machine Learning for Indoor Positioning
* BLE-based RSSI measurement
* Indoor Navigation
* Path Planning
* A* Search
* Mobile Robot Path Planning
* Machine Learning Model Deployment

The project also investigates practical challenges associated with RSSI-based indoor localization, including signal variability and environmental effects.

---

## 📚 References

The project research includes literature covering:

* BLE-based indoor localization and RSSI measurement
* RSSI fingerprint-based indoor localization
* Machine learning approaches for indoor positioning
* Indoor navigation systems
* A* path planning
* Path planning algorithms for mobile robots

Research papers and technical references used during development are maintained as part of the project's academic documentation.

---

## 📈 Development Journey

Tracer was developed incrementally through multiple stages:

```text
Project Idea
     │
     ▼
System Design
     │
     ▼
Android Application Setup
     │
     ▼
MVVM + Hilt Architecture
     │
     ▼
BLE Scanner
     │
     ▼
RSSI Data Collection
     │
     ▼
Fingerprint Dataset
     │
     ▼
Machine Learning Training
     │
     ▼
Localization Model
     │
     ▼
ONNX Integration
     │
     ▼
Indoor Map
     │
     ▼
A* Navigation
     │
     ▼
Route Guidance
     │
     ▼
Kiosk QR Handoff
     │
     ▼
Integrated Tracer Prototype
```

---

## 🚧 Project Status

### Final Major Project Milestone

* [x] Project Initialization
* [x] Android Application Development
* [x] Navigation Compose
* [x] MVVM Architecture
* [x] Hilt Integration
* [x] Tracer Branding
* [x] Material 3 UI
* [x] BLE Scanner
* [x] RSSI Fingerprinting Pipeline
* [x] Session-Based Data Collection
* [x] Machine Learning Training Pipeline
* [x] KNN Model
* [x] Random Forest Model
* [x] SVM Model
* [x] ONNX Localization Integration
* [x] Indoor Map
* [x] A* Indoor Navigation
* [x] Interactive Zoomable Map
* [x] Route Visualization
* [x] Animated Route Guidance
* [x] Current Location Display
* [x] Destination Selection
* [x] Recent Destination Persistence
* [x] First-Launch Tutorial
* [x] Tracer Kiosk QR Handoff
* [x] Left Wing Navigation MVP
* [x] Integrated Mobile Navigation Workflow

---

## 🗺 Roadmap / Future Scope

Although the major project implementation is complete, the system can be extended further.

Possible future improvements include:

* Right Wing Integration
* Additional Floor Mapping
* Multi-floor Navigation
* Improved Real-time Localization
* Dynamic Route Updating
* Dynamic Route Shrinking During Walking
* Voice Navigation Guidance
* Arrival Notifications
* Improved Localization Accuracy
* Additional BLE Beacon Configurations
* Augmented Reality Indoor Guidance
* Larger Smart Campus Deployment

---

## 📊 Project Objectives

The major objectives of Tracer are:

1. Develop a BLE-based indoor localization system.
2. Collect and organize RSSI fingerprint data from predefined indoor reference nodes.
3. Apply supervised machine learning algorithms for location prediction.
4. Integrate the trained localization model into an Android application.
5. Develop an indoor map and navigation graph.
6. Implement A* pathfinding for indoor route generation.
7. Provide an intuitive mobile navigation interface.
8. Integrate the mobile application with the Tracer Kiosk workflow.
9. Build a practical smart-campus indoor navigation prototype.

---

## 👨‍💻 Developers

**Shrihari N B G**

**Shreesha M Tembe**

**Shubhangi S Naik**

**Vageesh I Gaonkar**

Final Year Engineering Students

---

## 📄 License

Tracer is released under the **Apache License 2.0**.

Copyright © 2026 Shrihari N B G, Shreesha M Tembe, Shubhangi S Naik, and Vageesh I Gaonkar.

You may use, reproduce, modify, and distribute this software in accordance with the terms and conditions of the Apache License 2.0.

See the [`LICENSE`](LICENSE) file for the complete license text.

This project was developed as a Final Year Engineering Major Project for academic and research purposes.

---

## ⭐ Project Status

Tracer has progressed from an initial project concept to an integrated **BLE RSSI fingerprinting-based indoor localization and navigation prototype**.

The completed system brings together:

```text
BLE Scanning
      +
RSSI Fingerprinting
      +
Machine Learning Localization
      +
ONNX Runtime
      +
Indoor Mapping
      +
A* Pathfinding
      +
Route Guidance
      +
Kiosk QR Handoff
      ↓
Tracer
Smart Campus Indoor Navigation
```

---

<p align="center">
  <b>Built with ❤️ using Kotlin, Jetpack Compose, BLE, Machine Learning, ONNX Runtime, and A* Pathfinding.</b>
</p>

<p align="center">
  <b>Tracer — Making Indoor Campus Navigation Smarter.</b>
</p>
