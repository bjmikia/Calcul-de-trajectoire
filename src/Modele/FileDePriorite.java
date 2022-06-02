package Modele;

import Modele.Noeud;
import Modele.Sommet;

import java.util.ArrayList;
import java.util.HashMap;

public class FileDePriorite {

    ArrayList<Noeud> tas;
    HashMap<Sommet, Integer> indiceDansTas;
    int nbclefs;

    //--------------------------------------------------------------
    //                  Constructeur
    //--------------------------------------------------------------

    FileDePriorite (){
        tas = new ArrayList<Noeud>();
        tas.add(null);
        indiceDansTas = new HashMap<Sommet,Integer>();
        nbclefs = 0;
    }

    //------------------------------------------------------------
    //                   Getters et Setters
    //------------------------------------------------------------

    public Sommet getSommetVPrime(Sommet n){
        return tas.get(indiceDansF(n)).getSommet();
    }

    public Sommet getSommet(int indice){
        return tas.get(indice).getSommet();
    }

    //------------------------------------------------------------
    //                   Fonctions
    //------------------------------------------------------------
    /**
     *Verifitation si la file de priorité est vide
     */
    boolean estVide() {
        return (nbclefs==0);
    }

    /**
     * On part sur un tableau pas trié pour ensuite le trier comme un TAS récursivement.
     * @param i : une int, c'est l'indice du noeud à partir duquel on va commencé à trier.
     */
    public void remiseEnTas(int i){
        int g = 2*i; // indice du noeud gauche de i
        int d = (2*i) + 1; // indice du noeud droit de i
        int win = i;
        if((g<=nbclefs) && (tas.get(g).getDistance() < tas.get(win).getDistance())){
            win = g;
        }
        if((d<=nbclefs) && (tas.get(d).getDistance() < tas.get(win).getDistance())){
            win = d;
        }
        if (win != i){ // on échange de place les deux noeuds
            Noeud tmp = tas.get(i);
            tas.set(i,tas.get(win));
            tas.set(win,tmp);
            indiceDansTas.replace(tas.get(i).getSommet(),i);
            indiceDansTas.replace(tas.get(win).getSommet(),win);
            remiseEnTas(win);
        }
    }
    /**
     * Pour insérer un noeud au bon endroit pour ne pas perturber la tri en TAS
     * On insère aussi le Sommet dans indiceDansTas.
     * @param x : Le Sommet à insérer
     * @param c : La clé (distance )  du Sommet
     */

    public void insertion(Sommet x, int c){
        Noeud p = new Noeud(c,x);
        tas.add(p);
        nbclefs = nbclefs + 1;
        int i=nbclefs;
        while (i/2 >= 1 && tas.get(i/2).getDistance() > c){
            tas.set(i,tas.get(i/2));
            indiceDansTas.replace(tas.get(i).getSommet(),i);
            i = i/2;
        }
        tas.set(i,p);
        indiceDansTas.put(x,i);
    }

    /**
     * Extraire le minimum de la file de priorité (Tas)
     * -On enleve le premier Sommet qui est forcément le minimum
     * -On re-trie la file de priorité en TAS
     */

    public Sommet extraireMin(){
        assert (nbclefs > 0);
        Sommet res = tas.get(1).getSommet();
        tas.set(1,tas.get(nbclefs));
        tas.set(nbclefs,null);
        indiceDansTas.remove(res);
        nbclefs -= 1;
        if (nbclefs > 0){
            indiceDansTas.replace(tas.get(1).getSommet(),1);
            remiseEnTas(1);
        }
        return res;
    }
    /**
     * C'est pour  la mise-à-jour de la distance d’un sommet déjà stocké
     * on suppose que  la distance en argument est inferieur de la distance actuelle.
     * @param x : Le Sommet à modifier
     * @param c : La nouvelle distance du Sommet
     */
    public void maj(Sommet x, int c){
        int i = indiceDansTas.get(x);
        tas.get(i).setDistance(c);
        indiceDansTas.put(x,i);
        Noeud p = tas.get(i);
        while(((i/2)>= 1) && (tas.get(i/2).getDistance() > tas.get(i).getDistance())){
            Noeud tmp = tas.get(i);
            tas.set(i,tas.get(i/2));
            indiceDansTas.replace(tas.get(i).getSommet(),i);
            i = i /2;
        }
        tas.set(i,p);
        indiceDansTas.put(x,i);
    }

    /**
     * Recuperer la distance d'un Sommet dans indiceDansTas s'il existe, si non, on renvoie -1
     * @param n : Le Sommet à récuperer.
     * @return :  la distance ou bien -1 si le sommet n'est pas dans la hashmap
     */
    public int indiceDansF(Sommet n) {
        if (indiceDansTas.containsKey(n))
            return indiceDansTas.get(n);
        else
            return -1;
    }

}

