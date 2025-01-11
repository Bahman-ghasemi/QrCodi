package ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.repository

import com.github.alexzhirkevich.customqrgenerator.QrData
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model.DriverLicense
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model.isGreaterThan
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model.mapToQrWifiAuthentication
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.repository.ScanRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScanRepositoryImpl @Inject constructor(
    private val scanner: GmsBarcodeScanner
) : ScanRepository {

    override fun startScan(): Flow<QrData?> {
        return callbackFlow {
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    launch {
                        barcode.rawValue
                        send(getBarcodeDetail(barcode))
                    }
                }
                .addOnFailureListener { it.printStackTrace() }

            awaitClose {

            }
        }
    }

    private fun getBarcodeDetail(barcode: Barcode): QrData? {
        return when (barcode.valueType) {
            Barcode.TYPE_WIFI -> {
                val ssid = barcode.wifi?.ssid
                val password = barcode.wifi?.password
                val type = barcode.wifi?.encryptionType

                QrData.Wifi(ssid = ssid, psk = password, authentication = type?.mapToQrWifiAuthentication())
            }

            Barcode.TYPE_URL -> {
                val barcodeUrl = barcode.url
                barcodeUrl?.url?.let {
                    QrData.Url(it)
                }
            }

            Barcode.TYPE_CONTACT_INFO -> {
                val name = barcode.contactInfo?.name
                val title = barcode.contactInfo?.title
                val organization = barcode.contactInfo?.organization
                val emails = barcode.contactInfo?.emails
                val phones = barcode.contactInfo?.phones
                val urls = barcode.contactInfo?.urls
                val addresses = barcode.contactInfo?.addresses

                QrData.VCard(
                    title = title,
                    name = "${name?.first} ${name?.middle} ${name?.last}",
                    company = organization,
                    email = emails?.firstOrNull()?.address,
                    phoneNumber = phones?.firstOrNull()?.number,
                    address = addresses?.firstOrNull()?.addressLines.contentToString(),
                    website = urls?.firstOrNull()
                )
            }

            Barcode.TYPE_EMAIL -> {
                val type = barcode.email?.type
                val address = barcode.email?.address
                val subject = barcode.email?.subject
                val body = barcode.email?.body

                QrData.Email(email = address ?: "", subject = subject, body = body)
            }

            Barcode.TYPE_PHONE -> {
                barcode.phone?.number?.let {
                    QrData.Phone(phoneNumber = it)
                }
            }

            Barcode.TYPE_SMS -> {
                val phoneNumber = barcode.sms?.phoneNumber
                val subject = barcode.sms?.message
                val isMMS = barcode.sms?.message?.count().isGreaterThan(160)
                phoneNumber?.let {
                    subject?.let {
                        QrData.SMS(
                            phoneNumber = phoneNumber,
                            subject = subject,
                            isMMS = isMMS,
                        )
                    }
                }
            }

            Barcode.TYPE_GEO -> {
                val lat = barcode.geoPoint?.lat
                val lng = barcode.geoPoint?.lng
                QrData.GeoPos(
                    lat = lat?.toFloat() ?: 0.0f,
                    lon = lng?.toFloat() ?: 0.0f
                )
            }

            Barcode.TYPE_CALENDAR_EVENT -> {
                val start = barcode.calendarEvent?.start
                val end = barcode.calendarEvent?.end
                val status = barcode.calendarEvent?.status
                val summary = barcode.calendarEvent?.summary
                val location = barcode.calendarEvent?.location

                QrData.Event(
                    start = start.toString(),
                    end = end.toString(),
                    summary = summary
                )
            }

            Barcode.TYPE_DRIVER_LICENSE -> {
                val firstName = barcode.driverLicense?.firstName
                val lastName = barcode.driverLicense?.lastName
                val gender = barcode.driverLicense?.gender
                val birthDate = barcode.driverLicense?.birthDate
                val licenseNumber = barcode.driverLicense?.licenseNumber

                DriverLicense(
                    firstName = firstName,
                    lastName = lastName,
                    gender = gender,
                    birthDate = birthDate,
                    licenseNumber = licenseNumber
                )
            }

            else -> {
                barcode.rawValue?.let { QrData.Text(it) }
            }
        }
    }
}