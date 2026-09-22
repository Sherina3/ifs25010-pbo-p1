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
        String nim = sc.hasNextLine() ? sc.nextLine() : "";

        if (nim.length() != 8) {
            System.out.println("NIM harus 8 karakter");
            return;
        }

        String prefix = nim.substring(0, 3);
        String namaProdi = null;
        for (int i = 0; i < prefixKode.length; i++) {
            if (prefixKode[i].equals(prefix)) {
                namaProdi = prefixNama[i];
                break;
            }
        }

        if (namaProdi == null) {
            System.out.println("Kode tidak tersedia");
            return;
        }

        String kodeAngkatan = nim.substring(3, 5);
        int angkatan = Integer.parseInt("20" + kodeAngkatan);
        int urutan = Integer.parseInt(nim.substring(5, 8));

        System.out.println("Informasi NIM " + nim + ": ");
        System.out.println(">> Program Studi: " + namaProdi);
        System.out.println(">> Angkatan: " + angkatan);
        System.out.println(">> Urutan: " + urutan);
    }
}
