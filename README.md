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
  <img src="https://img.shields.io/badge/Status-Final%20Stage-success" alt="Status"/>
</p>

# Tracer – Smart Campus Indoor Navigation

Tracer is an **AI-assisted Android indoor navigation system** designed for smart campus environments.

The system uses **Bluetooth Low Energy (BLE) RSSI fingerprinting** to estimate the user's indoor location and combines the predicted location with **A* pathfinding** to provide indoor navigation.

The project is developed as a **Final Year Engineering Major Project** and is designed for indoor navigation within the **Electronics & Communication Engineering (ECE) Department**.

The current implementation integrates the complete pipeline from **BLE signal acquisition and fingerprinting to localization and route guidance**.

---

# 🚀 Key Features

### 📡 BLE-Based Localization

* BLE beacon scanning
* Real-time RSSI acquisition
* Multi-beacon RSSI fingerprint generation
* Session-based fingerprint data collection
* Indoor fingerprint database generation

### 🤖 Machine Learning Localization

* RSSI fingerprint-based indoor localization
* Supervised machine learning models
* K-Nearest Neighbors (KNN)
* Random Forest
* Support Vector Machine (SVM)
* Model evaluation and comparison
* ONNX-based model inference integration

### 🗺 Indoor Navigation

* A* pathfinding algorithm
* Indoor navigation graph
* Node-based route planning
* Current-location-aware navigation
* Interactive department map
* Zoomable and pannable map
* Animated route visualization
* Destination selection

### 📱 Android Application

* Modern Jetpack Compose UI
* Material 3 design
* MVVM architecture
* Hilt dependency injection
* BLE permission handling
* QR-based navigation handoff
* Persistent recent destinations
* First-launch tutorial
* Dark/light theme support

---

# 🧠 System Architecture

Tracer follows a layered **MVVM-based architecture**.

```text
                    ┌───────────────────────┐
                    │     Jetpack Compose   │
                    │          UI           │
                    └───────────┬───────────┘
                                │
                                ▼
                    ┌───────────────────────┐
                    │       ViewModel       │
                    │   Application Logic   │
                    └───────────┬───────────┘
                                │
                                ▼
                    ┌───────────────────────┐
                    │     Data / Domain     │
                    │       Layer           │
                    └───────────┬───────────┘
                                │
                 ┌──────────────┴──────────────┐
                 ▼                             ▼
        ┌─────────────────┐          ┌─────────────────┐
        │   BLE Scanner   │          │   Navigation    │
        │                 │          │     Engine      │
        └────────┬────────┘          └────────┬────────┘
                 │                            │
                 ▼                            ▼
        ┌─────────────────┐          ┌─────────────────┐
        │  RSSI Feature   │          │  A* Pathfinding │
        │   Generation    │          │      Graph      │
        └────────┬────────┘          └─────────────────┘
                 │
                 ▼
        ┌─────────────────┐
        │ Machine Learning│
        │   Localization  │
        └────────┬────────┘
                 │
                 ▼
        ┌─────────────────┐
        │ Current Location│
        │    Prediction   │
        └────────┬────────┘
                 │
                 └──────────────► Navigation
