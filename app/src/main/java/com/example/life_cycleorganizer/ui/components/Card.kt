package com.example.life_cycleorganizer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import com.example.life_cycleorganizer.R
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.life_cycleorganizer.ui.theme.LatoFamily
import com.example.life_cycleorganizer.ui.theme.neutral_1
import com.example.life_cycleorganizer.ui.theme.neutral_4
import com.example.life_cycleorganizer.ui.theme.primary_1
import com.example.life_cycleorganizer.ui.theme.primary_2
import com.example.life_cycleorganizer.ui.theme.primary_3
import com.example.life_cycleorganizer.ui.theme.primary_5
import com.example.life_cycleorganizer.ui.theme.status_1
import com.example.life_cycleorganizer.ui.theme.status_2

@Composable
fun StatCard1_3(current: String, total: String, title: String, progress: Float) {
    Card(
        modifier = Modifier
            .width(88.dp)
            .height(88.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = primary_1.copy(alpha = 0.67f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = LatoFamily,
                        color = neutral_1,
                    )
                    ) {
                        append(text = current)
                    }

                    withStyle(style = SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = LatoFamily,
                        color = primary_5,
                    )) {
                        append(text = total)
                    }
                }
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = LatoFamily,
                color = primary_5,
                modifier = Modifier
                    .height(32.dp)
                    .wrapContentHeight(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(4.dp))

            ProgressBar(progress)
        }
    }
}

@Composable
fun StatCard2(cardContent: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .height(88.dp)
            .width(88.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = primary_1.copy(alpha = 0.67f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            content = cardContent
        )
    }
}

@Composable
fun ProgressBar(currentProgress: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(RoundedCornerShape(100))
            .background(neutral_1),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(currentProgress)
                .fillMaxHeight()
                .clip(RoundedCornerShape(100))
                .background(primary_5)
        )
    }
}

@Composable
fun TaskUnfinishedCard(
    icon: String,
    title: String,
    goal: String,
    loopDisplay: String,
    streak: Int,
    progress: Float,
    onCheckChanged: () -> Unit
) {

    Card(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = primary_1)

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,

        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(primary_2, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(icon, fontSize = 24.sp)
            }


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(212.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column() {
                        Text(
                            text = title,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            fontFamily = LatoFamily,
                            color = primary_5
                        )
                        Text(
                            text = "$goal • $loopDisplay",
                            fontSize = 12.sp,
                            fontFamily = LatoFamily,
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Italic,
                            color = neutral_4
                        )
                    }

                    Row(
                        modifier = Modifier
                            .background(status_1, CircleShape)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🔥", fontSize = 12.sp)
                        Text(
                            text = "$streak days",
                            color = neutral_1,
                            fontFamily = LatoFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }

                ProgressBar(currentProgress = progress)

            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        onCheckChanged()
                    }
                    .background(neutral_1, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_uncheck),
                    contentDescription = "Status Uncheck",
                    modifier = Modifier.size(24.dp),

                    tint = primary_5
                )
            }
        }
    }
}

@Composable
fun TaskFinishedCard(
    icon: String,
    title: String,
    goal: String,
    loopDisplay: String,
    streak: Int,
    progress: Float,
    onCheckChanged: () -> Unit
) {

    Card(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = primary_2)

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,

            ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(primary_1, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(icon, fontSize = 24.sp)
            }


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(212.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column() {
                        Text(
                            text = title,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            fontFamily = LatoFamily,
                            color = primary_5,
                            style = TextStyle(textDecoration = TextDecoration.LineThrough)
                        )
                        Text(
                            text = "$goal • $loopDisplay",
                            fontSize = 12.sp,
                            fontFamily = LatoFamily,
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Italic,
                            color = neutral_4
                        )
                    }

                    Row(
                        modifier = Modifier
                            .background(status_1, CircleShape)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🔥", fontSize = 12.sp)
                        Text(
                            text = "$streak days",
                            color = neutral_1,
                            fontFamily = LatoFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }

                ProgressBar(currentProgress = progress)

            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        onCheckChanged()
                    }
                    .background(status_2, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "Status Check",
                    modifier = Modifier.size(24.dp),

                    tint = primary_5
                )
            }
        }
    }
}