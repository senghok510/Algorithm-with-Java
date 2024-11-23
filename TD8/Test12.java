import javax.swing.SwingUtilities;

public class Test12 {

    public static boolean TestEquals() {
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
        State s1 = new State(RH, new int[] { 4, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 1 });
        assert (!s1.equals(s0)) : "\nerreur : equals n'est pas bien implémenté; vous ne devez comparer que le champ pos et non le prev";

        State s = new State(s0, 11, 1);
		s = new State(s, 3, 1); s = new State(s, 11, -1); s = new State(s, 3, -1);
		assert(s.equals(s0)) : "\nerreur : equals n'est pas bien implémenté; vous ne devez comparer que le champ pos et non le prev";
        return true;
    }

    public static boolean TestFree(){
        int nbCars = 12;
        String[] color = new String[] { "rouge", "vert pale", "jaune", "orange",
                "violet pale", "bleu ciel", "rose", "violet", "vert", "noir",
                "beige", "bleu" };
        boolean[] horiz = new boolean[] { true, false, true, false, false, true, false,
                true, false, true, false, true };
        int[] len = new int[] { 2, 2, 3, 2, 3, 2, 2, 2, 2, 2, 2, 3 };
        int[] moveOn = new int[] { 2, 2, 0, 0, 3, 1, 1, 3, 0, 4, 5, 5 };
        RushHour RH = new RushHour(nbCars, color, horiz, len, moveOn);
        int[] pos = new int[] { 1, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 1 };
         
        // Lancer l'interface graphique pour pouvoir débugger
        SwingUtilities.invokeLater(() -> { 
            new RushHourGUI(RH, pos);
        });

        State s0 = new State(RH, pos);
        boolean[][] free = s0.free();
        assert(free.length==6): "Le résultat de free() n'est pas de taille 6*6";
        assert(free[0].length==6): "Le résultat de free() n'est pas un tableau à deux dimensions de taille 6*6";
        boolean[][] freeTest = {
            {true, true, false, false, false, false},
            {false, true, false, false, false, false},
            {false, false, false, false, true, true},
            {true, false, true, false, false, false},
            {false, false, false, false, true, false},
            {false, false, false, false, true, false}
        };

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++)
				assert(free[i][j] == freeTest[i][j]) : "\n free["+i+"]["+j+"] devrait renvoyer "+freeTest[i][j]+" mais renvoie "+free[i][j];
		}

        return true;
    }

    public static void main(String[] args) {

        // Pour s'assurer que les assert's sont activés
        if (!Test11.class.desiredAssertionStatus()) {
            System.err.println("Vous devez activer globalement l'option -ea de la JVM");
            System.err.println("Voir la rubirque \"Activer Assert\" au début du sujet");
            System.exit(1);
        }

        System.out.println("Question 1.2");
        System.out.print("Test de la methode equals : ");
        TestEquals();
        System.out.println("[OK]");
        System.out.print("Test de la methode free : ");
        TestFree();
        System.out.println("[OK]");
    }

}

