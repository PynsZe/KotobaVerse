plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(ktorLibs.plugins.ktor)
}

group = "io.github.pynsze"
version = "1.0.0-SNAPSHOT"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

tasks.shadowJar {
    mergeServiceFiles {
        include("META-INF/services/**")
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    // Ktor — server
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.netty)
    implementation(ktorLibs.server.auth)
    implementation(ktorLibs.server.callId)
    implementation(ktorLibs.server.callLogging)
    implementation(ktorLibs.server.config.yaml)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.cors)
    implementation(ktorLibs.server.requestValidation)
    implementation(ktorLibs.server.sessions)
    implementation(ktorLibs.server.statusPages)

    // Ktor — client (appels externes : MusicBrainz, LLM, …)
    implementation(ktorLibs.client.core)
    implementation(ktorLibs.client.apache)
    implementation(ktorLibs.serialization.kotlinx.json)

    // Logging
    implementation(libs.logback.classic)

    // Persistence — Exposed 1.x + PostgreSQL + HikariCP
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.kotlin.datetime)
    implementation(libs.postgresql)
    implementation(libs.hikaricp)

    // Migrations
    implementation(libs.flyway.core)
    implementation(libs.flyway.database.postgresql)

    // Tokenisation japonaise (v1.0-alpha serveur, migrera côté client en v1.0-beta)
    implementation(libs.kuromoji.ipadic)

    // Hasher
    implementation(libs.bcrypt)

    // Shared
    implementation(projects.shared)

    // Tests
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit5)
    testRuntimeOnly(libs.junit.jupiter.engine)
}