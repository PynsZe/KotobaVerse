package com.kotobaverse.shared.model

data class Token(
    val tokenIndex: Int,
    val charStart: Int,
    val charEnd: Int,
    val surface: String,
    val reading: String?,   // null = hors dico
    val lemma: String,
    val pos: String,        // POS IPADIC complet, ex: "名詞-固有名詞-地域-一般"
)

interface JapaneseTokenizer {
    val version: String
    fun tokenize(line: String): List<Token>
}