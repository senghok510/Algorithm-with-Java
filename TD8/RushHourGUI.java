import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class RushHourGUI extends JFrame {
    private RushHour RH;
    private int[] pos;

    public RushHourGUI(RushHour RH, int[] pos) {
        this.RH = RH;
        this.pos = pos;     
        setTitle("Rush Hour Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeGrid();
        setLayout(new GridLayout(6, 6,10,10));
        setVisible(true);
    }

    public Color getColorFromString(String colorName) {
        switch (colorName.toLowerCase()) {
            case "rouge":
                return Color.RED;
            case "vert":
                return Color.GREEN;
            case "vert pale":
                return new Color(0, 255, 0, 128);
            case "bleu":
                return Color.BLUE;
            case "bleu ciel":
                return new Color(0, 0,255, 128);
            case "jaune":
                return Color.YELLOW;
            case "noir":
                return Color.BLACK;
            case "blanc":
                return Color.WHITE;
            case "orange":
                return Color.ORANGE;
            case "violet":
                return new Color(128, 0, 128);
            case "beige":
                return new Color(232, 220, 222);
            case "rose":
                return new Color(255, 192, 203);
            // Ajoute d'autres couleurs selon tes besoins
            default:
                return Color.GRAY; // Couleur par défaut
        }
    }

    private void initializeGrid() {
        System.out.println("la grille dessinée représente ");
        System.out.println("nbCars =" + RH.nbCars);
        System.out.println("color =" + Arrays.toString(RH.color));
        System.out.println("horiz =" + Arrays.toString(RH.horiz));
        System.out.println("len =" + Arrays.toString(RH.len));
        System.out.println("moveOn =" + Arrays.toString(RH.moveOn));


        int cellSize = 100;
    
        // Crée une grille 6x6
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
              
                boolean carPresent = false;
                for (int i = 0; i < RH.nbCars; i++) {
                    if ((RH.horiz[i] && RH.moveOn[i] == row && pos[i] <= col && col < pos[i] + RH.len[i]) ||
                            (!RH.horiz[i] && pos[i] <= row && row < pos[i] + RH.len[i] && RH.moveOn[i] == col)) {
                        carPresent = true;
                        JLabel label = new JLabel(" "+i +" ", SwingConstants.CENTER);
                        // label.setPreferredSize(new Dimension(cellSize - 50, cellSize - 50)); // Ajustement
                        label.setOpaque(true);
                        label.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 5));
                        label.setBackground(getColorFromString(RH.color[i])); // Utiliser i directement
                        add(label);
                        ;
                    }
                }

                if (!carPresent) {
                    add(new JLabel("  ", SwingConstants.CENTER) {
                        {
                            setOpaque(true);
                            setBackground(Color.WHITE);
                            setPreferredSize(new Dimension(cellSize, cellSize)); // Ajustement
                        }
                    });

                }
            }
        }

    }

    public void update(int[] newPos) {
        this.pos = newPos; 
        repaint(); 
        
    }
   
    public void animateMove(int carIndex, int direction) {
        System.out.println("coucou");
        Timer timer = new Timer(100, new ActionListener() {
            int steps = 0; // Compte le nombre d'étapes

            @Override
            public void actionPerformed(ActionEvent e) {
                if (steps < 10) { // Déplace en 10 étapes
                    if (RH.horiz[carIndex]) {
                        pos[carIndex] += direction == 1 ? 0.1 : -0.1; // Déplacement progressif
                    } else {
                        pos[carIndex] += direction == 1 ? 0.1 : -0.1; // Déplacement progressif
                    }
                    repaint(); // Redessine l'interface
                    steps++;
                } else {
                    ((Timer) e.getSource()).stop(); // Arrête le timer
                    pos[carIndex] += (direction == 1) ? 1 : -1; // Met à jour la position finale
                    repaint(); // Redessine l'interface une dernière fois
                }
            }
        });
        timer.start(); // Démarre le timer
    }

    // @Override
    // public void paint(Graphics g) {
    //     super.paint(g);
    //     initializeGrid(); // Redessine la grille
    // }


    // public static void main(String[] args) {
    //     int nbCars = 12;
    //     String[] color = new String[] { "rouge", "vert pale", "jaune", "orange",
    //             "violet pale", "bleu ciel", "rose", "violet", "vert", "noir",
    //             "beige", "bleu" };
    //     boolean[] horiz = new boolean[] { true, false, true, false, false, true, false,
    //             true, false, true, false, true };
    //     int[] len = new int[] { 2, 2, 3, 2, 3, 2, 2, 2, 2, 2, 2, 3 };
    //     int[] moveOn = new int[] { 2, 2, 0, 0, 3, 1, 1, 3, 0, 4, 5, 5 };
    //     RushHour RH = new RushHour(nbCars, color, horiz, len, moveOn);
    //     int[] pos = new int[] { 1, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 1 };

    //     // Lancer l'interface graphique
    //     SwingUtilities.invokeLater(() -> {
    //         RushHourGUI rushHourGUI = new RushHourGUI(RH, pos);
    //     });
    // }
}
