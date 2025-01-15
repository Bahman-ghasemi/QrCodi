package ir.bahmanghasemi.qrcodeapp.feature_history.view.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.entity.QrCodeEntity

@Composable
fun HistoryScreen(modifier: Modifier = Modifier, qrCodes: List<QrCodeEntity> = emptyList()) {

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedQr by remember { mutableStateOf<QrCodeEntity?>(null) }

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
        ) {
            items(qrCodes) {
                HistoryItem(it) {
                    showBottomSheet = true
                    selectedQr = it
                }
            }
        }

        if (showBottomSheet) {
            selectedQr?.let {
                HistoryDetail(it) {
                    showBottomSheet = false
                }
            }
        }
    }
}

@Composable
//@Preview(showBackground = true, showSystemUi = true)
fun HistoryItem(qrCode: QrCodeEntity, onShowDetails: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clickable { onShowDetails() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(56.dp),
                painter = painterResource(qrCode.type.icon),
                contentDescription = qrCode.type.type.name
            )
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(qrCode.rawContent)
                Text(qrCode.creationDate.toString())
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetail(item: QrCodeEntity, onDismiss: () -> Unit) {
    ModalBottomSheet(onDismissRequest = { onDismiss() }) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterVertically)
        ) {
//            Image(painter = painterResource(item.rawContent))

            Text(item.type.type.name)
            Text(item.rawContent)
        }
    }
}