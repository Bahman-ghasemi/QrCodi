package ir.bahmanghasemi.qrcodeapp.data.repository

import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import ir.bahmanghasemi.qrcodeapp.domain.repository.ScannerRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScannerRepositoryImpl @Inject constructor(
    private val scanner: GmsBarcodeScanner
) : ScannerRepository {

    override fun startScan(): Flow<String?> {
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

    private fun getBarcodeDetail(barcode: Barcode): String {
        return when (barcode.valueType) {
            Barcode.TYPE_WIFI -> {
                val ssid = barcode.wifi?.ssid
                val password = barcode.wifi?.password
                val type = barcode.wifi?.encryptionType
                "ssid:$ssid \n" +
                        "password:$password \n" +
                        "encryptionType:$type"
            }

            Barcode.TYPE_URL -> {
                "url: ${barcode.url}"
            }

            Barcode.TYPE_CONTACT_INFO -> {
                val name = barcode.contactInfo?.name
                val title = barcode.contactInfo?.title
                val organization = barcode.contactInfo?.organization
                val emails = barcode.contactInfo?.emails
                val phones = barcode.contactInfo?.phones
                val urls = barcode.contactInfo?.urls
                val addresses = barcode.contactInfo?.addresses

                "name: $name \n" +
                        "title: $title \n" +
                        "organization: $organization \n" +
                        "emails: $emails \n" +
                        "phones: $phones \n" +
                        "urls: $urls \n" +
                        "addresses: $addresses"
            }

            Barcode.TYPE_EMAIL -> {
                val type = barcode.email?.type
                val address = barcode.email?.address
                val subject = barcode.email?.subject
                val body = barcode.email?.body
                "type= $type \n" +
                        "address= $address \n" +
                        "subject= $subject \n " +
                        "body= $body"
            }

            Barcode.TYPE_PHONE -> {
                "number: ${barcode.phone?.number}"
            }

            Barcode.TYPE_SMS -> {
                "content: ${barcode.sms?.message}"
            }

            Barcode.TYPE_TEXT -> {
                "content: ${barcode.rawValue}"
            }

            Barcode.TYPE_GEO -> {
                "geo: [${barcode.geoPoint?.lat}, ${barcode.geoPoint?.lng}]"
            }

            Barcode.TYPE_CALENDAR_EVENT -> {
                val start = barcode.calendarEvent?.start
                val end = barcode.calendarEvent?.end
                val status = barcode.calendarEvent?.status
                val summary = barcode.calendarEvent?.summary
                val location = barcode.calendarEvent?.location

                "start: $start \n" +
                        "end: $end \n" +
                        "status: $status \n" +
                        "summary: $summary \n" +
                        "location: $location \n"
            }

            Barcode.TYPE_DRIVER_LICENSE -> {
                val firstName = barcode.driverLicense?.firstName
                val lastName = barcode.driverLicense?.lastName
                val gender = barcode.driverLicense?.gender
                val birthDate = barcode.driverLicense?.birthDate
                val licenseNumber = barcode.driverLicense?.licenseNumber
                "firstName: $firstName \n " +
                        "lastName: $lastName \n " +
                        "gender: $gender \n " +
                        "birthDate: $birthDate \n " +
                        "licenseNumber: $licenseNumber"
            }

            else -> {
                barcode.rawValue ?: "invalid QrCode!"
            }
        }
    }
}