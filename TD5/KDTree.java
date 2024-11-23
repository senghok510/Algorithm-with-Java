import java.util.Vector;

public class KDTree {
	int depth;
	double[] point;
	KDTree left;
	KDTree right;

	KDTree(double[] point, int depth) {
		this.point = point;
		this.depth = depth;
	}

	boolean compare(double[] a) {
		double diff = a[this.depth%a.length]- this.point[depth%a.length];
		return (diff >=0);

	}
	static KDTree insert(KDTree tree, double[] p, int depth){
		if(tree == null){
			return new KDTree(p,depth);
		}
		if(tree.compare(p) == false){
			tree.left = insert(tree.left,p, depth+1);
		}
		else{
			tree.right = insert(tree.right,p,depth+1);
		}
		return tree;


	}

	static KDTree insert(KDTree tree, double[] p) {
		return insert(tree, p, 0);
		
	}

	static double sqDist(double[] a, double[] b) {
		double distance = 0.0;
		for(int i =0; i<a.length;i++){
			distance += (a[i]-b[i])*(a[i]-b[i]);
		}
		return distance;
	}

	static double[] closestNaive(KDTree tree, double[] a, double[] champion) {
		if(tree == null){
			return champion;
		}
		if(sqDist(a,champion)> sqDist(a,tree.point)){
			champion = tree.point;
		}
		champion = closestNaive(tree.left, a,champion);

		return closestNaive(tree.right,a,champion);
	
	}


	static double[] closestNaive(KDTree tree, double[] a) {
		return closestNaive(tree, a,tree.point);
	}

	double difference(double[] a) {
		//throw new Error("Methode double difference(double[] a) a completer (Question 2.1)");
		return a[depth % a.length] - point[depth % point.length];
	}

	static double[] closest(KDTree tree, double[] a, double[] champion) {

		if (tree == null)
			return champion;

		// sert a InteractiveClosest pour afficher la progression
		InteractiveClosest.trace(tree.point, champion);

		// throw new Error("Methode double[] closest(KDTree tree, double[] a, double[] champion) a completer (Question 3.3)");
		
		double c = tree.difference(a);
		KDTree first, second;
		if (c < 0) { // a est range dans le sous-arbre gauche
			first = tree.left;
			second = tree.right;
		} else { // a est range dans le sous-arbre droit
			first = tree.right;
			second = tree.left;
		}

		champion = closest(first, a, champion);

		double d = sqDist(a, champion);

		if (d >= c * c) {

			// on compare la distance entre a et champion et
			// celle entre a et le plan de coupure

			// si la distance entre a et champion de first est
			// strictement superieure a celle entre a et tree.point,
			// on actualise champion
			if (d > sqDist(a, tree.point))
				champion = tree.point;
			champion = closest(second, a, champion);
		}

		return champion;

	}




	

	static double[] closest(KDTree tree, double[] a) {
		// throw new Error("Methode double[] closest(KDTree tree, double[] a) a completer (Question 3.3)");
		if (tree == null)
			return null;
		return closest(tree, a, tree.point);
	}









	




		

	

	static int size(KDTree tree) {
		if(tree == null){   //sinon, NullpointerException
			return 0;
		}
		return 1 + size(tree.left) + size(tree.right);
		
	}

	//qui calcule la somme des points de l’arbre tree, l’ajoute à acc;

	static void sum(KDTree tree, double[] acc) {
		if(tree ==null){
			return;
		}
		for(int i =0; i<tree.point.length;i++){
			acc[i] += tree.point[i];
		}
		KDTree.sum(tree.left,acc);
		KDTree.sum(tree.right,acc);
	}

	//renvoie le point isobarycentre (c’est-à-dire la valeur moyenne) des points de l’arbre, ou null pour un arbre vide.

	static double[] average(KDTree tree) {
		double[] acc = new double[tree.point.length];

		KDTree.sum(tree,acc);
		for(int i =0; i<tree.point.length;i++){
			acc[i] = acc[i]/KDTree.size(tree);
		}

		return acc;

		
	}

	//La palette est composée à partir d’un arbre 3d contenant les couleurs 
	//de 20000 pixels choisis aléatoirement dans l’image. Les 256 couleurs seront obtenues 
	//en calculant l’isobarycentre des points de sous-arbres bien choisis.


	//L’objectif de cette partie est d’écrire la fonction palette qui renvoie un objet de type Vector<double[]> 
	//contenant un nombre prescrit de points à même d’approcher les points d’un arbre kd donné.

//le but c'est de compresser le tree(20000) couleur en 256 couleurs(max)
	static int palette(KDTree tree, int maxpoints, Vector<double[]> acc) {

		if( tree == null || maxpoints<=0){
			return 0;
		}
		if(maxpoints ==1){
			acc.add(average(tree));
			return 1;
		}
		
		int leftpoint = maxpoints/2;

		
		int total = palette(tree.right,leftpoint,acc);
		total += palette(tree.left, maxpoints-leftpoint,acc);
		if(total < maxpoints){
			acc.add(average(tree));
			total++;
		}
		
		return total;
	}

	// elague tree de maniere a ne conserver que
	// maxpoints, renvoie le vecteur de points qui en resulte
	static Vector<double[]> palette(KDTree tree, int maxpoints) {

		// throw new Error("Methode Vector<double[]> palette(KDTree, int) a completer (Question 4.2)");
		Vector<double[]> palete = new Vector<double[]>();
		palette(tree,maxpoints,palete);
		return palete;
	}

	public String pointToString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (this.point.length > 0)
			sb.append(this.point[0]);
		for (int i = 1; i < this.point.length; i++)
			sb.append("," + this.point[i]);
		sb.append("]");
		return sb.toString();
	}

}
