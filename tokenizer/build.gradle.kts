plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

application {
    mainClass.set("io.github.pynsze.tokenizer.KuromojiSpikeKt")
}

dependencies {
    api(project(":shared"))                // interface + Token
    implementation(libs.lucene.kuromoji.neologd) // 7.6.0-20190418 (le 20190325 est purgé)
    implementation(libs.lucene.core)             // 7.6.0
}