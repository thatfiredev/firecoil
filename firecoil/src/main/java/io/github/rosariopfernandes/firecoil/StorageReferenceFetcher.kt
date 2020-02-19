package io.github.rosariopfernandes.firecoil

import coil.bitmappool.BitmapPool
import coil.decode.DataSource
import coil.decode.Options
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.fetch.SourceResult
import coil.size.Size
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import okio.buffer
import okio.source

class StorageReferenceFetcher : Fetcher<StorageReference> {

    override suspend fun fetch(
        pool: BitmapPool,
        data: StorageReference,
        size: Size,
        options: Options
    ): FetchResult {
        val taskSnapshot = data.stream.await()

        return SourceResult(
            dataSource = DataSource.NETWORK,
            source = taskSnapshot.stream.source().buffer(),
            mimeType = null
        )
    }

    override fun key(data: StorageReference) = data.path

    override fun handles(data: StorageReference) = true
}
