package ir.bahmanghasemi.qrcodeapp.feature_qrcode.view.generator.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ir.bahmanghasemi.qrcodeapp.R
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model.QrAction
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model.QrGeneratorItem

@Composable
fun GenerationScreen(modifier: Modifier = Modifier, onClick: (QrAction) -> Unit) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {

        LazyVerticalGrid(columns = GridCells.Fixed(count = 3)) {
            items(generationQrList()) {
                QrGridItem(it)
            }
        }

        Text(
            "Social",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )

        LazyVerticalGrid(columns = GridCells.Fixed(count = 4)) {
            items(socialQrList()) {
                SocialGridItem(it)
            }
        }
    }
}


@Composable
fun QrGridItem(item: QrGeneratorItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(12.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { item.onClick },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(item.backgroundColor)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
        ) {
            Icon(
                modifier = Modifier
                    .size(36.dp),
                painter = painterResource(item.icon),
                contentDescription = item.text,
                tint = item.tintColor
            )
            Text(
                text = item.text,
                style = MaterialTheme.typography.bodyMedium,
                color = item.tintColor,
            )
        }
    }
}

@Composable
fun SocialGridItem(item: QrGeneratorItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(12.dp)
            .clip(CircleShape)
            .clickable { item.onClick },
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(item.backgroundColor)
    )
    {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(
                modifier = Modifier
                    .size(32.dp),
                painter = painterResource(item.icon),
                contentDescription = item.text,
                tint = item.tintColor
            )
        }
    }
}


private fun generationQrList(): List<QrGeneratorItem> {
    return listOf(
        QrGeneratorItem(
            text = "Email",
            icon = R.drawable.mail,
            tintColor = Color(0xffff3737),
            backgroundColor = Color(0xffffe6e8),
            onClick = { QrAction.Mail }),
        QrGeneratorItem(
            text = "Phone",
            icon = R.drawable.phone,
            tintColor = Color(0xff5ec1f3),
            backgroundColor = Color(0xFFE6F5FF),
            onClick = { QrAction.Phone }),
        QrGeneratorItem(
            text = "Message",
            icon = R.drawable.messaging,
            tintColor = Color(0xff50db63),
            backgroundColor = Color(0xFFE9FAEB),
            onClick = { QrAction.Message }),
        QrGeneratorItem(
            text = "Text",
            icon = R.drawable.text,
            tintColor = Color(0xffbcbcbd),
            backgroundColor = Color(0xFFF3F3F3),
            onClick = { QrAction.Text }),
        QrGeneratorItem(
            text = "URL",
            icon = R.drawable.url,
            tintColor = Color(0xffff9800),
            backgroundColor = Color(0xfffff8e3),
            onClick = { QrAction.Url }),
        QrGeneratorItem(
            text = "Card",
            icon = R.drawable.card,
            tintColor = Color(0xffff4abe),
            backgroundColor = Color(0xffffeaf7),
            onClick = { QrAction.Card })
    )
}

private fun socialQrList(): List<QrGeneratorItem> {
    return listOf(
        QrGeneratorItem(
            text = "Facebook",
            icon = R.drawable.facebook,
            tintColor = Color(0xff3f64b3),
            backgroundColor = Color(0xffebeff7),
            onClick = { QrAction.FaceBook }),
        QrGeneratorItem(
            text = "Instagram",
            icon = R.drawable.insta,
            tintColor = Color(0xffe3266d),
            backgroundColor = Color(0xfffce9f0),
            onClick = { QrAction.Instagram }),
        QrGeneratorItem(
            text = "WhatsApp",
            icon = R.drawable.whatsapp,
            tintColor = Color(0xff49c170),
            backgroundColor = Color(0xFFD5FFD9),
            onClick = { QrAction.Whatsapp }),
        QrGeneratorItem(
            text = "Viber",
            icon = R.drawable.phone_fill,
            tintColor = Color(0xff764bda),
            backgroundColor = Color(0xfff1edfb),
            onClick = { QrAction.Viber })
    )
}