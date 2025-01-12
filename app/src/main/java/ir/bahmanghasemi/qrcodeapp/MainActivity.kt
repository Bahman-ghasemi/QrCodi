package ir.bahmanghasemi.qrcodeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.bahmanghasemi.qrcodeapp.common.view.navigation.GenerationRoute
import ir.bahmanghasemi.qrcodeapp.common.view.navigation.HistoryRoute
import ir.bahmanghasemi.qrcodeapp.common.view.navigation.HomeRoute
import ir.bahmanghasemi.qrcodeapp.common.view.navigation.ScanRoute
import ir.bahmanghasemi.qrcodeapp.common.view.navigation.SettingRoute
import ir.bahmanghasemi.qrcodeapp.common.view.ui.theme.QrCodeAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QrCodeAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar() }
                ) { paddingValues ->
                    AppNavigation(modifier = Modifier.padding(paddingValues), navController)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier, navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = HomeRoute) {

        composable<HomeRoute> {
//            HomeScreen(modifier)
        }

        composable<ScanRoute> {
//            ScanScreen(modifier)
        }

        composable<GenerationRoute> {
//            GenerationScreen(modifier)
        }

        composable<HistoryRoute> {
//            HistoryScreen(modifier)
        }

        composable<SettingRoute> {
//            SettingScreen(modifier)
        }
    }
}


@Composable
fun BottomBar() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Gray)
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) {
                Icon(painter = painterResource(R.drawable.image), contentDescription = stringResource(R.string.image))
            }

            IconButton(onClick = {}) {
                Icon(painter = painterResource(R.drawable.barcode), contentDescription = stringResource(R.string.barcode))
            }

            IconButton(onClick = {}) {
                Icon(painter = painterResource(R.drawable.flash_off), contentDescription = stringResource(R.string.flashlight))
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) {
                Icon(painter = painterResource(R.drawable.scan), contentDescription = stringResource(R.string.scan_barcode))
            }

            IconButton(onClick = {}) {
                Icon(painter = painterResource(R.drawable.time), contentDescription = stringResource(R.string.barcode_history))
            }

            IconButton(onClick = {}) {
                Icon(painter = painterResource(R.drawable.qrcode), contentDescription = stringResource(R.string.generate_qrcode))
            }

            IconButton(onClick = {}) {
                Icon(painter = painterResource(R.drawable.setting), contentDescription = stringResource(R.string.setting))
            }
        }
    }
}