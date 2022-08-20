package gll.pub.onepeas.model

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class RequestTimer(val timeout: Long) {

    var startTime : Long = 0
    var endTime : Long = 0

    fun start() {
        this.startTime = System.nanoTime()
    }

    fun stop() {
        this.endTime = System.nanoTime()
    }

    fun getElapsedTime() : Long {
        return endTime - startTime;
    }

    fun hasTimedOut() : Boolean {
        return timeout < getElapsedTime()
    }

    companion object {
        fun getCurrentTime(format: String) : Timestamp {
            val sdfdb = SimpleDateFormat(format, Locale.US)
            val calendar = GregorianCalendar(TimeZone.getTimeZone("US/Central"))
            return Timestamp.valueOf(sdfdb.format(calendar.time))
        }
    }
}