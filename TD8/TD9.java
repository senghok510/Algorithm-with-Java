import java.util.*;



/**
 * la représentation du problème est la suivante :
 * la grille a 6 colonnes, numérotées 0 à 5 de gauche à droite
 * et 6 lignes, numérotées 0 à 5 de haut en bas
 *
 * il y a nbCars voitures, numérotées de 0 à nbCars-1
 * pour chaque voiture i :
 * - color[i] donne sa couleur
 * - horiz[i] indique s'il s'agit d'une voiture horizontale
 * - len[i] donne sa longueur (2 ou 3)
 * - moveOn[i] indique sur quelle ligne elle se déplace pour une voiture horizontale
 *   et sur quelle colonne pour une voiture verticale
 *
 * la voiture d'indice 0 est celle qui doit sortir, on a donc
 * horiz[0]==true, len[0]==2, moveOn[0]==2
 */
class RushHour {
	int nbCars;
	String[] color;
	boolean[] horiz;
	int[] len;
	int[] moveOn;

    public RushHour(int nbCars,String[] color,boolean[] horiz,int[] len,int[] moveOn){
        this.nbCars = nbCars;
        this.color = color;
        this.horiz = horiz;
        this.len = len;
        this.moveOn = moveOn;
    }
    
	/** renvoie la liste des déplacements possibles à partir de s */
	LinkedList<State> moves(State s) {
        throw new Error("Méthode moves(State s) à compléter (Question 2)");
	}


	State solveDFS(State s){
		throw new Error("Méthode solveDFS(State s) à compléter (Question 3.1)");
	}

	/** cherche une solution à partir de l'état s */
	State solveBFS(State s) {
		throw new Error("Méthode solveBFS(State s) à compléter (Question 3.2)");
	}

	/** affiche une solution */
	void printSolution(State s) {
		throw new Error("Méthode printSolution(State s) à compléter (Question 4)");
    }

	
	
}

/**
 * l'état donne la position de chaque voiture, avec la convention suivante :
 * pour une voiture horizontale c'est la colonne de sa case la plus à gauche
 * pour une voiture verticale c'est la colonne de sa case la plus haute
 * (rappel : la colonne la plus à gauche est 0, la ligne la plus haute est 0)
 */
class State {
	RushHour plateau;
	int[] pos;

	/** on se rappelle quel déplacement a conduit à cet état, pour l'affichage de la solution */
	State prev;
	int c;
	int d;

	/** construit un état initial (c, d et prev ne sont pas significatifs) */
	public State(RushHour plateau, int[] pos) {
		throw new Error("Constructeur State(RushHour plateau, int[] pos) à compléter (Question 1.1)");
	}

	/** construit un état obtenu à partir de s en déplaçant la voiture c de d (-1 ou +1) */
	public State(State s, int c, int d) {
		throw new Error("Constructeur State(State s, int c, int d) à compléter (Question 1.1)");
	}

	/** a-t-on gagné ? */
	public boolean success() {
		throw new Error("Méthode success() à compléter (Question 1.1)");
    }
	
	/** quelles sont les places libres */
	public boolean[][] free() {
		throw new Error("Méthode free() à compléter (Question 1.2)");
	}

	/* test d'égalité */
	public boolean equals(Object o) {
		throw new Error("Méthode equals(Object o) à compléter (Question 1.2)");
	}

	/** code de hachage */
	public int hashCode() {
		int h = 0;
		for (int i = 0; i < pos.length; i++)
			h = 37 * h + pos[i];
		return h;
	}


}

