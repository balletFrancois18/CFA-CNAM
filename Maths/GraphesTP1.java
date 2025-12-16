public class GraphesTP1 {

    // -----------------------------------------------------------------
    // Exercice 1 : Détermine si un graphe est orienté
    // -----------------------------------------------------------------
    
    public static boolean estOriente(int[][] matrice) {
        int n = matrice.length;

        // On parcourt la moitié supérieure de la matrice (plus efficace)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                
                // Si l'arc i->j est différent de l'arc j->i (pas de symétrie)
                if (matrice[i][j] != matrice[j][i]) {
                    return true; // Graphe Orienté
                }
            }
        }
        
        return false; // Graphe Non Orienté (symétrique)
    }

    // -----------------------------------------------------------------
    // Exercice 2 : Détermine si un graphe est valué (PONDÉRÉ)
    // -----------------------------------------------------------------
    
    /**
     * Un graphe est valué si au moins une arête/arc a un poids supérieur à 1.
     * @param matrice La matrice d'adjacence.
     * @return true si le graphe est valué, false sinon (binaire 0/1).
     */
    public static boolean estValue(int[][] matrice) {
        int n = matrice.length;
        
        // On parcourt TOUS les éléments de la matrice
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                
                // Si on trouve une valeur de connexion strictement supérieure à 1
                if (matrice[i][j] > 1) {
                    return true; // Le graphe est valué. On s'arrête.
                }
            }
        }
        
        return false; // Le graphe n'a que des 0 et des 1.
    }

    // -----------------------------------------------------------------
    // Point d'entrée du programme (méthode main)
    // -----------------------------------------------------------------
    public static void main(String[] args) {
        
        // --- Graphes de Test ---
        
        // Graphe du TP (Orienté et Non Valué)
        int[][] matriceTP1 = {
            {0, 1, 1, 0},
            {0, 0, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 1, 0}
        };

        // Graphe de la page 4 (Orienté et Valué)
        int[][] matriceValuee = {
            {0, 1, 5, 0}, // Le '5' rend le graphe valué
            {0, 0, 4, 6},
            {0, 0, 0, 0},
            {0, 0, 2, 0}
        };
        
        
        System.out.println("--- RESULTATS DES EXERCICES (MATRICE) ---");
        
        // --- Test Exercice 1 ---
        System.out.println("\n[EXO 1] Orientation");
        System.out.println("Matrice TP1 est orientee ?  : " + estOriente(matriceTP1) + " (Attendu: true)");
        
        // --- Test Exercice 2 ---
        System.out.println("\n[EXO 2] Valeur (Poids)");
        System.out.println("Matrice TP1 est valuee ?    : " + estValue(matriceTP1) + " (Attendu: false)");
        System.out.println("Matrice Valuee est valuee ? : " + estValue(matriceValuee) + " (Attendu: true)");
    }
}

// -----------------------------------------------------------------
    // Exercice 3a : Calcule le degré sortant d'un sommet (Ligne)
    // -----------------------------------------------------------------
    
    /**
     * Calcule le nombre d'arcs qui partent du sommet.
     * @param matrice La matrice d'adjacence.
     * @param sommet L'indice (0-indexé) du sommet dont on cherche le degré sortant.
     * @return Le degré sortant (Out-degree).
     */
    public static int degreSortant(int[][] matrice, int sommet) {
        // En Java, on peut facilement obtenir la taille de la ligne
        int n = matrice.length;
        int degre = 0;
        
        // On parcourt la ligne 'sommet' (les destinations j)
        for (int j = 0; j < n; j++) {
            // Si la case contient une valeur > 0, cela signifie qu'il y a un arc (même valué).
            if (matrice[sommet][j] > 0) {
                degre++;
            }
        }
        return degre;
    }
    
    // -----------------------------------------------------------------
    // Exercice 3b : Calcule le degré entrant d'un sommet (Colonne)
    // -----------------------------------------------------------------

    /**
     * Calcule le nombre d'arcs qui arrivent au sommet.
     * @param matrice La matrice d'adjacence.
     * @param sommet L'indice (0-indexé) du sommet dont on cherche le degré entrant.
     * @return Le degré entrant (In-degree).
     */
    public static int degreEntrant(int[][] matrice, int sommet) {
        int n = matrice.length;
        int degre = 0;
        
        // On parcourt la colonne 'sommet' (les sources i)
        for (int i = 0; i < n; i++) {
            // Si la case contient une valeur > 0, cela signifie qu'il y a un arc.
            // L'arc va de i vers 'sommet'.
            if (matrice[i][sommet] > 0) {
                degre++;
            }
        }
        return degre;
    }