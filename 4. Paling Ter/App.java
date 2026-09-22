import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Map<Integer, Integer> frekuensi = bacaFrekuensi(sc);
        if (frekuensi.isEmpty()) {
            return;
        }

        HasilAnalisis hasil = analisisFrekuensi(frekuensi);
        cetakHasil(hasil, frekuensi);
    }

    // Membaca setiap baris angka sampai "---", lalu menghitung berapa kali tiap angka muncul.
    // Baris kosong diabaikan.
    private static Map<Integer, Integer> bacaFrekuensi(Scanner sc) {
        Map<Integer, Integer> frekuensi = new HashMap<>();

        while (sc.hasNextLine()) {
            String baris = sc.nextLine().trim();
            if (baris.equals("---")) {
                break;
            }
            if (baris.isEmpty()) {
                continue;
            }
            int nilai = Integer.parseInt(baris);
            frekuensi.merge(nilai, 1, Integer::sum);
        }

        return frekuensi;
    }

    // Wadah sederhana untuk seluruh hasil analisis, supaya bisa dikembalikan sebagai satu objek.
    private static class HasilAnalisis {
        int tertinggi;
        int terendah;
        int terbanyakNilai;
        int tersedikitNilai;
        long jumlahTertinggiNilai;
        long jumlahTertinggiHasil;
        long jumlahTerendahNilai;
        long jumlahTerendahHasil;
    }

    private static HasilAnalisis analisisFrekuensi(Map<Integer, Integer> frekuensi) {
        HasilAnalisis hasil = new HasilAnalisis();

        hasil.tertinggi = Integer.MIN_VALUE;
        hasil.terendah = Integer.MAX_VALUE;

        int terbanyakFrek = -1;
        int tersedikitFrek = Integer.MAX_VALUE;
        long jumlahTertinggiHasil = Long.MIN_VALUE;
        boolean jumlahTertinggiSet = false;
        long jumlahTerendahHasil = Long.MAX_VALUE;
        boolean jumlahTerendahSet = false;

        for (Map.Entry<Integer, Integer> entry : frekuensi.entrySet()) {
            int nilai = entry.getKey();
            int frek = entry.getValue();

            if (nilai > hasil.tertinggi) hasil.tertinggi = nilai;
            if (nilai < hasil.terendah) hasil.terendah = nilai;

            // Aturan tie-breaking "Terbanyak": pemenang adalah frekuensi tertinggi.
            // Jika ada 2 angka atau lebih dengan frekuensi yang sama tingginya,
            // yang dipilih adalah angka (nilai) yang LEBIH BESAR.
            if (frek > terbanyakFrek || (frek == terbanyakFrek && nilai > hasil.terbanyakNilai)) {
                terbanyakFrek = frek;
                hasil.terbanyakNilai = nilai;
            }

            // Aturan tie-breaking "Tersedikit": pemenang adalah frekuensi terendah.
            // Jika seri, yang dipilih adalah angka (nilai) yang LEBIH KECIL
            // (kebalikan dari aturan "Terbanyak" di atas).
            if (frek < tersedikitFrek || (frek == tersedikitFrek && nilai < hasil.tersedikitNilai)) {
                tersedikitFrek = frek;
                hasil.tersedikitNilai = nilai;
            }

            long hasilKali = (long) nilai * frek;

            // Aturan tie-breaking "Jumlah Tertinggi": pemenang adalah hasil (nilai x frekuensi) terbesar.
            // Jika seri, yang dipilih adalah angka (nilai) yang LEBIH BESAR.
            if (!jumlahTertinggiSet || hasilKali > jumlahTertinggiHasil
                    || (hasilKali == jumlahTertinggiHasil && nilai > hasil.jumlahTertinggiNilai)) {
                jumlahTertinggiHasil = hasilKali;
                hasil.jumlahTertinggiNilai = nilai;
                jumlahTertinggiSet = true;
            }

            // Aturan tie-breaking "Jumlah Terendah": pemenang adalah hasil (nilai x frekuensi) terkecil.
            // Jika seri, yang dipilih adalah angka (nilai) yang LEBIH KECIL.
            if (!jumlahTerendahSet || hasilKali < jumlahTerendahHasil
                    || (hasilKali == jumlahTerendahHasil && nilai < hasil.jumlahTerendahNilai)) {
                jumlahTerendahHasil = hasilKali;
                hasil.jumlahTerendahNilai = nilai;
                jumlahTerendahSet = true;
            }
        }

        hasil.jumlahTertinggiHasil = jumlahTertinggiHasil;
        hasil.jumlahTerendahHasil = jumlahTerendahHasil;

        return hasil;
    }

    private static void cetakHasil(HasilAnalisis hasil, Map<Integer, Integer> frekuensi) {
        int terbanyakFrekAkhir = frekuensi.get(hasil.terbanyakNilai);
        int tersedikitFrekAkhir = frekuensi.get(hasil.tersedikitNilai);
        int frekJumlahTertinggi = frekuensi.get((int) hasil.jumlahTertinggiNilai);
        int frekJumlahTerendah = frekuensi.get((int) hasil.jumlahTerendahNilai);

        System.out.println("Tertinggi: " + hasil.tertinggi);
        System.out.println("Terendah: " + hasil.terendah);
        System.out.println("Terbanyak: " + hasil.terbanyakNilai + " (" + terbanyakFrekAkhir + "x)");
        System.out.println("Tersedikit: " + hasil.tersedikitNilai + " (" + tersedikitFrekAkhir + "x)");
        System.out.println("Jumlah Tertinggi: " + hasil.jumlahTertinggiNilai + " * " + frekJumlahTertinggi
                + " = " + hasil.jumlahTertinggiHasil);
        System.out.println("Jumlah Terendah: " + hasil.jumlahTerendahNilai + " * " + frekJumlahTerendah
                + " = " + hasil.jumlahTerendahHasil);
    }
}
