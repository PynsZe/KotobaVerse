package com.kotobaverse.client

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource

import com.kotobaverse.client.shared.resources.Res
import com.kotobaverse.client.shared.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            ) {
            Scaffold(

            ){
                Row {
                    Button(onClick = {  }) {
                        Text("Menu")
                    }
                    Button(onClick = {  }) {
                        Text("Catalogue")
                    }
                    Button(onClick = {  }) {
                        Text("Dico")
                    }
                }
            }
        }

    }
}