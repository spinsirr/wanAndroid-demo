package com.example.appproject.ui.util

import android.content.Context
import android.widget.Toast
import com.example.appproject.MainApplication

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}