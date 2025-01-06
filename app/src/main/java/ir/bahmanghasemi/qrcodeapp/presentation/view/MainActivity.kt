package ir.bahmanghasemi.qrcodeapp.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import ir.bahmanghasemi.qrcodeapp.common.view.ui.theme.QrCodeAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewmodel by viewModels<ScannerViewModel>()

        setContent {
            QrCodeAppTheme {
                val state = viewmodel.uiState.collectAsState()
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterVertically)
                    ) {

                        state.value.result?.let {
                            Text(it)
                        }

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 24.dp),

                            onClick = {
                                viewmodel.statScan()
                            }) {
                            Text("Start Scan")
                        }
                    }
                }
            }
        }
    }
}