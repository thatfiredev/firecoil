package io.github.rosariopfernandes.firecoil

import android.content.Context
import android.graphics.drawable.Drawable
import coil.ImageLoader
import coil.api.getAny
import coil.api.loadAny
import coil.request.GetRequestBuilder
import coil.request.LoadRequestBuilder
import coil.request.RequestDisposable
import com.google.firebase.storage.StorageReference

suspend inline fun ImageLoader.get(
    storageReference: StorageReference,
    builder: GetRequestBuilder.() -> Unit = {}
): Drawable = getAny(storageReference, builder)

inline fun ImageLoader.load(
    context: Context,
    storageReference: StorageReference,
    builder: LoadRequestBuilder.() -> Unit = {}
): RequestDisposable = loadAny(context, storageReference, builder)
