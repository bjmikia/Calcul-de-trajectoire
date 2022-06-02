package Modele;

import java.util.ArrayList;

public class Joueur {
    private int x;
    private int y;
    private int vitesseX;
    private int vitesseY;
    private final int numJoueur;
    private static int increment = 1;
    private boolean[] deplacementBonus ;
    private boolean[]  deplacementMalus;
    private int[] resetBonus ;
    private int[]  resetMalus;
    private boolean isMalusX;
    private boolean isMalusY;
    private int increTourBonus ;
    private int increTourMalus;
    private ArrayList<Sommet> possibilite;

    //--------------------------------------------------------------
  	//                  Constructeur
  	//--------------------------------------------------------------
    public Joueur(int x, int y){
        this.x = x;
        this.y = y;
        vitesseX = 0;
        vitesseY = 0;
        possibilite = new ArrayList<Sommet>();
        deplacementBonus = new boolean[2];
        deplacementMalus = new boolean[2];
        increTourBonus = 0;
        increTourMalus = 0;
        resetBonus = new int[2];
        resetMalus = new int[2];
        isMalusX = false;
        isMalusY = false;
        numJoueur = increment;
        increment++;
    }

    //--------------------------------------------------------------
    //                   Getters et Setters
    //--------------------------------------------------------------

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getVitesseX(){
        return vitesseX;
    }

    public int getVitesseY() {
        return vitesseY;
    }

    public void setX(int xo){
        this.x = xo;
    }

    public void setY(int yo){
        this.y = yo;
    }

    public void setVitesseY(int vitesseY){
        this.vitesseY = vitesseY;
    }

    public void setVitesseX(int vitesseX){
        this.vitesseX = vitesseX;
    }
    
    public  void setBonusY(boolean by) {
    	deplacementBonus[0] = by;
    }
    public  void setBonusX(boolean bx) {
    	deplacementBonus[1] = bx;
    }
    public  void setMalusY(boolean by) {
    	deplacementMalus[0] = by;
    }

    public  void setMalusX(boolean bx) {
    	deplacementMalus[1] = bx;
    }
  
    public void setIncreTourBonus(int x) {
    	increTourBonus = x;
    }
    
    public void setIncreTourMalus(int x) {
    	increTourMalus = x;
    }
    
    public void setResetbonus(int[] tabB) {
    	 resetBonus = tabB;
    }
    
    public void setResetmalus(int []tabM) {
    	 resetMalus = tabM;
    }
    public void setIsMalusY(boolean y) {
    	isMalusY = y;
    }
    
    public void setIsMalusX(boolean x) {
    	isMalusX = x;
    }
    
    public ArrayList<Sommet> getPossibilite() {
        return possibilite;
    }

    public void setPossibilite(ArrayList<Sommet> p){
        this.possibilite = p;
    }

    public int getNumJoueur() {
        return numJoueur;
    }

    public static void setIncrement(int increment) {
        Joueur.increment = increment;
    }
    
    //--------------------------------------------------------------
    //                Fonctions 
    //--------------------------------------------------------------

    /**
     * Crée un sommet avec la vitesse et les coordonnées du joueur 
     * pour recuperer la liste des sommets accessible par le joueur
     * (donc pareil que all possibilities SAUF le sommet sur lequel le joueur est lui même)
     * @param circuit : contient le tableau décrivant le circuit 
     */
    public void listeSuccesseurs(Circuit circuit){
        ArrayList<Sommet> possible = new ArrayList<Sommet>();
        Sommet sommetJoueur = new Sommet(this.getY(), this.getX(), this.getVitesseX(), this.getVitesseY() );
        miseAjour(sommetJoueur);
        sommetJoueur.allPossibilite(circuit.getTableau());
        possible = sommetJoueur.getSuccesseurs();
        reset();
        for(int i = 0; i< possible.size(); i ++ ){
            if(possible.get(i).getJ()== this.getX() && possible.get(i).getI() == this.getY()){
                possible.remove(possible.get(i));
            }
        }
        this.possibilite = possible;
    }

    /**
     * Réinitialise les informations du joueur une fois que la liste des successeurs du sommet 
     * de ce dernier à été crée.
     * Permet de transmettre correctement les informations à chaque tour.
     */
    private void reset() {
         deplacementBonus[0] = false;
         deplacementBonus[1] = false;
         deplacementMalus[0] = false;
         deplacementMalus[1] = false;
         resetBonus[0] = 0;
         resetBonus[1] = 0;
         resetMalus[0] = 0;
         resetMalus[1] = 0;
         increTourBonus = 0;
         increTourMalus = 0;
         isMalusX = false;
         isMalusY = false;
    }
    
    /**
     * Recupère les informations contenues dans les attributs du joueur pour mettre à jour les attributs du sommet
     * passé en paramètre.
     * Les modifications sont effectuées si un bonus ou un malus a été atteint.
     * @param s : le sommet à mettre à jour (celui correspondant au joueur )
     */
    private void miseAjour(Sommet s) {
    	if(deplacementBonus[0]||deplacementBonus[1]) {
	    	s.setDeplacementBonus(deplacementBonus);
	        s.setIncreTourBonus(increTourBonus);
	        s.setResetbonus(resetBonus);
    	}
    	if(deplacementMalus[0]||deplacementMalus[1]) {
    		s.setDeplacementMalus(deplacementMalus);
	        s.setIncreTourMalus(increTourMalus);
	        s.setResetMalus(resetMalus);
	        s.setIsMalusX(isMalusX);
	        s.setIsMalusY(isMalusY);
	    }
    }

}
