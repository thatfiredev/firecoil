package io.github.rosariopfernandes.firecoil

import android.widget.ImageView
import coil.request.Disposable
import coil.request.ImageRequest
import com.google.firebase.storage.StorageReference

inline fun ImageView.load(
    data: StorageReference,
    builder: ImageRequest.Builder.() -> Unit = {}
): Disposable {
    val loadRequest = ImageRequest.Builder(context).data(data).target(this@load)
        .apply(builder).build()
    return FireCoil.loader(context).enqueue(loadRequest)
}
