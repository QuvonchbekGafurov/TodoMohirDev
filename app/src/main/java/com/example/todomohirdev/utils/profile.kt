package com.example.todomohirdev.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import com.example.todomohirdev.R
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

private fun saveImageToPreferences(context: Context, bitmap: Bitmap) {
    val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
    sharedPreferences.edit().putString("profile_image", encodedImage).apply()
}

private fun getImageFromPreferences(context: Context): Bitmap? {
    val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    val encodedImage = sharedPreferences.getString("profile_image", null)
    return if (encodedImage != null) {
        val decodedBytes = Base64.decode(encodedImage, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } else {
        null
    }
}

@Composable
fun ProfileImagePicker() {
    val context = LocalContext.current
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(getImageFromPreferences(context)) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                selectedImageBitmap = bitmap
                saveImageToPreferences(context, bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    val painter: Painter = if (selectedImageBitmap != null) {
        BitmapPainter(selectedImageBitmap!!.asImageBitmap())
    } else {
        painterResource(id = R.drawable.ic_launcher_background) // Default image
    }

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.Gray)
            .clickable {
                launcher.launch("image/*")
            }
    )
}
