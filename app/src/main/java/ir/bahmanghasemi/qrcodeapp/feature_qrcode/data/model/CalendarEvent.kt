package ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model

import com.github.alexzhirkevich.customqrgenerator.QrData

data class CalendarEvent(
    val summary: String?,
    val description: String?,
    val location: String?,
    val organizer: String?,
    val startDate: String?,
    val endDate: String?,
    val status: String?
): QrData{
    override fun encode(): String = buildString {
        append("Calendar Event:")

        if (summary != null)
            append("summary: $summary;")

        if (description != null)
            append("description: $description;")

        if (location != null)
            append("location: $location;")

        if (organizer != null)
            append("organizer: $organizer;")

        if (startDate != null)
            append("startDate: $startDate;")

        if (endDate != null)
            append("endDate: $endDate;")

        if (status != null)
            append("status: $status;")

        append(";")
    }
}