package com.example.cofinder.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.cofinder.R

// Set of Material typography styles to start with

val pretendard = FontFamily(
    Font(R.font.pretendard_thin,FontWeight.Thin),
    Font(R.font.pretendard_light,FontWeight.Light),
    Font(R.font.pretendard_medium,FontWeight.Medium),
    Font(R.font.pretendard_semibold,FontWeight.SemiBold),
    Font(R.font.pretendard_bold,FontWeight.Bold),
    Font(R.font.pretendard_extrabold,FontWeight.ExtraBold)
)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)