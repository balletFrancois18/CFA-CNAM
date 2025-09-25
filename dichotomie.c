#include <stdio.h>

double a;

double f(double x) {
    return x * x * x - a;
}

int main() {
    double min_val, max_val;
    scanf("%lf %lf %lf", &a, &min_val, &max_val);
    
    double f_min = f(min_val);
    double f_max = f(max_val);
    
    if (f_min * f_max > 0) {
        printf("Il ne peut pas répondre à la question.\n");
        return 0;
    }
    
    double low = min_val;
    double high = max_val;
    
    while (high - low > 1e-6) {
        double mid = (low + high) / 2.0;
        if (f(mid) * f(low) > 0) {
            low = mid;
        } else {
            high = mid;
        }
    }
    
    double root = (low + high) / 2.0;
    printf("La solution est approximativement %.6f\n", root);
    
    return 0;
}
