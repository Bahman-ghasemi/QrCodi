package ir.bahmanghasemi.qrcodeapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface ScannerRepository {

    fun startScan(): Flow<String?>

}