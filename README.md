# 🚗 Fleet Tracker

Selamat datang di **Fleet Tracker**! 🚀 Aplikasi ini mensimulasikan pelacakan kendaraan dengan data simulasi sensor IoT secara real-time.

## 📜 Deskripsi

Fleet Tracker adalah aplikasi Android yang mensimulasikan sistem manajemen armada kendaraan. Aplikasi ini menampilkan lokasi kendaraan di peta serta berbagai data sensor seperti kecepatan, status mesin, dan status pintu.

## 🔧 Instalasi dan Konfigurasi

1. Clone repository ini:
   ```sh
   git clone [https://github.com/your-repo-url](https://github.com/anhaki/FleetTrackerTest)
   ```
2. Buka proyek di Android Studio.
3. Tambahkan API key Google Maps di `local.properties`:
   ```properties
   MAPS_API_KEY=AIzaSyALzdQOtH50TNc_90iVDOtAGYpIRiY7qw4
   ```
4. Jalankan aplikasi di emulator atau perangkat.

## ✨ Fitur Utama

✅ **Vehicle Map View**: Menampilkan lokasi kendaraan pada peta.
✅ **Simulasi Data IoT**: Status mesin, status pintu, dan kecepatan kendaraan.
✅ **Dashboard Real-Time**: Informasi kendaraan ditampilkan dengan pembaruan berkala.
✅ **Peringatan Otomatis**: Notifikasi jika kecepatan melebihi batas atau pintu terbuka saat kendaraan bergerak.
✅ **Clean Architecture**: Menggunakan MVVM untuk pemisahan logika bisnis dan UI.

## 🔑 Kredensial Login

Untuk masuk ke aplikasi, gunakan akun berikut:
- **Username**: `username`
- **Password**: `pass123`

## 📸 Screenshot
![WhatsApp Image 2025-03-26 at 23 58 59](https://github.com/user-attachments/assets/5f85a0d5-f004-4fd8-9985-068c4ee7e843)
![WhatsApp Image 2025-03-26 at 23 59 01 (1)](https://github.com/user-attachments/assets/5aeab615-a524-44a1-af3d-2acc7c677514)
![WhatsApp Image 2025-03-26 at 23 59 01](https://github.com/user-attachments/assets/04375c4c-7340-40ec-83d3-c0d60cfb6d32)
![WhatsApp Image 2025-03-26 at 23 59 00](https://github.com/user-attachments/assets/569217e7-c5ee-41a0-a60b-59ec9d81e691)



## 📚 Teknologi yang Digunakan

- **Kotlin** - Bahasa utama pengembangan aplikasi
- **Jetpack Components** - LiveData, ViewModel, Room
- **Google Maps SDK** - Menampilkan peta dan kendaraan
- **Coroutines** - Untuk menangani operasi asinkron

## 🛠 Arsitektur

Aplikasi ini dibangun dengan pendekatan **Clean Architecture** agar mudah diperluas dan dipelihara.

