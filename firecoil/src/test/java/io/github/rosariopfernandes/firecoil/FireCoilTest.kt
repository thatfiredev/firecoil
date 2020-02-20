package io.github.rosariopfernandes.firecoil

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FireCoilTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun `FireCoil#loader always returns an ImageLoader`() {
        val imageLoader = FireCoil.loader(context)

        Assert.assertNotNull(imageLoader)
    }

}