#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>

#define NBLIG 6
#define NBCOL 7

const char PION_A = 'X';
const char PION_B = 'O';
const char VIDE = '.';
const char INCONNU = ' ';
const int COLONNE_DEBUT = NBCOL/2;


typedef char Grille[NBLIG][NBCOL];

void initGrille(Grille g);
void afficher(Grille g, char pion);
int jouer(Grille g,char pion);
void finDePartie(char pion);

bool grillePleine(Grille g);

int choisirColonne (Grille g, char pion, int colonne);
int trouverLigne (Grille g,int colonne);
bool estVainqueur (Grille g, int ligne, int colonne);

int main () {
    char vainqueur;
    int ligne, colonne;
    Grille g;
    initGrille(g);
    vainqueur = INCONNU;
    afficher(g, PION_A);
    while ((vainqueur==INCONNU)&& !(grillePleine(g)))
    {
        ligne, colonne=jouer(g, PION_A);
        afficher(g, PION_B);
        if (estVainqueur(g, ligne, colonne))
        {
           vainqueur = PION_A;
        }
        else if (!(grillePleine(g)))
        {
            ligne, colonne=jouer(g, PION_B);
            afficher(g,  PION_A);
            if(estVainqueur(g, ligne, colonne))
                vainqueur = PION_B;
        }   
    }
    finDePartie(vainqueur);
    return EXIT_SUCCESS;
}


 
void initGrille(Grille g)
//Initialise la grille en affectant la constante VIDE à chacun de ses éléments.
{
    int i,j;
    for (i = 0; i < NBLIG; i++)
    {
        for (j = 0; j < NBCOL; j++)
        {
            g[i][j]=VIDE;
        }
        
    }
    
}


void afficher(Grille g, char pion)
/*Réalise l’affichage à l’écran du contenu de la grille avec les pions déjà joués. Cette procédure
affiche aussi, au-dessus de la grille, le prochain pion à tomber : il sera affiché au-dessus de la
colonne dont le numéro est donné en paramètre. Cette procédure commencera par effacer l’écran.
Les correcteurs seront sensibles à la qualité de l’affichage et à la fidélité à votre maquette
*/
{
    int i,j;
    printf(" \tA\t \tB\t \tC\t \tD\t \tE\t \tF\t \tG\n");
    for (i = 0; i < NBLIG; i++)
    {
        printf("%d|",i+1);
        for (j = 0; j < NBCOL; j++)
        {
            printf("\t%c\t|",g[i][j]);
        }
        printf("\n\n");
    }
    if (pion==PION_A)
    {
        printf("Joueur 1, c’est ton tour ! \n");
    }
    else if (pion==PION_B)
    {
        printf("Joueur 2, c’est ton tour ! \n");
    }
    printf("\n joueur 1: X \n joueur 2: O \n\n");

}


int jouer(Grille g,char pion)
/*permet à un joueur de jouer son pion. La procédure fait appel à choisirColonne, afin que le
joueur indique la colonne dans laquelle il veut jouer ; puis fait appel à trouverLigne pour définir
la case où ajouter le pion.
*/
{
    int ligne, colonne;
    printf("où veut tu mettre ton pion ? : ");
    scanf("%d",&colonne);
    ligne=trouverLigne(g, colonne);
    g[ligne][colonne]=pion;
    printf("\n\n\n\n\n");
    return ligne, colonne;
}


void finDePartie(char pion)
//Affiche le résultat d’une partie lorsqu’elle est terminée.
{
    if (pion==PION_A)
    {
        printf("Joueur 1, tu as gagné !!! ");
    }
    else if (pion==PION_B)
    {
         printf("Joueur 2, tu as gagné !!! ");
    }
    else
    {
        printf("égalité !!! ");
    } 
}


bool grillePleine(Grille g)
//Teste si toutes les cases de la grille sont occupées ou non.
{
    bool test;
    test=true;
    int i,j;
    for (i = 0; i < NBLIG; i++)
    {
        for (j = 0; j < NBCOL; j++)
        {
            if (g[i][j]==VIDE)
            {
                test=false;
            }
            
        }
        
    }
    return test;
}



int trouverLigne (Grille g,int colonne)
/*Consiste à trouver la première case non occupée de la colonne. Si la colonne est pleine, la
fonction retourne -1.*/
{
    int a, i;
    a=-1;
    for (i = 0; i < NBLIG; i++)
    {
        if (g[i][colonne]==VIDE)
        {
            a=i;
        }
        
    }
    return a;
}


bool estVainqueur (Grille g, int ligne, int colonne)
/*Indique si le pion situé dans la case repérée par les paramètres ligne et colonne a gagné la partie,
c’est-à-dire s’il y a une ligne, une colonne ou une diagonale formée d’au moins 4 de ses pions (la
ligne et la colonne passées en paramètres correspondent à la case où le joueur vient de jouer,
c’est-à-dire la case à partir de laquelle il faut rechercher 4 pions successifs identiques).
*/
{
    bool test;
    test=false;
    if (g[ligne][colonne]==g[ligne][colonne+1]==g[ligne][colonne+2]==g[ligne][colonne+3])
    {
        test=true;
    }
    else if (g[ligne][colonne-1]==g[ligne][colonne]==g[ligne][colonne+1]==g[ligne][colonne+2])
    {
        test=true;
    }
    else if (g[ligne][colonne-2]==g[ligne][colonne-1]==g[ligne][colonne]==g[ligne][colonne+1])
    {
        test=true;
    }
    else if (g[ligne][colonne-3]==g[ligne][colonne-2]==g[ligne][colonne-1]==g[ligne][colonne])
    {
        test=true;
    }
    else if (g[ligne][colonne]==g[ligne+1][colonne]==g[ligne+2][colonne]==g[ligne+3][colonne])
    {
        test=true;
    }
    else if (g[ligne][colonne]==g[ligne+1][colonne+1]==g[ligne+2][colonne+2]==g[ligne+3][colonne+3])
    {
        test=true;
    }
    else if (g[ligne-1][colonne-1]==g[ligne][colonne]==g[ligne+1][colonne+1]==g[ligne+2][colonne+2])
    {
        test=true;
    }
    else if (g[ligne-2][colonne-2]==g[ligne-1][colonne-1]==g[ligne][colonne]==g[ligne+1][colonne+1])
    {
        test=true;
    }
    else if (g[ligne-3][colonne-3]==g[ligne-2][colonne-2]==g[ligne-1][colonne-1]==g[ligne][colonne])
    {
        test=true;
    }
    else if (g[ligne][colonne]==g[ligne-1][colonne+1]==g[ligne-2][colonne+2]==g[ligne-3][colonne+3])
    {
        test=true;
    }
    else if (g[ligne+1][colonne-1]==g[ligne][colonne]==g[ligne-1][colonne+1]==g[ligne-2][colonne+2])
    {
        test=true;
    }
    else if (g[ligne+2][colonne-2]==g[ligne+1][colonne-1]==g[ligne][colonne]==g[ligne-1][colonne+1])
    {
        test=true;
    }
    else if (g[ligne+3][colonne-3]==g[ligne+2][colonne-2]==g[ligne+1][colonne-1]==g[ligne][colonne])
    {
        test=true;
    }
    return false;
}
