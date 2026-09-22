import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String jamAwalStr = sc.hasNextLine() ? sc.nextLine().trim() : "";

        String[] bagian = jamAwalStr.split(":");
        if (bagian.length != 2) {
            System.out.println("Jam tidak valid");
            return;
        }

        int jam, menit;
        try {
            jam = Integer.parseInt(bagian[0].trim());
            menit = Integer.parseInt(bagian[1].trim());
        } catch (NumberFormatException e) {
            System.out.println("Jam tidak valid");
            return;
        }

        if (jam < 0 || jam > 23 || menit < 0 || menit > 59) {
            System.out.println("Jam tidak valid");
            return;
        }

        int totalMenitAwal = jam * 60 + menit;
        int totalMenitSekarang = totalMenitAwal;
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

            while (totalMenitSekarang >= 1440) {
                totalMenitSekarang -= 1440;
                pergantianHari++;
            }
            while (totalMenitSekarang < 0) {
                totalMenitSekarang += 1440;
                pergantianHari++;
            }
        }

        int jamAkhir = totalMenitSekarang / 60;
        int menitAkhir = totalMenitSekarang % 60;

        String totalMenitStr;
        if (totalGeser > 0) {
            totalMenitStr = "+" + totalGeser;
        } else if (totalGeser == 0) {
            totalMenitStr = "0";
        } else {
            totalMenitStr = String.valueOf(totalGeser);
        }

        System.out.printf("Jam Awal: %02d:%02d%n", jam, menit);
        System.out.printf("Jam Akhir: %02d:%02d%n", jamAkhir, menitAkhir);
        System.out.println("Total Menit: " + totalMenitStr);
        System.out.println("Pergantian Hari: " + pergantianHari);
    }
}
