import java.util.LinkedList;


public class Test2 {

    public static boolean TestMoves() {
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
        LinkedList<State> listMoves = RH.moves(s0);
        int nbMoves = listMoves.size();
		assert(nbMoves == 5) : "\nerreur : vous devriez obtenir 5 déplacements possibles et vous en obtenez "+nbMoves;
        return true;
    }

    

    public static void main(String[] args) {

        // Pour s'assurer que les assert's sont activés
        if (!Test11.class.desiredAssertionStatus()) {
            System.err.println("Vous devez activer globalement l'option -ea de la JVM");
            System.err.println("Voir la rubirque \"Activer Assert\" au début du sujet");
            System.exit(1);
        }

        System.out.println("Question 2");
        System.out.print("Test de la methode moves : ");
        TestMoves();
        System.out.println("[OK]");
    }

}

