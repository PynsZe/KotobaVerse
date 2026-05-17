package io.github.pynsze

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.v1.jdbc.Database

fun Application.configureDatabase() {
    val url = readConfig("postgres.url", "DB_URL", "jdbc:postgresql://localhost:5432/kotobaverse")
    val user = readConfig("postgres.user", "DB_USER", "kotobaverse")
    val password = readConfig("postgres.password", "DB_PASSWORD", "kotobaverse")

    val dataSource = HikariDataSource(HikariConfig().apply {
        jdbcUrl = url
        driverClassName = "org.postgresql.Driver"
        username = user
        this.password = password
        maximumPoolSize = 10
    })

    Flyway.configure()
        .dataSource(dataSource)
        .locations("classpath:db/migration")
        .baselineOnMigrate(false)
        .validateMigrationNaming(true)
        .load()
        .migrate()

    Database.connect(datasource = dataSource)
}

private fun Application.readConfig(yamlKey: String, envVar: String, default: String): String =
    environment.config.propertyOrNull(yamlKey)?.getString()
        ?: System.getenv(envVar)
        ?: default
