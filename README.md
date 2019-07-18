# Android Tutorial: Creating Infinite Lists in Android

In this tutorial, you will learn how implement infinite scrolling in your Android app. You will learn how to optimize loading of large sets of data in chunks from an API.

## Prerequisites

You need the following installed on your machine:

* [Android Studio 3.4+](https://developer.android.com/studio/index)

## Getting started

### Running the app

To run the app, clone the repository and open the project in Android Studio.

### Setting up the backend

The backend sample is hosted [here](https://github.com/KingIdee/to-dos-api-express/). Follow the instructions in the repo to set your server up. The backend uses an in-memory database, so to populate it with a lot of data for testing, you can replace line 56 of the `index.js`:

with this:

```kotlin
for (let index = 0; index < 100; index++) {	
    await insertToDo({ message: "Buy pizza!" + index });	
}
```
