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
  <img src="https://img.shields.io/badge/Status-Under%20Development-red" alt="Status"/>
</p>

# Tracer – Smart Campus Indoor Navigation

Tracer is an AI-powered Android application that provides **BLE RSSI Fingerprinting-based Indoor Localization** and **Indoor Navigation** for smart campus environments. The application combines **BLE beacon scanning**, **ONNX-based machine learning localization**, and **A* pathfinding** to deliver real-time indoor navigation inside the Electronics & Communication Engineering (ECE) Department.

The project is being developed as a **Final Year Engineering Major Project** using modern Android development practices and currently includes a working **Left Wing Navigation MVP**.

---

## 🚀 Features

* 📡 BLE Beacon Scanning
* 📍 RSSI Fingerprinting-based Indoor Localization
* 🤖 ONNX Machine Learning Location Prediction
* 🗺 A* Indoor Navigation
* 🧭 Interactive Zoomable Department Map
* 🎞 Animated Route Guidance
* 📌 Live Current Location Display
* 💾 Persistent Recent Destinations (DataStore)
* 📱 Modern Material 3 UI
* 🏫 Smart Campus Support
* 🧭 Augmented Reality Navigation (Future Scope)

---

## 🏗 Architecture

The application follows **MVVM Architecture**.

```text
UI (Jetpack Compose)
        │
        ▼
ViewModel
        │
        ▼
Repository Layer
        │
        ▼
Localization Engine
(BLE Scanner + ONNX Model)
        │
        ▼
Navigation Engine
(A* Pathfinding + Graph)
```

---

## 🛠 Tech Stack

| Technology          | Usage                      |
| ------------------- | -------------------------- |
| Kotlin              | Programming Language       |
| Jetpack Compose     | UI Toolkit                 |
| Material 3          | Design System              |
| MVVM                | Architecture               |
| Hilt                | Dependency Injection       |
| BLE                 | Indoor Positioning         |
| RSSI Fingerprinting | Localization               |
| ONNX Runtime        | Machine Learning Inference |
| A* Algorithm        | Indoor Navigation          |
| Android DataStore   | Persistent Storage         |
| Android Studio      | Development IDE            |

---

## 📂 Project Structure

```text
Tracer-Android/

├── app/
│   ├── data/
│   │   ├── ble/
│   │   ├── localization/
│   │   ├── ml/
│   │   ├── navigation/
│   │   └── datastore/
│   ├── domain/
│   ├── ui/
│   │   ├── screens/
│   │   └── components/
│   └── di/
├── branding/
├── gradle/
├── docs/ (Coming Soon)
└── README.md
```

---

## 🎨 Branding

The project maintains a dedicated branding system.

See:

```text
branding/
```

for:

* Brand Guide
* Design Assets
* Color Palette
* Screenshots
* Mockups

---

## 📱 Screenshots

Application screenshots and navigation demonstrations will be added as development progresses.

---

## 🚧 Development Status

### Current Milestone

* [x] Project Initialization
* [x] Navigation Compose
* [x] MVVM Architecture
* [x] Hilt Integration
* [x] Tracer Branding
* [x] Material 3 UI
* [x] BLE Scanner
* [x] RSSI Fingerprinting Pipeline
* [x] ONNX Localization Integration
* [x] A* Indoor Navigation
* [x] Interactive Zoomable Map
* [x] Animated Route Visualization
* [x] Recent Destination Persistence
* [x] Left Wing Navigation MVP
* [ ] Right Wing Mapping
* [ ] Upper Floor Navigation
* [ ] Multi-floor Navigation
* [ ] AR Navigation

---

## 🗺 Current Coverage

The current prototype supports navigation across the **Left Wing of the ECE Department**, including:

* Left Wing Entrance
* Left Wing Corridor
* Ladies Room
* Faculty Cabin Area
* DC Lab
* Classrooms

---

## 🎯 Roadmap

* Right Wing Integration
* Real-time Moving User Cursor
* Dynamic Route Shrinking During Walking
* Voice Navigation Guidance
* Arrival Notifications
* Augmented Reality Indoor Guidance

---

## 📚 References

The project is based on extensive research in:

* BLE Indoor Localization
* RSSI Fingerprinting
* Machine Learning for Indoor Positioning
* ONNX Runtime Inference
* A* Path Planning
* Indoor Navigation Systems

---

## 👨‍💻 Developers

**Shrihari N B G**

**Shreesha M Tembe**

**Shubhangi S Naik**

**Vageesh I Gaonkar**

Final Year Engineering Students

---

## 📄 License

This repository is currently maintained for **academic and research purposes**.

An open-source license may be added in the future.

---

## ⭐ Project Status

### Current Status

* [x] BLE Beacon Scanning
* [x] RSSI Fingerprinting Pipeline
* [x] ONNX Localization
* [x] A* Indoor Navigation
* [x] Zoomable Interactive Map
* [x] Animated Route Guidance
* [x] Dynamic Current Location Display
* [x] Recent Destination Persistence
* [x] Left Wing Navigation MVP
* [ ] Right Wing Navigation
* [ ] AR Navigation

---

⭐ If you find this project interesting, consider giving it a star.

Built with ❤️ using **Kotlin**, **Jetpack Compose**, and **ONNX Runtime**.
