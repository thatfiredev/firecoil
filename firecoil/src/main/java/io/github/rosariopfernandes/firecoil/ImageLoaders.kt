package io.github.rosariopfernandes.firecoil

import android.content.Context
import coil.ImageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import coil.request.ImageResult
import com.google.firebase.storage.StorageReference

suspend inline fun ImageLoader.get(
    context: Context,
    storageReference: StorageReference,
    builder: ImageRequest.Builder.() -> Unit = {}
): ImageResult {
    val getRequest = ImageRequest.Builder(context)
        .data(storageReference)
        .apply(builder)
        .build()
    return execute(getRequest)
}

inline fun ImageLoader.load(
    context: Context,
    storageReference: StorageReference,
    builder: ImageRequest.Builder.() -> Unit = {}
): Disposable {
    val loadRequest = ImageRequest.Builder(context)
        .data(storageReference)
        .apply(builder).build()
    return enqueue(loadRequest)
}
