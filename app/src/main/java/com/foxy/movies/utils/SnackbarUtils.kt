package com.foxy.movies.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(message: String, btnText: String, listener: View.OnClickListener) {
    Snackbar
        .make(this, message, Snackbar.LENGTH_INDEFINITE)
        .setAction(btnText, listener)
        .show()
}
