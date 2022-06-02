package Modele;

import Modele.Circuit;
import Modele.Joueur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Partie {
    Joueur joueur;
    Circuit circuit;

    
    //--------------------------------------------------------------
  	//                  Constructeur
  	//--------------------------------------------------------------
    public Partie(Joueur j, Circuit c) {
        joueur = j;
        circuit = c;
    }
    
    //--------------------------------------------------------------
    //                   Getters et Setters
    //--------------------------------------------------------------

    public Circuit getCircuit() {
        return circuit;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    //------------------------------------------------------------
    //                   Fonctions
    //------------------------------------------------------------

    /**
     * Fonctionne pour que le joueur donne une bonne coordonnée X parmi les sommets disponibles, tant qu'elle n'est pas bonne,
     *  cette fonction ne renvoie pas x et redemande une bonne valeur.
     * @param j : un joueur
     */
    public void setCoordonnee(Joueur j) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Veuillez saisir le chiffre sur lequel vous souhaitez vous deplacer : ");
        String res = scanner.nextLine();
        while (true) {
            for (int i = 1; i <= j.getPossibilite().size(); i++) {
                if (res.equals(String.valueOf(i))) {
                    int x = Integer.parseInt(res);
                    Sommet tmp = j.getPossibilite().get(x-1);
                    x = tmp.getJ();
                    int y = tmp.getI();
                    int joueurX = j.getX();
                    int joueurY = j.getY();
                    j.setX(x);
                    j.setY(y);
                    j.setVitesseX(tmp.getVitesseX());
                    j.setVitesseY(tmp.getVitesseY());
                    setLesBonusMalus(tmp,joueurX,joueurY,x,y,j);
                    return;
                }
            }
            System.out.print("Veuillez saisir un chiffre valide : ");
            res = scanner.nextLine();
        }
    }
    /**
     * Permet de mettre à true les bonus ou malus du joueur si le sommet sélectionné par ce dernier
     * est un sommet bonus/malus.
     * Transmet également les variables pour traiter les bonus sur plusieurs tours.
     * 
     * @param tmp : le sommet sélectionné
     * @param joueurX : un entier (position x du joueur avant sélection du nouveau sommet)
     * @param joueurY : un entier (position y du joueur avant sélection du nouveau sommet)
     * @param x : un entier (position x du joueur après sélection du nouveau sommet)
     * @param y : un entier (position y du joueur après sélection du nouveau sommet)
     * @param j : une joueur 
     */
    public void setLesBonusMalus(Sommet tmp,int joueurX, int joueurY,int x, int y, Joueur j){
        if (tmp.getBonus()|| tmp.getBonusX()||tmp.getBonusY()) {
            if(joueurX != x) {
                j.setBonusX(true);
            }
            if(joueurY != y) {
                j.setBonusY(true);
            }
            j.setResetbonus(tmp.getResetbonus());
            j.setIncreTourBonus(tmp.getIncreTourBonus());
        }else if (tmp.getMalus()||tmp.getMalusX()||tmp.getMalusY()) {
            if (joueurX != x) {
                j.setMalusX(true);
            }
            if (joueurY != y) {
                j.setMalusY(true);
            }
            j.setResetmalus(tmp.getResetmalus());
            j.setIncreTourMalus(tmp.getIncreTourMalus());
            j.setIsMalusX(tmp.getIsMalusX());
            j.setIsMalusY(tmp.getIsMalusY());
        }
    }

    /**
     * Rétablit le contenu des cases après sélection d'un sommet.
     * 
     * @param j : un joueur
     */
    public void reAffichagePossibilitesNormale(Joueur j) {
        for (int i = 0; i < circuit.getTableau().length; i++) {
            for (int k = 0; k < circuit.getTableau()[i].length; k++) {
                for (int l = 21; l <= 29; l++) {
                    if (circuit.getTableau()[i][k] == l || circuit.getTableau()[i][k] == 20) {
                        circuit.getTableau()[i][k] = 1;
                    }
                }
            }
        }
        if (circuit.getTableau()[circuit.getDepart().getI()][circuit.getDepart().getJ()] == 1) {
            circuit.getTableau()[circuit.getDepart().getI()][circuit.getDepart().getJ()] = 2;
        }
        if (circuit.getTableau()[circuit.getArrivee().getI()][circuit.getArrivee().getJ()] == 1) {
            circuit.getTableau()[circuit.getArrivee().getI()][circuit.getArrivee().getJ()] = 3;
        }
    }

    /**
     * permet de reafficher le joueur sur le circuit
     * @param j : joueur
     * @param tab : tableau de int du circuit
     */
    public void setAffichagejoueur(Joueur j ,int[][] tab){
        tab[j.getY()][j.getX()] = j.getNumJoueur() + 14;
    }

    /**
     * affiche les possibilites du joueur sur le circuit
     * @param j : joueur
     * @param tab : tableau de int du circuit
     */
    public void setCasesPossibilite(Joueur j, int[][] tab){
        int indice = 21;
        for(Sommet s : j.getPossibilite()){
                tab[s.getI()][s.getJ()] = indice;
                indice++;
        }
    }

    //------------------------------------------------------------
    //                   Partie Seule
    //------------------------------------------------------------

    /**
     * Lancer le jeu ou jouer et terminer.
     */
    public void jeuSolo() {
        joueur.listeSuccesseurs(circuit);
        while ( (joueur.getX() != circuit.getArrivee().getJ() || joueur.getY() != circuit.getArrivee().getI()) && (! (joueur.getPossibilite().isEmpty())) ) {
            setAffichagejoueur(joueur, circuit.getTableau());
            setCasesPossibilite(joueur, circuit.getTableau());
            circuit.afficheCircuit(circuit.getTableau());
            circuit.getTableau()[joueur.getY()][joueur.getX()] = 1;
            setCoordonnee(joueur);
            miseAJourDuTableau(joueur);
        }
        if (joueur.getX() == circuit.getArrivee().getJ() && joueur.getY() == circuit.getArrivee().getI()){
            System.out.println("Bravo !");
        }
        else {
            System.out.println("Vous avez Perdu");
        }
    }

    /**
     * met a jour le circuit :
     * enleve l'affichage des possibilites
     * re affiche si besoin les bonus, malus
     * calcul les possibilites du joueur et les affiche sur le circuit
     * @param j : joueur
     */
    public void miseAJourDuTableau(Joueur j) {
        reAffichagePossibilitesNormale(j);
        for (Sommet s : j.getPossibilite()) {
            int x = s.getJ();
            int y = s.getI();
            if (s.getBonus()) {
                circuit.getTableau()[y][x] = 7;
            }
            if (s.getMalus()) {
                circuit.getTableau()[y][x] = 8;
            }
        }
        j.listeSuccesseurs(circuit);
        j.setPossibilite(j.getPossibilite());
    }

    //------------------------------------------------------------
    //                Partie Multijoueurs
    //------------------------------------------------------------
    /**
     * Jouer de 2 à 4 Joueurs en multijoueur LOCAL
     * - Lance le tour de chaque joueur
     * - définit s'ils ont perdu ( 0 possibilité ) ou gagné
     * - affiche un podium de fin
     * @param nbJoueur : un int qui définit combien de joueur jouerons en plus d'un joueur principal.
     */
    public void jeuMulti(int nbJoueur){
        ArrayList<Joueur> tour = new ArrayList<Joueur>();
        ArrayList<Joueur> podium = new ArrayList<Joueur>();
        ArrayList<Joueur> crash = new ArrayList<Joueur>();
        tour.add(this.joueur);
        for (int i =0;i<nbJoueur-1;i++){
            tour.add(new Joueur(circuit.getDepart().getJ(),circuit.getDepart().getI()));
        }
        while(!(tour.isEmpty())){
            ArrayList<Joueur> tmp = new ArrayList<Joueur>();
            for(Joueur j : tour){
                setAffichagejoueur(j, circuit.getTableau());
                System.out.println();
                System.out.println("A toi Joueur " + j.getNumJoueur() );
                System.out.println();
                tourMultijoueur(j);
                afficheLesJoueurs(tour,circuit);
                if ((j.getX() == circuit.getArrivee().getJ() && j.getY() == circuit.getArrivee().getI())) {
                    System.out.println();
                    System.out.println("Bravo Joueur "+ j.getNumJoueur() +", tu as réussi !");
                    podium.add(j);
                    tmp.add(j);
                }else if((j.getPossibilite().isEmpty())) {
                    System.out.println();
                    System.out.println("Dommage Joueur "+ j.getNumJoueur() +", tu t'es crashé !");
                    circuit.getTableau()[j.getY()][j.getX()] = 1;
                    crash.add(j);
                    tmp.add(j);
                }
            }
            for(int i = 0; i< tmp.size(); i++){
                tour.remove(tmp.get(i));
            }
        }
        System.out.println();
        System.out.println("La partie est fini ! Voici le classement final");
        System.out.println();
        for(Joueur j : podium){
            System.out.print("Joueur " + j.getNumJoueur() + " / ");
        }
        for(int j = crash.size() - 1 ; j >= 0; j--){
            System.out.print("Joueur " + crash.get(j).getNumJoueur() + " / ");
        }
    }
    /**
     * Fonction auxilière pour effectuer les actions à chaque tour quand on joue en Multijoueur
     * -Calculer les possibilités du joueur
     * -Le joueur choisi la possibilité qu'il veut
     * -On met à jour le tableau(circuit)
     * @param j : un joueur
     */
    public void tourMultijoueur(Joueur j){
        j.listeSuccesseurs(circuit);
        int jy = j.getY();
        int jx = j.getX();
        setCasesPossibilite(j, circuit.getTableau());
        circuit.afficheCircuit(circuit.getTableau());
        setCoordonnee(j);
        circuit.getTableau()[jy][jx] = 1;
        miseAJourDuTableau(j);
    }
    /**
     * Met à jour le tableau(circuit) avec la position de tous les joueurs.
     * @param joueur : la liste des joueurs
     * @param circuit : Le circuit sur lequel on joue
     */
    public void afficheLesJoueurs(ArrayList<Joueur> joueur,Circuit circuit){
        for (Joueur value : joueur) {
            circuit.getTableau()[value.getY()][value.getX()] = value.getNumJoueur() + 14;
        }
    }

    //------------------------------------------------------------
    //                Modele.Partie avec Robots
    //------------------------------------------------------------

    /**
     * lance le jeu avec des robots
     * @param nbRobot : nombre de robots voulus
     * @param bonus : indique s'il y a ou non des bonus sur le circuit
     */
    public void jeuRobots(int nbRobot, boolean bonus){
        System.out.println("Creation de la partie en Cours...\nVeuillez patienter...");
        ArrayList<Sommet> listeRobot = initRobots(nbRobot,circuit);
        LinkedHashMap<String,Integer> ordreArrivee = new LinkedHashMap<>();
        int distance = 0;
        int perduGagne = 0;
        joueur.listeSuccesseurs(circuit);
        while ((! (joueur.getPossibilite().isEmpty())) && (joueur.getX() != circuit.getArrivee().getJ() || joueur.getY() != circuit.getArrivee().getI())) {
            System.out.println("\nTour du Joueur :");
            int x = joueur.getX();
            int y = joueur.getY();
            setAffichagejoueur(joueur, circuit.getTableau());
            setCasesPossibilite(joueur, circuit.getTableau());
            circuit.afficheCircuit(circuit.getTableau());
            setCoordonnee(joueur);
            circuit.getTableau()[y][x] = 1;
            distance++;
            joueur.listeSuccesseurs(circuit);
            joueur.setPossibilite(joueur.getPossibilite());
            // Ici c'est pour réafficher correctement les elements à chaque affichage du circuit
            reAffichagePossibilitesNormale(joueur);
            for (int i = 0; i < listeRobot.size(); i++) {
                if (!ordreArrivee.containsKey("Robot "+(i+1))) {
                    System.out.println("Tour du Robot " + (i + 1) + " :");
                    tourRobot(listeRobot, i);
                    circuit.afficheCircuit(circuit.getTableau());
                    perduGagne = getPerduGagne(listeRobot, ordreArrivee, perduGagne, i);
                }
            }
            reAffichagePossibilitesNormale(joueur);
        }
        if (joueur.getX() == circuit.getArrivee().getJ() && joueur.getY() == circuit.getArrivee().getI()){
            System.out.println("Bravo !");
            perduGagne++;
            ordreArrivee.put("Vous (Joueur)",distance);
        }
        else if (joueur.getPossibilite().isEmpty()) {
            perduGagne++;
            System.out.println("Vous avez Perdu");
        }
        //tant que il n'y a pas le nombre de joueur (nb robot + soit meme) qui a gagné ou perdu
        //on va continuer le tour des robots
        System.out.println("\nVeuillez Patientez...\nLes Robots finissent leur course...\n");
        while(perduGagne != nbRobot+1) {
            for (int i = 0; i < listeRobot.size(); i++) {
                if (!ordreArrivee.containsKey("Robot " + (i + 1))) {
                    tourRobot(listeRobot, i);
                    perduGagne = getPerduGagne(listeRobot, ordreArrivee, perduGagne, i);
                }
            }
        }
        System.out.println();
        afficheClassement(ordreArrivee);
    }

    /**
     * initialise la liste des robots
     * @param nbRobot : nombre de robots voulus
     * @param circuit : circuit de la partie
     * @return une liste de robot
     */
    public ArrayList<Sommet> initRobots(int nbRobot, Circuit circuit){
        ArrayList<Sommet> listeRobot = new ArrayList<>();
        ArrayList<Integer> distPoss = circuit.distancePossible('d');
        for (int i = 0; i < nbRobot; i++) {
            Sommet a = circuit.trajetAleatoireInt(distPoss,'d');
            while (listeRobot.contains(a)) {
                a = circuit.trajetAleatoireInt(distPoss,'d');
            }
            listeRobot.add(i, a);
        }
        //initialisation du tableau avec les robots dedans
        for (int i=0;i<listeRobot.size();i++){
            Sommet f = listeRobot.get(i).cheminALendroit();
            listeRobot.set(i,f);
            circuit.getTableau()[listeRobot.get(i).getI()][listeRobot.get(i).getJ()] = 50+i;
        }
        return listeRobot;
    }

    /**
     * retourne si le robot a perdu ou gagne
     * @param listeRobot : liste des robots
     * @param ordreArrivee : ordre d'arrivee (classement)
     * @param perduGagne : nombre de robots ayant perdu/gagne
     * @param i : numero du robot ds la liste
     * @return un int
     */
    public int getPerduGagne(ArrayList<Sommet> listeRobot, LinkedHashMap<String, Integer> ordreArrivee, int perduGagne, int i) {
        if (listeRobot.get(i).getI() == circuit.getArrivee().getI() && listeRobot.get(i).getJ() == circuit.getArrivee().getJ()) {
            System.out.println("Le Robot " + (i + 1) + " a gagné !");
            ordreArrivee.put("Robot " + (i + 1), listeRobot.get(i).getDistance());
            perduGagne++;
            circuit.getTableau()[listeRobot.get(i).getI()][listeRobot.get(i).getJ()] = 3;
        }
        return perduGagne;
    }

    /**
     * gere le tour d'un robot
     * @param listeRobot : liste des robots
     * @param i : position du robot
     */
    public void tourRobot(ArrayList<Sommet> listeRobot, int i) {
        Sommet avantChgmt = listeRobot.get(i);
        listeRobot.set(i, avantChgmt.getSuivant());
        this.circuit.getTableau()[avantChgmt.getI()][avantChgmt.getJ()] = 1;
        this.circuit.getTableau()[listeRobot.get(i).getI()][listeRobot.get(i).getJ()] = 50 + i;
    }

    /**
     * affiche le classement
     * @param liste : classement final
     */
    public void afficheClassement(HashMap<String,Integer> liste){
        System.out.println("Affichage du classement :");
        int compt = 1;
        for (String i : liste.keySet()) {
            System.out.println("\t" + compt + " : " + i + ", avec une distance de : " + liste.get(i));
            compt++;
        }
    }

}

