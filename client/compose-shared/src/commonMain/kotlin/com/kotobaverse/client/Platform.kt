package com.kotobaverse.client

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform