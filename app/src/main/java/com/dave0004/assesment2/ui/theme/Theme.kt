package com.dave0004.assesment2.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


private val PurpleLightScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)
private val PurpleDarkScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val BlueLightScheme = lightColorScheme(
    primary = Color(0xFF1565C0),
    secondary = Color(0xFF42A5F5),
    tertiary = Color(0xFF0288D1)
)
private val BlueDarkScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),
    secondary = Color(0xFF42A5F5),
    tertiary = Color(0xFF81D4FA)
)

private val GreenLightScheme = lightColorScheme(
    primary = Color(0xFF2E7D32),
    secondary = Color(0xFF66BB6A),
    tertiary = Color(0xFF00897B)
)
private val GreenDarkScheme = darkColorScheme(
    primary = Color(0xFFA5D6A7),
    secondary = Color(0xFF66BB6A),
    tertiary = Color(0xFF80CBC4)
)
private val RedLightScheme = lightColorScheme(
    primary = Color(0xFFC62828),
    secondary = Color(0xFFEF9A9A),
    tertiary = Color(0xFFAD1457)
)
private val RedDarkScheme = darkColorScheme(
    primary = Color(0xFFEF9A9A),
    secondary = Color(0xFFE57373),
    tertiary = Color(0xFFF48FB1)
)
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun Assesment2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    selectedTheme: String = "Purple",
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> when (selectedTheme) {
            "Blue" -> if (darkTheme) BlueDarkScheme else BlueLightScheme
            "Green" -> if (darkTheme) GreenDarkScheme else GreenLightScheme
            "Red" -> if (darkTheme) RedDarkScheme else RedLightScheme
            else -> if (darkTheme) PurpleDarkScheme else PurpleLightScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}