import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = bacaUkuran(sc);
        int[][] matrix = bacaMatrix(sc, n);

        if (n == 1 || n == 2) {
            cetakKasusKecil(matrix, n);
            return;
        }

        int nilaiL = hitungNilaiL(matrix, n);
        int nilaiKebalikanL = hitungNilaiKebalikanL(matrix, n);
        int nilaiTengah = hitungNilaiTengah(matrix, n);
        int perbedaan = Math.abs(nilaiL - nilaiKebalikanL);
        int dominan = tentukanDominan(nilaiL, nilaiKebalikanL, nilaiTengah, perbedaan);

        cetakHasil(nilaiL, nilaiKebalikanL, nilaiTengah, perbedaan, dominan);
    }

    private static int bacaUkuran(Scanner sc) {
        return Integer.parseInt(sc.nextLine().trim());
    }

    private static int[][] bacaMatrix(Scanner sc, int n) {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] token = sc.nextLine().trim().split("\\s+");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(token[j]);
            }
        }
        return matrix;
    }

    // Untuk n=1 dan n=2, matriks tidak punya bentuk L/kebalikan-L yang bermakna,
    // sehingga semua nilai (Tengah & Dominan) dianggap sama dengan total isi matriks.
    private static void cetakKasusKecil(int[][] matrix, int n) {
        int total = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                total += matrix[i][j];
            }
        }
        System.out.println("Nilai L: Tidak Ada");
        System.out.println("Nilai Kebalikan L: Tidak Ada");
        System.out.println("Nilai Tengah: " + total);
        System.out.println("Perbedaan: Tidak Ada");
        System.out.println("Dominan: " + total);
    }

    // Nilai L: seluruh kolom pertama + baris terakhir (tanpa kolom pertama & pojok kanan bawah)
    private static int hitungNilaiL(int[][] matrix, int n) {
        int nilaiL = 0;
        for (int i = 0; i < n; i++) {
            nilaiL += matrix[i][0];
        }
        for (int j = 1; j < n - 1; j++) {
            nilaiL += matrix[n - 1][j];
        }
        return nilaiL;
    }

    // Nilai Kebalikan L: seluruh kolom terakhir + baris pertama (tanpa pojok kiri atas & kolom terakhir)
    private static int hitungNilaiKebalikanL(int[][] matrix, int n) {
        int nilaiKebalikanL = 0;
        for (int i = 0; i < n; i++) {
            nilaiKebalikanL += matrix[i][n - 1];
        }
        for (int j = 1; j < n - 1; j++) {
            nilaiKebalikanL += matrix[0][j];
        }
        return nilaiKebalikanL;
    }

    // Untuk n ganjil, nilai tengah adalah 1 sel di pusat matriks.
    // Untuk n genap, tidak ada sel pusat tunggal, sehingga dijumlahkan 4 sel yang mengelilingi pusat.
    private static int hitungNilaiTengah(int[][] matrix, int n) {
        if (n % 2 == 1) {
            return matrix[n / 2][n / 2];
        }
        int mid = n / 2;
        return matrix[mid - 1][mid - 1] + matrix[mid - 1][mid]
                + matrix[mid][mid - 1] + matrix[mid][mid];
    }

    // Jika Nilai L dan Kebalikan L sama (perbedaan = 0), Dominan diambil dari Nilai Tengah.
    // Jika berbeda, Dominan adalah yang lebih besar di antara Nilai L dan Kebalikan L.
    private static int tentukanDominan(int nilaiL, int nilaiKebalikanL, int nilaiTengah, int perbedaan) {
        if (perbedaan == 0) {
            return nilaiTengah;
        }
        return Math.max(nilaiL, nilaiKebalikanL);
    }

    private static void cetakHasil(int nilaiL, int nilaiKebalikanL, int nilaiTengah, int perbedaan, int dominan) {
        System.out.println("Nilai L: " + nilaiL);
        System.out.println("Nilai Kebalikan L: " + nilaiKebalikanL);
        System.out.println("Nilai Tengah: " + nilaiTengah);
        System.out.println("Perbedaan: " + perbedaan);
        System.out.println("Dominan: " + dominan);
    }
}
