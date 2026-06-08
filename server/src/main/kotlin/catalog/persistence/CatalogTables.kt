package com.kotobaverse.catalog.persistence

import io.github.pynsze.auth.persistance.UsersTable
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.java.javaUUID
import org.jetbrains.exposed.v1.datetime.date

/**
 * Objets Exposed mappant les tables du catalogue.
 *
 * Le schéma physique est détenu par Flyway (migrations SQL) ; ces objets ne servent
 * qu'au DSL (insert / select). On ne redéclare donc PAS :
 *   - les index et contraintes UNIQUE / CHECK (présents dans la migration),
 *   - les colonnes gérées par trigger en base (created_at / updated_at).
 * Exposed ne crée jamais ces tables (pas de SchemaUtils.create).
 */

object OriginsTable : Table("origins") {
    val id = long("id").autoIncrement()
    val type = text("type")
    val externalRef = text("external_ref").nullable()

    override val primaryKey = PrimaryKey(id)
}

object SongsTable : Table("songs") {
    val id = long("id").autoIncrement()
    val title = text("title")
    val mbidRecording = javaUUID("mbid_recording").nullable()
    val mbidWork = javaUUID("mbid_work").nullable()
    val language = text("language").default("ja")
    val durationMs = integer("duration_ms").nullable()
    val releaseDate = date("release_date").nullable()
    val originId = reference("origin_id", OriginsTable.id)

    val createdBy = reference("created_by", UsersTable.id)

    override val primaryKey = PrimaryKey(id)
}

object LinesTable : Table("lines") {
    val id = long("id").autoIncrement()
    val songId = reference("song_id", SongsTable.id, onDelete = ReferenceOption.CASCADE)
    val lineIndex = integer("line_index")
    val rawText = text("raw_text")
    val translationEn = text("translation_en").nullable()
    val translationFr = text("translation_fr").nullable()

    override val primaryKey = PrimaryKey(id)
}

object TokensTable : Table("tokens") {
    val id = long("id").autoIncrement()
    val lineId = reference("line_id", LinesTable.id, onDelete = ReferenceOption.CASCADE)
    val tokenIndex = integer("token_index")
    val charStart = integer("char_start")
    val charEnd = integer("char_end")
    val surface = text("surface")
    val reading = text("reading").nullable()
    val lemma = text("lemma").nullable()
    val pos = text("pos")
    val tokenizerVersion = text("tokenizer_version")

    // Futur FK vers entries(id) (migration JMdict) — null pendant toute cette phase.
    val inputId = long("input_id").nullable()

    override val primaryKey = PrimaryKey(id)
}