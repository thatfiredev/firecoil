# Extended Usage

Coil allows you to [extend its functionality](https://coil-kt.github.io/coil/image_pipeline/) so that
 you can adapt it to your own needs.

firecoil can also be extended with custom components such as [Mappers](https://coil-kt.github.io/coil/api/coil-base/coil.map/-mapper)
 and/or [Decoders](https://coil-kt.github.io/coil/api/coil-base/coil.decode/-decoder).

You simply need to add these components to the ImageLoader through its [componentRegistry](https://coil-kt.github.io/coil/api/coil-base/coil/-component-registry):

```kotlin
val imageLoader = ImageLoader(context) {
    componentRegistry {
        // This is the core of firecoil. Don't forget to add it
        add(StorageReferenceFetcher())

        // Add other Mappers and/or Decoders:
        add(ProductMapper())
        add(GifDecoder())
    }
}
```

## Creating Custom Mappers

As mentioned on the [Coil Docs](https://coil-kt.github.io/coil/image_pipeline/#mappers),
 Mappers allow you to add support for custom data types.

Suppose you're fetching this model from your server:

```kotlin
data class Product(
    val id: Long,
    val title: String,
    val product_type: String,
    // Suppose this is the path to the image in Cloud Storage
    val imageRef: String
)
```

You could write a custom mapper:

```kotlin
class ProductMapper : Mapper<Product, StorageReference> {
    override fun map(data: Product): StorageReference {
        return Firebase.storage.reference.child(data.imageRef)
    }
}
```

And display it on an ImageView using:

```kotlin
val product: Product = ...

val imageLoader = ImageLoader(context) {
    componentRegistry {
        // This is the core of firecoil. Don't forget to add it
        add(StorageReferenceFetcher())

        // Add the custom Mapper:
        add(ProductMapper())
    }
}

imageView.loadAny(product, imageLoader)
```

## Using Decoders

Unlike other image loading libraries, Coil doesn't support some formats by default, like GIF and SVG,
 so you need to use your own decoders in order to do that.

For example, to add GIF support, add the Gif Decoder dependency to your gradle file:

```gradle
implementation "io.coil-kt:coil-gif:0.9.5"
```

and add it to your [`ImageLoader`](https://coil-kt.github.io/coil/image_loaders/):

```kotlin
val imageLoader = ImageLoader(context) {
    componentRegistry {
        // This is the core of firecoil. Don't forget to add it
        add(StorageReferenceFetcher())

        // Add the Gif Decoder:
        if (SDK_INT >= P) {
            add(ImageDecoderDecoder())
        } else {
            add(GifDecoder())
        }
    }
}
```
