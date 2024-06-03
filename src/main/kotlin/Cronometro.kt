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

    var tempoFormatado by mutableStateOf("00:00:000")
    private var coroutineScope = CoroutineScope(Dispatchers.Default)
    private var emExecucao = false
    private var timeMillis = 0L
    private var ultimoTimestamp = 0L

    fun iniciar() {
        if(emExecucao) return

        coroutineScope.launch {
            ultimoTimestamp = System.currentTimeMillis()
            emExecucao = true
            while(emExecucao) {
                delay(10L)
                timeMillis += System.currentTimeMillis() - ultimoTimestamp
                ultimoTimestamp = System.currentTimeMillis()
                tempoFormatado = formatarTempo(timeMillis)

                if (timeMillis >= 59 * 60 * 1000 + 59 * 1000 + 999) {
                    emExecucao = false
                    timeMillis = 59L * 60 * 1000 + 59 * 1000 + 999
                    tempoFormatado = formatarTempo(timeMillis)
                }
            }
        }
    }

    fun pause() {
        emExecucao = false
    }

    fun reset() {
        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Default)
        timeMillis = 0L
        ultimoTimestamp = 0L
        tempoFormatado = "00:00:000"
        emExecucao = false
    }

    private fun formatarTempo(timeMillis: Long): String {
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