package ch.protonmail.android.protonmailtest.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = lightColors(
    primary = White,
    primaryVariant = White,
    secondary = Grey,
    secondaryVariant = LightGrey,
    background = White,
    surface = White,
    onPrimary = Black,
    onSecondary = Black,
    onBackground = Black,
    onSurface = Black,
    error = Purple
)

@Composable
fun TasksComposeAppTheme(
    useSystemUiController: Boolean = true,
    content: @Composable () -> Unit
) {

    if (useSystemUiController) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            color = LightColorPalette.primaryVariant
        )
    }

    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        content = content
    )
}