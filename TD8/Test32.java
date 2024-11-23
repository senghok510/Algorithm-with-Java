import javax.swing.SwingUtilities;

public class Test32 {

    public static boolean TestBFS() {
        int nbCars = 12;
        String[] color = new String[] { "rouge", "vert pale", "jaune", "orange",
                "violet pale", "bleu ciel", "rose", "violet", "vert", "noir",
                "beige", "bleu" };
        boolean[] horiz = new boolean[] { true, false, true, false, false, true, false,
                true, false, true, false, true };
        int[] len = new int[] { 2, 2, 3, 2, 3, 2, 2, 2, 2, 2, 2, 3 };
        int[] moveOn = new int[] { 2, 2, 0, 0, 3, 1, 1, 3, 0, 4, 5, 5 };
        RushHour RH = new RushHour(nbCars, color, horiz, len, moveOn);

        State s0 = new State(RH, new int[] { 1, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 1 });
        State s = RH.solveBFS(s0);
        assert(s.success()) : "\n L'état renvoyé ne correspond pas à une configuration gagnante";
        State t = s;
        int n=-1;
        while(s != null) {
            t=s;
            s = s.prev;
            n++;
        }
        assert(n==46) : "\n La solution que vous renvoyez n'est pas la plus courte, vous devriez obtenir 46 mouvements et vous en obtenez "+n;
        assert(t.equals(s0)) : "\n L'état renvoyé ne part pas de s0 (ou les champs prev ont été mal instancés)";

        // Lancer l'interface graphique
        SwingUtilities.invokeLater(() -> new RushHourGUI(RH, new int[] { 1, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 1 }));

        return true;
    }

    

    public static void main(String[] args) {

        // Pour s'assurer que les assert's sont activés
        if (!Test11.class.desiredAssertionStatus()) {
            System.err.println("Vous devez activer globalement l'option -ea de la JVM");
            System.err.println("Voir la rubirque \"Activer Assert\" au début du sujet");
            System.exit(1);
        }

        System.out.println("Question 3.2");
        System.out.print("Test de la methode solveBFS : ");
        TestBFS();
        System.out.println("[OK]");

        

    }

}


