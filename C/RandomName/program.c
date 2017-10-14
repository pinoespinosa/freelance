#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>   
#include "strmap.h"

#define BUF 255 

#define TAM_APELLIDOS_TOTAL 100 // Size of file with 
#define TAM_ARREGLO 50  // Size of array to work



int main(void) {

  char lista_apellidos[TAM_APELLIDOS_TOTAL][BUF];
  char apellidos[TAM_ARREGLO][BUF];
  FILE * plist = NULL;

  // Seed the rand thread
  srand(clock() % (500 + 1));

  // Reading the file
  plist = fopen("apellidos.txt", "r");
  int i = 0;
  while (fgets(lista_apellidos[i], BUF, plist)) {
    lista_apellidos[i][strlen(lista_apellidos[i]) - 1] = '\0';
    i++;
  }

  // Setting the 50 random values 
  for (int i = 0; i < TAM_ARREGLO; ++i) {
    strncpy(apellidos[i], lista_apellidos[rand() % (TAM_APELLIDOS_TOTAL + 1) ], BUF);
  }

  // Shows the original array
  printf("%s", "\n---------------- Arreglo Original --------------------\n");
  for (int i = 0; i < TAM_ARREGLO; ++i) {
    printf("%s", apellidos[i]);
    printf("%s", "\n");
  }
  printf("%s", "\n");

  // Removing repetis and counting them
  StrMap * sm;
  char buf[BUF];
  sm = sm_new(TAM_ARREGLO);

  for (int i = 0; i < TAM_ARREGLO; i++) {
    for (int j = 0; j < TAM_ARREGLO; j++) {

      if ( (i != j) && (strcmp(apellidos[j], apellidos[i]) == 0) ) {

          int result = sm_get(sm, apellidos[i], buf, sizeof(buf));
          if (result == 0) {
            sm_put(sm, apellidos[i], "2");
          }
          else{
            // Parse value and adding 1 rep          
            int valor = 0;
            sscanf(buf, "%d", &valor);
            sprintf(buf,"%d", valor + 1);
            sm_put(sm, apellidos[i], buf);

            }

          strncpy(apellidos[i], "", BUF);

        }
    }
  }

  printf("%s", "---------------- Elementos Rep. --------------------\n");

  for (int i = 0; i < TAM_ARREGLO; ++i) {
    if (apellidos[i] != "") {

      int result = sm_get(sm, apellidos[i], buf, sizeof(buf));
      if (result == 0) {} else {
        printf("%s", apellidos[i]);
        printf("%s", " ");
        printf("%s", buf);
        printf("%s", "\n");
 
      }

    }
  }

  sm_delete(sm);

  return 0;
}