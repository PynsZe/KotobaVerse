package com.kotobaverse.tokenizer

data class TokenizedWord(
    val surface: String,
    val reading: String?,
    val baseForm: String,
    val pos: String,
    val charStart: Int,
    val charEnd: Int,
)

interface JapaneseTokenizer {
    /** Identifiant stable persisté en DB (tokenizer_version), pour re-tokenisation déterministe */
    val version: String

    fun tokenize(text: String): List<TokenizedWord>
}