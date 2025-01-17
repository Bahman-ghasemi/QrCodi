package ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.repository

import android.content.Context
import android.net.Uri
import com.github.alexzhirkevich.customqrgenerator.QrData
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.common.InputImage
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.bahmanghasemi.qrcodeapp.common.view.util.isGreaterThan
import ir.bahmanghasemi.qrcodeapp.common.view.util.mapToQrWifiAuthentication
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model.scan_type.CalendarEvent
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model.scan_type.ContactInfo
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model.scan_type.DriverLicense
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.repository.ScanRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScanRepositoryImpl @Inject constructor(
    private val context: Context,
    private val scanner: GmsBarcodeScanner
) : ScanRepository {

    override suspend fun startCameraScan(): Flow<QrData?> {
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

    override suspend fun startPhotoScan(uri: Uri): Flow<QrData?> {
        return callbackFlow {
            try {
                // Load the image from the provided URI
                val inputImage = InputImage.fromFilePath(context, uri)
                val scanner = BarcodeScanning.getClient()

                // Process the image and wait for the result using Tasks.await
                val barcodes = Tasks.await(scanner.process(inputImage))
                val barcode = barcodes.firstOrNull()
                barcode?.let {
                    send(getBarcodeDetail(barcode))
                } ?: send(null)
            } catch (e: Exception) {
                // Emit null or an empty result on failure
                e.printStackTrace()
                send(null)
            }

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

                ContactInfo(
                    name = "${name?.first} ${name?.middle} ${name?.last}",
                    title = title,
                    organization = organization,
                    emails = emails?.map { it.address } ?: emptyList(),
                    phones = phones?.map { it.number } ?: emptyList(),
                    urls = urls ?: emptyList(),
                    addresses = addresses?.map { it.addressLines.contentToString() } ?: emptyList(),
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
                val summary = barcode.calendarEvent?.summary
                val description = barcode.calendarEvent?.description
                val location = barcode.calendarEvent?.location
                val organizer = barcode.calendarEvent?.organizer
                val startDate = barcode.calendarEvent?.start?.rawValue
                val endDate = barcode.calendarEvent?.end?.rawValue
                val status = barcode.calendarEvent?.status

                CalendarEvent(
                    summary = summary,
                    description = description,
                    location = location,
                    organizer = organizer,
                    startDate = startDate,
                    endDate = endDate,
                    status = status,
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