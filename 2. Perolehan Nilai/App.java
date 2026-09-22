import java.util.Locale;
import java.util.Scanner;

public class App {

    private static final String[] NAMA = {"Partisipatif", "Tugas", "Kuis", "Proyek", "UTS", "UAS"};
    private static final String[] SIMBOL = {"PA", "T", "K", "P", "UTS", "UAS"};
    private static final String PESAN_FORMAT =
            "Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int[] bobotAwal = bacaBobotAwal(input);
        int jumlahBobot = jumlahkan(bobotAwal);

        if (jumlahBobot != 100) {
            System.out.println("Total bobot harus 100");
            return;
        }

        // akumulasi[0][i] = total bobot yang sudah masuk, akumulasi[1][i] = total perolehan
        int[][] akumulasi = prosesBarisPenilaian(input);

        cetakPerolehanNilai(bobotAwal, akumulasi);
    }

    // Membaca 6 baris pertama sebagai bobot awal tiap komponen penilaian.
    private static int[] bacaBobotAwal(Scanner input) {
        int[] bobotAwal = new int[NAMA.length];
        for (int i = 0; i < bobotAwal.length; i++) {
            bobotAwal[i] = Integer.parseInt(input.nextLine().trim());
        }
        return bobotAwal;
    }

    private static int jumlahkan(int[] bobot) {
        int total = 0;
        for (int nilai : bobot) {
            total += nilai;
        }
        return total;
    }

    // Membaca baris "Simbol|Bobot|Perolehan" sampai menemukan "---",
    // sambil memvalidasi format, simbol, dan mengakumulasi bobot/perolehan per komponen.
    private static int[][] prosesBarisPenilaian(Scanner input) {
        int[][] akumulasi = new int[2][NAMA.length];

        while (input.hasNextLine()) {
            String baris = input.nextLine();
            if ("---".equals(baris.trim())) {
                break;
            }

            String[] bagian = baris.split("\\|", -1);
            if (bagian.length != 3) {
                System.out.println(PESAN_FORMAT);
                continue;
            }

            int bobot;
            int perolehan;
            try {
                bobot = Integer.parseInt(bagian[1].trim());
                perolehan = Integer.parseInt(bagian[2].trim());
            } catch (NumberFormatException e) {
                System.out.println(PESAN_FORMAT);
                continue;
            }

            int indeks = cariIndeks(bagian[0].trim());
            if (indeks < 0) {
                System.out.println("Simbol tidak dikenal");
                continue;
            }

            // Perolehan tidak boleh melebihi bobot ataupun bernilai negatif.
            perolehan = Math.max(0, Math.min(perolehan, bobot));

            akumulasi[0][indeks] += bobot;
            akumulasi[1][indeks] += perolehan;
        }

        return akumulasi;
    }

    private static int cariIndeks(String simbol) {
        for (int i = 0; i < SIMBOL.length; i++) {
            if (SIMBOL[i].equals(simbol)) {
                return i;
            }
        }
        return -1;
    }

    private static String tentukanGrade(double nilai) {
        double[] batas = {79.5, 72, 64.5, 57, 49.5, 34};
        String[] huruf = {"A", "AB", "B", "BC", "C", "D"};
        for (int i = 0; i < batas.length; i++) {
            if (nilai >= batas[i]) {
                return huruf[i];
            }
        }
        return "E";
    }

    // Menghitung persentase perolehan sebuah komponen sebagai double (bukan pembagian integer),
    // supaya bagian desimalnya tidak hilang sebelum dipakai menghitung kontribusi ke nilai akhir.
    // Contoh: perolehan 2 dari bobot 3 = 66.67%, bukan dibulatkan-turun menjadi 66% terlebih dahulu.
    private static double hitungPersentase(int totalPerolehan, int totalBobot) {
        if (totalBobot == 0) {
            return 0.0;
        }
        return totalPerolehan * 100.0 / totalBobot;
    }

    // Menghitung persentase & kontribusi tiap komponen, nilai akhir, lalu mencetak semuanya beserta grade.
    private static void cetakPerolehanNilai(int[] bobotAwal, int[][] akumulasi) {
        System.out.println("Perolehan Nilai:");
        double nilaiAkhir = 0;
        for (int i = 0; i < NAMA.length; i++) {
            double persen = hitungPersentase(akumulasi[1][i], akumulasi[0][i]);
            double kontribusi = persen / 100.0 * bobotAwal[i];
            nilaiAkhir += kontribusi;
            // Persentase ditampilkan sebagai bilangan bulat (dibulatkan ke bawah),
            // tetapi nilai desimalnya tetap dipakai utuh untuk perhitungan kontribusi di atas.
            System.out.println(String.format(Locale.US, ">> %s: %d/100 (%.2f/%d)",
                    NAMA[i], (int) persen, kontribusi, bobotAwal[i]));
        }

        System.out.println();
        System.out.println(String.format(Locale.US, ">> Nilai Akhir: %.2f", nilaiAkhir));

        double dibulatkan = Math.round(nilaiAkhir * 100.0) / 100.0;
        System.out.println(">> Grade: " + tentukanGrade(dibulatkan));
    }
}
