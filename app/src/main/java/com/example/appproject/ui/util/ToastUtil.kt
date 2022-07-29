package com.example.appproject.ui.util

import android.widget.Toast
import com.example.appproject.MainApplication

fun String.showToast() {
    Toast.makeText(MainApplication.context, this, Toast.LENGTH_SHORT).show()
}