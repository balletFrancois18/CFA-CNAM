import java.io.*; // Pour l'export et l'import (Exo 9 et 10)
import java.util.Scanner;

public class GraphesTP1 {

    // --- EXO 1 : ORIENTATION ---
    // On vérifie si la matrice est symétrique
    public static boolean estOriente(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if (mat[i][j] != mat[j][i]) return true;
            }
        }
        return false;
    }

    // --- EXO 2 : VALUÉ ---
    // Est-ce qu'il y a des nombres plus grands que 1 ?
    public static boolean estValue(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if (mat[i][j] > 1) return true;
            }
        }
        return false;
    }

    // --- EXO 3 : DEGRÉS ---
    public static int degreSortant(int[][] mat, int s) {
        int d = 0;
        for (int j = 0; j < mat.length; j++) if (mat[s][j] > 0) d++;
        return d;
    }

    public static int degreEntrant(int[][] mat, int s) {
        int d = 0;
        for (int i = 0; i < mat.length; i++) if (mat[i][s] > 0) d++;
        return d;
    }

    // --- EXO 4 : MIN / MAX ---
    public static int degreSortantMin(int[][] mat) {
        int min = mat.length;
        for (int i = 0; i < mat.length; i++) {
            int d = degreSortant(mat, i);
            if (d < min) min = d;
        }
        return min;
    }

    public static int degreSortantMax(int[][] mat) {
        int max = 0;
        for (int i = 0; i < mat.length; i++) {
            int d = degreSortant(mat, i);
            if (d > max) max = d;
        }
        return max;
    }

    // --- EXO 5 : LISTER SUCCESSEURS ---
    public static void afficherSuccesseurs(int[][] mat, int s) {
        System.out.print("Successeurs de x" + (s+1) + " : ");
        for (int j = 0; j < mat.length; j++) {
            if (mat[s][j] > 0) System.out.print("x" + (j+1) + " ");
        }
        System.out.println();
    }

    // --- EXO 6 : VOISIN COMMUN ---
    // Est-ce que s1 et s2 pointent vers le même sommet k ?
    public static boolean voisinCommun(int[][] mat, int s1, int s2) {
        for (int k = 0; k < mat.length; k++) {
            if (mat[s1][k] > 0 && mat[s2][k] > 0) return true;
        }
        return false;
    }

    // --- EXO 7 : ENTRÉE ET SORTIE ---
    // Une entrée : personne ne pointe vers lui (degEntrant = 0)
    public static boolean existeEntree(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            if (degreEntrant(mat, i) == 0) return true;
        }
        return false;
    }
    // Une sortie : il ne pointe vers personne (degSortant = 0)
    public static boolean existeSortie(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            if (degreSortant(mat, i) == 0) return true;
        }
        return false;
    }

    // --- EXO 8 : SOMMET UNIVERSEL ---
    // Un sommet qui pointe vers TOUS les autres (sauf lui-même)
    public static boolean estUniversel(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            if (degreSortant(mat, i) == mat.length - 1) return true;
        }
        return false;
    }

    // --- EXO 9 : EXPORTER DANS UN FICHIER ---
    public static void exporter(int[][] mat, String nomFichier) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(nomFichier));
            writer.println("MatriceAdj"); // Signature du fichier
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat.length; j++) {
                    writer.print(mat[i][j]);
                }
                writer.println();
            }
            writer.close();
            System.out.println("Exportation reussie dans " + nomFichier);
        } catch (IOException e) { System.out.println("Erreur export"); }
    }

    // --- EXO 10 : IMPORTER DEPUIS UN FICHIER ---
    // Note : Pour simplifier, on suppose que la matrice fait 4x4 comme le TP
    public static int[][] importer(String nomFichier) {
        int[][] mat = new int[4][4];
        try {
            Scanner scanner = new Scanner(new File(nomFichier));
            scanner.nextLine(); // On saute la ligne "MatriceAdj"
            for (int i = 0; i < 4; i++) {
                String ligne = scanner.nextLine();
                for (int j = 0; j < 4; j++) {
                    mat[i][j] = Character.getNumericValue(ligne.charAt(j));
                }
            }
            scanner.close();
            System.out.println("Importation reussie !");
        } catch (Exception e) { System.out.println("Erreur import"); }
        return mat;
    }

    // --- MAIN ---
    public static void main(String[] args) {
        int[][] mat = {
            {0, 1, 1, 0},
            {0, 0, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 1, 0}
        };

        System.out.println("--- TP GRAPHES (MATRICE) ---");
        System.out.println("Oriente : " + estOriente(mat));
        System.out.println("Value : " + estValue(mat));
        System.out.println("Degre Entrant x2 : " + degreEntrant(mat, 1));
        System.out.println("Record Max : " + degreSortantMax(mat));
        afficherSuccesseurs(mat, 0);
        System.out.println("Voisin commun x1 et x2 : " + voisinCommun(mat, 0, 1));
        System.out.println("Existe Entree : " + existeEntree(mat));
        System.out.println("Existe Sortie : " + existeSortie(mat));
        System.out.println("Sommet Universel : " + estUniversel(mat));

        // Test Export/Import
        exporter(mat, "graphe.txt");
        int[][] matImportee = importer("graphe.txt");
    }
}