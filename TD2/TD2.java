/* TD2. Fruits et tables de hachage
 * Ce fichier contient 7 classes:
 * 		- Row représente une ligne de fruits,
 * 		- CountConfigurationsNaive compte naïvement les configurations stables,
 * 		- Quadruple manipule des quadruplets,
 * 		- HashTable construit une table de hachage,
 * 		- CountConfigurationsHashTable compte les configurations stables en utilisant notre table de hachage,
 * 		- Triple manipule des triplets,
 * 		- CountConfigurationsHashMap compte les configurations stables en utilisant la HashMap de java.
 */

 import java.util.HashMap;
 import java.util.LinkedList;
 import java.util.Vector;
 
 class Row { // représente une ligne de fruits
	 private final int[] fruits;
 
	 // constructeur d'une ligne vide
	 Row() {
		 this.fruits = new int[0];
	 }
 
	 // constructeur à partir du champ fruits
	 Row(int[] fruits) {
		 this.fruits = fruits;
	 }
 
	 // méthode equals pour comparer la ligne à un objet o
	 @Override
	 public boolean equals(Object o) {
		 // on commence par transformer l'objet o en un objet de la classe Row
		 // ici on suppose que o sera toujours de la classe Row
		 Row that = (Row) o;
		 // on vérifie que les deux lignes ont la meme longueur
		 
		 if(len(that.fruits) != len(this.fruits)){
			 return false;
		 }
		 for(int fruit=0; fruit<len(this.fruits); fruit++){
			 if(this.fruits[fruit] == that.fruits[fruit]){
				 return false;
			 }
		 }
		 
		 // on a alors bien l'égalité des lignes
		 return true;
	 }
 
	 // code de hachage de la ligne
	 @Override
	 //hashcode is default in the String class
	 public int hashCode() {
		 int hash = 0;
		 for (int i = 0; i < fruits.length; ++i) {
			 hash = 2 * hash + fruits[i];
		 }
		 return hash;
	 }
 
	 // chaîne de caracteres représentant la ligne
	 @Override
	 public String toString() {
		 StringBuffer s = new StringBuffer();
		 for (int i = 0; i < fruits.length; ++i)
			 s.append(fruits[i]);
		 return s.toString();
	 }
 
	 // Question 1
 
	 // renvoie une nouvelle ligne en ajoutant fruit à la fin de la ligne
	 Row extendedWith(int fruit) {
		 // throw new Error("Méthode extendedWith(int fruit) à compléter (Question 1)");
		 int[] newFruits = new int[fruits.length + 1];
		 for (int i = 0; i < fruits.length; ++i)
			 newFruits[i] = fruits[i];
		 newFruits[fruits.length] = fruit;
		 return new Row(newFruits);
	 }
 
	 // renvoie la liste de toutes les lignes stables de largeur width
	 static LinkedList<Row> allStableRows(int width) {
		 // throw new Error("Méthode allStableRows(int width) à compléter (Question 1)");
		 LinkedList<Row> res = new LinkedList<Row>();
		 if (width == 0) {
			 res.add(new Row());
		 } 
		 else {
			 // on parcourt toutes les lignes stables de largueur width-1
			 for (Row r : allStableRows(width - 1)) {
				 // on essaie d'ajouter 0 ou 1 à chaque ligne r
				 for (int fruit = 0; fruit < 2; fruit++) {
					 // si la ligne était de longueur < 2 ou qu'au moins un
					 // de ses deux derniers fruits est distinct de fruit
					 // on peut ajouter fruit et préserver la stabilite
					 int l = r.fruits.length;
					 if (l < 2 || r.fruits[l - 2] != fruit || r.fruits[l - 1] != fruit)
						 res.add(r.extendedWith(fruit)); //when we extend the largeur of the ligne equal to width.
				 }
			 }
		 }
		 return res;
	 }
 
	 // vérifie si la ligne peut s'empiler avec les lignes r1 et r2
	 // sans avoir trois fruits du même type adjacents
	 boolean areStackable(Row r1, Row r2) {
		 // throw new Error("Méthode areStackable(Row r1, Row r2) à compléter (Question
		 // 1)");
		 if (this.fruits.length != r1.fruits.length || this.fruits.length != r2.fruits.length)
			 return false;
		 for (int i = 0; i < this.fruits.length; ++i) {
			 if (this.fruits[i] == r1.fruits[i] && this.fruits[i] == r2.fruits[i])
				 return false;
		 }
		 return true;
 
	 }
 }
 
 // COMPTAGE NAIF
 class CountConfigurationsNaive { // comptage des configurations stables
 
	 // Question 2
 
	 // renvoie le nombre de grilles dont les premières lignes sont r1 et r2,
	 // dont les lignes sont des lignes de rows et dont la hauteur est height
	 static long count(Row r1, Row r2, LinkedList<Row> rows, int height) {
		 // throw new Error(
		 // "Méthode count(Row r1, Row r2, LinkedList<Row> rows, int height) de la classe
		 // CountConfigurationsHashNaive à compléter (Question 2)");
		 if (height <= 1)
			 return 0; // aucune configuration car on veut au moins r1 et r2(soit (r1) soit (r2))
		 if (height == 2)
			 return 1; // une seule configuration (r1,r2)(stable)
		 long res = 0;
		 for (Row r3 : rows) {
			 if (r3.areStackable(r1, r2))
				 res += count(r2, r3, rows, height - 1);
		 }
		 return res;
 
	 }
 
	 // renvoie le nombre de grilles à n lignes et n colonnes
	 static long count(int n) {
		 // throw new Error("Méthode count(int n) de la classe
		 // CountConfigurationsHashNaive à compléter (Question 2)");
		 LinkedList<Row> rows = Row.allStableRows(n);
		 // n <= 1
		 if (n <= 1)
			 return rows.size();
		 // n > 1
		 long res = 0;
		 // on parcourt toutes les paires (r1,r2) ...
		 for (Row r1 : rows)
			 for (Row r2 : rows)
				 // ... et on ajoute le nombre de grilles qui commencent par (r1,r2)
				 res += count(r1, r2, rows, n);
		 return res;
	 }
 }
 
 // CONSTRUCTION ET UTILISATION D'UNE TABLE DE HACHAGE
 
 class Quadruple { // quadruplet (r1, r2, height, result)
	 Row r1;
	 Row r2;
	 int height;
	 long result;
 
	 Quadruple(Row r1, Row r2, int height, long result) {
		 this.r1 = r1;
		 this.r2 = r2;
		 this.height = height;
		 this.result = result;
	 }
 }
 
 class HashTable { // table de hachage
	 final static int M = 50000;
	 Vector<LinkedList<Quadruple>> buckets;
 
	 // Question 3.1
 
	 HashTable() {
		 // throw new Error("Constructeur HashTable() à compléter (Question 3.1)");
		 buckets = new Vector<LinkedList<Quadruple>>(M);
		 for (int i = 0; i < M; i++) {
			 buckets.add(new LinkedList<Quadruple>());
		 }
	 }
 
	 // Question 3.2
 
	 // renvoie le code de hachage du triplet (r1, r2, height)
	 static int hashCode(Row r1, Row r2, int height) {
		 // throw new Error("Méthode hashCode(Row r1, Row r2, int height) à compléter
		 // (Question 3.2)");
		 return 13 * r1.hashCode() * r1.hashCode() + 17 * r2.hashCode() * r2.hashCode() + height;
	 }
 
	 // renvoie le seau du triplet (r1, r2, height)
	 private static int bucket(Row r1, Row r2, int height) {
		 // throw new Error("Méthode bucket(Row r1, Row r2, int height) à compléter
		 // (Question 3.2)");
		 int mod = hashCode(r1, r2, height) % M;
		 return (mod >= 0 ? mod : mod + M);
	 }
 
	 // Question 3.3
 
	 // ajoute le quadruplet (r1, r2, height, result) dans le seau indiqué par la
	 // methode bucket
	 void add(Row r1, Row r2, int height, long result) {
		 // throw new Error("Méthode add(Row r1, Row r2, int height, long result) à
		 // compléter (Question 3.3)");
		 //to get acces to the element we use the modula above(get(modulo))
		 //in this stage, we add element with purpose with an indice defined by the hash method
		 buckets.get(bucket(r1, r2, height)).add(new Quadruple(r1, r2, height, result));
	 }
 
	 // Question 3.4
 
	 // recherche dans la table une entrée pour le triplet (r1, r2, height)
	 Long find(Row r1, Row r2, int height) {
		 // throw new Error("Méthode Quadruple find(Row r1, Row r2, int height) à
		 // compléter (Question 3.4)");
		 for (Quadruple q : buckets.get(bucket(r1, r2, height))) {
			 if (r1.equals(q.r1) && r2.equals(q.r2) && height == q.height)
				 //return new Long(q.result);
				 return Long.valueOf(q.result);
		 }	
		 return null;
	 }
 
 }
 
 class CountConfigurationsHashTable { // comptage des configurations stables en utilisant notre table de hachage
	 static HashTable memo = new HashTable();
 
	 // Question 4
 
	 // renvoie le nombre de grilles dont les premières lignes sont r1 et r2,
	 // dont les lignes sont des lignes de rows et dont la hauteur est height
	 // en utilisant notre table de hachage
	 static long count(Row r1, Row r2, LinkedList<Row> rows, int height) {
		 // throw new Error(
		 // "Méthode count(Row r1, Row r2, LinkedList<Row> rows, int height) de la classe
		 // CountConfigurationsHashTable à compléter (Question 4)");
		 if (height <= 1)
			 return 0; // aucune configuration car on veut au moins r1 et r2
		 if (height == 2)
			 return 1; // une seule configuration (r1,r2)
		 // on teste si on a deja fait ce calcul ...
		 Long res = memo.find(r1, r2, height);
		 if (res != null)
			 return res;
		 // ... sinon on le fait ...
		 res = 0L;
		 for (Row r3 : rows) {
			 if (r3.areStackable(r1, r2))
				 res += count(r2, r3, rows, height - 1);
		 }
		 // ... et on mémorise le résultat de ce calcul
		 memo.add(r1, r2, height, res);
		 return res;
 
	 }
 
	 // renvoie le nombre de grilles a n lignes et n colonnes
	 static long count(int n) {
		 // throw new Error("Méthode count(int n) de la classe
		 // CountConfigurationsHashTable à compléter (Question 4)");
		 LinkedList<Row> rows = Row.allStableRows(n);
		 // n <= 1
		 if (n <= 1)
			 return rows.size();
		 // n > 1
		 long res = 0;
		 // on parcourt toutes les paires (r1,r2) ...
		 for (Row r1 : rows)
			 for (Row r2 : rows)
				 // ... et on ajoute le nombre de grilles qui commencent par (r1,r2)
				 res += count(r1, r2, rows, n);
		 return res;
 
	 }
 }
 
 //UTILISATION DE HASHMAP
 
 class Triple { // triplet (r1, r2, height)
	 Row r1;
	 Row r2;
	 int height;
 
	 // constructeur
	 Triple(Row r1, Row r2, int height) {
		 this.r1 = r1;
		 this.r2 = r2;
		 this.height = height;
	 }
 
	 // méthode equals pour comparer le triplet à un objet o
	 @Override
	 public boolean equals(Object o) {
		 Triple that = (Triple) o;
		 return this.r1.equals(that.r1) && this.r2.equals(that.r2) && this.height == that.height;
	 }
 
	 // renvoie le code de hachage du triplet
	 @Override
	 public int hashCode() {
		 return HashTable.hashCode(r1, r2, height);
	 }
 
 }
 
 class CountConfigurationsHashMap { // comptage des configurations stables en utilisant la HashMap de java
	 static HashMap<Triple, Long> memo = new HashMap<Triple, Long>();
 
	 // Question 5
 
	 // renvoie le nombre de grilles dont les premières lignes sont r1 et r2,
	 // dont les lignes sont des lignes de rows et dont la hauteur est height
	 // en utilisant la HashMap de java
	 static long count(Row r1, Row r2, LinkedList<Row> rows, int height) {
		 // throw new Error(
		 // "Méthode count(Row r1, Row r2, LinkedList<Row> rows, int height) de la classe
		 // CountConfigurationsHashMap à compléter (Question 5)");
		 if (height <= 1)
			 return 0; // aucune configuration car on veut au moins r1 et r2
		 if (height == 2)
			 return 1; // une seule configuration (r1,r2)
		 Triple t = new Triple(r1, r2, height);
		 Long res = memo.get(t);
		 if (res != null)
			 return res;
		 res = new Long(0);
		 for (Row r3 : rows) {
			 if (r3.areStackable(r1, r2))
				 res += count(r2, r3, rows, height - 1);
		 }
		 memo.put(t, res);
		 return res;
	 }
 
	 // renvoie le nombre de grilles à n lignes et n colonnes
	 static long count(int n) {
		 // throw new Error("Méthode count(int n) de la classe CountConfigurationsHashMap
		 // à compléter (Question 5)");
		 LinkedList<Row> rows = Row.allStableRows(n);
		 // n <= 1
		 if (n <= 1)
			 return rows.size();
		 // n > 1
		 long res = 0;
		 // on parcourt toutes les paires (r1,r2) ...
		 for (Row r1 : rows)
			 for (Row r2 : rows)
				 // ... et on ajoute le nombre de grilles qui commencent par (r1,r2)
				 res += count(r1, r2, rows, n);
		 return res;
	 }
 }
 