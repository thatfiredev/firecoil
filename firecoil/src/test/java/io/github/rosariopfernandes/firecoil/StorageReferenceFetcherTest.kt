package io.github.rosariopfernandes.firecoil

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import coil.bitmappool.BitmapPool
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.Call
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@UseExperimental(ExperimentalCoroutinesApi::class)
class StorageReferenceFetcherTest {
    private val context: Context = ApplicationProvider.getApplicationContext()

    private lateinit var mainDispatcher: TestCoroutineDispatcher
    private lateinit var callFactory: Call.Factory
    private lateinit var pool: BitmapPool

    @Before
    fun before() {
        mainDispatcher = TestCoroutineDispatcher().apply { Dispatchers.setMain(this) }
        callFactory = OkHttpClient()
        pool = BitmapPool(0)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `basic firebase storage fetch`() {
        val fetcher = StorageReferenceFetcher()
        val firebaseApp = FirebaseApp.initializeApp(
            context,
            FirebaseOptions.Builder()
                .setApiKey("apiKey")
                .setApplicationId("appId")
                .setStorageBucket("my-bucket.appspot.com")
                .build()
        )
        val storageReference = FirebaseStorage.getInstance(firebaseApp)
            .reference.child("test.png")
        assertTrue(fetcher.handles(storageReference))
        assertEquals(storageReference.path, fetcher.key(storageReference))

        // TODO: Find a way to mock the fetch
        //
        // val result = runBlocking {
        //     fetcher.fetch(pool, storageReference, PixelSize(100, 100), createOptions())
        // }
        //
        // assertTrue(result is SourceResult)
    }

//    private fun createOptions(
//        config: Bitmap.Config = Bitmap.Config.ARGB_8888,
//        colorSpace: ColorSpace? = null,
//        scale: Scale = Scale.FILL,
//        allowInexactSize: Boolean = false,
//        allowRgb565: Boolean = false,
//        headers: Headers = Headers.Builder().build(),
//        parameters: Parameters = Parameters.Builder().build(),
//        networkCachePolicy: CachePolicy = CachePolicy.ENABLED,
//        diskCachePolicy: CachePolicy = CachePolicy.ENABLED
//    ): Options {
//        return Options(
//            config,
//            colorSpace,
//            scale,
//            allowInexactSize,
//            allowRgb565,
//            headers,
//            parameters,
//            networkCachePolicy,
//            diskCachePolicy
//        )
//    }
}
