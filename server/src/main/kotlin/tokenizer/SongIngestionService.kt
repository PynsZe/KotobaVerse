package com.kotobaverse.catalog

import com.kotobaverse.catalog.persistence.LinesTable
import com.kotobaverse.catalog.persistence.TokensTable
import com.kotobaverse.tokenizer.JapaneseTokenizer
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class SongIngestionService(
    private val tokenizer: JapaneseTokenizer,
) {
    /**
     * Découpe rawText en Lines, tokenise chaque Line, persiste Line + Token[].
     * Précondition : la Song (songId) existe déjà en DB.
     */
    fun ingest(songId: Long, rawText: String) {
        val lines = rawText
            .split("\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        // Tokenisation CPU-bound hors transaction : pas besoin de tenir
        // la connexion DB ouverte pendant que Kuromoji tourne.
        val lineTokenPairs = lines.map { lineText -> lineText to tokenizer.tokenize(lineText) }

        transaction {
            lineTokenPairs.forEachIndexed { lineIndex, (lineText, tokens) ->
                val lineId = LinesTable.insert {
                    it[LinesTable.songId] = songId
                    it[LinesTable.lineIndex] = lineIndex
                    it[LinesTable.rawText] = lineText
                } get LinesTable.id

                if (tokens.isEmpty()) return@forEachIndexed

                TokensTable.batchInsert(tokens.withIndex().toList()) { (tokenIndex, token) ->
                    this[TokensTable.lineId] = lineId
                    this[TokensTable.tokenIndex] = tokenIndex
                    this[TokensTable.charStart] = token.charStart
                    this[TokensTable.charEnd] = token.charEnd
                    this[TokensTable.surface] = token.surface
                    this[TokensTable.reading] = token.reading
                    this[TokensTable.lemma] = token.baseForm
                    this[TokensTable.pos] = token.pos
                    this[TokensTable.tokenizerVersion] = tokenizer.version
                }
            }
        }
    }
}