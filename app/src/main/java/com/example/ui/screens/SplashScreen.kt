package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.KhidmatiGold
import com.example.ui.theme.KhidmatiPrimary
import com.example.ui.theme.KhidmatiPrimaryDark
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    currentLanguage: String = "ar",
    onSplashFinished: () -> Unit
) {
    val isArabic = currentLanguage == "ar"

    // 10-second timer states
    var secondsRemaining by remember { mutableIntStateOf(10) }
    var rawProgress by remember { mutableFloatStateOf(0.0f) }

    val animatedProgress by animateFloatAsState(
        targetValue = rawProgress,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
        label = "splashProgress"
    )

    // Dynamic Algerian loading messages over 10 seconds
    val loadingMessage = when (10 - secondsRemaining) {
        0, 1 -> if (isArabic) "جاري تهيئة منصة مهنتي..." else "Initializing Mihnati platform..."
        2, 3 -> if (isArabic) "تحميل شبكة الحرفيين والمهن المعتمدة..." else "Loading verified artisan network..."
        4, 5 -> if (isArabic) "مزامنة بيانات الولايات الـ 69 والبلديات..." else "Syncing 69 Algerian Wilayas..."
        6, 7 -> if (isArabic) "فحص الضمان المهني والأمان الرقمي..." else "Checking professional verification..."
        8, 9 -> if (isArabic) "الاستعداد لتسجيل الدخول..." else "Preparing login interface..."
        else -> if (isArabic) "أهلاً بك في مهنتي!" else "Welcome to Mihnati!"
    }

    LaunchedEffect(Unit) {
        val totalMs = 10_000L
        val intervalMs = 100L
        val totalSteps = (totalMs / intervalMs).toInt()

        for (step in 1..totalSteps) {
            delay(intervalMs)
            val currentMs = step * intervalMs
            rawProgress = currentMs.toFloat() / totalMs.toFloat()
            secondsRemaining = ((totalMs - currentMs + 999) / 1000).toInt().coerceAtLeast(0)
        }
        delay(200)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF032215))
            .testTag("splash_screen")
    ) {
        // High-fidelity generated Splash Artwork Background
        Image(
            painter = painterResource(id = R.drawable.splash_screen_art_1791931881153),
            contentDescription = "Mihnati Splash Screen",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Dark gradient overlay to ensure perfect readability of UI elements
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x99021F13),
                            Color(0x40021F13),
                            Color(0xD9021F13)
                        )
                    )
                )
        )


        // Bottom Loading & Branding Section
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 28.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pill with Algeria 69 Wilayas badge
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF0F5132).copy(alpha = 0.85f),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = KhidmatiGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isArabic) "تغطية شاملة لجميع الـ 69 ولاية جزائرية" else "Full coverage of all 69 Wilayas",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Animated progress bar
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = KhidmatiGold,
                trackColor = Color.White.copy(alpha = 0.2f),
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Dynamic Step Text & Percentage
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = loadingMessage,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "${(animatedProgress * 100).toInt()}%",
                    color = KhidmatiGold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 10 Seconds countdown notice
            Text(
                text = if (isArabic)
                    "جاري التحميل... الانتقال التلقائي بعد $secondsRemaining ثوانٍ"
                else
                    "Loading... auto-advancing in $secondsRemaining seconds",
                color = Color.White.copy(alpha = 0.65f),
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
