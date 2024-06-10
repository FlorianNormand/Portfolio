#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#define LIGNE 6
#define COLONNE 7

typedef int t_grille[LIGNE][COLONNE];

int main(int argc, char **argv);
void chargerGrille(char **argv, t_grille grille);
bool colPleine(t_grille laGrille, int col);

const int VIDE = 0;
const int JOUEUR = 1;
const int ADVERSAIRE = 2;

/*
 * VOTRE STRATEGIE
 */
// strategie
int strategie(t_grille laGrille) {
  int last = 0;
  FILE *fic = fopen("last_col.txt", "r");
  if (fic) {
    fscanf(fic, "%d", &last);
    fclose(fic);
  }

  int colonne = -1;
  switch (last) {
  case 0:
    colonne = 3;
    break;
  case 1:
    colonne = 4;
    break;
  case 2:
    colonne = 5;
    break;
  case 3:
    colonne = 6;
    break;
  default:
    colonne = -1;
  }
  if (colPleine(laGrille, colonne)) {
    last = last + 1;
    colonne = strategie(laGrille);
  }
  if (last == 4) {
    last = 0;
  } else {
    last = last + 1;
  }
  fic = fopen("last_col.txt", "w");
  fprintf(fic, "%d", last);
  fclose(fic);
  return colonne;
}

// test si la colonne est pleine
bool colPleine(t_grille laGrille, int col) {
  bool test;
  test = true;
  int i;
  for (i = 0; i < LIGNE; i++) {
    if (laGrille[i][col] == VIDE) {
      test = false;
    }
  }
  return test;
}

// La fonction principale reçoit la grille du tour et retourne le coup choisi
// Vous n'avez pas à modifier cette fonction
int main(int argc, char **argv) {
  t_grille grille;

  chargerGrille(argv, grille);

  return strategie(grille);
}

// Charge la grille du tour actuel
// Vous n'avez pas à modifier cette fonction
void chargerGrille(char **argv, t_grille grille) {
  for (int i = 0; i < LIGNE; i++)
    for (int j = 0; j < COLONNE; j++)
      grille[i][j] = atoi(argv[i * COLONNE + j + 1]);
}
