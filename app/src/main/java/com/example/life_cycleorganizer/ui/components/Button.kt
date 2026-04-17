package com.example.life_cycleorganizer.ui.components

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.life_cycleorganizer.R
import com.example.life_cycleorganizer.ui.theme.LatoFamily
import com.example.life_cycleorganizer.ui.theme.neutral_1
import com.example.life_cycleorganizer.ui.theme.primary_1
import com.example.life_cycleorganizer.ui.theme.primary_3
import com.example.life_cycleorganizer.ui.theme.primary_5

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .height(40.dp)
            .width(72.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) primary_3 else primary_1)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = LatoFamily,
                color = primary_5,
                modifier = Modifier
                    .height(32.dp)
                    .wrapContentHeight(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
        ) }
    }
}

@Composable
fun AddTaskButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
            .background(color = primary_1, shape = RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "Add Task",
            tint = neutral_1,
            modifier = Modifier.size(24.dp)
        )
    }
}