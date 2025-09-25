#include <stdio.h>

int main(void) {
    int n;

    /* demander un entier entre 1 et 6 */
    do {
        printf("Donnez un entier n (1..6) : ");
        if (scanf("%d", &n) != 1) {
            /* entrée invalide : consommer le reste de la ligne */
            int c;
            while ((c = getchar()) != '\n' && c != EOF) ;
            n = 0; /* forcer la boucle */
        }
    } while (n < 1 || n > 6);

    /* construire et afficher la matrice de Hilbert */
    printf("Matrice de Hilbert d'ordre %d :\n", n);
    for (int i = 1; i <= n; ++i) {
        for (int j = 1; j <= n; ++j) {
            double aij = 1.0 / (i + j + 1.0); //Calcul de la matrice
            printf("%8.3f", aij);   /* largeur totale 8, 3 décimales */
        }
        putchar('\n');
    }

    return 0;
}
