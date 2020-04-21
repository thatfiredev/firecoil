# Advanced Usage

## Custom Targets

If you need to load your image in something other than an ImageView, like the background
 of a LinearLayout for example, you can do it by using the `FireCoil` singleton:

```kotlin
val linearLayout: LinearLayout = ...

FireCoil.load(this, storageRef) {
    target { drawable ->
        linearLayout.background = drawable
    }
}
```

## Using Transformations

As mentioned on the [Coil Docs](https://coil-kt.github.io/coil/transformations/):

> Transformations allow you to modify the pixel data of an image before the `Drawable` is returned from the request.

You can use one of the 4 transformations packed with Coil, like
 [circle crop](https://coil-kt.github.io/coil/api/coil-base/coil.transform/-circle-crop-transformation/):

```kotlin
imageView.load(storageRef) {
    transformations(CircleCropTransformation())
}
```

## Loading images in a different thread

Coil also provides `ImageLoader.get()`, which is a `suspend` function, so you can call it from another `suspend` function
 or a Coroutine Builder like `launch`:

```kotlin
launch {
    val result = FireCoil.get(storageRef) {
        size(width, height)
    }
    when (result) {
        is SuccessResult -> {
            val drawable = result.drawable
            // Use the drawable
        }
        is ErrorResult -> {
            val throwable = result.throwable
            // Handle the error
        }
    }
}
```

See more usage options under [Extended Usage](https://firebaseopensource.com/projects/rosariopfernandes/firecoil/docs/extended_usage.md/).
