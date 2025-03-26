# 🚗 Mini Fleet Tracker

Selamat datang di **Mini Fleet Tracker**! 🚀 Aplikasi ini mensimulasikan pelacakan kendaraan dengan data sensor IoT secara real-time.

## 📜 Deskripsi

Mini Fleet Tracker adalah aplikasi Android yang mensimulasikan sistem manajemen armada kendaraan. Aplikasi ini menampilkan lokasi kendaraan di peta serta berbagai data sensor seperti kecepatan, status mesin, dan status pintu.

## ✨ Fitur Utama

✅ **Live Vehicle Map View**: Menampilkan lokasi kendaraan pada peta secara real-time.
✅ **Simulasi Data IoT**: Status mesin, status pintu, dan kecepatan kendaraan.
✅ **Dashboard Real-Time**: Informasi kendaraan ditampilkan dengan pembaruan berkala.
✅ **Peringatan Otomatis**: Notifikasi jika kecepatan melebihi batas atau pintu terbuka saat kendaraan bergerak.
✅ **Dukungan Arsitektur Bersih**: Menggunakan MVVM untuk pemisahan logika bisnis dan UI.

## 🔧 Instalasi dan Konfigurasi

1. Clone repository ini:
   ```sh
   git clone https://github.com/your-repo-url
   ```
2. Buka proyek di Android Studio.
3. Tambahkan kunci API Google Maps di `local.properties`:
   ```properties
   MAPS_API_KEY=AIzaSyALzdQOtH50TNc_90iVDOtAGYpIRiY7qw4
   MAPS_API_BASE_URL=https://maps.googleapis.com/maps/api/
   ```
4. Jalankan aplikasi di emulator atau perangkat nyata.

## 🔑 Kredensial Login

Untuk masuk ke aplikasi, gunakan akun berikut:
- **Username**: `username`
- **Password**: `pass123`

## 📸 Screenshot

![Tampilan Peta](https://via.placeholder.com/600x300)  
_Gambar tampilan peta aplikasi_

![Dashboard Sensor](https://via.placeholder.com/600x300)  
_Gambar tampilan dashboard sensor_

## 📚 Teknologi yang Digunakan

- **Kotlin** - Bahasa utama pengembangan aplikasi
- **Jetpack Components** - LiveData, ViewModel, Room
- **Google Maps SDK** - Menampilkan peta dan kendaraan
- **Coroutines** - Untuk menangani operasi asinkron
- **Retrofit/Moshi** - Untuk pengolahan data jika diperlukan

## 🛠 Arsitektur

Aplikasi ini dibangun dengan pendekatan **MVVM** agar mudah diperluas dan dipelihara.

## 📜 Lisensi

MIT License. Lihat file [LICENSE](LICENSE) untuk informasi lebih lanjut.

🚀 Selamat Coding! 🎉

