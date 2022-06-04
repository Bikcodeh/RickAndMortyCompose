# Rick and Morty - Compose

This app is built to practice some concepts, skills and new technologies to me. The application allow fetch all the information from [Rick And Morty API](https://rickandmortyapi.com/).

## :star: Features

- [x] Fetch characters from the API and display them in a list.
- [x] Show error screen if try to fetch the data and doesn't have network connection.
- [x] Implements Pagination.
- [x] Save all the characters in local storage.
- [x] See the detail from a specific character.
- [x] Unit tests for domain, data and presentation layers
- [x] Implements code coverage with Jacoco

:runner: For run the app just clone the repository and execute the app on Android Studio.

### Requirements to install the app
- Use phones with Android Api 23+
- Having an internet connection

##### This application was developed using Kotlin and uses the following components:
- Jetpack compose
- Coroutines
- Clean architecture (Domain, Data, Presentation)
- MVVM
- Repository pattern
- Use cases
- Flow
- StateFlow
- Jetpack navigation
- Retrofit
- Room dabatase (Local storage)
- Pagination
- ViewModel
- Accompanist ( navigation animation)
- Dagger Hilt (Dependency injection)
- Coil (Load images)
- Retrofit (HTTP requests)
- Unit testing (Mockk, Turbine)
- Code coverage (Jacoco)

## Screnshots
|                    Main Screen                    |                        Detail                         |                       Detail episodes list                   
| :-----------------------------------------------: | :---------------------------------------------------: | :--------------------------------------------------------: 
|   ![Home](assets/home.png?raw=true)   |   ![Favorites](assets/detail.png?raw=true)   |   ![Post Detail](assets/detail_episodes.png?raw=true)

## Screnshots Dark Mode

|                    Main Screen                    |                        Detail                         |
| :-----------------------------------------------: | :---------------------------------------------------: |
|   ![Home](assets/home_dark.png?raw=true)   |   ![Favorites](assets/detail_dark.png?raw=true)  |

## :dart: Architecture

The application is built using Clean Architeture pattern based on [Architecture Components](https://developer.android.com/jetpack/guide#recommended-app-arch) on Android. The application is divided into three layers:

![Clean Arquitecture](https://devexperto.com/wp-content/uploads/2018/10/clean-architecture-own-layers.png)

- Domain: This layer contains the business logic of the application, here we define the data models and the use cases.
- Data: This layer contains the data layer of the application. It contains the database, network and the repository implementation.
- Presentation: This layer contains the presentation layer of the application.

The organization of the application is based on the following package diagram:

  app/
    ├── data/
    │	├── local/
    │	├── model/
    │	├── remote/
    │   └── repository/
    ├── di/
    ├── domain/
    │   └── model/
    │   └── repository/
    │   └── use_case/
    ├── navigation/
    ├── presentation/
    │   └── components/
    │        └── screens/
    │        └── util/
    └── ui/


## License

MIT

**Bikcodeh**
