package org.example.project.utils

import androidx.compose.ui.graphics.ImageBitmap



expect object ImageUtils {
    fun decodeBase64ToImageBitmap(base64: String): ImageBitmap?
}
