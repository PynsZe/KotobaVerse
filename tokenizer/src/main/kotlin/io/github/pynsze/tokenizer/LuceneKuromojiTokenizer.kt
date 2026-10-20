package io.github.pynsze.tokenizer

import com.kotobaverse.shared.model.JapaneseTokenizer
import com.kotobaverse.shared.model.Token
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute
import org.codelibs.neologd.ipadic.lucene.analysis.ja.JapaneseTokenizer.Mode
import org.codelibs.neologd.ipadic.lucene.analysis.ja.tokenattributes.BaseFormAttribute
import org.codelibs.neologd.ipadic.lucene.analysis.ja.tokenattributes.PartOfSpeechAttribute
import org.codelibs.neologd.ipadic.lucene.analysis.ja.tokenattributes.ReadingAttribute
import java.io.StringReader
import org.codelibs.neologd.ipadic.lucene.analysis.ja.JapaneseTokenizer as LuceneJa

class LuceneKuromojiTokenizer : JapaneseTokenizer {

    override val version = "kuromoji-ipadic-neologd-20171113"

    // Le dico (~60 Mo) est un singleton interne à Lucene : instancier un tokenizer
    // par appel est peu coûteux, et évite tout souci de thread-safety.
    override fun tokenize(line: String): List<Token> {
        val result = mutableListOf<Token>()
        LuceneJa(null, false, Mode.NORMAL).use { tk ->
            val term = tk.addAttribute(CharTermAttribute::class.java)
            val offset = tk.addAttribute(OffsetAttribute::class.java)
            val base = tk.addAttribute(BaseFormAttribute::class.java)
            val reading = tk.addAttribute(ReadingAttribute::class.java)
            val pos = tk.addAttribute(PartOfSpeechAttribute::class.java)

            tk.setReader(StringReader(line))
            tk.reset()
            var i = 0
            while (tk.incrementToken()) {
                val surface = term.toString()
                result += Token(
                    tokenIndex = i++,
                    charStart = offset.startOffset(),
                    charEnd = offset.endOffset(),
                    surface = surface,
                    reading = reading.reading,
                    lemma = base.baseForm ?: surface,
                    pos = pos.partOfSpeech ?: "?",
                )
            }
            tk.end()
        }
        return result
    }
}