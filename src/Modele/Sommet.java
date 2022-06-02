package Modele;


import java.util.ArrayList;

public class Sommet {

	private Sommet predecesseur = null;
    private int distanceParcourue;
    private final int i;
    private final int j;
    private int vitesseX;
    private int vitesseY;
    private boolean bonus;
    private boolean malus;
    private boolean isMalusX;
    private boolean isMalusY;
    private boolean[] deplacementBonus ;
    private boolean[] deplacementMalus;
    private int[] resetBonus ;
    private int[] resetMalus;
    private int increTourBonus ;
    private int increTourMalus ;
    private ArrayList<Sommet> successeurs;
    private Sommet suivant;
    
	 //--------------------------------------------------------------
	 //                  Constructeurs
	 //--------------------------------------------------------------
	 public Sommet(int i, int j){
        distanceParcourue = 0;
        this.i = i;
        this.j = j;
        vitesseX = 0;
        vitesseY = 0;
        bonus = false;
        malus = false;
        isMalusX = false;
        isMalusY = false;
        deplacementBonus = new boolean[2];
        deplacementMalus = new boolean[2];
        resetBonus = new int[2];
        resetMalus = new int[2];
        increTourBonus = 0;
        increTourMalus = 0;
        successeurs = new ArrayList<Sommet>();
	 }

	 public Sommet(int ii, int jj, int vx, int vy){
        distanceParcourue = 0;
        this.i = ii;
        this.j = jj;
        vitesseX = vx;
        vitesseY = vy;
        bonus = false;
        malus = false;
        isMalusX = false;
        isMalusY = false;
        deplacementBonus = new boolean[2];
        deplacementMalus = new boolean[2];
        resetBonus = new int[2];
        resetMalus = new int[2];
        increTourBonus = 0;
        increTourMalus = 0;
        successeurs = new ArrayList<Sommet>();
    }

    //--------------------------------------------------------------
    //                   Getters et Setters
    //--------------------------------------------------------------

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getVitesseX(){
        return vitesseX;
    }

    public int getVitesseY() {
        return vitesseY;
    }

    public void setVitesseX(int vitesseX){
        this.vitesseX = vitesseX;
    }

    public void setVitesseY(int vitesseY){
        this.vitesseY = vitesseY;
    }

    public Sommet getPredecesseur(){
        return this.predecesseur;
    }

    public void setPredecesseur(Sommet pred){
        predecesseur = pred;
    }

    public int getDistance(){
        return distanceParcourue;
    }

    public void setDistance(int dist){
        distanceParcourue = dist;
    }

    public boolean getBonus() {
    	return bonus;
    }

    public boolean getMalus() {
    	return malus;
    }

    public  void setBonusY(boolean b) {
    	deplacementBonus[0] = b;
    }

    public   void setBonusX(boolean b) {
    	deplacementBonus[1] = b;
    }

    public  boolean getBonusY() {
    	return deplacementBonus[0];
    }
    public  boolean getBonusX() {
    	return deplacementBonus[1];
    }

    public  void setMalusY(boolean b) {
    	deplacementMalus[0] = b;
    }

    public  void setMalusX(boolean b) {
    	deplacementMalus[1] = b;
    }

    public  boolean getMalusY() {
    	return deplacementMalus[0];
    }

    public  boolean getMalusX() {
    	return deplacementMalus[1];
    }


    public void setDeplacementBonus(boolean[] tabB) {
    	deplacementBonus = tabB;
    }

    public void setDeplacementMalus(boolean[] tabM) {
    	deplacementMalus = tabM;
    }

    public int[] getResetbonus() {
    	return resetBonus;
    }

    public int[] getResetmalus() {
    	return resetMalus;
    }

    public void  setResetbonus(int[] tabB) {
    	 resetBonus = tabB;
    }
    public void  setResetMalus(int[] tabM) {
   	 resetMalus = tabM;
   }

    public boolean getIsMalusY() {
    	return isMalusY;
    }

    public boolean getIsMalusX() {
    	return isMalusX;
    }

    public void setIsMalusY(boolean y) {
    	isMalusY = y;
    }

    public void setIsMalusX(boolean x) {
    	isMalusX = x;
    }

    public int getIncreTourBonus() {
    	return increTourBonus ;
    }

    public int getIncreTourMalus() {
    	return increTourMalus;
    }

    public void setIncreTourBonus(int x) {
    	increTourBonus = x;
    }

    public void setIncreTourMalus(int x) {
    	increTourMalus = x;
    }

    public ArrayList<Sommet> getSuccesseurs() {
        return successeurs;
    }

    public Sommet getSuivant() {
        return suivant;
    }

    //--------------------------------------------------------------
    //                        Vue.Buttons.Affichage
    //--------------------------------------------------------------

    public void afficheSommet(){
        System.out.println("Distance parcourue :"+distanceParcourue+".\n" +
                "Vitesse sur X :"+ vitesseX + ", Vitesse sur Y :" + vitesseY);
    }

    //--------------------------------------------------------------
    //               Fonction communes
    //--------------------------------------------------------------

    @Override
    public boolean equals(Object n) {
        if (n instanceof Sommet) {
            Sommet aux = (Sommet) n;
            return ((this.i==aux.i) && (this.j==aux.j) && (this.vitesseX == aux.vitesseX) && (this.vitesseY == aux.vitesseY));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return vitesseX + 100 * vitesseY + i + j;
    }

    public int distEntre(Sommet u){
        int dist,x,y;
        x = Math.abs(this.j - u.j);
        y = Math.abs(this.i - u.i);
        dist = (int) (Math.sqrt(x*x + y*y));
        return dist + 1;
    }

    public int distManhaEntre(Sommet u){
	     return Math.abs(this.j - u.j) + Math.abs(this.i - u.i);
    }

    public Sommet cheminALendroit(){
        Sommet remp = copie(this);
        while (remp.predecesseur != null){
            remp.predecesseur.suivant = copie(remp);
            remp = copie(remp.predecesseur);
        }
        return remp;
    }
    /**
     * Effectue la copie du sommet passé en argument
     * @param a : le sommet à copier
     * @return la copie de a
     */
    public Sommet copie(Sommet a){
        Sommet b = new Sommet (a.i,a.j,a.vitesseX,a.vitesseY);
        b.distanceParcourue = a.distanceParcourue;
        b.predecesseur = a.predecesseur;
        b.bonus = a.malus;
        b.malus = a.malus;
        b.successeurs = a.successeurs;
        b.suivant = a.suivant;
        b.isMalusX = a.isMalusX;
        b.isMalusY = a.isMalusY;
        b.deplacementBonus = a.deplacementBonus;
        b.deplacementMalus = a.deplacementBonus;
        b.resetBonus = a.resetBonus;
        b.resetMalus = a.resetMalus;
        b.increTourBonus = a.increTourBonus ;
        b.increTourMalus = a.increTourMalus;
        return b;
    }

    /**
     * Permet de calculer tous les successeurs possibles en fonction de la position et de la vitesse du sommet courant.
     * Un sommet peut possèder jusqu'à 9 succésseurs au total.
     * @param tableau : un tableau deux dimensions d'entiers
     */
    public  void allPossibilite(int [][]tableau) {
    	if(deplacementBonus[0]||deplacementBonus[1]) {
       	 	bonus();
       	}
       	if(deplacementMalus[0]||deplacementMalus[1]) {
       		malus();
       	}
        ArrayList<Sommet> liste = new ArrayList<>();
        //on calcule tous les successeurs possible
        for (int k=0;k<tableau.length;k++){
            for (int l=0;l<tableau[0].length;l++){
                if (k == this.i && l == this.j){
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            int vitxj = this.vitesseX + j;
                            int vityi = this.vitesseY + i;
                            int xj = this.j + vitxj;
                            int yi = this.i + vityi;
                            if (xj > -1 && xj < tableau[0].length && yi > -1 && yi <tableau.length) {
                                Sommet somm = new Sommet(yi,xj,vitxj,vityi);
                                if(tableau[yi][xj] == 7) {
                                	somm.bonus = true ;
                                }else if(tableau[yi][xj] == 8) {
                                	somm.malus = true ;
                                }
                                boolean possible = trajetSansMur(tableau,somm);
                                if (possible) {
                                	miseAjour(somm);
                                    liste.add(somm);
                                }
                            }
                        }
                    }
                }
            }
        }
        this.successeurs = liste;
    }

    /**
     * Vérifie que les cases entre le sommet courant et celui passé en paramètre.
     * ne soient pas des murs 
     * @param tableau : un tableau deux dimensions d'entiers
     * @param s : le sommet d'arrivée
     * @return true s'il n'y a pas de mur entre les deux sommets, false sinon
     */
    public boolean trajetSansMur(int[][] tableau,Sommet s) {
    	if (tableau[s.i][s.j] == 0) {
            return false;
        }
        //fonction verticale
        if (s.j == this.j){
            int minY = Math.min(this.i, s.i);
            int maxY = Math.max(this.i,s.i);
            for (int y = minY;y<maxY;y++){
                if (tableau[y][this.j] == 0){
                    return false;
                }else if(tableau[y][this.j] == 7) {
                	s.deplacementBonus[0] = true ;

                }else if(tableau[y][this.j] == 8) {
                	s.deplacementMalus[0] = true ;

                }
            }
        }
        //fonction affine
        double num = s.i - this.i;
        double den = s.j - this.j;
        double a;
        if (num == 0 || den == 0){
            a = 0;
        } else {
            a = num/den;
        }
        int minX = Math.min(this.j, s.j);
        int maxX = Math.max(this.j,s.j);
        double b = this.i - this.j*a;
        for (int x = minX;x < maxX;x++){
            int compt = 0;
            double xx = x;
            while (compt != 10) {
                if (compt != 0) {
                    xx = xx + 1/10.0;
                }
                double y = a*xx + b;
                if (Math.floor(y) > -1 && Math.floor(y) < tableau.length) {
                    if (tableau[(int) Math.floor(y)][(int) Math.floor(xx)] == 0) {
                        return false;
                    } else if (tableau[(int) Math.floor(y)][(int) Math.floor(xx)] == 7) {
                        if (this.i != Math.floor(y)) {
                            s.deplacementBonus[0] = true;
                        }
                        if (this.j != Math.floor(xx)) {
                            s.deplacementBonus[1] = true;
                        }
                    } else if (tableau[(int) Math.floor(y)][(int) Math.floor(xx)] == 8) {
                        if (this.i != Math.floor(y)) {
                            s.deplacementMalus[0] = true;
                        }
                        if (this.j != Math.floor(xx)) {
                            s.deplacementMalus[1] = true;
                        }
                    }
                }
                compt++;
            }
        }
        return true;
    }

    /**
     * Permet de passer le bonus ou malus aux successeurs du sommet courant.
     * Si le bonus/malus du sommet courant est activé, elle transmet au sommet passé en paramètre
     * les compteurs ainsi que les variables pour appliquer le bonus/malus au prochain tour 
     * 
     * @param s : un sommet (le successeur)
     */
    private void miseAjour(Sommet s) {
    	if(this.deplacementBonus[0]||this.deplacementBonus[1]) {
    		if(this.getJ() != s.getJ()) {
           	 s.setBonusX(true);
           }
           if(this.getI() != s.getI()) {
           	s.setBonusY(true);
           }
    		s.increTourBonus = this.increTourBonus;
    		s.resetBonus = this.resetBonus;
    	}
    	if(this.deplacementMalus[0]||this.deplacementMalus[1]) {
    		if(this.getJ() != s.getJ()) {
            	s.setMalusX(true);
            }
            if(this.getI() != s.getI()) {
            	 s.setMalusY(true);
            }
            if(this.vitesseX == 0 && this.vitesseY == 0) {
            	s.vitesseX = 0;
            	s.vitesseY = 0;
            }
    		s.increTourMalus = this.increTourMalus;
    		s.resetMalus = this.resetMalus;
    		s.isMalusX = this.isMalusX ;
    		s.isMalusY = this.isMalusY;
    	}
    }

    /**
     * Ajoute de la vitesse si le bonus du sommet est activé et que le compteur n'est pas égal à 3.
     *  En fonction de la position dans laquelle le successeur à été choisi, on ajoute de la vitesse à x et/ou y. 
     *  (en fonction de la direction du circuit le bonus est un aojut de vitesse si on va vers le bas (vitesse positive) 
     *  ou un retrait de vitesse si on va vers le haut (vitesse négative)).
     *   
     */
    private void bonus() {
    	if(increTourBonus == 3) {
    		if(this.vitesseY < 0) {
	    		 this.vitesseY += resetBonus[0];
             }
             else {
                 this.vitesseY -= resetBonus[0];
             }
             if(this.vitesseX < 0) {
                 this.vitesseX += resetBonus[1];
             }
             else {
                 this.vitesseX -= resetBonus[1];
             }
             increTourBonus = 0;
             deplacementBonus[0] = false;
             deplacementBonus[1] = false;
    	}
    	else if(increTourBonus < 3) {
    	 if (deplacementBonus[0]) {
    		 if(this.vitesseY < 0) {
    			 this.vitesseY -=2;
     		}
    		else {
     			this.vitesseY +=2;
     		}
    		 resetBonus[0] += 2;
    	 }
    	 if(deplacementBonus[1]) {
    		 if(this.vitesseX < 0) {
         		this.vitesseX -=2;
     		 }
    		 else {
     			this.vitesseX +=2;
     		}
    		 resetBonus[1] += 2;
    	 }
    	 increTourBonus += 1;
       }
    }

    /**
     *Retire de la vitesse si le malus du sommet est activé et que le compteur n'est pas égal à 3.
     *  En fonction de la position dans laquelle le successeur à été choisi, on retire de  la vitesse à x et/ou y. 
     *  (en fonction de la direction du circuit le malus est un retrait de vitesse si on va vers le bas (vitesse positive) 
     *  ou un ajout de vitesse si on va vers le haut (vitesse négative)).
     *  Dans le cas ou le malus ammène à une vitesse nulle, la vitesse reste nulle jusqu'à la fin du malus pour éviter d'avoir
     *  les possibilités en arrière.
     */
    private void malus() {
    	if(increTourMalus == 3) {
	    	 if(this.vitesseY < 0||this.isMalusY) {
	    		 this.vitesseY -= resetMalus[0];
	    	 }
	    	 else {
	    		 this.vitesseY += resetMalus[0];
	    	 }
	    	 if(this.vitesseX < 0 || this.isMalusX) {
	    		 this.vitesseX -= resetMalus[1];
	    	 }
	    	 else {
	    		 this.vitesseX += resetMalus[1];
	    	 }
	   		 increTourMalus = 0;
	   		 deplacementMalus[0] = false;
	   		 deplacementMalus[1] = false;
	   		 isMalusX = false;
	   		 isMalusY = false;
    	}
    	else if(increTourMalus < 3) {
		    	 if (deplacementMalus[0]) {
		    		 if(this.vitesseY < 0) {
		    			 this.vitesseY += 2;
		    			 if(this.vitesseY > 0) {
		    				 this.vitesseY =0;
		    			 }
		    			//permet de savoir si la vitesse y était négative avant d'être nulle
		    			 //pour pouvoir la rétablir à la fin du malus
		    			 this.isMalusY = true;
		     		 }
		    		 else {
		     			this.vitesseY -=2;
		     			if(this.vitesseY < 0) {
		     				this.vitesseY = 0;
		     			}
		     		 }
		    		 resetMalus[0] +=2;
		    	 }
		    	 if(deplacementMalus[1]) {
		    		 if(this.vitesseX < 0) {
		    			 this.vitesseX += 2;
		    			 if(this.vitesseX > 0) {
		    				 this.vitesseX = 0;
		    			 }
		    			//permet de savoir si la vitesse x était négative avant d'être nulle
		    			 //pour pouvoir la rétablir à la fin du malus
		    			 this.isMalusX = true;
		     		 }
		    		 else {
		     			this.vitesseX -= 2;
		     			if(this.vitesseX < 0) {
		     				this.vitesseX = 0;
		     			}
		     		}
	    		 resetMalus[1] +=2;
		    	}
    	 increTourMalus += 1;
       }
    }


}
