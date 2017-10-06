package red.padraig.twotapp.extensions

/**
 * Created by Red on 27/09/2017.
 */

private val HOUR_MILLIS = 1000 * 60 * 60
private val MINUTE_MILLIS = 1000 * 60
private val SECOND_MILLIS = 1000


fun Long.getHours() = this / HOUR_MILLIS

fun Long.getMinutes() = this % HOUR_MILLIS / MINUTE_MILLIS

fun Long.getSeconds() = this % HOUR_MILLIS % MINUTE_MILLIS / SECOND_MILLIS

fun Long.getMilliseconds() = this % HOUR_MILLIS % MINUTE_MILLIS % SECOND_MILLIS

fun Long.toTimerString(): String {
    val builder = StringBuilder()
    builder.append(this.getHours())
    builder.append("h ")
    builder.append(String.format("%02d", this.getMinutes()))
    builder.append("m ")
    builder.append(String.format("%02d", this.getSeconds()))
    builder.append("s")
    return builder.toString()
}

fun Long.toTimerStringWithMillis(): String {
    val builder = StringBuilder(this.toTimerString())
    builder.append(" ")
    builder.append(String.format("%03d", this.getMilliseconds()))
    return builder.toString()
}
