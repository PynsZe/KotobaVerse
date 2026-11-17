package com.kotobaverse.client.core.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val coreModule = module {
    // à remplir
}

// ajouter els modules ici
val appModules: List<Module> = listOf(coreModule)

fun initKoin(config: KoinAppDeclaration? = null) = startKoin {
    config?.invoke(this)
    modules(appModules)
}