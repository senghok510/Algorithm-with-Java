/* TD1. Bataille
 * Ce fichier contient deux classes :
 * 		- Deck représente un paquet de cartes,
 * 		- Battle représente un jeu de bataille.
 */

import java.util.LinkedList;

class Deck { // représente un paquet de cartes

	private static final boolean True = false;
	LinkedList<Integer> cards;

	// les méthodes toString, hashCode, equals, et copy sont utilisées pour 
  // l'affichage et les tests, vous ne devez pas les modifier.

	@Override
	public String toString() {
		return cards.toString();
	}

	@Override
	public int hashCode() {
		return 0;
	}
	
	@Override
	public boolean equals(Object o) {
		Deck d = (Deck) o;
		return cards.equals(d.cards);
	}

	Deck copy() {
		Deck d = new Deck();
		for (Integer card : this.cards)
			d.cards.addLast(card);
		return d;
	}

	// constructeur d'un paquet vide
	Deck() {
		cards = new LinkedList<Integer>();
	}

	// constructeur à partir du champ
	Deck(LinkedList<Integer> cards) {
		this.cards = cards;
	}

	// constructeur d'un paquet de cartes complet trié avec nbVals valeurs
	Deck(int nbVals) {
		cards = new LinkedList<Integer>();
		for (int j = 1; j <= nbVals; j++)
			for (int i = 0; i < 4; i++)
				cards.add(j);
	}

	// Question 1

	// prend une carte du paquet d pour la mettre à la fin du paquet courant
	int pick(Deck d) {
		if(!d.cards.isEmpty()){
		int card = d.cards.removeFirst();
		this.cards.addLast(card);
		return card;

		}
		else{
			return -1;
		}
		

		
	}

	// prend toutes les cartes du paquet d pour les mettre à la fin du paquet courant
	void pickAll(Deck d) {
		while(!d.cards.isEmpty()){
			this.pick(d);
		}
		
	}

	// vérifie si le paquet courant est valide
	boolean isValid(int nbVals) {
		int[] compteur = new int[nbVals];
		for (int c : this.cards){
			if( c> nbVals || c< 1){
				return false;
			}
			compteur[c-1]++;
			if( compteur[c-1]> 4){
				return false;
			}



		}
		return true;

		}

			

		




		

	// Question 2.1

	// choisit une position pour la coupe
	int cut() {
		int c =0;
		for(int i =0; i<this.cards.size();i++){
			if(Math.random()>0.5){
				c++;
			}
		}
		return c;
	}

	// coupe le paquet courant en deux au niveau de la position donnée par cut()
	Deck split() {
		int c = this.cut();
		Deck rs = new Deck();
		for(int i =0; i< c; i++){
			rs.pick(this);

		}
		return rs;
		
	}

	// Question 2.2

	// mélange le paquet courant et le paquet d
	void riffleWith(Deck d) {
		Deck f = new Deck();
		int a = this.cards.size();
		int b = d.cards.size();
		while(!d.cards.isEmpty() || !this.cards.isEmpty()){
		if(Math.random()< (a)/(a+b)){
			f.pick(d);
			
			
			
		}
		else{
			f.pick(this);
			
		}
	}
		f.cards = this.cards;

		
	}

	// Question 2.3

	// mélange le paquet courant au moyen du riffle shuffle
	 void riffleShuffle(int m) {
		for( int i =0; i<m ; i++){
			Deck t = this.split();
			this.riffleWith(t);
		}



		
	}
}

class Battle { // représente un jeu de bataille

	Deck player1;
	Deck player2;
	Deck trick;

	// constructeur d'une bataille sans cartes
	Battle() {
		player1 = new Deck();
		player2 = new Deck();
		trick = new Deck();
	}
	
	// constructeur à partir des champs
	Battle(Deck player1, Deck player2, Deck trick) {
		this.player1 = player1;
		this.player2 = player2;
		this.trick = trick;
	}

	// copie la bataille
	Battle copy() {
		Battle r = new Battle();
		r.player1 = this.player1.copy();
		r.player2 = this.player2.copy();
		r.trick = this.trick.copy();
		return r;
	}

	// chaîne de caractères représentant la bataille
	@Override
	public String toString() {
		return "Joueur 1 : " + player1.toString() + "\n" + "Joueur 2 : " + player2.toString() + "\nPli " + trick.toString();
	}

	// Question 3.1

	// constructeur d'une bataille avec un jeu de cartes de nbVals valeurs
	Battle(int nbVals) {
		Deck initialize = new Deck(4*nbVals);
		initialize.riffleShuffle(1);
		player1 = new Deck();
		player2 = new Deck();
		for(int i =0; i< initialize.cards.size()/2; i ++){
			player1.pick(initialize);
			player2.pick(initialize);
		}


	}

	// Question 3.2

	// teste si la partie est terminée
	boolean isOver() {
		return player1.cards.isEmpty() || player2.cards.isEmpty();
	}

	// effectue un tour de jeu
	boolean oneRound() {
		trick = new Deck();
		if(isOver()== true){
			return false;
		}
		
		while(!player1.cards.isEmpty() || !player2.cards.isEmpty()){
		int first = trick.pick(player1);
		int second = trick.pick(player2);
	
		if(first > second){
			player1.pickAll(trick);
		}
		else if(first < second){
			player2.pickAll(trick);
		}
		else{
			if(player1.cards.isEmpty()== true || player2.cards.isEmpty()==true){
				return false;
			}
			else{
			trick.pick(player1);
			trick.pick(player2);
		return true;}
		}
		
	}
	return false;


		

		
	}

	// Question 3.3

	// renvoie le gagnant
	int winner() {
		
		if(player1.cards.size() > player2.cards.size()){
			return 1;

			}
		else if(player2.cards.size() > player1.cards.size()){
			return 2;
			}
		else{
			return 0;
			}
		}
	
		
		
	

	// effectue une partie avec un nombre maximum de coups fixé
	int game(int turns) {
		while(turns >0 & oneRound()){
			turns--;
		}
		return winner();	
	}

	// Question 4.1

	// effectue une partie sans limite de coups, mais avec détection des parties infinies
	int game() {
		Battle b1 = new Battle(player1,player2,trick); //hare
		Battle b2 = this.copy(); //turtle
		while(!b1.toString().equals(b2.toString())){

			if(!b1.oneRound()) return b1.winner();
			if(!b1.oneRound()) return b1.winner();

			b2.oneRound();

			

		}
		return 3;

		
	}

	// Question 4.2

	// effectue des statistiques sur le nombre de parties infinies
	static void stats(int nbVals, int nbGames) {
		int[] results = new int[4]; //win draw 
		for(int i =0; i< nbGames; i++){
			results[new Battle(nbVals).game()]++;

		}


		
	}
}

