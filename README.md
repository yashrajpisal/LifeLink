# LifeLink 🚑

**LifeLink** is a JavaFX-based emergency hospital coordination and assistance system built as a college major project. It connects families in a medical emergency with hospitals, ambulance drivers, nurses, doctors, admins, and police through a single coordinated platform — aiming to cut down response time when every second counts.

## Overview

LifeLink is a multi-role desktop application (Java 17 + JavaFX) backed by Firebase/Firestore. Each stakeholder in an emergency gets a dedicated dashboard:

- **Family** — raise an emergency, find nearby care, get first-aid guidance, track ambulance status, view/save medical reports and hospitals
- **Driver (Ambulance)** — receive emergency assignments, voice-command support, trip history, notifications
- **Hospital** — manage doctors, resources, operation theatres, patient monitoring, emergency records, complaints
- **Nurse** — manage staff duties, ICU patient monitoring, voice-based reporting, trip logs
- **Admin** — oversee users, hospitals, ambulances, staff, complaints, analytics/reports, and system-wide emergency monitoring
- **Police** — profile and case history for emergencies requiring police coordination

## Key Features

- Role-based login/signup for six user types (Family, Driver, Hospital, Nurse, Admin, Police)
- Real-time data sync via **Firebase Firestore**
- Emergency SOS alert dispatch via **Green-API** (Telegram)
- Voice command / voice reporting using **Google Cloud Speech** and **Vosk** (offline speech recognition)
- Webcam integration for live video/monitoring features
- Cloud media storage via **Cloudinary**
- Modern JavaFX UI with custom components (shimmer loaders, sidebar navigation, animated flash/welcome screens)

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| UI Framework | JavaFX 21 |
| Build Tool | Maven |
| Icons | Ikonli (Material Design 2) |
| Backend / DB | Firebase Admin SDK, Cloud Firestore |
| Speech-to-Text | Google Cloud Speech, Vosk |
| Media Storage | Cloudinary |
| Messaging | Green-API (Telegram) |
| Camera | webcam-capture (Sarxos) |
| JSON | org.json |

## Project Structure

lifelink1/
├── pom.xml
└── src/main/
├── java/com/kurukshetra/
│ ├── config/ # Firebase configuration
│ ├── controller/ # Role-based controllers (admin, driver, family, hospital, nurse, police)
│ ├── dao/ # Data access objects per role
│ ├── model/ # Data models per role
│ ├── service/ # External service integrations (Green-API, etc.)
│ ├── view/ # JavaFX views/screens per role
│ └── Main.java # Application entry point
└── resources/
├── assets/ # Images, carousel, video, welcome screen assets
├── css/ # Stylesheets
├── fonts/
└── videos/


## Prerequisites

- JDK 17+
- Maven 3.8+
- A Firebase project with a Firestore database and a generated service-account key (`lifelinkFirebase.json`)
- (Optional) Google Cloud Speech API credentials, Cloudinary account, Green-API Telegram instance

## Setup

1. **Clone the repo**
   git clone https://github.com/yashrajpisal/LifeLink.git
   cd LifeLink/lifelink1

2. **Add your Firebase service account key**
   - Download your Firebase service-account JSON from the Firebase console.
   - Place it inside `src/main/resources/` (e.g. `lifelinkFirebase.json`).
   - Update the hardcoded path in `Main.java` and `FirebaseConfig.java` to point to your local file (or, better, refactor to load it via a relative path / environment variable).

3. **Set environment variables for Green-API** (recommended instead of the in-code fallback values)
   export GREEN_API_ID_INSTANCE=your_id_instance
   export GREEN_API_TOKEN_INSTANCE=your_api_token
   export GREEN_API_URL=https://your-instance.api.green-api.com

4. **Build and run**
   mvn clean javafx:run


## Notes

- This project was built for academic purposes as part of a Java/SuperX 2026 college project.
- Some paths (Firebase credentials, image assets) are currently hardcoded to a local Windows path and should be made relative before sharing the repo further.

## Author

Yashraj Pisal,
Jayraj Pisal,
Krushna Chandre,
Om Pawar,
Prajwal Ekande