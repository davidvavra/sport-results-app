# sport-results-app
Sample app showcasing best practices in Android development

<img src="https://user-images.githubusercontent.com/395884/178152093-8434ee57-1bc6-4794-99bd-552523156092.png" width="300">

## Features
The app is a simple tracker of sport results. It allows to enter name, place and duration of the sport results. You can save it locally or remotely and then filter the list based on it.

## Tech
- Kotlin
- Jetpack Compose for all UI (single Activity)
- Compose Navigation for navigating between screens
- Material3 theme (color mirrors system from Android 12)
- Dark mode support
- Portrait & landscape support, proper handling of orientation changes
- ViewModels for each composable screen
- Dependency injection via Hilt
- Room database with observing data via Flow
- Retrofit+Moshi+coroutines for network requests
- Backend is unauthenticated Firebase Realtime Database (but accessed via REST API, not SDK)
- Form validation, loading & error handling for new sport result
- Unit tests for viewmodels & repositories

## Further work
I skipped some tech I would normally include in a real production app due to time contraints:
- Localization (now hardcoded strings)
- Modularization (`feature/sport-results`, `feature/new-sport-result`, `common/storage`, `common/network`, `common/ui`, `app`)
- Screenshot tests
- Espresso tests
- CI/CD for pull request validation and releases via Github Actions
- Error handling, loading indicator and pull-to-refresh for the list of sport results
- Crashlytics & Analytics
- Timber library for better logging
- some authentication so users see only their own data
- possibility to delete & edit sport results
