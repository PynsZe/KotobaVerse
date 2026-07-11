package io.github.pynsze.tokenizer // adapte au package de ton :tokenizer

// Classes ja repackagées par codelibs (tokenizer + attributs ja-spécifiques + dico NEologd)
import org.codelibs.neologd.ipadic.lucene.analysis.ja.JapaneseTokenizer
import org.codelibs.neologd.ipadic.lucene.analysis.ja.JapaneseTokenizer.Mode
import org.codelibs.neologd.ipadic.lucene.analysis.ja.tokenattributes.BaseFormAttribute
import org.codelibs.neologd.ipadic.lucene.analysis.ja.tokenattributes.InflectionAttribute
import org.codelibs.neologd.ipadic.lucene.analysis.ja.tokenattributes.PartOfSpeechAttribute
import org.codelibs.neologd.ipadic.lucene.analysis.ja.tokenattributes.ReadingAttribute
// Attributs core → restent dans lucene-core (org.apache.lucene)
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute
import java.io.StringReader

/**
 * Spike Lucene Kuromoji NEologd (CodeLibs) — remplace KuromojiSpike.kt (API atilika).
 *
 * But : valider que le dico NEologd garde les noms propres composés INTACTS en
 * Mode.NORMAL. 東京スカイツリー doit sortir en UN token 固有名詞.
 *
 * Dépendances (Config B verrouillée) :
 *
 *   // settings.gradle.kts
 *   dependencyResolutionManagement {
 *       repositories {
 *           mavenCentral()
 *           maven("https://maven.codelibs.org/")
 *       }
 *   }
 *
 *   // build.gradle.kts du module :tokenizer
 *   implementation(libs.lucene.kuromoji.neologd) // org.codelibs:...:7.6.0-20190325
 *   implementation(libs.lucene.core)             // org.apache.lucene:lucene-core:7.6.0
 */
fun main() {
    val lines = listOf(
        "お寿司が食べたい。",
        "走れメロスは激怒した。",
        "東京スカイツリーへ行った",
        "関西国際空港", // contraste : en Mode.SEARCH ça se casserait en 関西 / 国際 / 空港
    )

    // discardPunctuation = false → garde 。 comme token 記号 (comme ton spike atilika)
    // Mode.NORMAL              → composés entiers. RAPPEL : le défaut Lucene est SEARCH.
    val tokenizer = JapaneseTokenizer(/* userDictionary = */ null, /* discardPunctuation = */ false, Mode.NORMAL)

    val termAtt = tokenizer.addAttribute(CharTermAttribute::class.java)
    val offsetAtt = tokenizer.addAttribute(OffsetAttribute::class.java)
    val baseAtt = tokenizer.addAttribute(BaseFormAttribute::class.java)
    val readingAtt = tokenizer.addAttribute(ReadingAttribute::class.java)
    val posAtt = tokenizer.addAttribute(PartOfSpeechAttribute::class.java)
    val inflAtt = tokenizer.addAttribute(InflectionAttribute::class.java)

    tokenizer.use { tk ->
        for (line in lines) {
            println("=".repeat(70))
            println("LINE: $line  (${line.length} chars)")
            println("=".repeat(70))

            tk.setReader(StringReader(line))
            tk.reset()
            var i = 0
            while (tk.incrementToken()) {
                val surface = termAtt.toString()
                val start = offsetAtt.startOffset()
                val end = offsetAtt.endOffset()

                // Lucene : baseForm null pour les mots NON fléchis (noms, particules)
                // → fallback sur la surface, pour rester comparable à ta sortie atilika.
                val lemma = baseAtt.baseForm ?: surface
                // reading null = mot hors dictionnaire (le vrai "inconnu" du tokenizer)
                val reading = readingAtt.reading
                // POS IPADIC hiérarchique séparé par "-" ; 1er segment = catégorie de tête.
                val posFull = posAtt.partOfSpeech
                val posHead = posFull?.substringBefore('-') ?: "?"
                val infl = inflAtt.inflectionForm // null hors flexion

                // Heuristique grossière : le vrai "known" viendra de la résolution JMdict.
                val known = reading != null

                println(
                    "[$i] surface='$surface'  read=${reading ?: "—"}  lemma=$lemma  " +
                            "pos=$posHead  posFull=$posFull  span=[$start,$end)  known=$known" +
                            (if (infl != null) "  infl=$infl" else ""),
                )
                i++
            }
            tk.end()
            tk.close()
        }
    }
}