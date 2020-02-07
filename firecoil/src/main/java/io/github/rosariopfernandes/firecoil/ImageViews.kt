package io.github.rosariopfernandes.firecoil

import android.widget.ImageView
import coil.ImageLoader
import coil.api.loadAny
import coil.request.LoadRequestBuilder
import coil.request.RequestDisposable
import com.google.firebase.storage.StorageReference

inline fun ImageView.load(
    data: StorageReference,
    builder: LoadRequestBuilder.() -> Unit = {}
): RequestDisposable {
    val imageLoader = ImageLoader(context) {
        componentRegistry {
            add(StorageReferenceFetcher())
        }
    }
    return imageLoader.loadAny(context, data) {
        target(this@load)
        builder()
    }
}
