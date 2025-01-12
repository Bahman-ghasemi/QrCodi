package ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model

import com.github.alexzhirkevich.customqrgenerator.QrData

data class ContactInfo(
    val name: String,
    val title: String?,
    val organization: String?,
    val emails: List<String?> = emptyList(),
    val phones: List<String?> = emptyList(),
    val urls: List<String?> = emptyList(),
    val addresses: List<String?> = emptyList(),
): QrData{
    override fun encode(): String = buildString {
        append("LICENSECARD:")

        append("Name: $name;")

        if (title != null)
            append("LastName: $title;")

        if (organization != null)
            append("organization: $organization;")

        if (emails.isNotEmpty()) {
            append("email:")
            emails.forEach {
                append("$it;")
            }
        }

        if (phones.isNotEmpty()) {
            append("email:")
            phones.forEach {
                append("$it;")
            }
        }

        if (urls.isNotEmpty()) {
            append("email:")
            urls.forEach {
                append("$it;")
            }
        }

        if (addresses.isNotEmpty()) {
            append("email:")
            addresses.forEach {
                append("$it;")
            }
        }

        append(";")
    }
}