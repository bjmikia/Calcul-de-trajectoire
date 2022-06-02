package Controleur;

import Modele.Circuit;

import java.awt.*;

public class Launcher {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Programme programme = new Programme();
                    programme.lancement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
