package com.example.todomohirdev.utils

import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.mutableStateOf
import com.example.todomohirdev.R

@Composable
fun ProfileImagePicker() {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    val painter: Painter = if (selectedImageUri != null) {
        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, selectedImageUri)
        BitmapPainter(bitmap.asImageBitmap())
    } else {
        painterResource(id = R.drawable.ic_launcher_background) // Default profile image
    }

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(Color.Gray) // Background color
            .clickable {
                launcher.launch("image/*")
            }
    )
}
