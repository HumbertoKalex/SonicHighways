# 🎸 SonicHighways

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-blue.svg)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-API%2024+-green.svg)](https://developer.android.com/)
[![Compose](https://img.shields.io/badge/Jetpack-Compose-orange.svg)](https://developer.android.com/jetpack/compose)

**SonicHighways** is an experimental music player that consumes the iTunes API to allow for the exploration of tracks and albums. The project focuses on demonstrating a modern, reactive architecture with smart local persistence.

---

## 🚀 Key Features

* 🔍 **Dynamic Search:** Real-time search for artists, songs, or albums.
* 💿 **Album Details:** Automatic track filtering (displays only tracks with available audio previews).
* 🎧 **Mini-Player:** Integrated playback for 30-second samples with Play/Pause and Seek controls.
* 💾 **Smart History (Room):** Automatically saves the last **10 selected songs** locally.
* 🔄 **Automatic Fallback:** If no history exists, the app automatically suggests "Rock" classics.
* 📱 **Adaptive UI:** Interface built 100% using **Material 3** and **Jetpack Compose**.

---

## 🛠 Tech Stack & Architecture

The project follows **Clean Architecture** and **MVVM** principles:

* **UI:** Jetpack Compose (States & Flows).
* **DI:** [Koin](https://insert-koin.io/) (Lightweight and fast dependency injection).
* **Network:** Retrofit + OkHttp + Gson.
* **Database:** Room + **KSP** (Kotlin Symbol Processing).
* **Async:** Kotlin Coroutines & Flow.
* **Versioning:** Gradle Version Catalog (`libs.versions.toml`).

---

## 🧪 Testing Strategy

The presentation and domain layers are covered by robust unit tests:

* **MockK:** For mocking Repositories and Managers.
* **Coroutines Test:** Handling Dispatchers in a testing environment.
* **JUnit 4:** Standard runner for the test suite.
* **InstantTaskExecutorRule:** Background task synchronization.

---

## 🏁 How to Run the Project

### 1. Clone the Repository
```bash
git clone [https://github.com/HumbertoKalex/SonicHighways.git](https://github.com/HumbertoKalex/SonicHighways.git)
```

### 2. Environment Setup
* Ensure you are using **Android Studio Ladybug** (or higher).
* Use **JDK 17**.

### 3. SourceSets Adjustment (KSP)
Due to a strict rule in recent Gradle versions with KSP, verify that your `gradle.properties` file contains the following flag:

```properties
android.disallowKotlinSourceSets=false
```

### 4. Build & Run
Simply sync Gradle and run the app on your emulator or physical device.


## 👤 Author

Developed by **Humberto Kalex**.

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/humbertokalex)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/HumbertoKalex)

---
*This project was created for architectural study and exploration of new Android technologies.*
