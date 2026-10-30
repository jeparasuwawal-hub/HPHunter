# HP Hunter Android — Starter Project

Project Android native (Kotlin + Jetpack Compose), tampilan Premium Dark.

## Yang sudah tersedia
- Dashboard modal dan target laba.
- Filter merek, kata kunci, harga maksimal, dan area COD.
- Tombol membuka pencarian Facebook Marketplace.
- Kalkulator laba bersih dengan biaya transport dan cadangan servis.
- Ringkasan rencana pembelian sementara.

## Batasan penting
Aplikasi ini belum mengakses atau memindai iklan Marketplace secara otomatis.
Tidak ada API publik umum untuk mengambil seluruh iklan Marketplace. Integrasi
otomatis harus memakai akses/sumber data yang diizinkan Meta. Jangan masukkan
kata sandi Facebook ke aplikasi ini.

## Membuka dan membuat APK
1. Ekstrak ZIP.
2. Buka folder `HPHunterAndroid` di Android Studio versi terbaru.
3. Tunggu Gradle Sync selesai (perlu internet untuk mengunduh dependensi).
4. Pilih Build > Build Bundle(s) / APK(s) > Build APK(s).
5. APK debug biasanya muncul di `app/build/outputs/apk/debug/app-debug.apk`.

Project ini adalah starter source; belum dikompilasi atau diuji pada perangkat.
