import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Map<Integer, Integer> frekuensi = new HashMap<>();
        boolean adaData = false;

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
            adaData = true;
        }

        if (!adaData) {
            return;
        }

        int tertinggi = Integer.MIN_VALUE;
        int terendah = Integer.MAX_VALUE;

        int terbanyakNilai = 0, terbanyakFrek = -1;
        int tersedikitNilai = 0, tersedikitFrek = Integer.MAX_VALUE;
        long jumlahTertinggiNilai = 0, jumlahTertinggiHasil = Long.MIN_VALUE;
        boolean jumlahTertinggiSet = false;
        long jumlahTerendahNilai = 0, jumlahTerendahHasil = Long.MAX_VALUE;
        boolean jumlahTerendahSet = false;

        for (Map.Entry<Integer, Integer> entry : frekuensi.entrySet()) {
            int nilai = entry.getKey();
            int frek = entry.getValue();

            if (nilai > tertinggi) tertinggi = nilai;
            if (nilai < terendah) terendah = nilai;

            // Terbanyak: frekuensi tertinggi, seri -> nilai lebih besar
            if (frek > terbanyakFrek || (frek == terbanyakFrek && nilai > terbanyakNilai)) {
                terbanyakFrek = frek;
                terbanyakNilai = nilai;
            }

            // Tersedikit: frekuensi terendah, seri -> nilai lebih kecil
            if (frek < tersedikitFrek || (frek == tersedikitFrek && nilai < tersedikitNilai)) {
                tersedikitFrek = frek;
                tersedikitNilai = nilai;
            }

            long hasil = (long) nilai * frek;

            // Jumlah Tertinggi: hasil terbesar, seri -> nilai lebih besar
            if (!jumlahTertinggiSet || hasil > jumlahTertinggiHasil
                    || (hasil == jumlahTertinggiHasil && nilai > jumlahTertinggiNilai)) {
                jumlahTertinggiHasil = hasil;
                jumlahTertinggiNilai = nilai;
                jumlahTertinggiSet = true;
            }

            // Jumlah Terendah: hasil terkecil, seri -> nilai lebih kecil
            if (!jumlahTerendahSet || hasil < jumlahTerendahHasil
                    || (hasil == jumlahTerendahHasil && nilai < jumlahTerendahNilai)) {
                jumlahTerendahHasil = hasil;
                jumlahTerendahNilai = nilai;
                jumlahTerendahSet = true;
            }
        }

        int terbanyakFrekAkhir = frekuensi.get(terbanyakNilai);
        int tersedikitFrekAkhir = frekuensi.get(tersedikitNilai);

        System.out.println("Tertinggi: " + tertinggi);
        System.out.println("Terendah: " + terendah);
        System.out.println("Terbanyak: " + terbanyakNilai + " (" + terbanyakFrekAkhir + "x)");
        System.out.println("Tersedikit: " + tersedikitNilai + " (" + tersedikitFrekAkhir + "x)");
        System.out.println("Jumlah Tertinggi: " + jumlahTertinggiNilai + " * " + frekuensi.get((int) jumlahTertinggiNilai) + " = " + jumlahTertinggiHasil);
        System.out.println("Jumlah Terendah: " + jumlahTerendahNilai + " * " + frekuensi.get((int) jumlahTerendahNilai) + " = " + jumlahTerendahHasil);
    }
}
