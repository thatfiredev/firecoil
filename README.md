[![Build Status](https://travis-ci.org/rosariopfernandes/firecoil.svg?branch=master)](https://travis-ci.org/rosariopfernandes/firecoil)
[![](https://jitpack.io/v/rosariopfernandes/firecoil.svg)](https://jitpack.io/#rosariopfernandes/firecoil)

# firecoil

**First things first: What is Cloud Storage?**
[Cloud Storage for Firebase](https://firebase.google.com/docs/storage/) is a Firebase Service for storing
 user-generated content, such as Images, Videos, Audios, etc.

firecoil allows you to load images from Cloud Storage in your Android app, using the image loading
 library [Coil](https://github.com/coil-kt/coil).

# Getting Started

## Prerequisites

- AndroidX
- Android Min SDK 16+
- Compile SDK 29+
- Java 8 (see [how to enable Java 8](#java-8))

## Adding the dependency

Step 1 - Add the jitpack maven in your root `build.gradle` at the end of repositories:
```gradle
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

Step 2 - Add the dependency to your app's `build.gradle` file:
```gradle
    dependencies {
        // other dependencies

        implementation 'com.github.rosariopfernandes:firecoil:0.0.1'

        // If you're not already using Coil, also add its dependency:
        implementation 'io.coil-kt:coil:0.9.4'
    }
```

## Usage

### Basic Usage
```kotlin
val storageRef: StorageReference = ...
val imageView: ImageView = ...

imageView.load(storageRef)
```

### Custom Requests
```kotlin
val storageRef: StorageReference = ...
val imageView: ImageView = ...

imageView.load(storageRef) {
    crossfade(true)
    placeholder(R.id.placeholder)
}
```

### Using an [ImageLoader](https://coil-kt.github.io/coil/image_loaders/)

Add the `StorageReferenceFetcher` when creating the ImageLoader:

```kotlin
val imageLoader = ImageLoader(this) {
    componentRegistry {
        add(StorageReferenceFetcher())
    }
}
```

**Load using Disposable requests:**

This also allows loading images to custom targets.
```kotlin
val request = imageLoader.load(context, storageRef) {
    // Load into the ImageView
    target(imageView)

    // Load into any other target:
    // target { drawable ->
    //     ...
    // }
}
// ...
// To cancel the request:
request.dispose()
```

**Load on a Background Thread:**

Note that `ImageLoader.get()` is a `suspend` function, so it needs to be called from another `suspend` function
 or a Coroutine Builder like `launch`.

```kotlin
launch {
    val drawable = imageLoader.get(storageRef) {
        size(width, height)
    }
}
```

# Java 8
Just like Coil, firecoil requires Java 8. To enable Java 8, add the following to your
 app's `build.gradle` file:
```gradle
    android {
        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
    }

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).all {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
```

# Running the Sample App

To get the sample app up and running, you have to:
1. Download this repository as ZIP and extract it or clone it using `git clone https://github.com/rosariopfernandes/firecoil`
1. Open the [Firebase Console](https://console.firebase.google.com/), create a new project (or use an existing one) and add an android app.
1. When prompted, use the application ID "io.github.rosariopfernandes.firecoilsampleapp".
1. Download the `google-services.json` file and copy it to the `sampleapp` folder.
1. Build the project

Note: Please make sure your Cloud Storage Security Rules allow reading files.

# Contributing
Anyone and everyone is welcome to contribute. Please take a moment to
review the [contributing guidelines](CONTRIBUTING.md).

# License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

# Acknowledgments
* Inspired by [FirebaseUI for Storage](https://github.com/firebase/FirebaseUI-Android/tree/master/storage)
