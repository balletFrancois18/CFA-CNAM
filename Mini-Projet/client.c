#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>

int main() {
    int choix, i, retour, choixReserv;
    int tub1[2]; // Client -> Serveur
    int tub2[2]; // Serveur -> Client
    
    if (pipe(tub1) == -1 || pipe(tub2) == -1) {
        perror("Erreur pipe");
        return 1;
    }

    char nomspect[5][50] = {
        "Lac des Cygnes", "Lac Vert", "Lac Bleu", "Le Choix", "Le Cirque"
    };    
    
    retour = fork();

    if (retour == -1) {
        perror("Erreur fork");
        return 1;
    }

    if (retour == 0) { // ================= PROCESSUS FILS (CLIENT) =================
        close(tub1[0]); 
        close(tub2[1]); 

        printf("=====================================\n");
        printf("          MENU PRINCIPAL             \n");
        printf("=====================================\n");
        printf("  0 - Consultation des spectacles\n");
        printf("  1 - Reservation des spectacles\n");
        printf("=====================================\n");
        printf("Entrez votre choix : ");
        
        if (scanf("%d", &choix) != 1) return 1;
        write(tub1[1], &choix, sizeof(int));

        switch (choix) {
            case 0: {
                int spect[5];
                printf("\n[Client] Demande de consultation...\n");
                read(tub2[0], spect, sizeof(spect));
                
                printf("\nVoici les places disponibles : \n");
                for (i = 0; i < 5; i++) {
                    printf(" - %s : %d places\n", nomspect[i], spect[i]);
                }
                break;
            }
            case 1: {
                printf("\nQuel spectacle ? (0:Lac Cygnes, 1:Vert, 2:Bleu, 3:Choix, 4:Cirque) : ");
                scanf("%d", &choixReserv);
                write(tub1[1], &choixReserv, sizeof(int));

                int nbPlaces;
                printf("Combien de places voulez-vous ? ");
                scanf("%d", &nbPlaces);
                write(tub1[1], &nbPlaces, sizeof(int));

                int reponse;
                read(tub2[0], &reponse, sizeof(int));

                if (reponse == 1)
                    printf("\n>>> [Succès] Réservation acceptée !\n");
                else
                    printf("\n>>> [Echec] Réservation refusée. Pas assez de places.\n");
                break;
            }
            default:
                printf("Choix invalide.\n");
                break;
        }

        close(tub1[1]);
        close(tub2[0]);
        exit(0);

    } else { // ================= PROCESSUS PÈRE (SERVEUR) =================
        close(tub1[1]);
        close(tub2[0]);

        int stocks[5] = {15, 65, 80, 30, 10}; 

        // Le serveur attend le choix du client
        if (read(tub1[0], &choix, sizeof(int)) > 0) {
            if (choix == 0) {
                write(tub2[1], stocks, sizeof(stocks));
            } 
            else if (choix == 1) {
                read(tub1[0], &choixReserv, sizeof(int));
                int demandees;
                read(tub1[0], &demandees, sizeof(int));

                int reponse = 0;
                if (choixReserv >= 0 && choixReserv < 5 && demandees <= stocks[choixReserv]) {
                    reponse = 1;
                    stocks[choixReserv] -= demandees;
                }
                write(tub2[1], &reponse, sizeof(int));
            }
        }

        close(tub1[0]);
        close(tub2[1]);
        wait(NULL); 
    }

    return 0;
}