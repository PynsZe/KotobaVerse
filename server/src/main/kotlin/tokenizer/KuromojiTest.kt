package com.kotobaverse.tokenizer.spike

import com.atilika.kuromoji.ipadic.Token
import com.atilika.kuromoji.ipadic.Tokenizer

/**
 * Spike de vérification Kuromoji — À SUPPRIMER une fois validé.
 *
 * But : confirmer que kuromoji-ipadic tokenise correctement et expose bien tous
 * les champs dont la TokensTable a besoin (surface, reading, lemma, pos,
 * char_start, char_end) AVANT de construire l'abstraction JapaneseTokenizer.
 *
 * Lancer : clic sur la gouttière du `main` dans IntelliJ (zéro config).
 */

// IPADIC encode l'absence de valeur par "*" (pas null/""). Les colonnes reading et
// lemma sont nullable côté DB → on convertit "*" en null à la persistance.
private const val IPADIC_NULL = "*"

private fun String?.orNullFeature(): String? =
    this?.takeUnless { it == IPADIC_NULL || it.isBlank() }

fun main() {
    val tokenizer = Tokenizer()

    val samples = listOf(
        "お寿司が食べたい。",        // exemple canonique du README Kuromoji
        "走れメロスは激怒した。",    // une ligne facile, genre Aozora
        "東京スカイツリーへ行った",  // nom propre : éclaté en IPADIC de base, gardé entier en NEologd
    )

    for (line in samples) {
        println("=".repeat(70))
        println("LINE: $line  (${line.length} chars)")
        println("=".repeat(70))

        val tokens: List<Token> = tokenizer.tokenize(line)

        tokens.forEachIndexed { i, t ->
            val start = t.position                 // offset de DÉBUT dans la ligne
            val end = start + t.surface.length     // Kuromoji ne donne que le début → on dérive la fin

            val reading = t.reading.orNullFeature() ?: "—"
            val lemma = t.baseForm.orNullFeature() ?: "—"

            println(
                "[$i] surface='${t.surface}'  read=$reading  lemma=$lemma  " +
                        "pos=${t.partOfSpeechLevel1}  span=[$start,$end)  known=${t.isKnown}"
            )
        }
        println()
    }
}