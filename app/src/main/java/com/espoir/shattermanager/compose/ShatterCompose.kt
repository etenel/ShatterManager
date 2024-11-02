package com.espoir.shattermanager.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.espoir.shattermanager.R

@Composable
fun ShatterCompose() {
    var text by remember { mutableStateOf("ShatterCompose") }
    Column(modifier = Modifier.fillMaxWidth().background(colorResource(R.color.teal_700)), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text, color = colorResource(R.color.purple_500), fontSize = 12.sp)
        Button(modifier = Modifier
            .size(200.dp,100.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.White), shape = RectangleShape, onClick = {
            text = "click"
        }) {
            Text("点击改变ssdfddg",  fontSize = 16.sp, color = Color.Cyan, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight(800))
        }
    }
}

@Preview(widthDp = 300, heightDp = 400)
@Composable
fun Pre() {
    ShatterCompose()
}