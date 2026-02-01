# Student Task Manager 📚✨

An Android application designed to help students and anyone manage their daily tasks efficiently. Built using modern Android development best practices with Kotlin and Jetpack Compose. This project demonstrates core mobile development concepts from UI design to local data persistence.

## Features 🚀

-   **Task Creation:** Easily add new tasks with a title (mandatory) and an optional description.
-   **Task List Display:** View all your tasks in a clean, scrollable list.
-   **Task Completion Toggle:** Mark tasks as completed or pending using a checkbox. Completed tasks are visually struck through.
-   **Task Deletion:** Remove tasks from your list with a dedicated delete button.
-   **Data Persistence:** All tasks are saved locally using Room Database, ensuring your data is retained even after closing the app.
-   **Navigation:** Seamless navigation between the task list and the add task screen using Jetpack Compose Navigation.
-   **Empty State UI:** A user-friendly message is displayed when there are no tasks.
-   **Light/Dark Mode Support:** The app's theme adapts automatically to the device's system-wide light or dark mode settings.
-   **Input Validation:** Basic validation for task title to ensure meaningful task creation.

## Technologies Used 🛠️

-   **Kotlin:** The primary programming language for Android development.
-   **Jetpack Compose:** Modern toolkit for building native Android UI.
-   **Jetpack Navigation Compose:** For managing in-app navigation between screens.
-   **Room Database:** An abstraction layer over SQLite to provide robust local data persistence.
-   **ViewModel:** Part of Android Jetpack's Architecture Components, used for UI-related data management and lifecycle awareness.
-   **Kotlin Coroutines & Flow:** For asynchronous programming and observing database changes.
-   **Material Design 3:** For consistent and modern UI components.

## Screenshots 📸

![Student Task Manager - Main Screen]([https://ibb.co/bh2pQqh](https://image2url.com/r2/default/images/1769969623156-f722ccd8-10f1-4336-9e6d-a5ffcafc48fb.png))
![Student Task Manager - Add Task Screen]([https://ibb.co/S7tgB4wv](https://image2url.com/r2/default/images/1769969672260-785d35f5-ea08-44bb-bc81-54033a1dacf2.png))
![Student Task Manager - Main Screen After adding]([https://ibb.co/PGctkL5Y](https://image2url.com/r2/default/images/1769969723714-82e18cdf-8058-42bd-8a36-a4fc4e993fdf.png))

## Getting Started (Local Setup) 🧑‍💻

To get a copy of the project up and running on your local machine for development and testing purposes:

1.  **Clone the repository:**
    git clone [https://github.com/05-M/Student-Task-Manager]
2.  **Open in Android Studio:** Open the cloned project in Android Studio (version Jellyfish | 2023.3.1 or newer is recommended).
3.  **Sync Gradle:** Allow Gradle to sync and download all necessary dependencies, This may take a few minutes for the first sync.
4.  **Run on emulator or device:** Build and run the app on an Android emulator or a physical device.

## Contributing 🤝

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are greatly appreciated.

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request
