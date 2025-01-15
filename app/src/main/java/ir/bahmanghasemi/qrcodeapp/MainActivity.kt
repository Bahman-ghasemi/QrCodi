package ir.bahmanghasemi.qrcodeapp

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.bahmanghasemi.qrcodeapp.common.view.navigation.GenerationRoute
import ir.bahmanghasemi.qrcodeapp.common.view.navigation.HistoryRoute
import ir.bahmanghasemi.qrcodeapp.common.view.navigation.ScanRoute
import ir.bahmanghasemi.qrcodeapp.common.view.navigation.SettingRoute
import ir.bahmanghasemi.qrcodeapp.common.view.ui.theme.QrCodeAppTheme
import ir.bahmanghasemi.qrcodeapp.feature_history.view.composable.HistoryScreen
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.view.QrViewModel
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.view.generator.composable.GenerationScreen
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.view.scanner.composable.ScanScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: QrViewModel
    private lateinit var imagePickerLauncher: ManagedActivityResultLauncher<String, Uri?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            viewModel = viewModels<QrViewModel>().value
            val navController = rememberNavController()
            QrCodeAppTheme {
                imagePickerLauncher =
                    rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                        uri?.let { viewModel.startPhotoScan(it) }
                    }

                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomBar(navController)
                    }
                ) { paddingValues ->
                    AppNavigation(modifier = Modifier.padding(paddingValues), navController)
                }
            }
        }
    }

    @Composable
    fun AppNavigation(modifier: Modifier = Modifier, navHostController: NavHostController) {
        NavHost(navController = navHostController, startDestination = ScanRoute) {

            composable<ScanRoute> {
                ScanScreen(modifier)
            }

            composable<GenerationRoute> {
                GenerationScreen(modifier) {

                }
            }

            composable<HistoryRoute> {
                HistoryScreen(modifier)
            }

            composable<SettingRoute> {
//            SettingScreen(modifier)
            }
        }
    }

    @Composable
    fun BottomBar(navHostController: NavHostController) {
        val state by viewModel.scanUiState.collectAsState()
        var selectedMenu by remember { mutableIntStateOf(0) }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (selectedMenu == 0) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xfff4f6f8))
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            imagePickerLauncher.launch("image/*")
                        }) {
                            Icon(painter = painterResource(R.drawable.image), contentDescription = stringResource(R.string.image))
                        }

                        IconButton(onClick = { viewModel.startCameraScan() }) {
                            Icon(painter = painterResource(R.drawable.barcode), contentDescription = stringResource(R.string.barcode))
                        }

                        IconButton(onClick = { viewModel.toggleFlashLight() }) {
                            Icon(
                                painter = painterResource(if (state.isFlashOn) R.drawable.flash_off else R.drawable.flash_on),
                                contentDescription = stringResource(R.string.flashlight)
                            )
                        }
                    }
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        selectedMenu = 0
                        navHostController.navigate(ScanRoute)
                        {
                            popUpTo(navHostController.graph.startDestinationId) { inclusive = true }
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.scan),
                            contentDescription = stringResource(R.string.scan_barcode),
                            tint = if (selectedMenu == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        )
                    }

                    IconButton(onClick = {
                        selectedMenu = 1
                        navHostController.navigate(HistoryRoute)
                        {
                            popUpTo(navHostController.graph.startDestinationId) { inclusive = true }
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.time),
                            contentDescription = stringResource(R.string.barcode_history),
                            tint = if (selectedMenu == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        )
                    }

                    IconButton(onClick = {
                        selectedMenu = 2
                        navHostController.navigate(GenerationRoute)
                        {
                            popUpTo(navHostController.graph.startDestinationId) { inclusive = true }
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.qrcode),
                            contentDescription = stringResource(R.string.generate_qrcode),
                            tint = if (selectedMenu == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        )
                    }

                    IconButton(onClick = {
                        selectedMenu = 3
                        navHostController.navigate(SettingRoute)
                        {
                            popUpTo(navHostController.graph.startDestinationId) { inclusive = true }
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.setting),
                            contentDescription = stringResource(R.string.setting),
                            tint = if (selectedMenu == 3) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun FlashlightPermissionRequest(onPermissionGranted: () -> Unit) {
        val context = LocalContext.current
        val permission = Manifest.permission.CAMERA
        var permissionGranted by remember { mutableStateOf(false) }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            permissionGranted = isGranted
            if (isGranted) {
                onPermissionGranted()
            }
        }

        LaunchedEffect(Unit) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                permissionGranted = true
                onPermissionGranted()
            }
        }

        if (!permissionGranted) {
            Button(onClick = { launcher.launch(permission) }) {
                Text("Grant Camera Permission")
            }
        }
    }
}
