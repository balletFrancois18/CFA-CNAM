
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

struct sigaction action, action_old;
int fich;

void fonction_handler2(int sig)
{
printf("signal SIGINT recu (ouverture du fichier)\n");
/* ouverture d'un fichier */
if((fich=open("attente.txt", O_CREAT|O_RDWR|O_TRUNC, S_IRUSR|S_IWUSR|
S_IRGRP|S_IROTH))==-1)
{
perror("fichier");
exit(1);
}
// Mise en place de la fonction initiale
sigaction(SIGINT, &action_old, &action);
}

void fonction_handler1(int sig)
{
printf("signal SIGINT recu (fermeture du fichier)\n");
close(fich);
// Mise en place de la nouvelle fonction
action.sa_handler=fonction_handler2;
sigaction(SIGINT, &action, &action_old);
}


void fonction_handler(void)
{
   printf("signal SIGINT recu\n");
   close(fich);
   
   exit(0);
}

int main(int argc, char **argv)
{
  int nb=0;

// Association fonction de traitement au signal SIGINT
action.sa_handler=fonction_handler1;
sigaction(SIGINT, &action, NULL);

  // Association fonction de traitement au signal SIGINT
//  signal(SIGINT, (__sighandler_t) fonction_handler);

  /*  ouverture d'un fichier */
  if((fich=open("attente.txt", O_CREAT|O_WRONLY, 0))==-1)
  {
    perror("fichier");
    exit(1);
  }

  while(1)
  {
     printf("En attente d'un signal.\n");
     sleep(2);
     nb=nb+2;
     write(fich, &nb, sizeof(nb));
  }
  
  exit(0);
}
