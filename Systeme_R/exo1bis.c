
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int fich;

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
  signal(SIGINT, (__sighandler_t) fonction_handler);

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
