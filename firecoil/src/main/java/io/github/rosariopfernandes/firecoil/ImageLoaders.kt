package io.github.rosariopfernandes.firecoil

import android.content.Context
import coil.ImageLoader
import coil.request.GetRequest
import coil.request.GetRequestBuilder
import coil.request.LoadRequest
import coil.request.LoadRequestBuilder
import coil.request.RequestDisposable
import coil.request.RequestResult
import com.google.firebase.storage.StorageReference

suspend inline fun ImageLoader.get(
    context: Context,
    storageReference: StorageReference,
    builder: GetRequestBuilder.() -> Unit = {}
): RequestResult {
    val getRequest = GetRequest.Builder(context)
        .data(storageReference)
        .apply(builder)
        .build()
    return execute(getRequest)
}

inline fun ImageLoader.load(
    context: Context,
    storageReference: StorageReference,
    builder: LoadRequestBuilder.() -> Unit = {}
): RequestDisposable {
    val loadRequest = LoadRequest.Builder(context)
        .data(storageReference)
        .apply(builder).build()
    return execute(loadRequest)
}
