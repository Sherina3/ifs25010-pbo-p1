import java.util.Locale;
import java.util.Scanner;

public class App {

    private static final String[] NAMA = {"Partisipatif", "Tugas", "Kuis", "Proyek", "UTS", "UAS"};
    private static final String[] SIMBOL = {"PA", "T", "K", "P", "UTS", "UAS"};
    private static final String PESAN_FORMAT =
            "Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai";

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

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int[] bobotAwal = new int[NAMA.length];
        int jumlahBobot = 0;
        for (int i = 0; i < bobotAwal.length; i++) {
            bobotAwal[i] = Integer.parseInt(input.nextLine().trim());
            jumlahBobot += bobotAwal[i];
        }

        if (jumlahBobot != 100) {
            System.out.println("Total bobot harus 100");
            return;
        }

        // akumulasi[0][i] = total bobot, akumulasi[1][i] = total perolehan
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

            perolehan = Math.max(0, Math.min(perolehan, bobot));

            akumulasi[0][indeks] += bobot;
            akumulasi[1][indeks] += perolehan;
        }

        System.out.println("Perolehan Nilai:");
        double nilaiAkhir = 0;
        for (int i = 0; i < NAMA.length; i++) {
            int persen = (akumulasi[0][i] == 0) ? 0 : (akumulasi[1][i] * 100) / akumulasi[0][i];
            double kontribusi = persen / 100.0 * bobotAwal[i];
            nilaiAkhir += kontribusi;
            System.out.println(String.format(Locale.US, ">> %s: %d/100 (%.2f/%d)",
                    NAMA[i], persen, kontribusi, bobotAwal[i]));
        }

        System.out.println();
        System.out.println(String.format(Locale.US, ">> Nilai Akhir: %.2f", nilaiAkhir));

        double dibulatkan = Math.round(nilaiAkhir * 100.0) / 100.0;
        System.out.println(">> Grade: " + tentukanGrade(dibulatkan));
    }
}