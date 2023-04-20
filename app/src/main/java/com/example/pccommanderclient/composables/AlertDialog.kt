package com.example.pccommanderclient.composables

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun Preview() {
    CustomAlertDialog(title = { Text(text = "Title")}, text = { Text(text = "Text") }, onDismiss = { /*TODO*/ }) {

    }
}

@Composable
fun CustomAlertDialog(
    title: @Composable () -> Unit,
    text: @Composable () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.wrapContentSize(),
        onDismissRequest = onDismiss,
        title = title,
        text = text,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        })
}