plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(ktorLibs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
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
    implementation(ktorLibs.client.apache)
    implementation(ktorLibs.client.core)
    implementation(ktorLibs.serialization.kotlinx.json)
    implementation(ktorLibs.server.auth)
    implementation(ktorLibs.server.callId)
    implementation(ktorLibs.server.callLogging)
    implementation(ktorLibs.server.config.yaml)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.cors)
    implementation(ktorLibs.server.netty)
    implementation(ktorLibs.server.requestValidation)
    implementation(ktorLibs.server.sessions)
    implementation(ktorLibs.server.statusPages)
    implementation(libs.logback.classic)
// Base de données — Exposed 1.x (DSL Kotlin idiomatique JetBrains)
    implementation("org.jetbrains.exposed:exposed-core:1.2.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:1.2.0")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:1.2.0")

    implementation("org.postgresql:postgresql:42.7.11")
    implementation("com.zaxxer:HikariCP:7.0.2")              // pool de connexions

// Migrations
    implementation("org.flywaydb:flyway-core:12.6.1")
    implementation("org.flywaydb:flyway-database-postgresql:12.6.1")

// Tokenisation japonaise
    implementation("com.atilika.kuromoji:kuromoji-ipadic:0.9.0")

// Tests — la version doit matcher la version de Ktor que tu utilises pour le serveur
    testImplementation("io.ktor:ktor-server-test-host:3.4.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")  // version héritée du plugin Kotlin
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.4")
}
