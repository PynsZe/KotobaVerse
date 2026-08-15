package com.kotobaverse.client

actual interface Platform {
    actual val name: String
}

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()