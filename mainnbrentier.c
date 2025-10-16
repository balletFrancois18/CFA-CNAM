/* Lit N (strictement positif) et affiche tous les "nombres premiers" entre 1 et N.
   NOTE: ici on considère 1 comme "premier" pour respecter l'exemple de l'énoncé. */

   #include <stdio.h>
#include <stdlib.h>

int main(void) {
    int N;
    if (scanf("%d", &N) != 1) {
        fprintf(stderr, "Erreur de lecture de N\n");
        return 1;
    }
    if (N <= 0) {
        fprintf(stderr, "N doit etre strictement positif\n");
        return 1;
    }

    int first_printed = 0;
    for (int i = 1; i <= N; ++i) {
        if (i == 1) {            /* suivant l'énoncé on affiche 1 */
            if (first_printed) printf(" ");
            printf("1");
            first_printed = 1;
            continue;
        }
        int is_prime = 1;
        for (int j = 2; j*j <= i; ++j) {
            if (i % j == 0) {
                is_prime = 0;
                break;
            }
        }
        if (is_prime) {
            if (first_printed) printf(" ");
            printf("%d", i);
            first_printed = 1;
        }
    }
    printf("\n");
    return 0;
}
