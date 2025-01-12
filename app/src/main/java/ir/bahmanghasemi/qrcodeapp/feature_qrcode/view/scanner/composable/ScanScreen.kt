package ir.bahmanghasemi.qrcodeapp.feature_qrcode.view.scanner.composable

import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.provider.ContactsContract
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.alexzhirkevich.customqrgenerator.QrData
import ir.bahmanghasemi.qrcodeapp.R
import ir.bahmanghasemi.qrcodeapp.common.view.util.Connectivity
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model.CalendarEvent
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model.ContactInfo


@Composable
@Preview(showBackground = true)
fun ScanComposable(
    content: QrData? = null
) {
    val context = LocalContext.current
    var isVisible by remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically)
    ) {
        Image(
            modifier = Modifier
                .size(150.dp)
                .aspectRatio(1f),
            painter = painterResource(R.drawable.scan_qrcode), contentDescription = stringResource(R.string.scan_icon)
        )

        AnimatedVisibility(isVisible) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color.LightGray)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterVertically)
            ) {

                content?.let {
                    Text(it.toString())
                    when (it) {

                        is QrData.Url -> {
                            ActionButton("Open Url") {
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    setData(Uri.parse(it.url))
                                }
                                context.startActivity(intent)
                            }
                        }

                        is QrData.SMS -> {
                            ActionButton("Send Sms") {
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    setData(Uri.parse("smsto:${it.phoneNumber}"))
                                    putExtra("sms_body", it.subject)
                                }
                                context.startActivity(intent)
                            }
                        }

                        is QrData.GeoPos -> {
                            ActionButton("Go to Location") {
                                val uri = Uri.parse("geo:${it.lat},${it.lon}?z=15")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(intent)
                            }
                        }

                        is QrData.Phone -> {
                            ActionButton("Call ${it.phoneNumber}") {
                                val intent = Intent(Intent.ACTION_CALL).apply {
                                    setData(Uri.parse("tel:${it.phoneNumber}"))
                                }
                                context.startActivity(intent)
                            }
                        }

                        is CalendarEvent -> {
                            ActionButton("Add to Calendar") {
                                val intent = Intent(Intent.ACTION_INSERT).apply {
                                    setData(CalendarContract.Events.CONTENT_URI)
                                    putExtra(CalendarContract.Events.TITLE, it.summary)
                                    putExtra(CalendarContract.Events.EVENT_LOCATION, it.location)
                                    putExtra(CalendarContract.Events.DESCRIPTION, it.description)
                                    /*putExtra(
                                        CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                        ZonedDateTime.parse(it.start).toLocalDateTime().toString()
                                    )*/
                                    /*putExtra(
                                        CalendarContract.EXTRA_EVENT_END_TIME,
                                        ZonedDateTime.parse(it.end).toLocalDateTime().toString()
                                    )*/
                                }
                                context.startActivity(intent)
                            }
                        }

                        is QrData.Email -> {
                            ActionButton("Send Email") {
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    setType("message/rfc822")
                                    putExtra(Intent.EXTRA_EMAIL, it.email)
                                    putExtra(Intent.EXTRA_SUBJECT, it.subject)
                                    putExtra(Intent.EXTRA_TEXT, it.body)
                                }
                                context.startActivity(intent)
                            }
                        }

                        is ContactInfo -> {
                            ActionButton("Save Contact") {
                                val intent = Intent(Intent.ACTION_INSERT).apply {
                                    setType(ContactsContract.RawContacts.CONTENT_TYPE)
                                    putExtra(ContactsContract.Intents.Insert.NAME, it.name)
                                    putExtra(ContactsContract.Intents.Insert.JOB_TITLE, it.title)
                                    putExtra(ContactsContract.Intents.Insert.COMPANY, it.organization)
                                    it.phones.filterNotNull().forEachIndexed { index, phone ->
                                        when (index) {
                                            1 -> {
                                                putExtra(ContactsContract.Intents.Insert.PHONE, phone)
                                            }

                                            2 -> {
                                                putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, phone)
                                            }

                                            3 -> {
                                                putExtra(ContactsContract.Intents.Insert.TERTIARY_PHONE, phone)
                                            }
                                        }
                                    }
                                    it.emails.filterNotNull().forEachIndexed { index, email ->
                                        when (index) {
                                            1 -> {
                                                putExtra(ContactsContract.Intents.Insert.EMAIL, email)
                                            }

                                            2 -> {
                                                putExtra(ContactsContract.Intents.Insert.SECONDARY_EMAIL, email)
                                            }

                                            3 -> {
                                                putExtra(ContactsContract.Intents.Insert.TERTIARY_EMAIL, email)
                                            }
                                        }
                                    }
                                    it.addresses.first()?.let { address ->
                                        putExtra(ContactsContract.Intents.Insert.POSTAL, address)
                                    }
                                }
                                context.startActivity(intent)
                            }
                        }

                        is QrData.Wifi -> {
                            if (it.ssid?.isNotEmpty() == true && it.psk?.isNotEmpty() == true)
                                ActionButton("Connect To ${it.ssid}") {
                                    Connectivity.connectToWifi(context, it)
                                }
                        }

                    }
                }
            }
        }

        LaunchedEffect(true) {
            content?.let {
                isVisible = true
            }
        }

    }
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = onClick
    ) {
        Text(text)
    }
}