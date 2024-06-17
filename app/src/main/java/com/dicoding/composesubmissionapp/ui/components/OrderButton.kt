package com.dicoding.composesubmissionapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.composesubmissionapp.ui.theme.ComposeSubmissionAppTheme

@Composable
fun OrderButton(
    text:String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,

    onClick: () -> Unit,
) {
  Button(
      onClick = onClick,
      enabled = enabled,
      modifier = modifier
          .fillMaxWidth()
          .height(52.dp)
          .semantics(mergeDescendants = true) {
              contentDescription = "Order Button"
          }
          .clip(RoundedCornerShape(30.dp))
  ) {
      Text(
          text = text,
          modifier = Modifier.align(Alignment.CenterVertically)
      )
  }
}

@Composable
@Preview(showBackground = true)
fun OrderButtonPreview() {
    ComposeSubmissionAppTheme {
        OrderButton(
            text = "Pesan",
            onClick = {}
        )
    }
}