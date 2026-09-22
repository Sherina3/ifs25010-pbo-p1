import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String jamAwalStr = sc.hasNextLine() ? sc.nextLine().trim() : "";

        int[] jamMenitAwal = parseJamAwal(jamAwalStr);
        if (jamMenitAwal == null) {
            System.out.println("Jam tidak valid");
            return;
        }
        int jam = jamMenitAwal[0];
        int menit = jamMenitAwal[1];

        // index 0 = totalMenitSekarang, index 1 = totalGeser, index 2 = pergantianHari
        int[] hasil = prosesPerintahPergeseran(sc, jam, menit);

        cetakHasil(jam, menit, hasil);
    }

    // Mem-parsing string "JJ:MM" menjadi {jam, menit}, atau null jika formatnya tidak valid
    // (bukan 2 bagian, bukan angka, atau di luar rentang 00:00-23:59).
    private static int[] parseJamAwal(String jamAwalStr) {
        String[] bagian = jamAwalStr.split(":");
        if (bagian.length != 2) {
            return null;
        }

        int jam, menit;
        try {
            jam = Integer.parseInt(bagian[0].trim());
            menit = Integer.parseInt(bagian[1].trim());
        } catch (NumberFormatException e) {
            return null;
        }

        if (jam < 0 || jam > 23 || menit < 0 || menit > 59) {
            return null;
        }

        return new int[]{jam, menit};
    }

    // Membaca perintah pergeseran ("+N" / "-N") satu per satu sampai "---",
    // lalu mengakumulasi pergeserannya ke waktu berjalan.
    // Mengembalikan array {totalMenitSekarang, totalGeser, pergantianHari}.
    private static int[] prosesPerintahPergeseran(Scanner sc, int jam, int menit) {
        int totalMenitSekarang = jam * 60 + menit;
        int totalGeser = 0;
        int pergantianHari = 0;

        while (sc.hasNextLine()) {
            String baris = sc.nextLine().trim();
            if (baris.equals("---")) {
                break;
            }
            if (baris.isEmpty()) {
                continue;
            }

            if (baris.length() < 2 || (baris.charAt(0) != '+' && baris.charAt(0) != '-')) {
                System.out.println("Perintah tidak valid");
                continue;
            }

            String sisaAngka = baris.substring(1);
            int n;
            try {
                n = Integer.parseInt(sisaAngka);
            } catch (NumberFormatException e) {
                System.out.println("Perintah tidak valid");
                continue;
            }

            int geser = (baris.charAt(0) == '+') ? n : -n;
            totalMenitSekarang += geser;
            totalGeser += geser;

            // Waktu berjalan disimpan dalam total menit sejak 00:00 dan bisa melewati batas
            // 1 hari (1440 menit) ke arah manapun. normalisasiWaktu "membungkus" (wrap-around)
            // nilai tersebut kembali ke rentang 0-1439, sambil mengembalikan berapa kali
            // pergantian hari terjadi (maju bila total >= 1440, mundur bila total < 0).
            int[] hasilNormalisasi = normalisasiWaktu(totalMenitSekarang);
            totalMenitSekarang = hasilNormalisasi[0];
            pergantianHari += hasilNormalisasi[1];
        }

        return new int[]{totalMenitSekarang, totalGeser, pergantianHari};
    }

    // Mengembalikan array {totalMenitTernormalisasi, jumlahPergantianHari} untuk satu nilai total menit.
    private static int[] normalisasiWaktu(int totalMenit) {
        int pergantianHari = 0;
        while (totalMenit >= 1440) {
            totalMenit -= 1440;
            pergantianHari++;
        }
        while (totalMenit < 0) {
            totalMenit += 1440;
            pergantianHari++;
        }
        return new int[]{totalMenit, pergantianHari};
    }

    // Format total pergeseran menit dengan tanda eksplisit: "+N" jika maju, "-N" jika mundur, "0" jika tidak berubah.
    private static String formatTotalMenit(int totalGeser) {
        if (totalGeser > 0) {
            return "+" + totalGeser;
        } else if (totalGeser == 0) {
            return "0";
        } else {
            return String.valueOf(totalGeser);
        }
    }

    private static void cetakHasil(int jam, int menit, int[] hasil) {
        int totalMenitSekarang = hasil[0];
        int totalGeser = hasil[1];
        int pergantianHari = hasil[2];

        int jamAkhir = totalMenitSekarang / 60;
        int menitAkhir = totalMenitSekarang % 60;

        System.out.printf("Jam Awal: %02d:%02d%n", jam, menit);
        System.out.printf("Jam Akhir: %02d:%02d%n", jamAkhir, menitAkhir);
        System.out.println("Total Menit: " + formatTotalMenit(totalGeser));
        System.out.println("Pergantian Hari: " + pergantianHari);
    }
}
