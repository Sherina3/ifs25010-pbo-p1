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

        int tertinggi = hitungTertinggi(frekuensi);
        int terendah = hitungTerendah(frekuensi);
        int terbanyakNilai = hitungTerbanyak(frekuensi);
        int tersedikitNilai = hitungTersedikit(frekuensi);
        // index 0 = nilai, index 1 = hasil (nilai * frekuensi)
        long[] jumlahTertinggi = hitungJumlahTertinggi(frekuensi);
        long[] jumlahTerendah = hitungJumlahTerendah(frekuensi);

        cetakHasil(frekuensi, tertinggi, terendah, terbanyakNilai, tersedikitNilai,
                jumlahTertinggi, jumlahTerendah);
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

    private static int hitungTertinggi(Map<Integer, Integer> frekuensi) {
        int tertinggi = Integer.MIN_VALUE;
        for (int nilai : frekuensi.keySet()) {
            if (nilai > tertinggi) {
                tertinggi = nilai;
            }
        }
        return tertinggi;
    }

    private static int hitungTerendah(Map<Integer, Integer> frekuensi) {
        int terendah = Integer.MAX_VALUE;
        for (int nilai : frekuensi.keySet()) {
            if (nilai < terendah) {
                terendah = nilai;
            }
        }
        return terendah;
    }

    // Aturan tie-breaking "Terbanyak": pemenang adalah frekuensi tertinggi.
    // Jika ada 2 angka atau lebih dengan frekuensi yang sama tingginya,
    // yang dipilih adalah angka (nilai) yang LEBIH BESAR.
    private static int hitungTerbanyak(Map<Integer, Integer> frekuensi) {
        int terbanyakNilai = 0;
        int terbanyakFrek = -1;
        for (Map.Entry<Integer, Integer> entry : frekuensi.entrySet()) {
            int nilai = entry.getKey();
            int frek = entry.getValue();
            if (frek > terbanyakFrek || (frek == terbanyakFrek && nilai > terbanyakNilai)) {
                terbanyakFrek = frek;
                terbanyakNilai = nilai;
            }
        }
        return terbanyakNilai;
    }

    // Aturan tie-breaking "Tersedikit": pemenang adalah frekuensi terendah.
    // Jika seri, yang dipilih adalah angka (nilai) yang LEBIH KECIL
    // (kebalikan dari aturan "Terbanyak" di atas).
    private static int hitungTersedikit(Map<Integer, Integer> frekuensi) {
        int tersedikitNilai = 0;
        int tersedikitFrek = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : frekuensi.entrySet()) {
            int nilai = entry.getKey();
            int frek = entry.getValue();
            if (frek < tersedikitFrek || (frek == tersedikitFrek && nilai < tersedikitNilai)) {
                tersedikitFrek = frek;
                tersedikitNilai = nilai;
            }
        }
        return tersedikitNilai;
    }

    // Aturan tie-breaking "Jumlah Tertinggi": pemenang adalah hasil (nilai x frekuensi) terbesar.
    // Jika seri, yang dipilih adalah angka (nilai) yang LEBIH BESAR.
    // Hasil dikembalikan sebagai array {nilai, hasil}.
    private static long[] hitungJumlahTertinggi(Map<Integer, Integer> frekuensi) {
        long nilaiTerpilih = 0;
        long hasilTerpilih = Long.MIN_VALUE;
        boolean sudahDiset = false;
        for (Map.Entry<Integer, Integer> entry : frekuensi.entrySet()) {
            long nilai = entry.getKey();
            long frek = entry.getValue();
            long hasil = nilai * frek;
            if (!sudahDiset || hasil > hasilTerpilih || (hasil == hasilTerpilih && nilai > nilaiTerpilih)) {
                hasilTerpilih = hasil;
                nilaiTerpilih = nilai;
                sudahDiset = true;
            }
        }
        return new long[]{nilaiTerpilih, hasilTerpilih};
    }

    // Aturan tie-breaking "Jumlah Terendah": pemenang adalah hasil (nilai x frekuensi) terkecil.
    // Jika seri, yang dipilih adalah angka (nilai) yang LEBIH KECIL.
    // Hasil dikembalikan sebagai array {nilai, hasil}.
    private static long[] hitungJumlahTerendah(Map<Integer, Integer> frekuensi) {
        long nilaiTerpilih = 0;
        long hasilTerpilih = Long.MAX_VALUE;
        boolean sudahDiset = false;
        for (Map.Entry<Integer, Integer> entry : frekuensi.entrySet()) {
            long nilai = entry.getKey();
            long frek = entry.getValue();
            long hasil = nilai * frek;
            if (!sudahDiset || hasil < hasilTerpilih || (hasil == hasilTerpilih && nilai < nilaiTerpilih)) {
                hasilTerpilih = hasil;
                nilaiTerpilih = nilai;
                sudahDiset = true;
            }
        }
        return new long[]{nilaiTerpilih, hasilTerpilih};
    }

    private static void cetakHasil(Map<Integer, Integer> frekuensi, int tertinggi, int terendah,
            int terbanyakNilai, int tersedikitNilai, long[] jumlahTertinggi, long[] jumlahTerendah) {

        int terbanyakFrek = frekuensi.get(terbanyakNilai);
        int tersedikitFrek = frekuensi.get(tersedikitNilai);
        int frekJumlahTertinggi = frekuensi.get((int) jumlahTertinggi[0]);
        int frekJumlahTerendah = frekuensi.get((int) jumlahTerendah[0]);

        System.out.println("Tertinggi: " + tertinggi);
        System.out.println("Terendah: " + terendah);
        System.out.println("Terbanyak: " + terbanyakNilai + " (" + terbanyakFrek + "x)");
        System.out.println("Tersedikit: " + tersedikitNilai + " (" + tersedikitFrek + "x)");
        System.out.println("Jumlah Tertinggi: " + jumlahTertinggi[0] + " * " + frekJumlahTertinggi
                + " = " + jumlahTertinggi[1]);
        System.out.println("Jumlah Terendah: " + jumlahTerendah[0] + " * " + frekJumlahTerendah
                + " = " + jumlahTerendah[1]);
    }
}
