# 📱 Mudah Chat

A coding challenge to develop a simple chat screen for Android with preloaded messages, mock API posting, idle response logic, and robust architecture.

---

## 🧩 Overview

Given this sample chat data:

```json
{
  "chat": [
    {
      "timestamp": "2018-05-28T10:00:00.000Z",
      "direction": "OUTGOING",
      "message": "Hello"
    },
    {
      "timestamp": "2018-05-29T11:05:00.000Z",
      "direction": "INCOMING",
      "message": "Hi"
    },
    ...
  ]
}

```

Requirements: 

🧱 Build UI with  Jetpack Compose

✉️ POST new messages to: https://reqres.in/api/users
Example:

```json
{ "message": "Hello, world" }

```

Response:
```json
{
  "message": "Hello, world",
  "id": "287",
  "createdAt": "2018-06-30T13:08:04.623Z"
}

```
⏱️ If the user is idle for 1 minute, the app should send "Are you there?" automatically

 Tech Stack (Android)

| Layer           | Tech                          |
|------------------|-------------------------------|
| Language         | Kotlin                        |
| Architecture     | MVVM                          |
| UI               | Jetpack Compose               |
| DB               | Room                          |
| Network          | Retrofit + RxJava2            |
| DI               | Hilt                          |
| Threading        | RxJava2                        |
| Error Handling   | Network + Server Exceptions   |
| Testing          | JUnit, Espresso               |

Unit Tests
```bash 
./gradlew test
./gradlew connectedAndroidTest
```

## Notes
Room inserts are done using Completable on Schedulers.io() to avoid IllegalStateException

Retrofit calls are mocked using MockInterceptor for offline testing

Compose is used instead of XML for modern UI practice


