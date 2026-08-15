package com.kotobaverse.tokenizer.lucene

import com.kotobaverse.tokenizer.JapaneseTokenizer
import com.kotobaverse.tokenizer.TokenizedWord
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute
import org.codelibs.neologd.ipadic.lucene.analysis.ja.JapaneseTokenizer.Mode
import org.codelibs.neologd.ipadic.lucene.analysis.ja.tokenattributes.BaseFormAttribute
import org.codelibs.neologd.ipadic.lucene.analysis.ja.tokenattributes.PartOfSpeechAttribute
import org.codelibs.neologd.ipadic.lucene.analysis.ja.tokenattributes.ReadingAttribute
import java.io.StringReader
import org.codelibs.neologd.ipadic.lucene.analysis.ja.JapaneseTokenizer as LuceneTokenizer

class LuceneKuromojiTokenizer : JapaneseTokenizer {

    override val version = "kuromoji-ipadic-neologd-20171113"

    override fun tokenize(text: String): List<TokenizedWord> {
        val tokenizer = LuceneTokenizer(null, null, false, Mode.NORMAL)
        tokenizer.setReader(StringReader(text))

        val termAttr = tokenizer.addAttribute(CharTermAttribute::class.java)
        val offsetAttr = tokenizer.addAttribute(OffsetAttribute::class.java)
        val readingAttr = tokenizer.addAttribute(ReadingAttribute::class.java)
        val baseFormAttr = tokenizer.addAttribute(BaseFormAttribute::class.java)
        val posAttr = tokenizer.addAttribute(PartOfSpeechAttribute::class.java)

        val results = mutableListOf<TokenizedWord>()

        tokenizer.reset()
        try {
            while (tokenizer.incrementToken()) {
                val surface = termAttr.toString()
                results += TokenizedWord(
                    surface = surface,
                    reading = readingAttr.reading,
                    baseForm = baseFormAttr.baseForm ?: surface,
                    pos = posAttr.partOfSpeech,
                    charStart = offsetAttr.startOffset(),
                    charEnd = offsetAttr.endOffset(),
                )
            }
            tokenizer.end()
        } finally {
            tokenizer.close()
        }

        return results
    }
}