package com.kotobaverse.client

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotobaverse.client.theme.KotobaVerseTheme
import com.kotobaverse.client.theme.YoruPalette

@Composable
fun App() {
    KotobaVerseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                // Wordmark
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        "ことば",
                        color = YoruPalette.Accent,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("KotobaVerse", color = YoruPalette.TextTertiary, fontSize = 13.sp)
                }

                Text(
                    text = "Direction B · Yoru — theme check",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineMedium,
                )

                // Carte catalogue type
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(YoruPalette.Surface, RoundedCornerShape(16.dp))
                        .border(1.5.dp, YoruPalette.Accent, RoundedCornerShape(16.dp))
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(13.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color(0xFF3A4A6E), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("故", color = Color(0xFFCFE0FF), fontSize = 22.sp)
                    }
                    Column(Modifier.weight(1f)) {
                        Text(
                            "故郷",
                            color = YoruPalette.TextPrimaryHigh,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            "Furusato · Traditional · 1914",
                            color = YoruPalette.TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(
                                YoruPalette.PublicDomainGreenBg,
                                RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 7.dp, vertical = 4.dp),
                    ) {
                        Text(
                            "PUBLIC DOMAIN",
                            color = YoruPalette.PublicDomainGreen,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Tokens avec furigana + soulignement POS
                Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    TokenChip(kanji = "兎", reading = "うさぎ", posColor = YoruPalette.PosNoun)
                    TokenChip(kanji = "追いし", reading = "おいし", posColor = YoruPalette.PosVerb)
                    TokenChip(kanji = "かの", reading = "かの", posColor = YoruPalette.PosPreNoun)
                }

                // Légende POS
                Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    PosDot("Noun", YoruPalette.PosNoun)
                    PosDot("Verb", YoruPalette.PosVerb)
                    PosDot("Pre-noun", YoruPalette.PosPreNoun)
                    PosDot("Particle", YoruPalette.PosParticle)
                }

                Spacer(Modifier.weight(1f))

                Button(
                    onClick = { /* no-op — spike de validation uniquement */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = YoruPalette.Accent,
                        contentColor = YoruPalette.OnAccent,
                    ),
                    shape = RoundedCornerShape(13.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                ) {
                    Text("Save to dictionary", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun TokenChip(kanji: String, reading: String, posColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(reading, color = YoruPalette.Accent, fontSize = 10.sp)
        Text(
            text = kanji,
            color = YoruPalette.TextPrimaryHigh,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 2.dp),
        )
        Box(
            Modifier
                .height(2.dp)
                .width(28.dp)
                .background(posColor),
        )
    }
}

@Composable
private fun PosDot(label: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            Modifier
                .size(8.dp)
                .background(color, RoundedCornerShape(50)),
        )
        Text(label, color = YoruPalette.TextSecondary, fontSize = 11.sp)
    }
}