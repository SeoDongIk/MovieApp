package com.example.movieapp.ui.component.dialog

import androidx.compose.runtime.Composable
import com.example.movieapp.ui.models.dialog.DialogButton
import com.example.movieapp.ui.models.dialog.DialogContent
import com.example.movieapp.ui.models.dialog.DialogText
import com.example.movieapp.ui.models.dialog.DialogTitle

@Composable
fun DialogPopup.Default(
    title: String,
    bodyText: String,
    buttons: List<DialogButton>
) {
    BaseDialogPopup(
        dialogTitle = DialogTitle.Default(title),
        dialogContent = DialogContent.Default(
            DialogText.Default(bodyText)
        ),
        buttons = buttons
    )
}