package ir.bahmanghasemi.qrcodeapp.feature_scan.domain.repository

import kotlinx.coroutines.flow.Flow

interface ScannerRepository {

    fun startScan(): Flow<String?>

}