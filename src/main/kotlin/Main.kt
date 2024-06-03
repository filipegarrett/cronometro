import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

@Composable
@Preview
fun App() {
    val verdePrimary = Color(0xFF4CAF50)
    val amareloPrimary = Color(0xFFFF9800)
    val vermelhoPrimary = Color(0xFFF44336)

    val customThemeColors = lightColors(
        primary = verdePrimary,
        secondary = amareloPrimary,
        error = vermelhoPrimary
    )

    MaterialTheme(colors = customThemeColors) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            val cronometro = remember { Cronometro() }
            CronometroDisplay(
                formattedTime = cronometro.tempoFormatado,
                onStartClick = cronometro::iniciar,
                onPauseClick = cronometro::pause,
                onResetClick = cronometro::reset
            )
        }
    }
}

fun main() = application {
    val windowState = rememberWindowState(width = 350.dp, height = 200.dp)
    val icon: Painter = painterResource("icons/timer_48dp.png")
    Window(
        onCloseRequest = ::exitApplication,
        title = "Cronômetro",
        state = windowState,
        alwaysOnTop = true,
        resizable = false,
        icon = icon
    ) {
        App()
    }
}
