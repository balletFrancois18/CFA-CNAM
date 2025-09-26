#include <stdio.h>

// Fonction pour vérifier si un nombre est premier (inclut 1 comme "premier")
int est_premier(int n) {
    if (n == 1) {
        return 1;  // 1 est considéré comme "premier" pour cet exercice
    }
    
    // Vérification des diviseurs de 2 à sqrt(n)
    for (int j = 2; j * j <= n; j++) {
        if (n % j == 0) {
            return 0;  // Pas premier si diviseur trouvé
        }
    }
    
    return 1;  // Premier si aucun diviseur trouvé
}

int main() {
    int N;
    scanf("%d", &N);  // Lecture de N

    for (int i = 1; i <= N; i++) {
        if (est_premier(i)) {
            printf("%d ", i);  // Affichage avec espace
        }
    }
    printf("\n");  // Saut de ligne à la fin

    return 0;
}
