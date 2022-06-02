package Modele;

public class Noeud {
    private int distance;
    private Sommet sommet;

    //--------------------------------------------------------------
    //                  Constructeur
    //--------------------------------------------------------------

    public Noeud( int distance, Sommet sommet) {
        this.distance= distance;
        this.sommet = sommet;
    }

    //------------------------------------------------------------
    //                   Getters et Setters
    //------------------------------------------------------------

    public void setDistance(int x) {
        this.distance = x;
    }

    public int getDistance() {
        return distance;
    }

    public Sommet getSommet() {
        return sommet;
    }

}
