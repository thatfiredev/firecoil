[![Build Status](https://travis-ci.org/rosariopfernandes/firecoil.svg?branch=master)](https://travis-ci.org/rosariopfernandes/firecoil)
[![](https://jitpack.io/v/rosariopfernandes/firecoil.svg)](https://jitpack.io/#rosariopfernandes/firecoil)

# firecoil

firecoil allows you to load images from [Cloud Storage for Firebase](https://firebase.google.com/docs/storage/)
 in your Android app
 (through a [StorageReference](https://firebase.google.com/docs/reference/android/com/google/firebase/storage/StorageReference))
 , using the image loading library [Coil](https://github.com/coil-kt/coil).

# Usage

## Basic Usage
```kotlin
val storageRef: StorageReference = ...
val imageView: ImageView = ...

imageView.load(storageRef)
```

## Custom Requests
```kotlin
val storageRef: StorageReference = ...
val imageView: ImageView = ...

imageView.load(storageRef) {
    crossfade(true)
    placeholder(R.id.placeholder)
}
```

See more usage options on the [Documentation](https://firebaseopensource.com/projects/rosariopfernandes/firecoil/docs/getting-started.md).

A [sample app](https://github.com/rosariopfernandes/firecoil/tree/master/sampleapp) is also available for trying out firecoil.

# Contributing
Anyone and everyone is welcome to contribute. Please take a moment to
review the [contributing guidelines](CONTRIBUTING.md).

# License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

# Acknowledgment
Inspired by [FirebaseUI for Storage](https://github.com/firebase/FirebaseUI-Android/tree/master/storage)
