package Controleur;

import Modele.*;
import Vue.*;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

public class Controleur {

    private static Affichage affichage;

    public Controleur(){
    }

    /** fonction gérant le lancement de la partie graphique
     *
     */
    public void lancement (){
        affichage = new Affichage(new Controleur());
        affichage.setVisible(true);
        affichage.ecranPresentation();
    }

    /**
     * lance l'ecran de choix du début
     */
    public void choixDebut(){
        affichage.ecranChoixDebut();
    }

    /**
     * lance l'ecran de choix des algorithmes 1
     * @param pcc : détermine si on a choisis ou non un chemin ayant comme distance finale le pcc
     */
    public void choixAlgo1(boolean pcc){
        affichage.ecranChoixAlgo1(pcc);
    }

    /**
     * lance l'ecran de choix des algorithmes 2
     */
    public void choixAlgo2(){
        affichage.ecranChoixAlgo2();
    }

    /**
     * lance l'ecran de choix de circuit 1
     * @param algo : algo choisit
     * @param pcc : pcc pour true et false pour distance aléatoire
     * @param bonus : bonus sur le circuit ou non
     */
    public void choixCircuit1(char algo,boolean pcc,boolean bonus){
        affichage.ecranChoixCircuit1(algo,pcc,bonus);
    }

    /**
     * lance l'ecran de choix de circuit 2
     * @param algo1 : algo 1
     * @param d1 : distance voulue pour l'algorithme 1
     * @param algo2 : algo 2
     * @param d2 : distance voulue pour l'algorithme 2
     * @param bonus : bonus sur le circuit ou non
     */
    public void choixCircuit2(String algo1,String d1, String algo2, String d2, String bonus){
        boolean pcc1 = recupererDistance(d1);
        boolean pcc2 = recupererDistance(d2);
        boolean up = bonus.equals("Oui");
        affichage.ecranChoixCircuit2(algo1,pcc1,algo2,pcc2,up);
    }

    /**
     * lance l'ecran de choix de circuit 3
     * @param mode : mode de partie choisit
     * @param nb : nb de joueurs/robot
     * @param bonus : bonus sur le circuit ou non
     */
    public void choixCircuit3(String mode,String nb, String bonus){
        affichage.ecranChoixCircuit3(mode,nb,bonus);
    }

    //--------------------------------------------------------------
    //            trajet Aleatoire + algo simple
    //--------------------------------------------------------------

    /**
     * affiche l'ecran de visualisation du chemin
     * @param algo : algo choisit
     * @param bonus : bonus sur le circuit ou non
     * @param c : numéro du circuit
     * @param pcc : chemin pcc ou non
     */
    public void voirDemo(char algo, boolean bonus, int c,boolean pcc){
        Circuit circuit = retrouveCircuit(c,bonus);
        int distance = -1;
        if (algo == 'a' || algo == 'p' || algo == 'd') {
            distance = getDistance(pcc, circuit, algo);
        }
        if (bonus){
            distance = lanceAlgorithmeAvecBonus(circuit,algo,distance);
        }else {
            distance = lanceAlgorithmeSansBonus(circuit, algo, distance);
        }
        String alg = retrouveNomAlgo(algo);
        affichage.ecranAlgorithme(alg,bonus,distance, circuit);
    }

    /**
     * retourne un circuit
     * @param c : numéro du circuit
     * @param bonus : bonus sur le circuit ou non
     * @return un circuit en fonction des parametres
     */
    public Circuit retrouveCircuit(int c, boolean bonus){
        return switch (c) {
            case 1 -> new Circuit("circuit1.ppm", "circuit1", bonus);
            case 2 -> new Circuit("circuit2.ppm", "circuit2", bonus);
            case 3 -> new Circuit("circuit3.ppm", "circuit3", bonus);
            case 4 -> new Circuit("circuit4.ppm", "circuit4", bonus);
            case 5 -> new Circuit("circuit5.ppm", "circuit5", bonus);
            default -> null;
        };
    }

    /**
     * convertit un char en String
     * @param algo : char de l'algorithme
     * @return un String avec le nom complet de l'algorithme
     */
    public String retrouveNomAlgo(char algo){
        return switch (algo) {
            case 'p' -> "PL";
            case 'd' -> "Dijkstra";
            case 'a' -> "A*";
            case 'l' -> "PL (variante)";
            case 'j' -> "Dijkstra (variante)";
            case '*' -> "A* (variante)";
            default -> "";
        };
    }

    /**
     * lance l'algorithme sur le circuit (cas sans bonus)
     * @param circuit : circuit utilisé
     * @param algo : algorithme choisit
     * @param distance : distance voulue
     * @return la distance finale du chemin
     */
    public int lanceAlgorithmeSansBonus(Circuit circuit, char algo, int distance){
        new Sommet(0, 0);
        Sommet chemin = switch (algo) {
            case 'p' -> circuit.parcoursEnLargeur(false, distance);
            case 'd' -> circuit.dijkrstA(false, distance);
            case 'a' -> circuit.AStar(false, distance);
            case 'l' -> circuit.parcoursEnLargeur(true, -1);
            case 'j' -> circuit.dijkrstA(true, -1);
            case '*' -> circuit.AStar(true, -1);
            default -> new Sommet(0, 0);
        };
        circuit.ajouteChemin(circuit.getTableau(),chemin,4);
        return chemin.getDistance();
    }

    /**
     * lance l'algorithme sur le circuit (cas avec bonus)
     * @param circuit : circuit utilisé
     * @param algo : algorithme choisit
     * @param distance : distance voulue
     * @return la distance finale du chemin
     */
    public int lanceAlgorithmeAvecBonus(Circuit circuit, char algo, int distance){
        Sommet chemin = new Sommet(0,0);
        ArrayList<Integer> liste;
        switch (algo) {
            case 'p' -> {
                liste = circuit.distancePossible('p');
                if (distance == circuit.getPCC('p')) {
                    chemin = circuit.parcoursEnLargeur(false, -1);
                } else {
                    chemin = circuit.trajetAleatoireInt(liste, 'p');
                }
            }
            case 'd' -> {
                liste = circuit.distancePossible('d');
                if (distance == circuit.getPCC('d')) {
                    chemin = circuit.dijkrstA(false, -1);
                } else {
                    chemin = circuit.trajetAleatoireInt(liste, 'd');
                }
            }
            case 'a' -> {
                liste = circuit.distancePossible('a');
                if (distance == circuit.getPCC('a')) {
                    chemin = circuit.AStar(false, -1);
                } else {
                    chemin = circuit.trajetAleatoireInt(liste, 'a');
                }
            }
            case 'l' -> chemin = circuit.parcoursEnLargeur(true, -1);
            case 'j' -> chemin = circuit.dijkrstA(true, -1);
            case '*' -> chemin = circuit.AStar(true, -1);
        }
        circuit.ajouteChemin(circuit.getTableau(),chemin,4);
        return chemin.getDistance();
    }

    /**
     * retourne une distance d'un parcours
     * @param pcc : distance pcc ou non
     * @param circuit : circuit utilisé
     * @param alg : algorithme choisit
     * @return distance d'un chemin
     */
    public int getDistance(boolean pcc, Circuit circuit, char alg){
        if (pcc){
            return circuit.getPCC(alg);
        }else {
            ArrayList<Integer> liste = circuit.distancePossible(alg);
            Random rand = new Random();
            int pos = rand.nextInt(liste.size());
            return liste.get(pos);
        }
    }

    //--------------------------------------------------------------
    //                  Comparaison algos
    //--------------------------------------------------------------

    /**
     * lance la comparaison de 2 algorithme sur un circuit
     * @param a1 : algo 1
     * @param d1 : dsitance pcc de l'algo 1 ou non
     * @param a2 : algo 2
     * @param d2 : distance pcc de l'algo 2 ou non
     * @param bonus : bonus sur le circuit (ou non)
     * @param numCircuit : numéro du circuit choisit
     */
    public void comparaison(String a1,boolean d1, String a2, boolean d2, boolean bonus, int numCircuit){
        Circuit circuit = retrouveCircuit(numCircuit,bonus);
        char alg1 = recupererAlgo(a1);
        char alg2 = recupererAlgo(a2);
        int dist1 = getDistance(d1,circuit,alg1);
        int dist2 = getDistance(d2,circuit,alg2);
        circuit.concoursAlgos(alg1,dist1,alg2,dist2);
        affichage.ecranComparaison(a1,dist1,a2,dist2,circuit);
    }

    /**
     * convertir un string en char
     * @param a1 : String de l'algo choisit
     * @return le char correspondant a l'algo
     */
    public char recupererAlgo(String a1){
        return switch (a1) {
            case "PL" -> 'p';
            case "Dijkstra" -> 'd';
            case "A*" -> 'a';
            case "PL (variante)" -> 'l';
            case "Dijkstra (variante)" -> 'j';
            case "A* (variante)" -> '*';
            default -> ' ';
        };
    }

    /**
     * retourne si le String est un pcc ou non
     * @param pcc : String Pcc ou non
     * @return un boolean déterminant si on veut le pcc ou non
     */
    public boolean recupererDistance(String pcc){
        return pcc.equals("PCC");
    }

    //--------------------------------------------------------------
    //                   Parties
    //--------------------------------------------------------------

    /**
     * lance l'ecran du choix des mode de la partie
     */
    public void choixMode(){
        affichage.ecranChoixModePartie();
    }

    /**
     * commence une partie
     * @param cir : circuit utilisé
     * @param mode : modé choisit
     * @param bonus : bonus ou non sur le circuit
     * @param nb : nombre de joueur/robot
     */
    public void commencerPartie(int cir, String mode, String bonus, String nb){
        boolean bon = bonus.equals("Avec Bonus");
        Circuit circuit = retrouveCircuit(cir,bonus);
        Joueur.setIncrement(1);
        if (mode.equals("Seul")){
            Joueur joueur = new Joueur(circuit.getDepart().getJ(),circuit.getDepart().getI());
            Partie partie = new Partie(joueur,circuit);
            circuitInitial(partie);
        } else if (mode.equals("Avec des Robots")){
            Joueur joueur = new Joueur(circuit.getDepart().getJ(),circuit.getDepart().getI());
            Partie partie = new Partie(joueur,circuit);
            circuitRobot(partie,Integer.parseInt(nb),bon);
        } else {
            Joueur joueur = new Joueur(circuit.getDepart().getJ(),circuit.getDepart().getI());
            Partie partie = new Partie(joueur,circuit);
            circuitJoueurs(partie,Integer.parseInt(nb));
        }
    }

    /**
     * retrouve un circuit en fonction des parametres
     * @param c : numéro du circuit
     * @param bon : bonus ou non sur le circuit
     * @return un circuit correspondant aux attentes
     */
    public Circuit retrouveCircuit(int c, String bon){
        boolean bonus;
        bonus = bon.equals("Avec Bonus");
        return retrouveCircuit(c,bonus);
    }

    //--------------------------------------------------------------
    //                   Jeu solo
    //--------------------------------------------------------------

    /**
     * lance une partie solo
     * @param partie : partie en cours
     */
    public void circuitInitial(Partie partie){
        partie.getJoueur().listeSuccesseurs(partie.getCircuit());
        int jy = partie.getJoueur().getY();
        int jx = partie.getJoueur().getX();
        partie.setCasesPossibilite(partie.getJoueur(),partie.getCircuit().getTableau());
        partie.setAffichagejoueur(partie.getJoueur(),partie.getCircuit().getTableau());
        if (partie.getJoueur().getPossibilite().isEmpty()){
            affichage.ecranFinPartieRobot(false,new LinkedHashMap<String,Integer>());
        }else {
            affichage.ecranCircuitJeuSolo(partie,new LinkedHashMap<String,Integer>());
            partie.getCircuit().getTableau()[jy][jx] = 1;
            partie.reAffichagePossibilitesNormale(partie.getJoueur());
            affichageNormalAvecBonus(partie);
            partie.getJoueur().listeSuccesseurs(partie.getCircuit());
            partie.getJoueur().setPossibilite(partie.getJoueur().getPossibilite());
        }
    }

    /**
     * reaffiche le tableau du circuit de la partie normalement (bonus..)
     * @param partie : partie en cours
     */
    public void affichageNormalAvecBonus(Partie partie) {
        for (Sommet s : partie.getJoueur().getPossibilite()) {
            int x = s.getJ();
            int y = s.getI();
            if (s.getBonus()) {
                partie.getCircuit().getTableau()[y][x] = 7;
            }
            if (s.getMalus()) {
                partie.getCircuit().getTableau()[y][x] = 8;
            }
        }
    }

    //--------------------------------------------------------------
    //                   Jeu Multi
    //--------------------------------------------------------------

    /**
     * lance une partie multijoueurs
     * @param partie : partie en cours
     * @param nbJoueur : nombre de joueurs
     */
    public void circuitJoueurs(Partie partie, int nbJoueur){
        ArrayList<Joueur> tour = new ArrayList<Joueur>();
        ArrayList<Joueur> podium = new ArrayList<Joueur>();
        ArrayList<Joueur> crash = new ArrayList<Joueur>();
        tour.add(partie.getJoueur());
        for (int i =0;i<nbJoueur;i++){
            tour.add(new Joueur(partie.getCircuit().getDepart().getJ(),partie.getCircuit().getDepart().getI()));
        }
        partie.getJoueur().listeSuccesseurs(partie.getCircuit());
        jeuMulti(partie,tour,-1,podium,crash, new ArrayList<Joueur>());
    }

    /**
     * verifie si un joueur a gané ou perdu
     * @param partie : partie en cours
     * @param tour : liste de l'ordre des joeurs
     * @param pos : position du joueur dans la liste tour
     * @param podium : liste du classement des gagnant
     * @param crash : liste du classement des perdants
     * @param maj : liste permettant l'update de tour
     */
    public void verifGagnePerd(Partie partie, ArrayList<Joueur> tour, int pos, ArrayList<Joueur> podium, ArrayList<Joueur> crash, ArrayList<Joueur> maj) {
        tour.get(pos).listeSuccesseurs(partie.getCircuit());
        if ((tour.get(pos).getX() == partie.getCircuit().getArrivee().getJ() && tour.get(pos).getY() == partie.getCircuit().getArrivee().getI())) {
            podium.add(tour.get(pos));
            maj.add(tour.get(pos));
            genererPopUp(tour.get(pos),true);
        } else if ((tour.get(pos).getPossibilite().isEmpty())) {
            crash.add(tour.get(pos));
            maj.add(tour.get(pos));
            genererPopUp(tour.get(pos),false);
        }
        jeuMulti(partie,tour,pos,podium,crash,maj);
    }

    /**
     * partie multi
     * @param partie : partie en cours
     * @param tour : liste des joueurs
     * @param pos : position du joueur dans tour
     * @param podium liste classement gagnant
     * @param crash : liste classement perdant
     * @param maj : liste permettant l'update de tour
     */
    public void jeuMulti(Partie partie, ArrayList<Joueur> tour, int pos, ArrayList<Joueur> podium, ArrayList<Joueur> crash, ArrayList<Joueur> maj){
        if (pos == tour.size()-1){
            pos = 0;
            majListe(partie,tour,maj);
        } else {
            pos+=1;
        }
        partie.afficheLesJoueurs(tour,partie.getCircuit());
        if (!(tour.isEmpty())){ //liste pas vide
            partie.setAffichagejoueur(tour.get(pos), partie.getCircuit().getTableau());
            tourMultiGraph(partie,tour,pos,podium,crash,maj);
        } else { //affichage classement
            ArrayList<Joueur> finale = faireListeFinale(podium,crash);
            affichage.ecranFinPartieJoueurs(finale);
        }
    }

    /**
     * update lecran du circuit (affichage graphique)
     * @param partie : partie en cours
     * @param tour : liste des joueurs
     * @param pos : position du joueur dans tour
     * @param podium liste classement gagnant
     * @param crash : liste classement perdant
     * @param maj : liste permettant l'update de tour
     */
    public void tourMultiGraph(Partie partie, ArrayList<Joueur> tour, int pos, ArrayList<Joueur> podium, ArrayList<Joueur> crash, ArrayList<Joueur> maj){
        tour.get(pos).listeSuccesseurs(partie.getCircuit());
        tour.get(pos).setPossibilite(tour.get(pos).getPossibilite());
        int jy = tour.get(pos).getY();
        int jx = tour.get(pos).getX();
        partie.setCasesPossibilite(tour.get(pos), partie.getCircuit().getTableau());
        //circuit.afficheCircuit(circuit.getTableau());
        //setCoordonnee(j);
        affichage.ecranCircuitJeuJoueursTourJoueur(partie,tour,pos,podium,crash,maj);
        partie.getCircuit().getTableau()[jy][jx] = 1;
        partie.reAffichagePossibilitesNormale(tour.get(pos));
        for (Sommet s : tour.get(pos).getPossibilite()) {
            int x = s.getJ();
            int y = s.getI();
            if (s.getBonus()) {
                partie.getCircuit().getTableau()[y][x] = 7;
            }
            if (s.getMalus()) {
                partie.getCircuit().getTableau()[y][x] = 8;
            }
        }
    }

    /**
     * met a jour la liste tour des joueurs en fonction de maj
     * @param partie : partie en cours
     * @param tour : liste des joueurs encore en train de jouer
     * @param maj : liste des joueurs ayant gagnes/perdus
     */
    public void majListe(Partie partie, ArrayList<Joueur> tour, ArrayList<Joueur> maj){
        for (Joueur joueur : maj) {
            partie.getCircuit().getTableau()[joueur.getY()][joueur.getX()] = 1;
            tour.remove(joueur);
        }
        maj = new ArrayList<Joueur>();
    }

    /**
     * etablie une liste du classement finale en fonction des 2 listes
     * @param podium : liste des gagnants
     * @param crash : liste des perdants
     * @return une liste du classement générale
     */
    public ArrayList<Joueur> faireListeFinale(ArrayList<Joueur> podium, ArrayList<Joueur> crash){
        ArrayList<Joueur> finale = new ArrayList<>(podium);
        for(int j = crash.size() - 1 ; j >= 0; j--){
            finale.add(crash.get(j));
        }
        return finale;
    }

    /**
     * genere un pop up quand un joueur gagne ou perd
     * @param joueur : joueur qui a gagné/perdu
     * @param win : determine s'il a gagné ou perdu
     */
    public void genererPopUp(Joueur joueur, boolean win){
        if (win){
            URL urlImage = getClass().getResource("/gagne.jpg");
            Icon icone = new ImageIcon(urlImage);
            JOptionPane.showMessageDialog (affichage, "Le Joueur "+joueur.getNumJoueur()+" est arrive a la fin du circuit !","Information",JOptionPane.INFORMATION_MESSAGE,icone);
        } else {
            URL urlImage = getClass().getResource("/perdu.jpg");
            Icon icone = new ImageIcon(urlImage);
            JOptionPane.showMessageDialog (affichage, "Le Joueur "+joueur.getNumJoueur()+" est rentre dans un mur :(","Information",JOptionPane.INFORMATION_MESSAGE,icone);
        }
    }
    //--------------------------------------------------------------
    //                   Jeu Robots
    //--------------------------------------------------------------

    /**
     *  initialise les robots
     * @param partie : partie en cours
     * @param nbRobot : nombre de robot voulus
     * @param bonus : bonus ou non sur le circuit
     */
    public void circuitRobot(Partie partie, int nbRobot, boolean bonus){
        ArrayList<Sommet> listeRobot = partie.initRobots(nbRobot,partie.getCircuit());
        Circuit circuit = partie.getCircuit();
        LinkedHashMap<String, Integer> ordreArrivee = new LinkedHashMap<>();
        //initialisation du tableau avec les robots dedans
        for (int i=0;i<listeRobot.size();i++){
            circuit.getTableau()[listeRobot.get(i).getI()][listeRobot.get(i).getJ()] = 50+i;
        }
        jeuAvecRobotsTourJoueurs(partie,listeRobot,ordreArrivee,0,0);
    }

    /**
     * jeu avec des robots
     * @param partie : partie en cours
     * @param liste : liste des robots
     * @param ordre : ordre d'arrivée
     * @param total : total de personne ayant fini/echoué
     * @param distance : distance du joueur
     */
    public void jeuAvecRobotsTourJoueurs(Partie partie, ArrayList<Sommet> liste, LinkedHashMap<String, Integer> ordre, int total, int distance){
        if (partie.getJoueur().getX() == partie.getCircuit().getArrivee().getJ() && partie.getJoueur().getY() == partie.getCircuit().getArrivee().getI()){
            ordre.put("Vous (Joueur)",distance);
            total++;
            pourFinirLeJeu(total,liste,ordre, partie);
            affichage.ecranFinPartieRobot(true,ordre);
        }else {
            partie.setAffichagejoueur(partie.getJoueur(),partie.getCircuit().getTableau());
            partie.getJoueur().listeSuccesseurs(partie.getCircuit());
            partie.getJoueur().setPossibilite(partie.getJoueur().getPossibilite());
            if (partie.getJoueur().getPossibilite().isEmpty()) {
                total++;
                if (total == liste.size() + 1) {
                    affichage.ecranFinPartieRobot(false,ordre);
                } else {
                    pourFinirLeJeu(total,liste,ordre, partie);
                    affichage.ecranFinPartieRobot(false,ordre);
                }
            } else {
                partie.setCasesPossibilite(partie.getJoueur(), partie.getCircuit().getTableau());
                distance++;
                affichage.ecranCircuitJeuRobotTourJoueur(partie, liste, ordre, total,distance);
                partie.reAffichagePossibilitesNormale(partie.getJoueur());
                partie.setAffichagejoueur(partie.getJoueur(),partie.getCircuit().getTableau());
            }
        }
    }

    /**
     * fin du jeu lorsque il ne reste que les robots
     * @param total : nombre de joueurs ayant fini/echoué
     * @param liste : liste des robots
     * @param ordre : ordre d'arrivée
     * @param partie : partie en cours
     */
    public void pourFinirLeJeu(int total, ArrayList<Sommet> liste, LinkedHashMap<String,Integer> ordre, Partie partie){
        while (total != liste.size() + 1) {
            for (int i = 0; i < liste.size(); i++) {
                if (!ordre.containsKey("Robot " + (i + 1))) {
                    partie.tourRobot(liste, i);
                    total = getPerduGagne(liste, ordre, total, i,partie);
                }
            }
        }
    }

    /**
     * tour des robots
     * @param partie : partie en cours
     * @param liste : liste des robots
     * @param ordre : ordre d'arrivée
     * @param total : total de personne ayant fini/echoué
     * @param distance : distance du joueur
     */
    public void jeuAvecRobotsTourRobot(Partie partie, ArrayList<Sommet> liste, LinkedHashMap<String, Integer> ordre, int total, int distance) {
        affichageNormalAvecBonus(partie);
        for (int i = 0; i < liste.size(); i++) {
            if (!ordre.containsKey("Robot "+(i+1))) {
                partie.tourRobot(liste, i);
                total = getPerduGagne(liste, ordre, total, i,partie);
            }
        }
        partie.reAffichagePossibilitesNormale(partie.getJoueur());
        jeuAvecRobotsTourJoueurs(partie, liste, ordre, total,distance);
    }

    /**
     * retourne si le robot a perdu ou gagne
     * @param listeRobot : liste des robots
     * @param ordreArrivee : ordre d'arrivee (classement)
     * @param perduGagne : nombre de robots ayant perdu/gagne
     * @param i : numero du robot ds la liste
     * @return un int
     */
    public int getPerduGagne(ArrayList<Sommet> listeRobot, LinkedHashMap<String, Integer> ordreArrivee, int perduGagne, int i, Partie partie) {
        if (listeRobot.get(i).getI() == partie.getCircuit().getArrivee().getI() && listeRobot.get(i).getJ() == partie.getCircuit().getArrivee().getJ()) {
            ordreArrivee.put("Robot " + (i + 1), listeRobot.get(i).getDistance());
            perduGagne++;
            partie.getCircuit().getTableau()[listeRobot.get(i).getI()][listeRobot.get(i).getJ()] = 3;
        }
        return perduGagne;
    }

    //--------------------------------------------------------------
    //           Fonctions Communes aux Parties
    //--------------------------------------------------------------

    /**
     * affiche l'ecran de fin de partie
     * @param partie
     * @param ordre
     */
    public void afficheFin(Partie partie, LinkedHashMap<String,Integer> ordre){
        affichage.ecranFinPartieRobot(true,ordre);
    }

    /**
     * met a jour le joueur sur sa possibilite voulue
     * @param i : i de la possibilite
     * @param j ; j de la possibilite
     * @param joueur : joueur
     * @param partie : partie en cours
     */
    public void getPossibilite(int i, int j, Joueur joueur, Partie partie){
        for (int k = 0; k < joueur.getPossibilite().size(); k++) {
            if (joueur.getPossibilite().get(k).getI() == i && joueur.getPossibilite().get(k).getJ() == j) {
                Sommet tmp = joueur.getPossibilite().get(k);
                int x = tmp.getJ();
                int y = tmp.getI();
                int joueurX = joueur.getX();
                int joueurY = joueur.getY();
                joueur.setX(x);
                joueur.setY(y);
                joueur.setVitesseX(tmp.getVitesseX());
                joueur.setVitesseY(tmp.getVitesseY());
                partie.setLesBonusMalus(tmp,joueurX,joueurY,x,y, joueur);
                return;
            }
        }
    }

}
