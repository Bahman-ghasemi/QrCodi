package ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model

import com.github.alexzhirkevich.customqrgenerator.QrData

data class DriverLicense(
    val firstName: String?,
    val lastName: String?,
    val gender: String?,
    val birthDate: String?,
    val licenseNumber: String?,
): QrData{
    override fun encode(): String = buildString {
        append("LICENSECARD:")
        if (firstName != null)
            append("FirstName:$firstName;")

        if (lastName != null)
            append("LastName:$lastName;")

        if (gender != null)
            append("Sex:$gender;")

        if (birthDate != null)
            append("Date of Birth:$birthDate;")

        if (licenseNumber != null)
            append("LicenseNumber:$licenseNumber;")
        append(";")
    }
}
