# Android Development Course Projects (MIREA) 📱

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![SQLite](https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white)

Данный репозиторий представляет собой многомодульный Android-проект, разработанный в рамках курса по мобильной разработке (РТУ МИРЭА). 

Проект демонстрирует последовательное освоение Android SDK: от базовой верстки и жизненного цикла компонентов до работы с аппаратными датчиками, базами данных, фоновыми задачами и картографическими сервисами.

---

## 🌟 Главный проект: MireaProject

Основное приложение репозитория, объединяющее изученные технологии в единый интерфейс с использованием бокового меню (`Navigation Drawer`). 

**Ключевые возможности MireaProject:**
* **Аппаратная часть (Hardware):** Взаимодействие с камерой (через `MediaStore` и `FileProvider`), запись и воспроизведение аудио (`MediaRecorder`, `MediaPlayer`), получение данных с акселерометра (`SensorManager`).
* **Фоновые задачи:** Использование `WorkManager` для выполнения отложенных операций вне UI-потока.
* **Картография (Places):** Интеграция **OSMDroid** (OpenStreetMap) с отображением компаса, шкалы масштаба и пользовательских маркеров (POI).
* **Хранение данных:** 
  * Сохранение настроек профиля через `SharedPreferences`.
  * Работа с файловой системой устройства (сохранение и чтение файлов).
  * Базовое шифрование текстовых данных (Base64).
* **Сеть (Network):** Выполнение асинхронных HTTP-запросов к публичным REST API и обновление UI.
* **Web:** Встраивание веб-страниц с помощью `WebView`.

---

## 📚 Учебные модули (Lessons 1-8)

Помимо `MireaProject`, репозиторий содержит отдельные модули-уроки, каждый из которых посвящен конкретной теме Android-разработки:

* **Lesson 1-2 (UI & Lifecycle):** Базовые Layouts (`ConstraintLayout`, `LinearLayout`), жизненный цикл `Activity`, неявные и явные `Intent`, работа с диалоговыми окнами (`AlertDialog`, `DatePicker`) и уведомлениями (`NotificationManager`).
* **Lesson 3 (Navigation):** Работа с `Fragment`, передача данных между экранами, использование `ActivityResultLauncher` (вместо устаревшего `startActivityForResult`).
* **Lesson 4 (Background Work):** Многопоточность (`Thread`, `Looper`, `Handler`), `Service` (включая Foreground Services для музыкального плеера), `WorkManager`, основы криптографии (`Cipher`, `KeyGenerator`).
* **Lesson 5 (Hardware):** Взаимодействие с `SensorManager`, `Camera API`, работа с микрофоном.
* **Lesson 6 (Data Storage):** Внутреннее и внешнее хранилище, `EncryptedSharedPreferences`, локальные базы данных через **Room ORM** (DAO, Entities).
* **Lesson 7 (Networking):** HTTP-запросы (`HttpURLConnection`), парсинг JSON, интеграция **Firebase Authentication** (регистрация, авторизация, верификация email).
* **Lesson 8 (Maps):** Интеграция картографических сервисов **Yandex MapKit** и **OSMDroid**, работа с геолокацией (`ACCESS_FINE_LOCATION`), построение маршрутов.

---

## 🛠️ Технологический стек

* **Язык:** Java
* **Архитектура и UI:** ViewBinding, Fragments, Navigation Component, Material Design.
* **Асинхронность:** Threads, Handlers/Loopers, WorkManager, AsyncTaskLoader.
* **Хранение данных:** Room (SQLite), SharedPreferences, EncryptedSharedPreferences, File I/O.
* **Сеть и Облако:** HttpURLConnection, Firebase Auth, REST API.
* **Карты:** Yandex MapKit, OSMDroid.

---

## 🚀 Как запустить проект

1. Склонируйте репозиторий:
   ```bash
   git clone https://github.com/efrosim/MobDevAndroid.git
   ```
2. Откройте проект в **Android Studio**.
3. Дождитесь завершения синхронизации Gradle.
4. **Настройка Firebase (для модуля Lesson 7 - firebaseauth):**
   * Создайте проект в [Firebase Console](https://console.firebase.google.com/).
   * Добавьте Android-приложение и скачайте файл `google-services.json`.
   * Поместите файл `google-services.json` в папку `Lesson7/firebaseauth/`.
5. **Настройка Yandex Maps (для модуля Lesson 8):**
   * Получите API-ключ в кабинете разработчика Яндекса.
   * Замените значение `MAPKIT_API_KEY` в классе `App.java` (в модулях `yandexmaps` и `yandexdriver`) на ваш ключ.
6. Выберите нужный модуль (например, `app` для MireaProject) в выпадающем списке конфигураций запуска и нажмите **Run**.

---
*Проект разработан в образовательных целях в рамках программы РТУ МИРЭА.*