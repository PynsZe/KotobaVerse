package com.kotobaverse.client.core.di

import com.kotobaverse.shared.model.JapaneseTokenizer
import io.github.pynsze.tokenizer.LuceneKuromojiTokenizer
import org.koin.dsl.module

actual fun platformModule() = module {
    single<JapaneseTokenizer> { LuceneKuromojiTokenizer() }
}