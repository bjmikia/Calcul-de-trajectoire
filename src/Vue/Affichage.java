package Vue;

import Controleur.Controleur;
import Modele.Circuit;
import Modele.Joueur;
import Modele.Partie;
import Modele.Sommet;
import Vue.Buttons.*;
import Vue.Buttons.Robot;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Affichage extends JFrame{

    private CardLayout cl = new CardLayout();
    private JPanel main = new JPanel(cl);
    MyButtons[][] circuit_buttons;
    Controleur controleur;


    public Affichage(Controleur control) {
        controleur = control;
        setTitle("Circuits");
        setBackground(new Color(0,51,100));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(950,700));
        setResizable(false);
        setVisible(true);
    }


    //------------------------------------------------------------
    //                   Présentation
    //------------------------------------------------------------


    /**
     * affichage de l'ecran de presentation
     */
    public void ecranPresentation(){
        setVisible(true);
        setAlwaysOnTop(true);
        JPanel princ = new JPanel();
        princ.setBackground(new Color(0,38,77));
        princ.setPreferredSize(new Dimension(700, 600));
        princ.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 30, 0);
        c.gridy = 0;
        c.gridx = 1;
        JLabel intro = new JLabel("Projet Circuit 3");
        intro.setForeground(Color.LIGHT_GRAY);
        intro.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        princ.add(intro, c);

        c.gridy = 1;
        c.gridx = 1;
        c.insets = new Insets(0, 0, 30, 0);
        URL urlImage = getClass().getResource("/information.png");
        Icon icone = new ImageIcon(urlImage);
        JButton butt = new JButton("", icone);
        butt.setFocusPainted(false);
        butt.setBorderPainted(false);
        butt.setContentAreaFilled(false);
        butt.setVerticalTextPosition(SwingConstants.BOTTOM);
        butt.setHorizontalTextPosition(SwingConstants.CENTER);
        princ.add(butt, c);

        c.gridy = 2;
        c.gridx = 1;
        JButton butt1 = new JButton("Commencer");
        butt1.setPreferredSize(new Dimension(250, 50));
        princ.add(butt1, c);

        butt1.addActionListener(event -> {
            controleur.choixDebut();
        });


        main.add(princ, "choix");
        getContentPane().add(main);
        main.setBounds(0, 0, 700, 600);
        setVisible(true);
    }


    //------------------------------------------------------------
    //                  Ecran de Choix
    //------------------------------------------------------------

    /**
     * affiche l'ecran des choix possible au début
     */
    public void ecranChoixDebut(){
        setAlwaysOnTop(false);
        JPanel big = new JPanel(new BorderLayout());

        JPanel princ = new JPanel();
        princ.setBackground(new Color(0,38,77));
        princ.setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
        princ.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        c.gridx = 1;
        c.insets = new Insets(0, 0, 100, 0);
        JLabel intro = new JLabel("Que voulez-vous faire ?");
        intro.setForeground(Color.LIGHT_GRAY);
        intro.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        princ.add(intro,c);

        c.insets = new Insets(0, 0, 50, 350);
        c.gridy = 2;
        JButton demo = new JButton("Voir une demo (trajet Aleatoire)");
        demo.setPreferredSize(new Dimension(250,80));
        princ.add(demo, c);
        demo.addActionListener(event -> {
            controleur.choixAlgo1(false);
        });

        c.insets = new Insets(0, 350, 50, 0);
        JButton algo = new JButton("Lancer un Algorithme (PCC)");
        algo.setPreferredSize(new Dimension(250,80));
        princ.add(algo, c);
        algo.addActionListener(event -> {
            controleur.choixAlgo1(true);
        });

        c.insets = new Insets(0, 0, 50, 350);
        c.gridy = 3;
        c.gridx = 1;
        JButton compa = new JButton("Comparer 2 Algorithmes");
        compa.setPreferredSize(new Dimension(250,80));
        princ.add(compa, c);
        compa.addActionListener(event -> {
            controleur.choixAlgo2();
        });

        c.insets = new Insets(0, 350, 50, 0);
        c.gridy = 3;
        c.gridx = 1;
        JButton partie = new JButton("Jouer sur un circuit");
        partie.setPreferredSize(new Dimension(250,80));
        princ.add(partie, c);
        partie.addActionListener(event -> {
            controleur.choixMode();
        });

        big.add(princ,BorderLayout.CENTER);
        main.add(big, "choixDebut");
        cl.show(main,"choixDebut");
    }


    //------------------------------------------------------------
    //                  Ecran de Choix Algos
    //------------------------------------------------------------

    /**
     * affiche l'ecran de choix des algos n°1 : trajet aléatoire ou demo
     * @param pcc : détermine si on voulais ou non un PCC
     */
    public void ecranChoixAlgo1(boolean pcc){
        setAlwaysOnTop(false);
        JPanel big = new JPanel(new BorderLayout());

        JPanel princ = new JPanel();
        princ.setBackground(new Color(0,38,77));
        princ.setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
        princ.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        ButtonGroup box = new ButtonGroup();
        JRadioButton c1 = new JRadioButton("PL",true);
        box.add(c1);
        c1.setPreferredSize(new Dimension(150,50));
        c1.setBackground(new Color(0,38,77));
        c1.setForeground(Color.LIGHT_GRAY);
        JRadioButton c2 = new JRadioButton("Dijkstra",false);
        c2.setPreferredSize(new Dimension(150,50));
        c2.setBackground(new Color(0,38,77));
        box.add(c2);
        c2.setForeground(Color.LIGHT_GRAY);
        JRadioButton c3 = new JRadioButton("A*",false);
        c3.setPreferredSize(new Dimension(150,50));
        c3.setBackground(new Color(0,38,77));
        box.add(c3);
        c3.setForeground(Color.LIGHT_GRAY);
        JRadioButton c4 = new JRadioButton("PL (variante)",false);
        c4.setPreferredSize(new Dimension(150,50));
        c4.setBackground(new Color(0,38,77));
        box.add(c4);
        c4.setForeground(Color.LIGHT_GRAY);
        JRadioButton c5 = new JRadioButton("Dijkstra (variante)",false);
        c5.setPreferredSize(new Dimension(150,50));
        c5.setBackground(new Color(0,38,77));
        box.add(c5);
        c5.setForeground(Color.LIGHT_GRAY);
        JRadioButton c6 = new JRadioButton("A* (variante)",false);
        c6.setPreferredSize(new Dimension(150,50));
        c6.setBackground(new Color(0,38,77));
        box.add(c6);
        c6.setForeground(Color.LIGHT_GRAY);

        ButtonGroup box2 = new ButtonGroup();
        JRadioButton b1 = new JRadioButton("Sans Bonus",true);
        box2.add(b1);
        b1.setPreferredSize(new Dimension(150,50));
        b1.setBackground(new Color(0,38,77));
        b1.setForeground(Color.LIGHT_GRAY);
        JRadioButton b2 = new JRadioButton("Avec Bonus",false);
        b2.setPreferredSize(new Dimension(150,50));
        b2.setBackground(new Color(0,38,77));
        box2.add(b2);
        b2.setForeground(Color.LIGHT_GRAY);

        c.gridy = 0;
        c.gridx = 1;
        c.insets = new Insets(0, 0, 50, 0);
        JLabel intro = new JLabel("Choissisez l'Algorithme");
        intro.setForeground(Color.LIGHT_GRAY);
        intro.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        princ.add(intro,c);


        c.insets = new Insets(0, 0, 0, 0);


        c.insets = new Insets(10,0,10,0);
        c.gridy = 2;
        c.gridx = 0;
        princ.add(c1,c);

        c.gridx = 1;
        princ.add(c2,c);

        c.gridx = 2;
        princ.add(c3,c);

        c.gridy = 3;
        c.gridx = 0;
        princ.add(c4,c);

        c.gridx = 1;
        princ.add(c5,c);

        c.gridx = 2;
        princ.add(c6,c);

        c.insets = new Insets(0,0,0,300);
        c.gridy = 4;
        c.gridx = 1;
        princ.add(b1,c);
        c.insets = new Insets(0,300,0,0);
        princ.add(b2,c);


        c.insets = new Insets(40, 0, 0, 0);
        c.gridy = 5;
        c.gridx = 1;
        JButton butt2 = new JButton("Choisir le circuit");
        butt2.setPreferredSize(new Dimension(200, 50));
        princ.add(butt2, c);

        butt2.addActionListener(event -> {
            boolean bonus = !b1.isSelected();
            if (c1.isSelected()){
                controleur.choixCircuit1('p',pcc,bonus);
            }else if (c2.isSelected()){
                controleur.choixCircuit1('d',pcc,bonus);
            }else if (c3.isSelected()){
                controleur.choixCircuit1('a',pcc,bonus);
            }else if (c4.isSelected()){
                controleur.choixCircuit1('l',pcc,bonus);
            }else if (c5.isSelected()){
                controleur.choixCircuit1('j',pcc,bonus);
            }else {
                controleur.choixCircuit1('*',pcc,bonus);
            }
        });

        c.insets = new Insets(60, 0, 0, 0);
        c.gridy = 6;
        c.gridx = 0;
        JButton butt3 = new JButton("Retour");
        butt3.setPreferredSize(new Dimension(200, 50));
        princ.add(butt3, c);

        butt3.addActionListener(event -> {
            controleur.choixDebut();
        });

        c.gridx = 2;
        JButton butt4 = new JButton("Quitter");
        butt4.setPreferredSize(new Dimension(200, 50));
        princ.add(butt4, c);

        butt4.addActionListener(event -> {
            System.exit(0);
        });

        big.add(princ,BorderLayout.CENTER);
        main.add(big, "choixAlgo1");
        cl.show(main,"choixAlgo1");
    }

    /**
     * affiche l'ecran de choix des algos n°2 : comparaison des algos
     */
    public void ecranChoixAlgo2(){
        setAlwaysOnTop(false);
        JPanel big = new JPanel(new BorderLayout());

        JPanel princ = new JPanel();
        princ.setBackground(new Color(0,38,77));
        princ.setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
        princ.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        c.gridx = 1;
        c.insets = new Insets(0, 0, 50, 0);
        JLabel intro = new JLabel("Choissisez l'Algorithme");
        intro.setForeground(Color.LIGHT_GRAY);
        intro.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        princ.add(intro,c);

        String[] algos = {"PL","Dijkstra", "A*", "PL (variante)", "Dijkstra (variante)", "A* (variante)"};
        String[] distance = {"PCC", "Aléatoire"};
        c.gridy = 2;
        c.gridx = 1;
        c.insets = new Insets(0, 0, 50, 400);
        JLabel a1 = new JLabel("Pour l'Algorithme 1 :");
        a1.setForeground(Color.LIGHT_GRAY);
        princ.add(a1,c);

        c.insets = new Insets(0, 0, 50, 0);
        JComboBox<String> algo1 = new JComboBox<>(algos);
        algo1.setPreferredSize(new Dimension(150, 50));
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        algo1.setRenderer(listRenderer);
        princ.add(algo1, c);

        c.insets = new Insets(0, 400, 50, 0);
        JComboBox<String> distance1 = new JComboBox<>(distance);
        distance1.setPreferredSize(new Dimension(150, 50));
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        distance1.setRenderer(listRenderer);
        princ.add(distance1, c);


        c.gridy = 3;
        c.gridx = 1;
        c.insets = new Insets(0, 0, 50, 400);
        JLabel a2 = new JLabel("Pour l'Algorithme 2 :");
        a2.setForeground(Color.LIGHT_GRAY);
        princ.add(a2,c);

        c.insets = new Insets(0, 0, 50, 0);
        JComboBox<String> algo2 = new JComboBox<>(algos);
        algo2.setPreferredSize(new Dimension(150, 50));
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        algo2.setRenderer(listRenderer);
        princ.add(algo2, c);

        c.insets = new Insets(0, 400, 50, 0);
        JComboBox<String> distance2 = new JComboBox<>(distance);
        distance2.setPreferredSize(new Dimension(150, 50));
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        distance2.setRenderer(listRenderer);
        princ.add(distance2, c);


        c.gridy = 4;
        c.gridx = 1;
        c.insets = new Insets(0, 0, 50, 300);
        JLabel q = new JLabel("Des bonus sur le chemin ?");
        q.setForeground(Color.LIGHT_GRAY);
        princ.add(q,c);
        String[] bon = {"Non", "Oui"};
        c.insets = new Insets(0, 200, 50, 0);
        JComboBox<String> bonus = new JComboBox<>(bon);
        bonus.setPreferredSize(new Dimension(150, 50));
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        bonus.setRenderer(listRenderer);
        princ.add(bonus, c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridy = 5;
        c.gridx = 1;
        JButton butt2 = new JButton("Choisir le circuit");
        butt2.setPreferredSize(new Dimension(200, 50));
        princ.add(butt2, c);

        butt2.addActionListener(event -> {
            if (algo1.getSelectedItem() != algo2.getSelectedItem()) {
                controleur.choixCircuit2((String)algo1.getSelectedItem(),(String)distance1.getSelectedItem(),
                        (String)algo2.getSelectedItem(),(String)distance2.getSelectedItem(),(String)bonus.getSelectedItem());
            }
        });

        c.insets = new Insets(30, 0, 0, 0);
        c.gridy = 6;
        c.gridx = 0;
        JButton butt3 = new JButton("Retour");
        butt3.setPreferredSize(new Dimension(150, 50));
        princ.add(butt3, c);

        butt3.addActionListener(event -> {
            controleur.choixDebut();
        });

        c.gridx = 2;
        JButton butt4 = new JButton("Quitter");
        butt4.setPreferredSize(new Dimension(150, 50));
        princ.add(butt4, c);

        butt4.addActionListener(event -> {
            System.exit(0);
        });

        big.add(princ,BorderLayout.CENTER);
        main.add(big, "choixAlgo2");
        cl.show(main,"choixAlgo2");
    }

    //------------------------------------------------------------
    //                  Ecran de Choix Modele.Circuit
    //------------------------------------------------------------

    /**
     * ecran de choix de circuit pour un trajet aléatoire ou pcc
     * @param algo : algo choisis
     * @param pcc : détermine si on veut le pcc de notre algo ou non
     * @param bonus : détermine si on souhaite ou non des bonus sur le circuit
     */
    public void ecranChoixCircuit1(char algo,boolean pcc,boolean bonus){
        setAlwaysOnTop(false);
        JPanel big = new JPanel(new BorderLayout());

        JPanel princ = new JPanel();
        princ.setBackground(new Color(0,38,77));
        princ.setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
        princ.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        CheckboxGroup box = new CheckboxGroup();

        Checkbox c1 = new Checkbox("Circuit 1",box,true);
        c1.setForeground(Color.LIGHT_GRAY);
        Checkbox c2 = new Checkbox("Circuit 2",box,false);
        c2.setForeground(Color.LIGHT_GRAY);
        Checkbox c3 = new Checkbox("Circuit 3",box,false);
        c3.setForeground(Color.LIGHT_GRAY);
        Checkbox c6 = new Checkbox("Circuit 4",box,false);
        c6.setForeground(Color.LIGHT_GRAY);
        Checkbox c7 = new Checkbox("Circuit 5",box,false);
        c7.setForeground(Color.LIGHT_GRAY);

        c.gridy = 0;
        c.gridx = 1;
        c.insets = new Insets(0, 0, 50, 0);
        JLabel intro = new JLabel("Choissisez votre Circuit");
        intro.setForeground(Color.LIGHT_GRAY);
        intro.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        princ.add(intro,c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridy = 1;
        c.gridx = 0;
        URL urlImage = getClass().getResource("/c1.png");
        Icon icone = new ImageIcon(urlImage);
        JLabel laba = new JLabel(icone);
        princ.add(laba,c);

        c.gridy = 1;
        c.gridx = 1;
        urlImage = getClass().getResource("/c2.png");
        icone = new ImageIcon(urlImage);
        JLabel labb = new JLabel(icone);
        princ.add(labb,c);

        c.gridy = 1;
        c.gridx = 2;
        urlImage = getClass().getResource("/c3.png");
        icone = new ImageIcon(urlImage);
        JLabel labd = new JLabel(icone);
        princ.add(labd,c);

        c.insets = new Insets(10,0,10,0);
        c.gridy = 2;
        c.gridx = 0;
        princ.add(c1,c);

        c.gridy = 2;
        c.gridx = 1;
        princ.add(c2,c);

        c.gridy = 2;
        c.gridx = 2;
        princ.add(c3,c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridy = 3;
        c.gridx = 0;
        urlImage = getClass().getResource("/c4.png");
        icone = new ImageIcon(urlImage);
        JLabel labe = new JLabel(icone);
        princ.add(labe,c);

        c.gridy = 3;
        c.gridx = 1;
        urlImage = getClass().getResource("/c5.png");
        icone = new ImageIcon(urlImage);
        JLabel labf = new JLabel(icone);
        princ.add(labf,c);

        c.insets = new Insets(10,0,10,0);
        c.gridy = 4;
        c.gridx = 0;
        princ.add(c6,c);

        c.gridy = 4;
        c.gridx = 1;
        princ.add(c7,c);

        c.insets = new Insets(30, 0, 0, 0);
        c.gridy = 5;
        c.gridx = 1;
        JButton butt2 = new JButton();
        butt2.setText("Voir le chemin");
        butt2.setPreferredSize(new Dimension(200, 50));
        princ.add(butt2, c);

        butt2.addActionListener(event -> {
            Checkbox recup = box.getSelectedCheckbox();
            if (recup == c1){
                controleur.voirDemo(algo,bonus,1,pcc);
            }else if (recup == c2){
                controleur.voirDemo(algo,bonus,2,pcc);
            }else if (recup == c3){
                controleur.voirDemo(algo,bonus,3,pcc);
            }else if (recup == c6){
                controleur.voirDemo(algo,bonus,4,pcc);
            }else {
                controleur.voirDemo(algo,bonus,5,pcc);
            }
        });

        c.insets = new Insets(30, 0, 0, 0);
        c.gridy = 6;
        c.gridx = 0;
        JButton butt3 = new JButton("Retour");
        butt3.setPreferredSize(new Dimension(200, 50));
        princ.add(butt3, c);

        butt3.addActionListener(event -> {
            controleur.choixAlgo1(pcc);
        });

        c.gridx = 2;
        JButton butt4 = new JButton("Quitter");
        butt4.setPreferredSize(new Dimension(200, 50));
        princ.add(butt4, c);

        butt4.addActionListener(event -> {
            System.exit(0);
        });

        big.add(princ,BorderLayout.CENTER);
        main.add(big, "choixCircuit1");
        cl.show(main,"choixCircuit1");
    }

    /**
     * ecran de choix de circuit pour la comparaison des algos
     * @param algo1 : algo 1 choisis
     * @param pcc1 : determine si l'algo 1 sera le pcc ou non
     * @param algo2 : algo 2 choisis
     * @param pcc2 : determine si l'algo 2 sera le pcc ou non
     * @param bonus : détermine si on souhaite ou non des bonus sur le circuit
     */
    public void ecranChoixCircuit2(String algo1,boolean pcc1,String algo2, boolean pcc2,boolean bonus){
        setAlwaysOnTop(false);
        JPanel big = new JPanel(new BorderLayout());

        JPanel princ = new JPanel();
        princ.setBackground(new Color(0,38,77));
        princ.setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
        princ.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        CheckboxGroup box = new CheckboxGroup();

        Checkbox c1 = new Checkbox("Circuit 1",box,true);
        c1.setForeground(Color.LIGHT_GRAY);
        Checkbox c2 = new Checkbox("Circuit 2",box,false);
        c2.setForeground(Color.LIGHT_GRAY);
        Checkbox c3 = new Checkbox("Circuit 3",box,false);
        c3.setForeground(Color.LIGHT_GRAY);
        Checkbox c6 = new Checkbox("Circuit 4",box,false);
        c6.setForeground(Color.LIGHT_GRAY);
        Checkbox c7 = new Checkbox("Circuit 5",box,false);
        c7.setForeground(Color.LIGHT_GRAY);

        c.gridy = 0;
        c.gridx = 1;
        c.insets = new Insets(0, 0, 50, 0);
        JLabel intro = new JLabel("Choissisez votre Circuit");
        intro.setForeground(Color.LIGHT_GRAY);
        intro.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        princ.add(intro,c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridy = 1;
        c.gridx = 0;
        URL urlImage = getClass().getResource("/c1.png");
        Icon icone = new ImageIcon(urlImage);
        JLabel laba = new JLabel(icone);
        princ.add(laba,c);

        c.gridy = 1;
        c.gridx = 1;
        urlImage = getClass().getResource("/c2.png");
        icone = new ImageIcon(urlImage);
        JLabel labb = new JLabel(icone);
        princ.add(labb,c);

        c.gridy = 1;
        c.gridx = 2;
        urlImage = getClass().getResource("/c3.png");
        icone = new ImageIcon(urlImage);
        JLabel labd = new JLabel(icone);
        princ.add(labd,c);

        c.insets = new Insets(10,0,10,0);
        c.gridy = 2;
        c.gridx = 0;
        princ.add(c1,c);

        c.gridy = 2;
        c.gridx = 1;
        princ.add(c2,c);

        c.gridy = 2;
        c.gridx = 2;
        princ.add(c3,c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridy = 3;
        c.gridx = 0;
        urlImage = getClass().getResource("/c4.png");
        icone = new ImageIcon(urlImage);
        JLabel labe = new JLabel(icone);
        princ.add(labe,c);

        c.gridy = 3;
        c.gridx = 1;
        urlImage = getClass().getResource("/c5.png");
        icone = new ImageIcon(urlImage);
        JLabel labf = new JLabel(icone);
        princ.add(labf,c);

        c.insets = new Insets(10,0,10,0);
        c.gridy = 4;
        c.gridx = 0;
        princ.add(c6,c);

        c.gridy = 4;
        c.gridx = 1;
        princ.add(c7,c);

        c.insets = new Insets(10, 0, 0, 0);
        c.gridy = 5;
        c.gridx = 1;
        JButton butt2 = new JButton("Voir la Comparaison des Algorithmes");
        butt2.setPreferredSize(new Dimension(250, 50));
        princ.add(butt2, c);

        butt2.addActionListener(event -> {
            Checkbox recup = box.getSelectedCheckbox();
            if (recup == c1){
                controleur.comparaison(algo1,pcc1,algo2,pcc2,bonus,1);
            }else if (recup == c2){
                controleur.comparaison(algo1,pcc1,algo2,pcc2,bonus,2);
            }else if (recup == c3){
                controleur.comparaison(algo1,pcc1,algo2,pcc2,bonus,3);
            }else if (recup == c6){
                controleur.comparaison(algo1,pcc1,algo2,pcc2,bonus,4);
            }else {
                controleur.comparaison(algo1,pcc1,algo2,pcc2,bonus,5);
            }
        });

        c.insets = new Insets(10, 0, 0, 0);
        c.gridy = 6;
        c.gridx = 0;
        JButton butt3 = new JButton("Retour");
        butt3.setPreferredSize(new Dimension(200, 50));
        princ.add(butt3, c);

        butt3.addActionListener(event -> {
            controleur.choixAlgo2();
        });

        c.gridx = 2;
        JButton butt4 = new JButton("Quitter");
        butt4.setPreferredSize(new Dimension(200, 50));
        princ.add(butt4, c);

        butt4.addActionListener(event -> {
            System.exit(0);
        });

        big.add(princ,BorderLayout.CENTER);
        main.add(big, "choixCircuit2");
        cl.show(main,"choixCircuit2");
    }

    /**
     * ecran de choix de circuit pour une partie
     * @param mode : mode de partie souhaité (multi, solo ou robot)
     * @param nb : nombre de robot ou joueurs voulu (inutile pour solo)
     * @param bonus : détermine si on souhaite ou non des bonus sur le circuit
     */
    public void ecranChoixCircuit3(String mode,String nb, String bonus){
        setAlwaysOnTop(false);
        JPanel big = new JPanel(new BorderLayout());

        JPanel princ = new JPanel();
        princ.setBackground(new Color(0,38,77));
        princ.setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
        princ.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        CheckboxGroup box = new CheckboxGroup();

        Checkbox c1 = new Checkbox("Circuit 1",box,true);
        c1.setForeground(Color.LIGHT_GRAY);
        Checkbox c2 = new Checkbox("Circuit 2",box,false);
        c2.setForeground(Color.LIGHT_GRAY);
        Checkbox c3 = new Checkbox("Circuit 3",box,false);
        c3.setForeground(Color.LIGHT_GRAY);
        Checkbox c6 = new Checkbox("Circuit 4",box,false);
        c6.setForeground(Color.LIGHT_GRAY);
        Checkbox c7 = new Checkbox("Circuit 5",box,false);
        c7.setForeground(Color.LIGHT_GRAY);

        c.gridy = 0;
        c.gridx = 1;
        c.insets = new Insets(0, 0, 50, 0);
        JLabel intro = new JLabel("Choissisez votre Circuit");
        intro.setForeground(Color.LIGHT_GRAY);
        intro.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        princ.add(intro,c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridy = 1;
        c.gridx = 0;
        URL urlImage = getClass().getResource("/c1.png");
        Icon icone = new ImageIcon(urlImage);
        JLabel laba = new JLabel(icone);
        princ.add(laba,c);

        c.gridy = 1;
        c.gridx = 1;
        urlImage = getClass().getResource("/c2.png");
        icone = new ImageIcon(urlImage);
        JLabel labb = new JLabel(icone);
        princ.add(labb,c);

        c.gridy = 1;
        c.gridx = 2;
        urlImage = getClass().getResource("/c3.png");
        icone = new ImageIcon(urlImage);
        JLabel labd = new JLabel(icone);
        princ.add(labd,c);

        c.insets = new Insets(10,0,10,0);
        c.gridy = 2;
        c.gridx = 0;
        princ.add(c1,c);

        c.gridy = 2;
        c.gridx = 1;
        princ.add(c2,c);

        c.gridy = 2;
        c.gridx = 2;
        princ.add(c3,c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridy = 3;
        c.gridx = 0;
        urlImage = getClass().getResource("/c4.png");
        icone = new ImageIcon(urlImage);
        JLabel labe = new JLabel(icone);
        princ.add(labe,c);

        c.gridy = 3;
        c.gridx = 1;
        urlImage = getClass().getResource("/c5.png");
        icone = new ImageIcon(urlImage);
        JLabel labf = new JLabel(icone);
        princ.add(labf,c);

        c.insets = new Insets(10,0,10,0);
        c.gridy = 4;
        c.gridx = 0;
        princ.add(c6,c);

        c.gridy = 4;
        c.gridx = 1;
        princ.add(c7,c);

        c.insets = new Insets(10, 0, 0, 0);
        c.gridy = 5;
        c.gridx = 1;
        JButton butt2 = new JButton("Commencer la partie");
        butt2.setPreferredSize(new Dimension(200, 50));
        princ.add(butt2, c);

        butt2.addActionListener(event -> {
            Checkbox recup = box.getSelectedCheckbox();
            if (recup == c1){
                controleur.commencerPartie(1, mode, bonus, nb);
            }else if (recup == c2){
                controleur.commencerPartie(2, mode, bonus, nb);
            }else if (recup == c3){
                controleur.commencerPartie(3, mode, bonus, nb);
            }else if (recup == c6){
                controleur.commencerPartie(4, mode, bonus, nb);
            }else {
                controleur.commencerPartie(5, mode, bonus, nb);
            }
        });

        c.insets = new Insets(20, 0, 0, 0);
        c.gridy = 6;
        c.gridx = 0;
        JButton butt3 = new JButton("Retour");
        butt3.setPreferredSize(new Dimension(200, 50));
        princ.add(butt3, c);

        butt3.addActionListener(event -> {
            controleur.choixMode();
        });

        c.gridx = 2;
        JButton butt4 = new JButton("Quitter");
        butt4.setPreferredSize(new Dimension(200, 50));
        princ.add(butt4, c);

        butt4.addActionListener(event -> {
            System.exit(0);
        });

        big.add(princ,BorderLayout.CENTER);
        main.add(big, "choixCircuit");
        cl.show(main,"choixCircuit");
    }


    //------------------------------------------------------------
    //       Algorithmes (PCC ou Aleatoire)
    //------------------------------------------------------------

    /**
     * ecran de visualisation du parcours pour le pcc ou trajet aléatoire
     * @param algo : algo choisis
     * @param bonus : determine s'il y a ou non des bonus sur le circuit
     * @param distance : distance totale du chemin
     * @param chemin : circuit du parcours
     */
    public void ecranAlgorithme(String algo,boolean bonus, int distance, Circuit chemin){
        setAlwaysOnTop(false);
        JPanel big = new JPanel(new BorderLayout());
        big.setBackground(new Color(0,38,77));
        String up;
        if (bonus){
            up = ", avec des bonus, ";
        }else {
            up = ", sans bonus, ";
        }
        JLabel info = new JLabel(algo+ ", sur le "+chemin.getNom()+up+"avec une distance de "+ distance);
        info.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        info.setForeground(Color.LIGHT_GRAY);
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setVerticalAlignment(SwingConstants.CENTER);
        big.add(info,BorderLayout.NORTH);

        JPanel princ = new JPanel();
        princ.setBackground(new Color(0,38,77));
        princ.setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
        princ.setLayout(new GridLayout(chemin.getTableau().length,chemin.getTableau()[0].length));

        circuit_buttons = new MyButtons[chemin.getTableau().length][chemin.getTableau()[0].length];
        for (int i=0;i<chemin.getTableau().length;i++){
            for (int j=0; j<chemin.getTableau()[0].length;j++){
                changercolor(chemin.getTableau(), i, j);
                circuit_buttons[i][j].setLocation(i,j);
                princ.add(circuit_buttons[i][j]);
            }
        }
        big.add(princ,BorderLayout.CENTER);

        JButton butt = new JButton("Accueil");
        butt.setPreferredSize(new Dimension(40,30));
        butt.addActionListener(event -> {
            controleur.choixDebut();
        });
        big.add(butt,BorderLayout.SOUTH);

        main.add(big, "algo");
        cl.show(main,"algo");
    }

    //------------------------------------------------------------
    //       Comparaisons entre 2 Algorithmes
    //------------------------------------------------------------

    /**
     * ecran de visualisation du concours des algorithmes
     * @param algo1 : algo 1
     * @param distance1 : distance finale de l'algo 1
     * @param algo2 : algo 2
     * @param distance2 : distance finale de l'algo 2
     * @param chemin : circuit des algos
     */
    public void ecranComparaison(String algo1, int distance1, String algo2, int distance2, Circuit chemin){
        setAlwaysOnTop(false);
        JPanel big = new JPanel(new BorderLayout());
        big.setBackground(new Color(0,38,77));
        JLabel info = new JLabel("Comparaison de "+algo1+" ( "+distance1+" ) et "+algo2+" ( "+distance2+" )");
        info.setFont(new Font(Font.SERIF, Font.BOLD, 30));
        info.setForeground(Color.LIGHT_GRAY);
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setVerticalAlignment(SwingConstants.CENTER);
        big.add(info,BorderLayout.NORTH);

        JPanel princ = new JPanel();
        princ.setBackground(new Color(0,38,77));
        princ.setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
        princ.setLayout(new GridLayout(chemin.getTableau().length,chemin.getTableau()[0].length));

        circuit_buttons = new MyButtons[chemin.getTableau().length][chemin.getTableau()[0].length];
        for (int i=0;i<chemin.getTableau().length;i++){
            for (int j=0; j<chemin.getTableau()[0].length;j++){
                changercolor(chemin.getTableau(), i, j);
                circuit_buttons[i][j].setLocation(i,j);
                princ.add(circuit_buttons[i][j]);
            }
        }


        JButton butt = new JButton("Accueil");
        butt.setPreferredSize(new Dimension(40,30));
        butt.addActionListener(event -> {
            controleur.choixDebut();
        });
        big.add(butt,BorderLayout.SOUTH);

        big.add(princ,BorderLayout.CENTER);
        main.add(big, "jeuRobot");
        cl.show(main,"jeuRobot");
    }

    //------------------------------------------------------------
    //                  Ecran de Choix Modele.Partie
    //------------------------------------------------------------

    /**
     * ecran du choix des modes possibles pour une partie
     */
    public void ecranChoixModePartie(){
        setAlwaysOnTop(false);
        JPanel big = new JPanel(new BorderLayout());

        JPanel princ = new JPanel();
        princ.setBackground(new Color(0,38,77));
        princ.setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
        princ.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        c.gridx = 1;
        c.insets = new Insets(0, 0, 100, 0);
        JLabel intro = new JLabel("Choissisez votre mode de jeu");
        intro.setForeground(Color.LIGHT_GRAY);
        intro.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        princ.add(intro,c);

        c.insets = new Insets(0, 0, 20, 0);
        c.gridy = 1;
        c.gridx = 0;
        JLabel lab1 = new JLabel("Quel type de Partie ?");
        lab1.setForeground(Color.LIGHT_GRAY);
        princ.add(lab1,c);

        c.gridy = 2;
        c.gridx = 0;
        String[] listeMode = {"Seul","Multijoueur (local)", "Avec des Robots"};
        JComboBox<String> mode = new JComboBox<>(listeMode);
        mode.setPreferredSize(new Dimension(150, 50));
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        mode.setRenderer(listRenderer);
        princ.add(mode, c);

        //choix des autres joueurs/robots eventuels
        c.gridy = 1;
        c.gridx = 1;
        JLabel lab2 = new JLabel("Combien d'autres Joueurs/Robots ?");
        lab2.setForeground(Color.LIGHT_GRAY);
        princ.add(lab2,c);
        c.gridy = 2;
        c.gridx = 1;
        String[] autres = {"1","2", "3"};
        JComboBox<String> others = new JComboBox<>(autres);
        others.setPreferredSize(new Dimension(100, 50));
        DefaultListCellRenderer listRenderer1 = new DefaultListCellRenderer();
        listRenderer1.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        others.setRenderer(listRenderer1);
        princ.add(others, c);

        //choix si bonus ou non
        c.gridy = 1;
        c.gridx = 2;
        JLabel lab3 = new JLabel("Avec ou Sans Bonus ?");
        lab3.setForeground(Color.LIGHT_GRAY);
        princ.add(lab3,c);
        c.gridy = 2;
        c.gridx = 2;
        String[] plus = {"Sans bonus","Avec Bonus"};
        JComboBox<String> bonus = new JComboBox<>(plus);
        bonus.setMinimumSize(new Dimension(100,50));
        bonus.setPreferredSize(new Dimension(100, 50));
        DefaultListCellRenderer listRenderer2 = new DefaultListCellRenderer();
        listRenderer2.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        bonus.setRenderer(listRenderer2);
        princ.add(bonus, c);

        c.insets = new Insets(40, 0, 0, 0);
        c.gridy = 4;
        c.gridx = 1;
        JButton butt2 = new JButton("Passer au choix du Circuit");
        butt2.setPreferredSize(new Dimension(200, 50));
        princ.add(butt2, c);

        butt2.addActionListener(event -> {
            controleur.choixCircuit3((String)mode.getSelectedItem(),(String)others.getSelectedItem(),(String)bonus.getSelectedItem());
        });

        c.insets = new Insets(20, 0, 0, 0);
        c.gridy = 5;
        c.gridx = 0;
        JButton butt3 = new JButton("Retour");
        butt3.setPreferredSize(new Dimension(200, 50));
        princ.add(butt3, c);

        butt3.addActionListener(event -> {
            controleur.choixDebut();
        });

        c.gridx = 2;
        JButton butt4 = new JButton("Quitter");
        butt4.setPreferredSize(new Dimension(200, 50));
        princ.add(butt4, c);

        butt4.addActionListener(event -> {
            System.exit(0);
        });

        big.add(princ,BorderLayout.CENTER);
        main.add(big, "choixMode");
        cl.show(main,"choixMode");
    }

    //------------------------------------------------------------
    //       Jeu Solo
    //------------------------------------------------------------

    /**
     * ecran de visualisation de la partie en cours (pour un jeu solo)
     * @param partie : partie en cours
     * @param ordre : ici liste nulle car il s'agit d'un jeu solo -> pas de classement
     */
    public void ecranCircuitJeuSolo(Partie partie, LinkedHashMap<String,Integer> ordre){
        Circuit chemin = partie.getCircuit();
        Joueur joueur = partie.getJoueur();
        setAlwaysOnTop(false);
        JPanel big = new JPanel(new BorderLayout());

        JPanel princ = new JPanel();
        princ.setBackground(new Color(0,38,77));
        princ.setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
        princ.setLayout(new GridLayout(chemin.getTableau().length,chemin.getTableau()[0].length));

        circuit_buttons = new MyButtons[chemin.getTableau().length][chemin.getTableau()[0].length];
        for (int i=0;i<chemin.getTableau().length;i++){
            for (int j=0; j<chemin.getTableau()[0].length;j++){
                changercolor(chemin.getTableau(), i, j);
                circuit_buttons[i][j].setLocation(i,j);
                princ.add(circuit_buttons[i][j]);
                if (circuit_buttons[i][j] instanceof Possible){
                    int finalJ = j;
                    int finalI = i;
                    circuit_buttons[i][j].addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            partie.setCircuit(chemin);
                            partie.setJoueur(joueur);
                            if (chemin.getArrivee().getI() == finalI && chemin.getArrivee().getJ() == finalJ){
                                controleur.afficheFin(partie,ordre);
                            } else {
                                partie.getCircuit().getTableau()[partie.getJoueur().getY()][partie.getJoueur().getX()] = 1;
                                controleur.getPossibilite(finalI, finalJ, joueur,partie);
                                partie.setJoueur(joueur);
                                controleur.circuitInitial(partie);
                            }
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                    });
                }

            }
        }


        big.add(princ,BorderLayout.CENTER);
        main.add(big, "circuit");
        cl.show(main,"circuit");
    }

    //------------------------------------------------------------
    //       Jeu Robot
    //------------------------------------------------------------

    /**
     * affichage du circuit quand on joue avec des robot
     * @param partie : partie en cours
     * @param liste : liste des robots
     * @param ordre : classement finale du jeu (1er dans la liste = dernier du classement)
     * @param total : nombre total de personnes dans la partie
     * @param distance : distance du joueur
     */
    public void ecranCircuitJeuRobotTourJoueur(Partie partie, ArrayList<Sommet> liste, LinkedHashMap<String, Integer> ordre, int total, int distance){
        Circuit chemin = partie.getCircuit();
        Joueur joueur = partie.getJoueur();
        setAlwaysOnTop(false);
        JPanel big = new JPanel(new BorderLayout());

        JPanel princ = new JPanel();
        princ.setBackground(new Color(0,38,77));
        princ.setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
        princ.setLayout(new GridLayout(chemin.getTableau().length,chemin.getTableau()[0].length));

        circuit_buttons = new MyButtons[chemin.getTableau().length][chemin.getTableau()[0].length];
        for (int i=0;i<chemin.getTableau().length;i++){
            for (int j=0; j<chemin.getTableau()[0].length;j++){
                changercolor(chemin.getTableau(), i, j);
                circuit_buttons[i][j].setLocation(i,j);
                princ.add(circuit_buttons[i][j]);
                if (circuit_buttons[i][j] instanceof Possible){
                    int finalJ = j;
                    int finalI = i;
                    circuit_buttons[i][j].addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            partie.getCircuit().getTableau()[partie.getJoueur().getY()][partie.getJoueur().getX()] = 1;
                            partie.setCircuit(chemin);
                            partie.setJoueur(joueur);
                            controleur.getPossibilite(finalI, finalJ, joueur,partie);
                            partie.setJoueur(joueur);
                            controleur.jeuAvecRobotsTourRobot(partie, liste, ordre, total,distance);

                        }

                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                    });
                }

            }
        }
        big.add(princ,BorderLayout.CENTER);
        main.add(big, "jeuRobot");
        cl.show(main,"jeuRobot");
    }

    /**
     * fonction permettant d'attribuer un bouton en fonction de la composition du tableau
     * @param tableau : tableau de int du circuit
     * @param i : position i (ou Y) de la case
     * @param j : position j (ou X) de la case
     */
    public void changercolor(int[][] tableau, int i, int j){
        if (tableau[i][j] == -1){
            circuit_buttons[i][j] = new Voiture(i,j,5);
        }else if (tableau[i][j] == 0){
            circuit_buttons[i][j] = new Mur(i,j);
        } else if (tableau[i][j] == 1){
            circuit_buttons[i][j] = new Piste(i,j);
        } else if (tableau[i][j] == 2){
            circuit_buttons[i][j] = new Arrivee(i,j);
        }else if (tableau[i][j] == 3){
            circuit_buttons[i][j] = new Depart(i,j);
        }else if (tableau[i][j] == 4){
            circuit_buttons[i][j] = new Voiture(i,j,1);
        }else if (tableau[i][j] == 5){
            circuit_buttons[i][j] = new Voiture(i,j,2);
        }else if (tableau[i][j] == 7){
            circuit_buttons[i][j] = new Bonus(i,j);
        }else if (tableau[i][j] == 8){
            circuit_buttons[i][j] = new Malus(i,j);
        }else if (tableau[i][j] == 15){
            circuit_buttons[i][j] = new Voiture(i,j,1);
        }else if (tableau[i][j] == 16){
            circuit_buttons[i][j] = new Voiture(i,j,2);
        }else if (tableau[i][j] == 17){
            circuit_buttons[i][j] = new Voiture(i,j,3);
        }else if (tableau[i][j] == 18){
            circuit_buttons[i][j] = new Voiture(i,j,4);
        }else if (tableau[i][j] == 21) {
            circuit_buttons[i][j] = new Possible(i,j);
        } else if (tableau[i][j] == 22) {
            circuit_buttons[i][j] = new Possible(i,j);
        } else if (tableau[i][j] == 23) {
            circuit_buttons[i][j] = new Possible(i,j);
        } else if (tableau[i][j] == 24) {
            circuit_buttons[i][j] = new Possible(i,j);
        } else if (tableau[i][j] == 25) {
            circuit_buttons[i][j] = new Possible(i,j);
        } else if (tableau[i][j] == 26) {
            circuit_buttons[i][j] = new Possible(i,j);
        } else if (tableau[i][j] == 27) {
            circuit_buttons[i][j] = new Possible(i,j);
        } else if (tableau[i][j] == 28) {
            circuit_buttons[i][j] = new Possible(i,j);
        } else if (tableau[i][j] == 29){
            circuit_buttons[i][j] = new Possible(i,j);
        }else if (tableau[i][j] == 50){
            circuit_buttons[i][j] = new Robot(i,j,1);
        } else if (tableau[i][j] == 51) {
            circuit_buttons[i][j] = new Robot(i,j,2);
        } else if (tableau[i][j] == 52) {
            circuit_buttons[i][j] = new Robot(i,j,3);
        }

    }

    /**
     * ecran de classement pour la fin d'une partie avec des robots
     * @param win : détermine si le joueur a gagné ou non
     * @param ordre : liste du classement (1er dedans = 1er qui gagne)
     */
    public void ecranFinPartieRobot(boolean win, LinkedHashMap<String,Integer> ordre){
        JPanel big = new JPanel(new BorderLayout());

        JPanel princ = new JPanel();
        princ.setBackground(new Color(0,38,77));
        princ.setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
        princ.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(0, 0, 50, 0);
        c.gridy = 0;
        c.gridx = 1;
        JLabel intro;
        if (win && ordre.isEmpty()) {
            intro = new JLabel("Bravo, vous avez gagne !");
        }else if (!win && ordre.isEmpty()){
            intro = new JLabel("Dommage, vous avez perdu...");
        } else{
            intro = new JLabel("Classement");
        }
        intro.setForeground(Color.LIGHT_GRAY);
        intro.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        princ.add(intro, c);

        int y = 2;

        int compt = 0;
        for (String i : ordre.keySet()) {
            c.gridx = 1;
            c.gridy = y+compt;
            String res = (compt+1)+ " : "+i + ", avec une distance de "+ordre.get(i);
            JLabel aff = new JLabel(res);
            aff.setForeground(getColor(i));
            aff.setBackground(new Color(0,80,77));
            princ.add(aff,c);
            compt++;
        }

        c.gridy = y+compt + 1;
        c.gridx = 0;
        JButton butt1 = new JButton("Rejouer");
        butt1.setPreferredSize(new Dimension(100, 50));
        princ.add(butt1, c);

        butt1.addActionListener(event -> {
            controleur.choixMode();
        });

        c.gridx = 2;
        JButton butt2 = new JButton("Quitter");
        butt2.setPreferredSize(new Dimension(100, 50));
        princ.add(butt2, c);

        butt2.addActionListener(event -> {
            System.exit(0);
        });

        big.add(princ,BorderLayout.CENTER);
        main.add(big, "fin");
        cl.show(main,"fin");
    }

    /**
     * fonction retournant une couleur en fonction d'un String
     * @param i : String définissant le robot
     * @return la couleur associée au robot pendant la partie
     */
    public Color getColor (String i){
        if (i.equals("Robot 1")){
            return new Color(204,153,255);
        }else if (i.equals("Robot 2")){
            return new Color(153,153,255);
        }else if (i.equals("Robot 3")){
            return new Color(255,153,153);
        }else if (i.equals("Robot 4")){
            return new Color(255,153,187);
        }else if (i.equals("Vous (Joueur)")){
            return new Color(255,255,77);
        }else {
            return Color.black;
        }
    }

    //------------------------------------------------------------
    //       Jeu Multi
    //------------------------------------------------------------

    /**
     * affichage de la partie pour un jeu multijoueur
     * @param partie : partie en cours
     * @param tour : liste des joueurs déterminant l'ordre de jeu
     * @param pos : position du joueur dans la liste tour
     * @param podium : classement du podium
     * @param crash : classement des perdents
     * @param maj : liste permettant la mise a jour de tour
     */
    public void ecranCircuitJeuJoueursTourJoueur(Partie partie, ArrayList<Joueur> tour, int pos, ArrayList<Joueur> podium, ArrayList<Joueur> crash, ArrayList<Joueur> maj){
        Circuit chemin = partie.getCircuit();
        setAlwaysOnTop(false);
        JPanel big = new JPanel(new BorderLayout());
        big.setBackground(new Color(0,38,77));
        JLabel info = new JLabel("A toi de jouer Joueur "+tour.get(pos).getNumJoueur());
        //info.setPreferredSize(new Dimension(100,35));
        info.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        //info.setBounds(100,150,100,35);
        info.setForeground(getColor(tour.get(pos).getNumJoueur()));
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setVerticalAlignment(SwingConstants.CENTER);
        big.add(info,BorderLayout.NORTH);

        JPanel princ = new JPanel();
        princ.setBackground(new Color(0,38,77));
        princ.setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
        princ.setLayout(new GridLayout(chemin.getTableau().length,chemin.getTableau()[0].length));

        circuit_buttons = new MyButtons[chemin.getTableau().length][chemin.getTableau()[0].length];
        for (int i=0;i<chemin.getTableau().length;i++){
            for (int j=0; j<chemin.getTableau()[0].length;j++){
                changercolor(chemin.getTableau(), i, j);
                circuit_buttons[i][j].setLocation(i,j);
                princ.add(circuit_buttons[i][j]);
                if (circuit_buttons[i][j] instanceof Possible){
                    int finalJ = j;
                    int finalI = i;
                    circuit_buttons[i][j].addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            partie.getCircuit().getTableau()[tour.get(pos).getY()][tour.get(pos).getX()] = 1;
                            partie.setCircuit(chemin);
                            controleur.getPossibilite(finalI, finalJ, tour.get(pos),partie);
                            controleur.verifGagnePerd(partie,tour,pos,podium,crash,maj);
                        }
                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                    });
                }

            }
        }
        big.add(princ,BorderLayout.CENTER);
        main.add(big, "jeuJoueur");
        cl.show(main,"jeuJoueur");
    }

    /**
     * ecran de fin de partie pour une partie multijoueur
     * @param ordreClassement : classement final de la partie
     */
    public void ecranFinPartieJoueurs(ArrayList<Joueur> ordreClassement){
        JPanel big = new JPanel(new BorderLayout());

        JPanel princ = new JPanel();
        princ.setBackground(new Color(0,38,77));
        princ.setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
        princ.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(0, 0, 50, 0);
        c.gridy = 0;
        c.gridx = 1;
        JLabel intro = new JLabel("Classement");
        intro.setForeground(Color.LIGHT_GRAY);
        intro.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        princ.add(intro, c);

        int y = 2;

        int compt = 0;
        for (Joueur i : ordreClassement) {
            c.gridx = 1;
            c.gridy = y+compt;
            String res = (compt+1)+ " : Joueur "+i.getNumJoueur();
            JLabel aff = new JLabel(res);
            aff.setForeground(getColor(i.getNumJoueur()));
            aff.setBackground(new Color(0,80,77));
            princ.add(aff,c);
            compt++;
        }

        c.gridy = y+compt + 1;
        c.gridx = 0;
        JButton butt1 = new JButton("Rejouer");
        butt1.setPreferredSize(new Dimension(100, 50));
        princ.add(butt1, c);

        butt1.addActionListener(event -> {
            controleur.choixMode();
        });

        c.gridx = 2;
        JButton butt2 = new JButton("Quitter");
        butt2.setPreferredSize(new Dimension(100, 50));
        princ.add(butt2, c);

        butt2.addActionListener(event -> {
            System.exit(0);
        });

        big.add(princ,BorderLayout.CENTER);
        main.add(big, "fin");
        cl.show(main,"fin");
    }

    /**
     * fonction qui retourne la couleur associée au joueur
     * @param i : numéro du joueur
     * @return la couleur du joueur pendant la partie
     */
    public Color getColor (int i){
        if (i == 1){
            return new Color(255,255,77);
        }else if (i == 2){
            return new Color(153,204,255);
        }else if (i == 3){
            return new Color(153,255,204);
        }else if (i == 4){
            return new Color(255,204,255);
        }else {
            return Color.black;
        }
    }

}
