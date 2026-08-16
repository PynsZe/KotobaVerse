package com.kotobaverse.client

expect interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
