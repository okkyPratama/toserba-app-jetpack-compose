package com.dicoding.composesubmissionapp.ui.screen.about

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.composesubmissionapp.R

@Preview
@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,

            )  {
            Image(
                painter = painterResource(R.drawable.foto_profil),
                contentDescription = "about_page",
                modifier = Modifier
                    .size(200.dp)
                    .border(
                        BorderStroke(2.dp, Color.Black)
                    )
            )

            Text(
                text = stringResource(R.string.about_name),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(R.string.about_email),
                textAlign = TextAlign.Center,

            )

        }
    }
}