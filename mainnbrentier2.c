#include <stdio.h>
#include <stdlib.h>


int est_premier(int n) {
    if (n == 1) return 1;   
    if (n < 2) return 0;
    for (int d = 2; d*d <= n; ++d) {
        if (n % d == 0) return 0;
    }
    return 1;
}

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

    int first = 1;
    for (int i = 1; i <= N; ++i) {
        if (est_premier(i)) {
            if (!first) printf(" ");
            printf("%d", i);
            first = 0;
        }
    }
    printf("\n");
    return 0;
}
