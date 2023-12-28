package com.part.computer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.part.computer.ui.theme.JetPartTheme

/**
 * MainActivity merupakan titik masuk dari aplikasi JetPart.
 * Ini menyiapkan komponen-komponen UI Compose utama, seperti tema dan konten.
 *
 * @property JetPartTheme Tema Compose yang digunakan untuk menata gaya komponen UI.
 */
class MainActivity : ComponentActivity() {
    /**
     * Dipanggil ketika aktivitas pertama kali dibuat. Di sinilah Anda seharusnya
     * menginisialisasi komponen-komponen UI dan menetapkan tampilan konten.
     *
     * @param savedInstanceState Jika aktivitas diinisialisasi kembali setelahnya
     *     sebelumnya dihentikan, Bundle ini berisi data yang paling baru
     *     yang diberikan dalam onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Tetapkan konten aktivitas menggunakan JetPartTheme
        setContent {
            JetPartTheme {
                // Setel permukaan utama dengan Modifier untuk mengisi ukuran maksimum
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Tampilkan antarmuka aplikasi JetPart
                    JetPartApp()
                }
            }
        }
    }
}
