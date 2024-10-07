# Rick and Morty Character Explorer

An Android app built using Kotlin that showcases characters from the Rick and Morty universe. The app allows users to explore, search, and favorite characters.

## Features

- **Character List**: Displays a list of characters with name, species, gender, location, and the episode they appear in.
- **Search Functionality**: Filter characters by name with a real-time search view.
- **Character Details**: View detailed information about a selected character.
- **Favorite Characters**: Users can favorite or unfavorite characters, and these preferences are persisted.
- **Smooth UI Updates**: Efficient list updating with `DiffUtil` for minimal UI changes.
- **MVVM Architecture**: Clean separation of concerns with ViewModel and LiveData to manage UI-related data.
  
## Tech Stack

- **Kotlin**: Primary language for Android development.
- **Dagger Hilt**: For dependency injection.
- **Retrofit**: For network requests to the Rick and Morty API.
- **Glide**: For loading images.
- **LiveData and ViewModel**: For managing UI-related data.
- **RecyclerView**: For displaying the character list.

## Screenshots

![Character List]()
![Character Details](https://link_to_screenshot_2)
