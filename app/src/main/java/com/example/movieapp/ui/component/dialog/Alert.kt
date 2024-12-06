package com.example.movieapp.ui.component.dialog

import androidx.compose.runtime.Composable
import com.example.movieapp.ui.models.dialog.DialogButton
import com.example.movieapp.ui.models.dialog.DialogContent
import com.example.movieapp.ui.models.dialog.DialogText
import com.example.movieapp.ui.models.dialog.DialogTitle

object DialogPopup

@Composable
fun DialogPopup.Alert(
    title: String,
    bodyText: String,
    buttons: List<DialogButton>
) {
    BaseDialogPopup(
        dialogTitle = DialogTitle.Header(title),
        dialogContent = DialogContent.Large(
            DialogText.Default(
                bodyText
            )
        ),
        buttons = buttons
    )
}