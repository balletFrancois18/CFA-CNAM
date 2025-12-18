public class GraphesTP1 {

    // =================================================================
    // EXO 1 : ORIENTATION (Vérifie la symétrie)
    // =================================================================
    public static boolean estOriente(int[][] matrice) {
        int n = matrice.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (matrice[i][j] != matrice[j][i]) {
                    return true; // Dès qu'une différence est trouvée
                }
            }
        }
        return false;
    }

    // =================================================================
    // EXO 2 : VALUÉ (Vérifie si les poids sont > 1)
    // =================================================================
    public static boolean estValue(int[][] matrice) {
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice.length; j++) {
                if (matrice[i][j] > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    // =================================================================
    // EXO 3 : DEGRÉS (Ligne pour Sortant, Colonne pour Entrant)
    // =================================================================
    public static int degreSortant(int[][] matrice, int sommet) {
        int degre = 0;
        for (int j = 0; j < matrice.length; j++) {
            if (matrice[sommet][j] > 0) degre++;
        }
        return degre;
    }

    public static int degreEntrant(int[][] matrice, int sommet) {
        int degre = 0;
        for (int i = 0; i < matrice.length; i++) {
            if (matrice[i][sommet] > 0) degre++;
        }
        return degre;
    }

    // =================================================================
    // EXO 4 : RECORDS (Min et Max des degrés sortants)
    // =================================================================
    public static int degreSortantMin(int[][] matrice) {
        int min = matrice.length; 
        for (int i = 0; i < matrice.length; i++) {
            int d = degreSortant(matrice, i);
            if (d < min) min = d;
        }
        return min;
    }

    public static int degreSortantMax(int[][] matrice) {
        int max = 0;
        for (int i = 0; i < matrice.length; i++) {
            int d = degreSortant(matrice, i);
            if (d > max) max = d;
        }
        return max;
    }

    // =================================================================
    // EXO 5 : LISTER LES SUCCESSEURS
    // =================================================================
    public static void afficherSuccesseurs(int[][] matrice, int sommet) {
        System.out.print("Successeurs du sommet x" + (sommet + 1) + " : ");
        
        boolean aDesSuccesseurs = false;
        for (int j = 0; j < matrice.length; j++) {
            if (matrice[sommet][j] > 0) {
                System.out.print("x" + (j + 1) + " ");
                aDesSuccesseurs = true;
            }
        }
        
        if (!aDesSuccesseurs) {
            System.out.print("aucun");
        }
        System.out.println(); 
    }

    // =================================================================
    // MÉTHODE MAIN : AFFICHAGE GLOBAL
    // =================================================================
    public static void main(String[] args) {
        // Le graphe de référence du TP (Page 1)
        int[][] matriceTP = {
            {0, 1, 1, 0}, // x1 -> vers x2, x3
            {0, 0, 1, 1}, // x2 -> vers x3, x4
            {0, 0, 0, 0}, // x3 -> vers rien
            {0, 0, 1, 0}  // x4 -> vers x3
        };

        System.out.println("========== COMPTE-RENDU TP1 (MATRICES) ==========");

        // --- Test Exo 1 ---
        System.out.println("\n[EXO 1] Orientation :");
        System.out.println("   > Le graphe est-il oriente ? " + estOriente(matriceTP));

        // --- Test Exo 2 ---
        System.out.println("\n[EXO 2] Valeur :");
        System.out.println("   > Le graphe est-il value ?    " + estValue(matriceTP));

        // --- Test Exo 3 ---
        System.out.println("\n[EXO 3] Degres du sommet x2 (indice 1) :");
        System.out.println("   > Degre sortant : " + degreSortant(matriceTP, 1));
        System.out.println("   > Degre entrant  : " + degreEntrant(matriceTP, 1));

        // --- Test Exo 4 ---
        System.out.println("\n[EXO 4] Statistiques du graphe :");
        System.out.println("   > Degre sortant MIN : " + degreSortantMin(matriceTP));
        System.out.println("   > Degre sortant MAX : " + degreSortantMax(matriceTP));

        // --- Test Exo 5 ---
        System.out.println("\n[EXO 5] Listes des successeurs :");
        
        afficherSuccesseurs(matriceTP, 0); // Pour x1
        afficherSuccesseurs(matriceTP, 1); // Pour x2
        afficherSuccesseurs(matriceTP, 2); // Pour x3
        afficherSuccesseurs(matriceTP, 3); // Pour x4
        
        System.out.println("\n================================================");
    }
}