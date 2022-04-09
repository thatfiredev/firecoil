package io.github.rosariopfernandes.firecoil

import coil.key.Keyer
import coil.request.Options
import com.google.firebase.storage.StorageReference

class StorageReferenceKeyer : Keyer<StorageReference> {
    override fun key(data: StorageReference, options: Options): String = data.path
}