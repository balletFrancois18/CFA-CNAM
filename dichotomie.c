#include <stdio.h>
#include <math.h>

double a; // variable globale

// Fonction f(x) = x^3 - a
double f(double x) {
    return x * x * x - a;
}

int main() {
    double min_val, max_val;

    // Lecture des valeurs
    printf("Entrez a, min et max : ");
    scanf("%lf %lf %lf", &a, &min_val, &max_val);

    double f_min = f(min_val);
    double f_max = f(max_val);

    // Vérification des signes
    if (f_min * f_max > 0) {
        printf("Calcul impossible\n");
        return 0;
    }

    double low = min_val;
    double high = max_val;
    double mid, f_mid;

    // Dichotomie jusqu'à 10^-6 près
    while ((high - low) > 1e-6) {
        mid = (low + high) / 2.0;
        f_mid = f(mid);

        if (f_min * f_mid <= 0) {
            high = mid;
            f_max = f_mid;
        } else {
            low = mid;
            f_min = f_mid;
        }
    }

    // Affichage du résultat
    printf("Solution approchée : %.6f\n", (low + high) / 2.0);

    return 0;
}
