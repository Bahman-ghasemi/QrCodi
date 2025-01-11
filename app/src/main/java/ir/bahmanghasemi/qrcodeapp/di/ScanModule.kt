package ir.bahmanghasemi.qrcodeapp.di

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import ir.bahmanghasemi.qrcodeapp.common.domain.use_case.QrUseCase
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.repository.QrGeneratorRepositoryImpl
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.repository.QrGeneratorRepository
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.use_case.QrGeneratorUseCase
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.repository.ScanRepositoryImpl
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.repository.ScanRepository
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.use_case.QrScanUseCase

@Module
@InstallIn(ViewModelComponent::class)
object ScanModule {

    @Provides
    @ViewModelScoped
    fun provideScannerOption(): GmsBarcodeScannerOptions {
        return GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
    }

    @Provides
    @ViewModelScoped
    fun provideGmsScanner(@ApplicationContext context: Context, options: GmsBarcodeScannerOptions): GmsBarcodeScanner {
        return GmsBarcodeScanning.getClient(context, options)
    }

    @Provides
    @ViewModelScoped
    fun provideScannerRepository(scanner:GmsBarcodeScanner): ScanRepository {
        return ScanRepositoryImpl(scanner)
    }

    @Provides
    @ViewModelScoped
    fun provideGenerateRepository(@ApplicationContext context: Context): QrGeneratorRepository{
        return QrGeneratorRepositoryImpl(context)
    }

    @Provides
    @ViewModelScoped
    fun provideQrUseCase(
        scanRepository: ScanRepository,
        generatorRepository: QrGeneratorRepository
    ): QrUseCase {
        return QrUseCase(
            QrScanUseCase(scanRepository),
            QrGeneratorUseCase(generatorRepository)
        )
    }
}