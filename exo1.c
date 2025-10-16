#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(void)
{
    while(1)
    {
        printf("Je suis vivant et vais me mettre en sommeil pour 2s.\n");
        sleep(2);
    }
    exit(0);
}