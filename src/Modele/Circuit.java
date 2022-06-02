package Modele;

import java.util.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Circuit {

    private final Sommet depart;
    private final Sommet arrivee;
    private int[][] tableau;
    String nom;

    //--------------------------------------------------------------
  	//                  Constructeur
  	//--------------------------------------------------------------

    public Circuit(String cheminFichier, String name,boolean bonusMalus){
        tableau = PPM.lireFichierCircuit(cheminFichier);
        depart = getDepart(PPM.recupereDepartArrivee(tableau));
        arrivee = getArrivee(PPM.recupereDepartArrivee(tableau));
        nom = name;
        if(bonusMalus) {
	        placeBonus();
	        placeMalus();
        }
        
    }

   
    //------------------------------------------------------------
    //                   Getters et Setters
    //------------------------------------------------------------

   public Sommet getDepart(Sommet[] recupereDepartArrivee) {
       return new Sommet(recupereDepartArrivee[0].getI(),recupereDepartArrivee[0].getJ());
   }

   public Sommet getArrivee(Sommet[] recupereDepartArrivee) {
       return new Sommet(recupereDepartArrivee[1].getI(),recupereDepartArrivee[1].getJ());
   }

    public Sommet getDepart() {
        return depart;
    }

    public Sommet getArrivee() {
        return arrivee;
    }

    public int[][] getTableau() {
        return tableau;
    }

    public String getNom() {
        return nom;
    }

    /**
     * retourne le pcc du circuit en fonction de l'algorithme choisit
     * @param algo : algo choisit
     * @return distance pcc
     */
    public int getPCC(char algo){
        new Sommet(0, 0);
        Sommet fin = switch (algo) {
            case 'a' -> AStar(false, -1);
            case 'd' -> dijkrstA(false, -1);
            case 'p' -> parcoursEnLargeur(false, -1);
            case '*' -> AStar(true, -1);
            case 'j' -> dijkrstA(true, -1);
            case 'l' -> parcoursEnLargeur(true, -1);
            default -> new Sommet(0, 0);
        };
        return fin.getDistance();
    }

    /**
     *
     * @return les distances maximales possibles (environ) pour le circuit
     */
    public int getMax(char a){
        switch (this.nom) {
            case "circuit1" :
                if (a == 'a'){
                    return 27;
                }else {
                    return 25;
                }
            case "circuit2" : return 40;
            case "circuit3" :
                if (a == 'a'){
                    return 24;
                }else {
                    return 20;
                }
            case "circuit4" : return 63;
            case "circuit5" :
                if (a == 'a'){
                    return 20;
                }else {
                    return 18;
                }
        }
        return -1;
    }


    /**
     *
     * @param alg algo choisit
     * @return une liste contenant les distances possibles d'un chemin
     */
    public ArrayList<Integer> distancePossible(char alg){
        ArrayList<Integer> liste = new ArrayList<>();
        for (int i=getPCC(alg);i<=getMax(alg);i++){
            liste.add(i);
        }
        return liste;
    }

    //------------------------------------------------------------
    //                   Affichages et bonus/malus
    //------------------------------------------------------------

    /**
     * Chemin en partant  de l'arrivée
     * @param a : un sommet
     * @param tab : un tableau deux dimensions d'entiers
     */
    public void cheminArrivee(Sommet a, int[][] tab){
        a = a.getPredecesseur();
        while(a.getPredecesseur() != null){
            tab[a.getI()][a.getJ()] = 15;
            a = a.getPredecesseur();
        }
        afficheCircuit(tab);
    }
    
    /**
     * Affiche le circuit courant
     * @param tab: un tableau deux dimensions d'entiers
     */
    public void afficheCircuit(int[][] tab){
        for (int[] ints : tab) {
            for (int j = 0; j < tab[0].length; j++) {
                if (ints[j] == 15) {
                    System.out.print("o");
                } else if (ints[j] == -1) {
                    System.out.print("~");
                } else if (ints[j] == 0) {
                    System.out.print("X");
                } else if (ints[j] == 1) {
                    System.out.print(".");
                } else if (ints[j] == 2) {
                    System.out.print("A");
                } else if (ints[j] == 3) {
                    System.out.print("B");
                } else if (ints[j] == 4) {
                    System.out.print("1");
                } else if (ints[j] == 5) {
                    System.out.print("2");
                } else if (ints[j] == 7) {
                    System.out.print("+");
                } else if (ints[j] == 8) {
                    System.out.print("-");
                } else if (ints[j] == 21) {
                    System.out.print("1");
                } else if (ints[j] == 22) {
                    System.out.print("2");
                } else if (ints[j] == 23) {
                    System.out.print("3");
                } else if (ints[j] == 24) {
                    System.out.print("4");
                } else if (ints[j] == 25) {
                    System.out.print("5");
                } else if (ints[j] == 26) {
                    System.out.print("6");
                } else if (ints[j] == 27) {
                    System.out.print("7");
                } else if (ints[j] == 28) {
                    System.out.print("8");
                } else if (ints[j] == 29) {
                    System.out.print("9");
                } else if (ints[j] == 50 || ints[j] == 16) {
                    System.out.print("#");
                } else if (ints[j] == 51 || ints[j] == 17) {
                    System.out.print("%");
                } else if (ints[j] == 52 || ints[j] == 18) {
                    System.out.print("$");
                } else {
                    System.out.print("?");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Place trois bonus de manière aléatoire
     */
    public void placeBonus() {
    	int bonusplace = 0;
    	int i;
		int j;
    	while(bonusplace < 3) {
    		Random random = new Random();
    		i = random.nextInt(tableau.length);
    		j = random.nextInt(tableau[i].length);
            if(tableau[i][j] == 1) {
    			tableau[i][j] = 7;
    			bonusplace +=1;
    		}
    	}
    }
 
   /**
    * Place trois malus de manière aléatoire
    */
   public void placeMalus() {
   	int malusplace = 0;
   	int i;
	int j;
   	while(malusplace < 3) {
   		Random random = new Random();
   		i = random.nextInt(tableau.length);
   		j = random.nextInt(tableau[i].length);
        if(tableau[i][j] == 1) {
   			tableau[i][j] = 8;
   			malusplace +=1;
   		}
   	}
  }


    //------------------------------------------------------------
    //                   Algorithmes
    //------------------------------------------------------------

    /**
     * Algorithme d'A Star
     * @param variante : pour le cas de l'arrivée avec vitesse nulle 
     * @param distance : distance souhaité (-1 si pcc voulu)
     * @return null ou un sommet correspondant à l'arrivée et ayant une telle distance
     */
    public Sommet AStar(boolean variante,int distance) {
        FileDePriorite prio = new FileDePriorite();
        //Heuristique utilisée pour avoir une approximation de la distance qui sépare le Sommet de l'arrivée
        int heurist;

        // Si il existe, le Sommet dans la liste equivalent a un successeur de u
        Sommet vPrio;

        /*
         indPrio est l'indice dans tas du Sommet équivalent au successeur de u (-1 si il n'existe pas)
         distTerm est la distance à l'origine du Sommet dans Term équivalent au successeur de u (null si il n'existe pas)
        */
        Integer indPrio, distTerm;

        HashMap<Sommet, Integer> term = new HashMap<>(); //Liste des sommets déjà parcourus
        prio.insertion(depart, depart.distManhaEntre(arrivee));

        while (!prio.estVide()) {
            //Extraction du sommet ayant la priorité la plus haute dans la File de Prio
            Sommet u = prio.extraireMin();
            term.put(u, u.getDistance());
            //si le sommet correspond à l'arrivée
            if (u.getI() == this.arrivee.getI() && u.getJ() == this.arrivee.getJ()) {
                if (distance == -1) { //cas du pcc
                    if (variante){
                        if (u.getVitesseY() == 0 && u.getVitesseX() == 0){
                            return u;
                        }
                    }else {
                        return u;
                    }
                }else if (u.getDistance() == distance){ //cas de distance précise
                    if (variante){
                        if (u.getVitesseY() == 0 && u.getVitesseX() == 0){
                            return u;
                        }
                    }else {
                        return u;
                    }
                }
            }
            u.allPossibilite(tableau);
            for (Sommet v : u.getSuccesseurs()) {

                heurist = v.distManhaEntre(arrivee);
                indPrio = prio.indiceDansF(v);
                if (indPrio != -1) {
                    vPrio = prio.getSommet(indPrio);
                    if (vPrio.getDistance() > u.getDistance() + 1) {
                        vPrio.setDistance(u.getDistance() + 1);
                        vPrio.setPredecesseur(u);
                        prio.maj(vPrio, vPrio.getDistance() + heurist);
                    }
                } else {
                    distTerm = term.get(v);
                    if (distTerm != null) {
                        if (distTerm > u.getDistance() + 1) {
                            v.setDistance(u.getDistance() + 1);
                            v.setPredecesseur(u);
                            term.remove(v);
                            prio.insertion(v, v.getDistance() + heurist);
                        }
                    } else {
                        v.setDistance(u.getDistance() + 1);
                        v.setPredecesseur(u);
                        prio.insertion(v, v.getDistance() + heurist);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Algorihtme de Dijkstra
     * @param variante : pour le cas de l'arrivée avec vitesse nulle 
     * @param distance : distance souhaité (-1 si pcc voulu)
     * @return null ou un sommet correspondant à l'arrivée et ayant une telle distance
     */
    public Sommet dijkrstA(boolean variante, int distance){
        FileDePriorite listePrio = new FileDePriorite();
        HashSet<Sommet> parcourue = new HashSet<>();
        listePrio.insertion(depart, 0);
        while (!listePrio.estVide()) {
            //extraction du minimum dans la file de priorite
            Sommet u = listePrio.extraireMin();
            //si le sommet correspond à l'arrivée
            //cas de la varitante cad vitesse X et Y à 0
            if (u.getI() == this.arrivee.getI() && u.getJ() == this.arrivee.getJ()) {
                if (distance == -1) { //cas du pcc
                    if (variante){
                        if (u.getVitesseY() == 0 && u.getVitesseX() == 0){
                            return u;
                        }
                    }else {
                        return u;
                    }
                }else if (u.getDistance() == distance){ //cas de distance précise
                    if (variante){
                        if (u.getVitesseY() == 0 && u.getVitesseX() == 0){
                            return u;
                        }
                    }else {
                        return u;
                    }
                }
            }
            //pour chaque arc possible en partant de notre point u
            //u.allPossibilite(circuit.getTableau());
            u.allPossibilite(tableau);
            for (Sommet v : u.getSuccesseurs() ){
                //debut de l'arc = u
                //fin de l'arc = v
                //calcul de la distance de l'arc entre u et v
                int valeurDeLarc = 1;
                //si le v est deja dans la liste de priorité
                if (listePrio.indiceDansF(v) != -1) { //mettre diff de -1
                    //si la distance de v est plus grande que celle de u + la valeur de l'arc
                    if (listePrio.getSommetVPrime(v).getDistance() >= u.getDistance() + valeurDeLarc) { //comparaison avec celui dans listre prio
                        //on change la distance de v et son predecesseur
                        //puis on met a jour le sommet v dans la liste de priorite
                        v.setDistance(u.getDistance() + valeurDeLarc);
                        v.setPredecesseur(u);
                        listePrio.maj(v,v.getDistance());
                        parcourue.add(v);
                    }
                } else {
                    //si la distance de v vaut -1
                    // => -1 est ici l'initialisation donc ce qui veut dire que c'est un peu comme null
                    if (! parcourue.contains(v)) {
                        //update du sommet v en ajoutant une distance et un predecesseur
                        //puis insertion du sommet v dans la liste de priorite
                        v.setDistance(u.getDistance() + valeurDeLarc);
                        v.setPredecesseur(u);
                        listePrio.insertion(v, v.getDistance());
                    }
                }
            }
        }
        return null;
    }

    /**
     * Algorithme de parcours à la volée (Parcours en Largeur)
     * @param variante : pour le cas de l'arrivée avec vitesse nulle 
     * @param distance : distance souhaité (-1 si pcc voulu)
     * @return null ou un sommet correspondant à l'arrivée et ayant une telle distance
     */
    public Sommet parcoursEnLargeur(boolean variante, int distance)  {
    	HashSet<Sommet> sommetsParcourus = new HashSet<>();
        Queue<Sommet> file = new LinkedList<>();
        sommetsParcourus.add(depart);
        file.add(depart);
        while ( file.size() != 0) {// i.e tant que la file des successeurs n'est pas vide
            Sommet dernierSommet = file.remove(); // on récupère la tête de file (queue)
            dernierSommet.allPossibilite(tableau);//on calcule les successeurs
            for( Sommet successeur:dernierSommet.getSuccesseurs()) {
                if(!sommetsParcourus.contains(successeur)) {
                    // si la case correspond à l'arrivée on met a jour les infos puis on sort du programme
                    if (variante){  //dans le cas de la variante avec vitesse X et Y à 0
                        if(successeur.getI() == arrivee.getI() && successeur.getJ() == arrivee.getJ() &&
                                successeur.getVitesseX() == 0 && successeur.getVitesseY() == 0) {

                            arrivee.setDistance(dernierSommet.getDistance()+1);
                            arrivee.setVitesseX(successeur.getVitesseX());
                            arrivee.setVitesseY(successeur.getVitesseY());
                            arrivee.setPredecesseur(dernierSommet);
                            sommetsParcourus.add(arrivee);

                            return arrivee;

                        }else{
                            sommetsParcourus.add(successeur);
                            successeur.setDistance(dernierSommet.getDistance()+1);
                            successeur.setPredecesseur(dernierSommet);
                            file.add(successeur);
                        }
                    }
                    // dans le cas de base
                    else {
                        if (successeur.getI() == arrivee.getI() && successeur.getJ() == arrivee.getJ()) {
                            arrivee.setDistance(dernierSommet.getDistance() + 1);
                            arrivee.setVitesseX(successeur.getVitesseX());
                            arrivee.setVitesseY(successeur.getVitesseY());
                            arrivee.setPredecesseur(dernierSommet);
                            sommetsParcourus.add(arrivee);

                            return arrivee;

                        } else {
                            sommetsParcourus.add(successeur);
                            successeur.setDistance(dernierSommet.getDistance() + 1);
                            successeur.setPredecesseur(dernierSommet);
                            file.add(successeur);
                        }
                    }

                }

            }

        }
        return null;
    }


    //------------------------------------------------------------
    //       Comparaisons entre 2 Algorithmes
    //------------------------------------------------------------

    /**
     * Fait un concours entre 2 algorithmes choisis
     * @param algo1 : le premier algortihme à comparer
     * @param dist1 : distance du chemin de l'algorithme 1
     * @param algo2 : le deuxième algortihme à comparer
     * @param dist2 : distance du chemin de l'algorithme 2
     */
    public void concoursAlgos(char algo1, int dist1,char algo2, int dist2){
        Sommet chemin1 = choixAlgo(algo1,dist1);
        Sommet chemin2 = choixAlgo(algo2,dist2);
        ajouteChemin(this.tableau,chemin1,4);
        ajouteChemin(this.tableau,chemin2,5);
    }

    /**
     * retourne un chemin en fonction du choix de l'algorithme
     * @param algo : algo choisit
     * @param d : distance voulue
     * @return un chemin
     */
    public Sommet choixAlgo(char algo, int d){
        return switch (algo) {
            case 'a' -> AStar(false,d);
            case 'd' -> dijkrstA(false,d);
            case 'p' -> parcoursEnLargeur(false,d);
            case '*' -> AStar(true,-1);
            case 'j' -> dijkrstA(true,-1);
            case 'l' -> parcoursEnLargeur(true,-1);
            default -> new Sommet(0, 0);
        };
    }

    /**
     * ajoute un chemin sur un circuit
     * @param tab : tableau du circuit
     * @param chemin : chemin
     * @param num : numéro du chemin (pour l'affichage et la distinction)
     */
    public void ajouteChemin(int[][] tab, Sommet chemin, int num){
        chemin = chemin.getPredecesseur();
        while(chemin.getPredecesseur() != null){
            if (tab[chemin.getI()][chemin.getJ()] == 1 || tab[chemin.getI()][chemin.getJ()] == 0) {
                tab[chemin.getI()][chemin.getJ()] = num;
            } else if (tab[chemin.getI()][chemin.getJ()] != 2 && tab[chemin.getI()][chemin.getJ()] != 3){
                tab[chemin.getI()][chemin.getJ()] = -1;
            }
            chemin = chemin.getPredecesseur();
        }
    }

    //------------------------------------------------------------
    //               Trajet Aléatoire
    //------------------------------------------------------------

    /**
     * genere un chemin aleatoire
     * @param listeDistPoss : liste de distance de chemin possible
     * @param algo : algorithme choisit
     * @return un trajet aleatoire en fonction de lalgorithme et ayant une distance parmis les distance possible
     */
    public Sommet trajetAleatoireInt(ArrayList<Integer> listeDistPoss, char algo){
        Random rand = new Random();
        int pos = rand.nextInt(listeDistPoss.size());
        int dist = listeDistPoss.get(pos);
        listeDistPoss.remove(pos);
        if (algo == 'd') {
            return dijkrstA(false, dist);
        }else if (algo == 'p'){
            return parcoursEnLargeur(false,dist);
        } else {
            return AStar(false,dist);
        }
    }

}
