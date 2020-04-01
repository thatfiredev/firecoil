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

        implementation 'com.github.rosariopfernandes:firecoil:0.0.2'
    }
```

## Java 8

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

## Cancelling Requests

The ImageView.load() function returns a [RequestDisposable](https://coil-kt.github.io/coil/api/coil-base/coil.request/-request-disposable)
 which can be used to dispose the request:

```kotlin
val request = imageView.load(storageRef)

// Cancel the request
request.dispose()
```

See more usage options under [Advanced Usage](https://firebaseopensource.com/projects/rosariopfernandes/firecoil/docs/advanced_usage.md/).
