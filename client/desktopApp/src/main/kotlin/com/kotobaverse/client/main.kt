package com.kotobaverse.client

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kotobaverse.client.core.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "KotobaVerse",
    ) {
        App()
    }
}