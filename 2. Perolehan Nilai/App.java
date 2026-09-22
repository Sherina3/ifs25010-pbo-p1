import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int bobotPA = Integer.parseInt(sc.nextLine().trim());
        int bobotT = Integer.parseInt(sc.nextLine().trim());
        int bobotK = Integer.parseInt(sc.nextLine().trim());
        int bobotP = Integer.parseInt(sc.nextLine().trim());
        int bobotUTS = Integer.parseInt(sc.nextLine().trim());
        int bobotUAS = Integer.parseInt(sc.nextLine().trim());

        int totalBobotAwal = bobotPA + bobotT + bobotK + bobotP + bobotUTS + bobotUAS;
        if (totalBobotAwal != 100) {
            System.out.println("Total bobot harus 100");
            return;
        }

        int totalPA = 0, totalT = 0, totalK = 0, totalP = 0, totalUTS = 0, totalUAS = 0;
        int perolehanPA = 0, perolehanT = 0, perolehanK = 0, perolehanP = 0, perolehanUTS = 0, perolehanUAS = 0;

        while (sc.hasNextLine()) {
            String baris = sc.nextLine();
            if (baris.trim().equals("---")) {
                break;
            }

            String[] bagian = baris.split("\\|");
            if (bagian.length != 3) {
                System.out.println("Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai");
                continue;
            }

            String simbol = bagian[0].trim();
            String bobotStr = bagian[1].trim();
            String perolehanStr = bagian[2].trim();

            int bobot;
            int perolehan;
            try {
                bobot = Integer.parseInt(bobotStr);
                perolehan = Integer.parseInt(perolehanStr);
            } catch (NumberFormatException e) {
                System.out.println("Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai");
                continue;
            }

            if (perolehan > bobot) {
                perolehan = bobot;
            }
            if (perolehan < 0) {
                perolehan = 0;
            }

            switch (simbol) {
                case "PA":
                    totalPA += bobot;
                    perolehanPA += perolehan;
                    break;
                case "T":
                    totalT += bobot;
                    perolehanT += perolehan;
                    break;
                case "K":
                    totalK += bobot;
                    perolehanK += perolehan;
                    break;
                case "P":
                    totalP += bobot;
                    perolehanP += perolehan;
                    break;
                case "UTS":
                    totalUTS += bobot;
                    perolehanUTS += perolehan;
                    break;
                case "UAS":
                    totalUAS += bobot;
                    perolehanUAS += perolehan;
                    break;
                default:
                    System.out.println("Simbol tidak dikenal");
                    break;
            }
        }

        double kontribusiPA = kontribusi(perolehanPA, totalPA, bobotPA);
        double kontribusiT = kontribusi(perolehanT, totalT, bobotT);
        double kontribusiK = kontribusi(perolehanK, totalK, bobotK);
        double kontribusiP = kontribusi(perolehanP, totalP, bobotP);
        double kontribusiUTS = kontribusi(perolehanUTS, totalUTS, bobotUTS);
        double kontribusiUAS = kontribusi(perolehanUAS, totalUAS, bobotUAS);

        double nilaiAkhir = kontribusiPA + kontribusiT + kontribusiK + kontribusiP + kontribusiUTS + kontribusiUAS;

        System.out.println("Perolehan Nilai:");
        System.out.printf(">> Partisipatif: %d/100 (%.2f/%d)%n", persen(perolehanPA, totalPA), kontribusiPA, bobotPA);
        System.out.printf(">> Tugas: %d/100 (%.2f/%d)%n", persen(perolehanT, totalT), kontribusiT, bobotT);
        System.out.printf(">> Kuis: %d/100 (%.2f/%d)%n", persen(perolehanK, totalK), kontribusiK, bobotK);
        System.out.printf(">> Proyek: %d/100 (%.2f/%d)%n", persen(perolehanP, totalP), kontribusiP, bobotP);
        System.out.printf(">> UTS: %d/100 (%.2f/%d)%n", persen(perolehanUTS, totalUTS), kontribusiUTS, bobotUTS);
        System.out.printf(">> UAS: %d/100 (%.2f/%d)%n", persen(perolehanUAS, totalUAS), kontribusiUAS, bobotUAS);
        System.out.println();
        System.out.printf(">> Nilai Akhir: %.2f%n", nilaiAkhir);
        System.out.println(">> Grade: " + grade(nilaiAkhir));
    }

    static int persen(int perolehan, int total) {
        if (total == 0) return 0;
        return (perolehan * 100) / total;
    }

    static double kontribusi(int perolehan, int total, int bobot) {
        int p100 = persen(perolehan, total);
        return (p100 / 100.0) * bobot;
    }

    static String grade(double nilai) {
        if (nilai >= 79.5) return "A";
        if (nilai >= 72) return "AB";
        if (nilai >= 64.5) return "B";
        if (nilai >= 57) return "BC";
        if (nilai >= 49.5) return "C";
        if (nilai >= 34) return "D";
        return "E";
    }
}
