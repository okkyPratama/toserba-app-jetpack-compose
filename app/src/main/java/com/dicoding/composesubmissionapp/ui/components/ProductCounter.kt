package com.dicoding.composesubmissionapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.composesubmissionapp.R
import com.dicoding.composesubmissionapp.ui.theme.ComposeSubmissionAppTheme

@Composable
fun ProductCounter(
    orderId: Long,
    orderCount: Int,
    onProductIncreased: (Long) -> Unit,
    onProductDecreased: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .size(width = 110.dp, height = 40.dp)
            .padding(4.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(size = 5.dp),
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            color = Color.Transparent,
            contentColor = MaterialTheme.colors.primary,
            modifier = Modifier.size(30.dp)
        ) {
            Text(
                text = stringResource(R.string.minus_symbol),
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable {
                        onProductDecreased(orderId)
                    }

            )

        }
        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = orderCount.toString(),
            modifier = Modifier
                .testTag("count"),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(16.dp))


        Surface(
            shape = RoundedCornerShape(size = 5.dp),
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            color = Color.Transparent,
            contentColor = MaterialTheme.colors.primary,
            modifier = Modifier.size(30.dp)
        ) {
            Spacer(modifier = Modifier.width(16.dp))

            Text(
               text = stringResource(R.string.plus_symbol ),
               fontSize = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable {
                        onProductIncreased(orderId)
                    }
            )

        }
    }

}

@Preview
@Composable
fun ProductCounterPreview() {
    ComposeSubmissionAppTheme {
        ProductCounter(
            orderId = 1,
            orderCount = 0,
            onProductIncreased = { },
            onProductDecreased = { }
        )
    }
}

