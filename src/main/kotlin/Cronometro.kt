import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class Cronometro {

    var formattedTime by mutableStateOf("00:00:000")
    private var coroutineScope = CoroutineScope(Dispatchers.Default)
    private var isRunning = false
    private var timeMillis = 0L
    private var lastTimestamp = 0L

    fun start() {
        if(isRunning) return

        coroutineScope.launch {
            lastTimestamp = System.currentTimeMillis()
            isRunning = true
            while(isRunning) {
                delay(10L)
                timeMillis += System.currentTimeMillis() - lastTimestamp
                lastTimestamp = System.currentTimeMillis()
                formattedTime = formatTime(timeMillis)
            }
        }
    }

    fun startCountdown(initialTimeMillis: Long) {
        if(isRunning) return

        coroutineScope.launch {
            timeMillis = initialTimeMillis
            lastTimestamp = System.currentTimeMillis()
            isRunning = true
            while(isRunning && timeMillis > 0) {
                delay(10L)
                val elapsed = System.currentTimeMillis() - lastTimestamp
                timeMillis = maxOf(0, timeMillis - elapsed)
                lastTimestamp = System.currentTimeMillis()
                formattedTime = formatTime(timeMillis)
            }
            isRunning = false
        }
    }

    fun pause() {
        isRunning = false
    }

    fun reset() {
        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Default)
        timeMillis = 0L
        lastTimestamp = 0L
        formattedTime = "00:00:000"
        isRunning = false
    }

    private fun formatTime(timeMillis: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMillis),
            ZoneId.systemDefault()
        )
        val formatter = DateTimeFormatter.ofPattern(
            "mm:ss:SSS",
            Locale.getDefault()
        )
        return localDateTime.format(formatter)
    }
}