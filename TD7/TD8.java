
import java.awt.image.PackedColorModel;
import java.rmi.server.LogStream;
import java.time.chrono.ThaiBuddhistChronology;
import javax.swing.text.html.ListView;

/* TD8. Tri fusion en place et généricité 
 * Ce fichier contient 5 classes:
 * 	- Singly<E> : listes chaînées génériques,
 * 	- MergeSortString : algorithme merge-sort pour les listes (chaînées) de chaînes de caractères,
 * 	- Occurrence : comptage des mots d'un texte,
 *  - MergeSort : algorithme merge-sort générique (on remplace le type «String» par le type générique «E»),
 *  - Median : calcul de la médiane d'un ensemble de valeurs numériques
 */

/* Remarque : ne sont declarées «public» que les constructeurs, ainsi que les méthodes dont la 
 * visibilité ne peut pas être réduite, ici toString et compareTo.
 */

// SINGLY 
 
class Singly<E> {
	E element;
	Singly<E> next;

	// On choisit de représenter la liste vide par null, les deux constructeurs qui suivent ne
	// peuvent donc pas construire de liste vide.

	// Cree une liste a un element.
	
	public Singly(E element, Singly<E> next) {
		this.element = element;
		this.next = next;
	}

	// Crée une liste à partir d'un tableau non vide.
	
	public Singly(E[] data) {
		assert (data.length > 0) : "\nLe constructeur Singly(E[] data) ne peut pas être utilisé avec un tableau vide"
				+ "\ncar on ne peut pas construire une liste non vide sans données.";
		this.element = data[0];
		this.next = null;
		Singly<E> cursor = this;
		for (int i = 1; i < data.length; i++) {
			cursor.next = new Singly<E>(data[i], null);
			cursor = cursor.next;
		}
		;
	}

	// Copie physique d'une liste (pour les tests uniquement)
	//res the copied element
	// check condition when l is null
	//make a pointer
	//construct res
	
	static <E> Singly<E> copy(Singly<E> l) {
		if (l == null)
			return null;
		Singly<E> res = new Singly<E>(l.element, l.next);
		Singly<E> cursor = res;  //pointer act like an index to access to table(cursor is the builder of res)
		while (l.next != null) {
			l = l.next;
			cursor.next = new Singly<E>(l.element, l.next);
			cursor = cursor.next;
		}
		return res;
	}

	// Test l'égalite de deux chaînes.
	
	static <E> boolean areEqual(Singly<E> chain1, Singly<E> chain2) {
		while (chain1 != null && chain2 != null) {
			if (!chain1.element.equals(chain2.element))
				return false;
			chain1 = chain1.next;
			chain2 = chain2.next;
		}
		return chain1 == chain2;
	}
	
	// Crée une chaîne de caractères à partir d'une liste chaînée (nécessaire à l'affichage).
	
	public String toString() {
		Singly<E> cursor = this;
		String answer = "[ ";
		while (cursor != null) {
			answer = answer + (cursor.element).toString() + " ";
			cursor = cursor.next;
		}
		answer = answer + "]";
		return answer;
	}

	// Question 1
	// Longueur d'une chaîne. Implémentation itérative pour éviter les 
	// débordements de pile («stack overflow»).
	
	static<E> int length(Singly<E> l) {
		int res =0;


		Singly<E> tmp =l; // pointer
		while(tmp != null){
			res++;
			tmp = tmp.next; //pointeur de l est le pointeur de l.next
		}
		return res;


	}

	
	
	// Question 1
	// Coupe la seconde moitié de la chaîne passée en argument, 
	// la partie supprimée est renvoyée.
	// La méthode split modifie donc la liste passée en argument.

	
	
	static<E> Singly<E> split(Singly<E> l) {
		//supposer que 

		int n = length(l);
		
	

		if( n<=1){
			return null;
		}
		
		

		Singly<E> curr = l; //we set curr to traverse the linkedlist
		for(int k =0; k<(n-1)/2; k++){

			curr=curr.next;   //we move the pointer of curr to point to the last node in order to cut

		}

		Singly<E> res = curr.next;

		curr.next = null;

		
			
		

		  //break the linkedlist chain ( pointeur of curr is null)

		return res;

		
	}
}

/* MERGE_SORT_STRING */

class MergeSortString {

	// Question 2.2
	// Réalise la fusion des chaînes passées en argument, renvoie la chaîne obtenue.
	// Les deux chaînes passées en arguments sont détruites puisque l'opération 
	// se fait «en place».
	static Singly<String> merge(Singly<String> l1, Singly<String> l2) {
		if (l1 == null) return l2;
		if (l2 == null) return l1;

		Singly<String> result;
		if(l1.element.compareTo(l2.element)<0){
			//append the element l1 to result
			result = l1;
			l1 = l1.next; //since l1 is appended to result
		}
		else{
			result = l2;
			l2 = l2.next;
		}

		result.next = null;
		Singly<String> last = result;

		while( l1 != null && l2 !=null){
			if( l1.element.compareTo(l2.element)<0){
				last.next = l1;
				last = last.next;    //move last to the next element so it stays at the end of the array

				l1 = l1.next;
			}
			else{
				last.next = l2;
				last = last.next;

				l2 = l2.next;
			}

		}
		if( l1 != null){
			last.next = l1;

		}
		if(l2 != null){

			last.next = l2;
		}

		return result;



		
	}
	
	

	// Question 2.2
	// Trie (récursivement) la liste passée en argument en triant chacune de ses deux  
	// moitiés séparement avant de fusionner les deux moitiés triées.  
	// La liste passée en argument est détruite au cours de l'opération.
	
	static Singly<String> sort(Singly<String> l) {

		if (l == null || l.next == null) return l;

		Singly<String> l1 = sort(Singly.split(l));
		Singly<String> l2 = sort(l);

		return merge(l1,l2);

	
	}

}

/* OCCURRENCE */

class Occurrence implements Comparable<Occurrence> {
	String word;
	int count;

	Occurrence(String word, int count) {
		this.word = word;
		this.count = count;
	}
	
	public String toString() {
		return word;
	}
	
	// Question 2.3
	// Renvoie une liste dont chaque maillon contient un mot présent 
	// dans la liste de mots passée en argument, avec sa multiplicité.   
	// La liste passée en argument peut être détruite.
	
	static Singly<Occurrence> count(Singly<String> l) {

		if( l == null){

			return null;
		}
		l = MergeSortString.sort(l);




		Singly<Occurrence> list = null;

		 
		while( l!= null){
			Occurrence occ = new Occurrence(l.element, 1);


			while(l.next != null && l.next.element.equals(occ.word )){

				
					occ.count++;

					l = l.next;
				
			}

			list = new Singly<Occurrence>(occ, list);

			l = l.next;
		}

		return list;



	}
	
	// Question 3.2
	// Méthode de comparaison nécessaire a l'utilisation de l'algorithme de tri
	
	public int compareTo(Occurrence that) {
		int diff = that.count - this.count;
		if (diff != 0){
			return diff;

		}
		return this.word.compareTo(that.word);

		
	}

	// Question 3.2
	// Identique à la méthode count(Singly<String> l) excepté que la liste renvoyée 
	// est triée par ordre décroissant de multiplicité.
	
	static Singly<Occurrence> sortedCount(Singly<String> l) {

		
		return MergeSort.sort(count(l));

	}
}

/* MERGE_SORT */

// Version générique de MergeSortString
// On remplace le type «String» par le type générique «E» dans l'implémentation de MergeSort

class MergeSort {
	
	// Question 3.1
	// Identique à merge(Singly<String> l1, Singly<String> l2) avec «E» au lieu de «String»
	
	static<E extends Comparable<E>> Singly<E> merge(Singly<E> l1, Singly<E> l2) {

		if (l1 == null) return l2;
		if (l2 == null) return l1;

		Singly<E> result;
		if(l1.element.compareTo(l2.element)<0){
			//append the element l1 to result
			result = l1;   //pointer of l1
			l1 = l1.next; //since l1 is appended to result
		}
		else{
			result = l2;  //pointer of l2
			l2 = l2.next;
		}

		result.next = null;
		Singly<E> last = result;  //another pointer of result

		while( l1 != null && l2 !=null){
			if( l1.element.compareTo(l2.element)<0){
				last.next = l1;
				last = last.next;    //move last to the next element so it stays at the end of the array

				l1 = l1.next;
			}
			else{
				last.next = l2;
				last = last.next;

				l2 = l2.next;
			}

		}
		if( l1 != null){
			last.next = l1;

		}
		if(l2 != null){

			last.next = l2;
		}

		return result;


	
		
	}

	// Question 3.1
	// Identique à sort(Singly<String> l) avec «E» au lieu de «String»
	
	static<E extends Comparable<E>> Singly<E> sort(Singly<E> l) {

		if (l == null || l.next == null) return l;

		Singly<E> l1 = sort(Singly.split(l));
		Singly<E> l2 = sort(l);

		return merge(l1,l2);

		
	}

}

/* MEDIAN */

class Median {

	// Question 3.3
	// Renvoie une médiane de l'ensemble de valeurs numériques passé en argument
	// sous la forme d'une liste chaînée.
	
	static Pair<Double> median (Singly<Double> data) {

		if( data == null){
			return new Pair<>(Double.NaN, Double.NaN);

		}
		if( Singly.length(data) == 1){

			return new Pair(data.element, data.element);

		}

		data = MergeSort.sort(data);

		int n = Singly.length(data);



		
		for(int i = 1; i< n/2; i++){
				data = data.next;
			}
		if(n % 2 ==0){
			return new Pair(data.element, data.next.element);
		}
		
		
		return new Pair(data.next.element, data.next.element);

	}
}
		