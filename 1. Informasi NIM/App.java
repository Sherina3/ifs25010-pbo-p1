import java.util.Scanner;

public class App {

    static String[] prefixKode = {
        "11S", "12S", "13S", "21S", "22S", "31S", "32S", "114", "113", "133"
    };
    static String[] prefixNama = {
        "Sarjana Informatika",
        "Sarjana Sistem Informasi",
        "Sarjana Teknik Elektro",
        "Sarjana Manajemen Rekayasa",
        "Sarjana Teknik Metalurgi",
        "Sarjana Teknik Bioproses",
        "Sarjana Bioteknologi",
        "Diploma 4 Teknologi Rekayasa Perangkat Lunak",
        "Diploma 3 Teknologi Informasi",
        "Diploma 3 Teknologi Komputer"
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nim = bacaNim(sc);

        if (!validasiPanjangNim(nim)) {
            System.out.println("NIM harus 8 karakter");
            return;
        }

        String namaProdi = cariNamaProdi(nim);
        if (namaProdi == null) {
            System.out.println("Kode tidak tersedia");
            return;
        }

        // Karakter ke-4 s.d. ke-8 (kode angkatan + nomor urut) wajib berupa digit,
        // supaya tidak terjadi crash saat di-parse menjadi angka.
        if (!validasiDigitAngkatanDanUrutan(nim)) {
            System.out.println("Kode angkatan dan urutan pada NIM harus berupa angka");
            return;
        }

        int angkatan = parseAngkatan(nim);
        int urutan = parseUrutan(nim);

        cetakInformasiNim(nim, namaProdi, angkatan, urutan);
    }

    // Membaca satu baris input NIM dari Scanner.
    private static String bacaNim(Scanner sc) {
        return sc.hasNextLine() ? sc.nextLine() : "";
    }

    // NIM valid hanya jika panjangnya tepat 8 karakter.
    private static boolean validasiPanjangNim(String nim) {
        return nim.length() == 8;
    }

    // Mencocokkan 3 karakter pertama NIM dengan daftar kode prodi.
    private static String cariNamaProdi(String nim) {
        String prefix = nim.substring(0, 3);
        for (int i = 0; i < prefixKode.length; i++) {
            if (prefixKode[i].equals(prefix)) {
                return prefixNama[i];
            }
        }
        return null;
    }

    // Mengecek apakah 5 karakter terakhir NIM (indeks 3-7) semuanya digit 0-9.
    private static boolean validasiDigitAngkatanDanUrutan(String nim) {
        for (int i = 3; i < nim.length(); i++) {
            if (!Character.isDigit(nim.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // Karakter ke-4 dan ke-5 adalah 2 digit terakhir tahun angkatan (diasumsikan 20xx).
    private static int parseAngkatan(String nim) {
        String kodeAngkatan = nim.substring(3, 5);
        return Integer.parseInt("20" + kodeAngkatan);
    }

    // Karakter ke-6 sampai ke-8 adalah nomor urut mahasiswa.
    private static int parseUrutan(String nim) {
        return Integer.parseInt(nim.substring(5, 8));
    }

    private static void cetakInformasiNim(String nim, String namaProdi, int angkatan, int urutan) {
        System.out.println("Informasi NIM " + nim + ": ");
        System.out.println(">> Program Studi: " + namaProdi);
        System.out.println(">> Angkatan: " + angkatan);
        System.out.println(">> Urutan: " + urutan);
    }
}
