package com.kotobaverse.client

import android.os.Build

actual interface Platform {
    actual val name: String
}

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()