package Controleur;

import Modele.*;

import java.util.Scanner;

public class Programme {

    public Programme(){
        System.out.println("Bienvenue dans le Programme.");
    }

    //------------------------------------------------------------
    //           Fonctions communes
    //------------------------------------------------------------

    /**
     * fonction gerant une demande de réponse
     * @return la réponse entrée dans la console
     */
    public String demandeReponse(){
        System.out.print("\nEntrez votre choix :");
        Scanner sc = new Scanner(System.in);
        String rep =  sc.nextLine();
        System.out.println();
        return rep;

    }

    /**
     * fonction gérant une demande de circuit
     * @param bonus : qui permet de déterminer si sur le circuit, on veut des bonus ou non
     * @return un circuit en fonction du choix de l'utilisateur, et du bonus
     */
    public Circuit demandeCircuit(boolean bonus){
        System.out.println("Choisissez un circuit :");
        afficheChoixCircuit();
        String reponse = demandeReponse();
        while (! reponseValideChoixCircuit(reponse)){
            System.out.println("Merci de rentrer une reponse correcte parmis les choix suivants :");
            afficheChoixCircuit();
            reponse = demandeReponse();
        }
        return creationCircuit(reponse,bonus);
    }

    /**
     * fonction affichant les circuits que l'on peut choisir
     */
    public void afficheChoixCircuit(){
        System.out.println("\t1 : Circuit 1");
        System.out.println("\t2 : Circuit 2");
        System.out.println("\t3 : Circuit 3");
        System.out.println("\t4 : Circuit 4");
        System.out.println("\t5 : Circuit 5");
        System.out.println("\tA : Revenir a l'accueil");
        System.out.println("\tE : Quitter le programme");
    }

    /**
     * fonction déterminant si ma réponse de mon choix du circuit est correcte ou non
     * @param choix : choix choisis par l'utilisateur dans la console précédement
     * @return un boolean déterminant si la réponse entrée etait correcte ou non
     */
    public boolean reponseValideChoixCircuit(String choix){
        return (choix.equals("1") || choix.equals("2") || choix.equals("3") || choix.equals("4") || choix.equals("5") || choix.equals("A") || choix.equals("E"));
    }

    /**
     *  fonction créant un circuit en fonction des choix de l'utilisateur
     * @param choix : choix choisi par l'utilisateur déterminant le circuit voulu
     * @param bonus : boolean déterminant si les bonus seront (ou non) mis dans le circuit à sa création
     * @return un circuit contruit en fonction des choix de l'utilisateur (numéro du circuit et bonus)
     */
    public Circuit creationCircuit(String choix, boolean bonus){
        switch (choix) {
            case "1":
                return new Circuit("circuit1.ppm","circuit1",bonus);
            case "2":
                return new Circuit("circuit2.ppm","circuit2",bonus);
            case "3":
                return new Circuit("circuit3.ppm","circuit3",bonus);
            case "4":
                return new Circuit("circuit4.ppm","circuit4",bonus);
            case "5":
                return new Circuit("circuit5.ppm","circuit5",bonus);
            case "A":
                lancementConsole();
                break;
            case "E":
                System.exit(0);
                break;
        }
        return null;
    }

    /**
     * fonction qui gère la demande de bonus dans un circuit
     * @return la réponse de l'utilisateur sous forme de boolean
     */
    public boolean questionBonus(){
        System.out.println("Voulez vous implementez des bonus ?");
        choixImplemBonus();
        String choix = demandeReponse();
        while (!reponseValideChoixBonus(choix)){
            System.out.println("Merci de rentrer une reponse correcte.");
            choix = demandeReponse();
        }
        return choixEnBooleanBonus(choix);
    }

    /**
     * fonction affichant les choix possible pour la question du bonus
     */
    public void choixImplemBonus(){
        System.out.println("\t1 : Oui (bonus et malus)");
        System.out.println("\t2 : Non (aucun boost)");
        System.out.println("\tA : Revenir a l'accueil");
        System.out.println("\tE : Quitter le programme");
    }

    //------------------------------------------------------------
    //           0 Lancement du Programme
    //------------------------------------------------------------

    /**
     * lancement du programme : 1ere fonction appelée lors du démarrage
     */
    public void lancement(){
        System.out.println("Comment voulez vous utiliser le Programme ?");
        afficheDebut();
        String reponse = demandeReponse();
        while(! reponseValide(reponse)){
            System.out.println("Merci de rentrer une reponse correcte parmis les choix suivants :");
            afficheDebut();
            reponse = demandeReponse();
        }
        lanceDebut(reponse);
    }

    /**
     * lance le mode console/graphique en fonction du choix de l'utilisateur
     * @param reponse : choix de l'utilisateur de sa façon dont il veut utiliser le programme
     */
    public void lanceDebut(String reponse){
        if (reponse.equals("2")){
            lancementConsole();
        }else {
            lancementGraphique();
        }
    }

    /**
     * fonction déterminant si ma réponse dans la console est correcte ou non
     * @param choix : choix de l'utilisateur
     * @return si le choix est valide ou non
     */
    public boolean reponseValide(String choix){
        if (choix.equals("A")){
            lancement();
        }else if (choix.equals("E")){
            System.exit(0);
        }
        return choix.equals("1") || choix.equals("2");
    }

    /**
     * fonction affichant les modes possibles du programme (console ou graphique)
     */
    public void afficheDebut(){
        System.out.println("\t1 : En Mode Graphique");
        System.out.println("\t2 : En Mode Console");
        System.out.println("\tA : Revenir a l'accueil");
        System.out.println("\tE : Quitter le programme");
    }

    /*** lance le mode graphique
     *
     */
    public void lancementGraphique(){
        Controleur controleur = new Controleur();
        controleur.lancement();
    }

    /*** lance le mode console
     *
     */
    public void lancementConsole(){
        System.out.println("Que voulez-vous faire ?");
        afficheChoix();
        String reponse = demandeReponse();
        while(! reponseValideChoixDebut(reponse)){
            System.out.println("Merci de rentrer une reponse correcte parmis les choix suivants :");
            afficheChoix();
            reponse = demandeReponse();
        }
        lancementChoix(reponse);
    }

    /*** affiche les choix possible à faire quand on est dans le mode console
     *
     */
    public void afficheChoix(){
        System.out.println("\t1 : Voir une demo (trajet Aleatoire)");
        System.out.println("\t2 : Lancer un Algorithme (PCC)");
        System.out.println("\t3 : Faire une Comparaison entre 2 Algorithmes");
        System.out.println("\t4 : Jouer sur un circuit");
        System.out.println("\tE : Quitter le programme");
    }

    /*** affiche le choix de l'utilisateur pour faire une introduction
     *
     * @param rep : choix de l'utilsateur
     */
    public void afficheChoixChoisis(String rep){
        switch (rep){
            case "1"->System.out.println("Vous avez choisis de voir une demo.\nChoisissez l'Algorithme.");
            case "2"->System.out.println("Vous avez choisis de lancer un Algorithme.\nChoisissez l'Algorithme.");
            case "3"->System.out.println("Vous avez choisis de lancer la comparaison d'Algorithmes.\nVous devez choisir 2 Algorithmes a comparer.\n Voici la liste des Algorithmes :");
            case "4"->System.out.println("Vous avez choisis de jouer.");
        }
    }

    /*** fonction déterminant si le choix de l'utilsateur est correcte ou non
     *
     * @param reponse : choix de l'utilisateur
     * @return un boolean qui détermine si la réponse est valide ou non
     */
    public boolean reponseValideChoixDebut(String reponse){
        if (reponse.equals("E")){
            System.exit(0);
        }
        return reponse.equals("1") || reponse.equals("2") || reponse.equals("3") || reponse.equals("4");
    }

    /**
     * lance la fonction associée au choix de l'utilisateur
     * @param choix : action décidée par l'utilsateur
     */
    public void lancementChoix(String choix){
        afficheChoixChoisis(choix);
        switch (choix) {
            case "1" -> lancementDemo();
            case "2" -> lancementAlgo();
            case "3" -> lancementConcours();
            case "4" -> lancementPartie();
        }
    }

    //------------------------------------------------------------
    //           1 Voir une Demo (trajet Aleatoire)
    //------------------------------------------------------------

    /**
     * lancement d'une démo
     */
    public void lancementDemo(){
        afficheChoixAlgorithmes();
        String choixAlgo = demandeReponse();
        while (! reponseValideChoixAlgo(choixAlgo, false)){
            System.out.println("Merci de rentrer une reponse correcte parmis les choix suivants :");
            afficheChoixAlgorithmes();
            choixAlgo = demandeReponse();
        }
        boolean bonus = questionBonus();
        Circuit choixCir = demandeCircuit(bonus);
        recapAlgoChoisi(choixAlgo,choixCir,bonus,false);
        lanceAlgoChoisi(choixAlgo,choixCir,false);
        System.out.println();
        lancementConsole();
    }

    //------------------------------------------------------------
    //           2 Lancer un Algorithme (PCC)
    //------------------------------------------------------------

    /**
     * lancement d'un trajet PCC
     */
    public void lancementAlgo(){
        afficheChoixAlgorithmes();
        String choixAlgo = demandeReponse();
        while (! reponseValideChoixAlgo(choixAlgo, false)){
            System.out.println("Merci de rentrer une reponse correcte parmis les choix suivants :");
            afficheChoixAlgorithmes();
            choixAlgo = demandeReponse();
        }
        boolean bonus = questionBonus();
        Circuit choixCir = demandeCircuit(bonus);
        recapAlgoChoisi(choixAlgo,choixCir,bonus,true);
        lanceAlgoChoisi(choixAlgo,choixCir,true);
        System.out.println();
        lancementConsole();
    }

    /**
     * affiche les algorithmes possibles de choisir
     */
    public void afficheChoixAlgorithmes(){
        System.out.println("\t1 : Parcours en Largeur");
        System.out.println("\t2 : Dijkstra");
        System.out.println("\t3 : A*");
        System.out.println("\t4 : Variante du PL (arrivee a vitesse nulle)");
        System.out.println("\t5 : Variante de Dijkstra (arrivee a vitesse nulle)");
        System.out.println("\t6 : Variante de A* (arrivee a vitesse nulle)");
        System.out.println("\tA : Revenir a l'accueil");
        System.out.println("\tE : Quitter le programme");
    }

    /**
     * détermine si le choix de l'algorithme est correct ou non
     * @param choix : choix de l'utilisateur
     * @param sautLigne : détermine si on saute une ligne (uniquement esthétique)
     * @return un boolean déterminant si la réponse est correcte ou non
     */
    public boolean reponseValideChoixAlgo(String choix, boolean sautLigne){
        if (choix.equals("A")){
            if (sautLigne){
                System.out.println();
            }
            lancementConsole();
        }else if (choix.equals("E")){
            System.exit(0);
        }
        return (choix.equals("1") || choix.equals("2") || choix.equals("3") ||
                choix.equals("4") || choix.equals("5") || choix.equals("6"));
    }

    /**
     * lance l'algorithme choisit sur le circuit
     * @param choix : choix de l'utilisateur
     * @param circuit : circuit choisit
     * @param pcc : détermine si on veut un chemin pcc ou non
     */
    public void lanceAlgoChoisi(String choix, Circuit circuit, boolean pcc){
        Sommet fin = new Sommet(0,0);
        switch (choix) {
            case "1" -> {
                if (pcc) {
                    fin = circuit.parcoursEnLargeur(false, -1);
                } else {
                    fin = circuit.trajetAleatoireInt(circuit.distancePossible('p'),'p');
                }
                circuit.cheminArrivee(fin, circuit.getTableau());
            }
            case "2" -> {
                if (pcc) {
                    fin = circuit.dijkrstA(false, -1);
                } else {
                    fin = circuit.trajetAleatoireInt(circuit.distancePossible('d'),'d');
                }
                circuit.cheminArrivee(fin, circuit.getTableau());
            }
            case "3" -> {
                if (pcc) {
                    fin = circuit.AStar(false, -1);
                } else {
                    fin = circuit.trajetAleatoireInt(circuit.distancePossible('a'),'a');
                }
                circuit.cheminArrivee(fin, circuit.getTableau());
            }
            case "4" -> {
                fin = circuit.parcoursEnLargeur(true, -1);
                circuit.cheminArrivee(fin, circuit.getTableau());
            }
            case "5" -> {
                fin = circuit.dijkrstA(true, -1);
                circuit.cheminArrivee(fin, circuit.getTableau());
            }
            case "6" -> {
                fin = circuit.AStar(true, -1);
                circuit.cheminArrivee(fin, circuit.getTableau());
            }
        }
        fin.afficheSommet();
    }

    /**
     * récapitule les informations
     * @param choix : choix de l'utilisateur
     * @param circuit : circuit sur lequel on lance l'algorithme
     * @param bonus : détermine s'il y a ou non des bonus sur le circuit
     * @param pcc : détermine si le chemin est PCC ou non
     */
    public void recapAlgoChoisi(String choix, Circuit circuit, boolean bonus, boolean pcc){
        String bon;
        if (bonus){
            bon = " avec des bonus/malus.";
        }else {
            bon = " sans bonus/malus";
        }
        String dis;
        if (pcc){
            dis = "le PCC";
        }else {
            dis = "un chemin aleatoire";
        }
        switch (choix) {
            case "1" -> System.out.println("Vous avez decide de lancer "+dis+" de PL sur le " + circuit.getNom() + bon);
            case "2" -> System.out.println("Vous avez decide de lancer "+dis+" de Dijkstra sur le " + circuit.getNom() + bon);
            case "3" -> System.out.println("Vous avez decide de lancer "+dis+" de A* sur le " + circuit.getNom() + bon);
            case "4" -> System.out.println("Vous avez decide de lancer "+dis+" de PL (varitante) sur le " + circuit.getNom() + bon);
            case "5" -> System.out.println("Vous avez decide de lancer "+dis+" de Dijkstra (variante) sur le " + circuit.getNom() + bon);
            case "6" -> System.out.println("Vous avez decide de lancer "+dis+" de A* (variante) sur le " + circuit.getNom() + bon);
        }
    }

    //------------------------------------------------------------
    //           3 Faire un concours entre 2 Algorithmes
    //------------------------------------------------------------

    /**
     * lancement d'un concours d'algorithme
     */
    public void lancementConcours(){
        afficheChoixAlgorithmes();
        System.out.print("\nChoisissez le 1er :");
        String reponse1 = demandeReponse();
        while (! reponseValideChoixAlgo(reponse1,true)){
            System.out.println("Merci de rentrer une reponse correcte parmi les choix ci-dessus :");
            reponse1 = demandeReponse();
        }
        afficheChoixDistance();
        String distance1 = demandeReponse();
        while (! reponseValideChoixDistance(distance1)){
            System.out.println("Merci de rentrer une reponse correcte parmi les choix ci-dessus :");
            distance1 = demandeReponse();
        }
        System.out.print("Choisissez le 2eme :");
        String reponse2 = demandeReponse();
        while (! reponseValideChoixAlgo(reponse2,true) || reponse2.equals(reponse1)){
            if (reponse1.equals(reponse2)){
                System.out.println("Vous ne pouvez pas comparer un Algorithme a lui meme.\nMerci de choisir un autre algorithme parmi ceux listes ci-dessus");
            }
            else {
                System.out.println("Merci de rentrer une reponse correcte parmi les choix ci-dessus :");
            }
            reponse2 = demandeReponse();
        }
        afficheChoixDistance();
        String distance2 = demandeReponse();
        while (! reponseValideChoixDistance(distance2)){
            System.out.println("Merci de rentrer une reponse correcte parmi les choix ci-dessus :");
            distance2 = demandeReponse();
        }
        boolean bonus = questionBonus();
        Circuit circuit = demandeCircuit(bonus);
        afficheCircuitChoisis(reponse1,1);
        afficheCircuitChoisis(reponse2,2);
        circuit.concoursAlgos(charAlgo(reponse1),recupereDistance(distance1,reponse1,circuit),charAlgo(reponse2),recupereDistance(distance2,reponse2,circuit));
        circuit.afficheCircuit(circuit.getTableau());
        System.out.println();
        lancementConsole();
    }

    /**
     * permet de récuperer une distance
     * @param distance : string déterminant si on veut le pcc ou non
     * @param rep : string déterminant l'algorithme choisit
     * @param circuit : circuit utilisé
     * @return la distance qu'aura le chemin sur le circuit
     */
    public int recupereDistance(String distance, String rep, Circuit circuit){
        switch (rep){
            case "p" :
                if (distance.equals("1")){
                    return -1;
                } else {
                    return circuit.trajetAleatoireInt(circuit.distancePossible('p'),'p').getDistance();
                }
            case "d" :
                if (distance.equals("1")){
                    return -1;
                } else {
                    return circuit.trajetAleatoireInt(circuit.distancePossible('d'),'d').getDistance();
                }
            case "a" :
                if (distance.equals("1")){
                    return -1;
                } else {
                    return circuit.trajetAleatoireInt(circuit.distancePossible('a'),'a').getDistance();
                }
            case "l" :
            case "j" :
            case "*" :
                return -1;
        }
        return -1;
    }

    /**
     * récapitule l'algorithme choisit
     * @param choix : choix de l'algorithme
     * @param num : détermine le numéro de l'algorithme choisit (1 ou 2)
     */
    public void afficheCircuitChoisis(String choix, int num){
        switch (choix) {
            case "1" -> System.out.println("Votre Algorithme " + num + " est : Parcours en Largeur");
            case "2" -> System.out.println("Votre Algorithme " + num + " est : Dijkstra");
            case "3" -> System.out.println("Votre Algorithme " + num + " est : A*");
            case "4" -> System.out.println("Votre Algorithme " + num + " est : Variante du Parcours en Largeur (vitesse d'arrivee nulle)");
            case "5" -> System.out.println("Votre Algorithme " + num + " est : Variante de Dijkstra (vitesse d'arrivee nulle)");
            case "6" -> System.out.println("Votre Algorithme " + num + " est : Variante de A* (vitesse d'arrivee nulle)");
        }
    }

    /**
     * affiche les choix des distances possible (PCC ou aléatoire)
     */
    public void afficheChoixDistance(){
        System.out.println("Quelle distance souhaitez vous ?");
        System.out.println("\t1 : PCC");
        System.out.println("\t2 : Aleatoire");
        System.out.println("\tA : Revenir a l'accueil");
        System.out.println("\tE : Quitter le programme");
    }

    /**
     * détermine si la réponse est correcte ou non
     * @param choix : choix de la distance voulue
     * @return un boolean déterminant si la réponse choisis est correcte ou non
     */
    public boolean reponseValideChoixDistance(String choix){
        if (choix.equals("A")){
            lancementConsole();
        }else if (choix.equals("E")){
            System.exit(0);
        }
        return choix.equals("1") || choix.equals("2");
    }

    /**
     * convertit un String en Char
     * @param choix : numéro de l'algortihme voulue
     * @return un char déterminant de quel algorithme il s'agit
     */
    public char charAlgo(String choix){
        return switch (choix) {
            case "1" -> 'p';
            case "2" -> 'd';
            case "3" -> 'a';
            case "4" -> 'l';
            case "5" -> 'j';
            case "6" -> '*';
            default -> ' ';
        };
    }

    //------------------------------------------------------------
    //           4 Jouer
    //------------------------------------------------------------

    /**
     * lancement d'une partie
     */
    public void lancementPartie(){
        System.out.println("Quel mode voulez vous jouer ?");
        choixModeJeu();
        String modeJeu = demandeReponse();
        while (! reponseValideChoixMode(modeJeu)){
            System.out.println("Merci de rentrer une réponse correcte parmi les choix proposes.");
            modeJeu = demandeReponse();
        }
        lanceModeJeu(modeJeu);
        System.out.println();
        lancementConsole();
    }

    /**
     * affiche les mode possible d'une partie
     */
    public void choixModeJeu(){
        System.out.println("\t1 : Jouer Seul");
        System.out.println("\t2 : Jouer contre un/des robot(s)");
        System.out.println("\t3 : Jouer contre un/des autre(s) joueur(s) (meme terminal)");
        System.out.println("\tA : Revenir a l'accueil");
        System.out.println("\tE : Quitter le programme");
    }

    /**
     * détermine si le mode voulu d'une partie est correct ou non
     * @param rep : mode voulu
     * @return un boolean qui détermine si c'est correct ou non
     */
    public boolean reponseValideChoixMode(String rep){
        if (rep.equals("A")){
            lancementConsole();
        }else if (rep.equals("E")){
            System.exit(0);
        }
        return (rep.equals("1") || rep.equals("2") || rep.equals("3") );
    }

    /**
     * lance la partie selon le mode choisit
     * @param mode : mode voulu de l'utilisateur
     */
    public void lanceModeJeu(String mode){
        boolean bonus;
        Circuit circuit;
        Joueur.setIncrement(1);
        Joueur joueur;
        Partie partie;
        switch (mode) {
            case "1" -> {
                bonus = questionBonus();
                circuit = demandeCircuit(bonus);
                joueur = new Joueur(circuit.getDepart().getJ(), circuit.getDepart().getI());
                partie = new Partie(joueur, circuit);
                partie.jeuSolo();
            }
            case "2" -> {
                int nbBot = demandeBots();
                bonus = questionBonus();
                circuit = demandeCircuit(bonus);
                joueur = new Joueur(circuit.getDepart().getJ(), circuit.getDepart().getI());
                partie = new Partie(joueur, circuit);
                partie.jeuRobots(nbBot,bonus);
            }
            case "3" -> {
                int nbJoueur = demandeJoueur();
                bonus = questionBonus();
                circuit = demandeCircuit(bonus);
                joueur = new Joueur(circuit.getDepart().getJ(), circuit.getDepart().getI());
                partie = new Partie(joueur, circuit);
                partie.jeuMulti(nbJoueur);
                System.out.println();
            }
        }
    }

    /**
     * détermine si le choix du bonus voulu dans une partie est correct ou non
     * @param rep : réponse de l'utilisateur
     * @return un boolean qui détermine si c'est correct ou non
     */
    public boolean reponseValideChoixBonus(String rep){
        if (rep.equals("A")){
            System.out.println();
            lancementConsole();
        }else if (rep.equals("E")){
            System.exit(0);
        }
        return rep.equals("1") || rep.equals("2");
    }

    /**
     * convertit un string en boolean
     * @param rep : réponse en String
     * @return un boolean déterminant si on a (ou non) des bonus sur le circuit
     */
    public boolean choixEnBooleanBonus(String rep){
        return rep.equals("1");
    }

    /**
     * demande à l'utilisateur le nombre de robot voulu
     * @return le nombre choisit (une fois la vérification faite)
     */
    public int demandeBots(){
        System.out.println("Avec combien de robot voulez-vous jouer dans votre partie ?");
        choixBots();
        String choix = demandeReponse();
        while (!reponseValideChoixBots(choix)){
            System.out.println("Merci de rentrer une reponse correcte.");
            choix = demandeReponse();
        }
        return Integer.parseInt(choix);
    }

    /**
     * affiche les choix du nombre de robots possible
     */
    public void choixBots(){
        System.out.println("\t1 : 1 Robot");
        System.out.println("\t2 : 2 Robots");
        System.out.println("\t3 : 3 Robots");
        System.out.println("\tA : Revenir a l'accueil");
        System.out.println("\tE : Quitter le programme");
    }

    /**
     * détermine si la réponse est correcte ou non
     * @param choix : réponse choisie
     * @return un boolean déterminant si la réponse est correcte ou non
     */
    public boolean reponseValideChoixBots(String choix){
        if (choix.equals("A")){
            System.out.println();
            lancementConsole();
        } else if (choix.equals("E")){
            System.exit(0);
        }
        return choix.equals("1") || choix.equals("2") || choix.equals("3");
    }

    /**
     * détermine si la réponse est correcte ou non
     * @param choix : réponse choisie
     * @return un boolean déterminant si la réponse est correcte ou non
     */
    public boolean reponseValideChoixJoueurs(String choix){
        if (choix.equals("A")){
            System.out.println();
            lancementConsole();
        } else if (choix.equals("E")){
            System.exit(0);
        }
        return choix.equals("1") || choix.equals("2") || choix.equals("3") || choix.equals("4");
    }

    /**
     * demande à l'utilisateur le nombre de joueurs voulu
     * @return le nombre choisit (une fois la vérification faite)
     */
    public int demandeJoueur(){
        System.out.println("Combien de joueurs voulez-vous?");
        choixJoueurs();
        String choix = demandeReponse();
        while (!reponseValideChoixJoueurs(choix)){
            System.out.println("Merci de rentrer une reponse correcte.");
            choix = demandeReponse();
        }
        return Integer.parseInt(choix);
    }

    /**
     * affiche le nombre possible de joueurs
     */
    public void choixJoueurs(){
        System.out.println("\t1 : 1 Joueur");
        System.out.println("\t2 : 2 Joueurs");
        System.out.println("\t3 : 3 Joueurs");
        System.out.println("\t4 : 4 Joueurs");
        System.out.println("\tA : Revenir a l'accueil");
        System.out.println("\tE : Quitter le programme");
    }
}
