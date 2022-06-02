package Modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PPM {
	
	/**
	 * Parse un fichier PPM. Dans un premier temps la fonction crée un tableau de pixels qui sert ensuite 
	 * à crée un tableau de int : 0 si le pixel est noir, 1 s'il est blanc, 2 s'il est rouge, 3 s'il est bleu, et 5 s'il est gris
	 * 
	 * @param cheminFichier : chemin du fichier à parser
	 * @return un tableau d'entiers à deux dimensions décrivant le circuit
	 */
    public static int[][] lireFichierCircuit(String cheminFichier) {
        BufferedReader lecteur;
        String ligne;
        String nbColEtLig = "";

        int  nbCol = 0;
        int nbLig = 0;
        int maxP = 255;
        int cpt;
        int []tabValeurs = new int[0];

        try {
            lecteur = new BufferedReader(new FileReader(cheminFichier));
            ligne = lecteur.readLine(); // lecture du symbole P3

            /*on recupere la deuxième ligne du fichier si c'est un commentaire( commence par '#')
             * on passe à la ligne suivante. Sinon on récupere la ligne et on split là où il y a un espace,
             * les 2 sous chaines récupérées correspondent au nombre de ligne et de colonnes de l'image.
             *
             */
            nbColEtLig = lecteur.readLine();
            if(nbColEtLig.substring(0, 1).equals("#")) {
                nbColEtLig = lecteur.readLine();
            }
            String nbCEtL[] = nbColEtLig.split(" ");
            nbCol = Integer.parseInt(nbCEtL[0]);
            nbLig = Integer.parseInt(nbCEtL[1]);
            maxP  = Integer.parseInt(lecteur.readLine());
            tabValeurs = new int[3+nbLig*nbCol*3];
            tabValeurs[0] = nbCol;
            tabValeurs[1] = nbLig;
            tabValeurs[2] = maxP;
            cpt = 3;

            while (((ligne = lecteur.readLine())!=null) && (cpt < 3+nbLig*nbCol*3)){
                tabValeurs[cpt] = Integer.parseInt(ligne);
                cpt++;
            }
            lecteur.close();
        }
        catch(FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        }
        catch(IOException e) {
            System.out.println("Erreur lecture de ligne");
        }

        int[][] t = new int[nbLig][nbCol];
        int k = 3;
        int nbPixParLigne = 0;
        int numLigne = 0;
        int numCol = 0;
        while (k < tabValeurs.length) {
            if(tabValeurs[k] == 0 && tabValeurs[k+1] == 0 && tabValeurs[k+2] == 0){
                t[numLigne][numCol] = 0;
            }else if (tabValeurs[k] == 150 && tabValeurs[k+1] == 150 && tabValeurs[k+2] == 150){
                t[numLigne][numCol] = 5;
            }else if(tabValeurs[k] > 250 && tabValeurs[k+1] > 250 && tabValeurs[k+2] > 250){
                t[numLigne][numCol] = 1;
            }else if(tabValeurs[k] > 250 && tabValeurs[k+1] < 50 && tabValeurs[k+2] < 50){
                t[numLigne][numCol] = 2;
            }else if(tabValeurs[k] < 50 && tabValeurs[k+1] < 50 && tabValeurs[k+2] > 250){
                t[numLigne][numCol] = 3;
            }
            k +=3;
            nbPixParLigne++;
            if (nbPixParLigne == nbCol) { numLigne++; numCol = 0; nbPixParLigne = 0; }
            else numCol++;
        }
        return t;
    }
    
    /**
     * Parcours le tableau parsé pour recuperer le départ et l'arrivée et construire 
     * les sommets correspondant. 
     * @param tab : un tableau deux dimensions d'entiers
     * @return un tableau contenant le départ et l'arrivée sous forme de sommets
     */
    public static Sommet[] recupereDepartArrivee(int [][] tab) {
        Sommet[] DepartEtArrivee = new Sommet[2];
        for(int i = 0; i<tab.length;i++) {
            for(int j  = 0; j< tab[i].length;j++) {
                if(tab[i][j] == 2) {
                    DepartEtArrivee[0] = new Sommet(i,j);

                }else if(tab[i][j] == 3) {
                    DepartEtArrivee[1] = new Sommet(i,j);
                }
            }
        }
        return DepartEtArrivee;
    }

}

