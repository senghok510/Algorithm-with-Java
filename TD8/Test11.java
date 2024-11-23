public class Test11 {

    public static boolean TestSuccess() {
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
        assert(s0!=null) : "\n le constructeur State renvoie un objet null";
        assert (!s0.success()) : "\nerreur : success(s0) devrait valoir false et non true";
        State s1 = new State(RH, new int[] { 4, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 1 });
        assert (s1.success()) : "\nerreur : success(s1) devrait valoir true et non false";

        State s = new State(s0, 11, 1);
		assert(s.prev == s0) : "\n le champ prev est mal initialisé";
		assert(s.pos[11]-s0.pos[11]==1) : "\n la valeur de pos est mal définie après l'appel de State(s0, 11, 1)";
		s = new State(s, 3, 1); s = new State(s, 11, -1); s = new State(s, 3, -1);
        
        boolean test = true;
        for (int i=0;i<nbCars;i++){
            if (s.pos[i]!= s0.pos[i])
                test = false;
        }
        assert(test) : "\n State(s,c,d) ne fonctionne pas correctement";
        
        int[] movingCars = {11, 9, 8, 6, 3, 0, 1, 2, 2, 1, 1, 0, 3, 2, 4, 7, 10, 11, 10, 9, 1, 7, 7, 4, 4, 5, 10, 10, 5, 5, 4, 4, 7, 7, 7, 6, 4, 4, 1, 11, 11, 11, 4, 0, 0, 0};
		int[] moves = {1, 1, -1, 1, -1, -1, 1, -1, -1, 1, 1, 1, 1, -1, -1, -1, -1, 1, -1, 1, 1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, -1, 1, 1, -1, -1, -1, -1, 1, 1, 1, 1};
		for(int i = 0; i < movingCars.length; i++) {
			s = new State(s, movingCars[i], moves[i]);
		}
		assert(s.success()):"\n State(s,c,d) ne fonctionne pas correctement";
        return true;

    }

    public static void main(String[] args) {

        // Pour s'assurer que les assert's sont activés
        if (!Test11.class.desiredAssertionStatus()) {
            System.err.println("Vous devez activer globalement l'option -ea de la JVM");
            System.err.println("Voir la rubirque \"Activer Assert\" au début du sujet");
            System.exit(1);
        }

        System.out.println("Question 1.1");
        System.out.print("Test du constructeur «State» et de la méthode «success»");
        TestSuccess();
        System.out.println("[OK]");
    }

}
