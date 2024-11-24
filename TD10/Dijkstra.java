/* TD10. Plus courts chemins */

import java.lang.reflect.Field;
import java.util.PriorityQueue;

// Algorithme de Dijkstra
class Dijkstra {
	final Graph g; // le graphe de travail
	final int source; // source du plus court chemin recherche
	final int dest; // destination du plus court chemin recherche
	private Fenetre f; // fenetre pour la visualisation

	// Questions 1.1, 1.2 et 4
	int[] dist;
       
	int[] pred;
	boolean[] settled;
	PriorityQueue<Node> unsettled;
	
	
	/* Méthodes à compléter */
	
	// Questions 1.1 et 1.2
	
	// constructeur
	Dijkstra(Graph g, int source, int dest) {
		this.g = g;
		this.source = source;
		this.dest = dest;
		this.dist = new int[g.nbVertices];
		this.settled = new boolean[g.nbVertices];
		this.pred = new int[g.nbVertices];
		

		for(int i =0;i<g.nbVertices;i++){
			if( i == source){
				dist[i] =0;
				continue;
			}
			dist[i] = Integer.MAX_VALUE;
		}
		for(int i =0; i<g.nbVertices;i++){
			pred[i] = -1;
		}

		for(int i =0; i<g.nbVertices;i++){
			settled[i] = false;
		}
		pred[source] = source;
		this.unsettled = new PriorityQueue<>();
		this.unsettled.add(new Node(source, 0));

		
	}
	
	// Question 2.1 et 2.2

	// mise a jour de la distance, de la priorite, et du predecesseur d'un sommet
	//check if the shorter path to the successor node has been found via current node
	// succ et current sont les sommets
	void update(int succ, int current) {

		
		if(dist[succ]> dist[current] + g.weight(current,succ )){
			dist[succ] = dist[current] + g.weight(current, succ);
			pred[succ] = current;
			this.unsettled.add(new Node(succ, dist[succ])); //add the node in w  //we update the dis in unsettle(w)
			g.drawUnsettledPoint(f, succ);
		}
	}	
	
	
	// Question 2.1

	// trouve le prochain sommet de unsettled non traite
	int nextNode() {

		while(!unsettled.isEmpty()){
			Node n = unsettled.poll();  //la plus petite valeur
			if(settled[n.id] == false){
				return n.id;
			}

		}
		return -1;			
	}
	
	// Questions 2.1, 2.2 et 4

	// une etape de l'algorithme Dijkstra

	//
	int oneStep() {
		slow();



		int current = nextNode();  //it 's the next sommet that 's not been settled yet(on l'extait de w) 
		if(current == -1){
			return -1;
		}		
		this.settled[current] = true;   //traité
		g.drawSettledPoint(f, current);
		for(int next : g.successors(current)) {
				update(next, current); //update dist[next](we found an optimized path)
			}

		return current;

			
	}
	
	// Question 2.1

	// algorithme de Dijkstra complet
	int compute() {
		int curr=0;

		while(curr != dest && curr != -1){
			curr = oneStep();  //we extract iteratively from w and mark it as traité,
								// we calculate the 
		}

		return (curr == dest) ? dist[curr] : -1;


			
	}
	
	// Question 4
	
	public int getSteps() {
		throw new Error("Méthode Dijkstra.getSteps() à implémenter (Question 4)");
				
	}
	
	/* Méthodes à ne pas changer */
	
	// ralentisseur visualisation
	void slow(){
		if(f == null) return;
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {}
	}
	
	void setFenetre (Fenetre f) { this.f = f; }

	// Cette fonction vérifie si le vecteur 'int[] name' est 
	// présent dans la classe et le renvoie. Sinon, renvoie null 
	private int[] getIntArray(String name) {
		Field field = null;
		for (Field f : getClass().getDeclaredFields()) {
			if (f.getName().equals(name)) {
				field = f;
				break;
			}
		}
		if (field == null)
			return null;
		
		int[] result = null;
		try {
			result = (int[]) field.get(this);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int[] getPred() {
		return getIntArray("pred");
	}
	
	public int[] getDist() {
		return getIntArray("dist");
	}	
		
	public void draw () {
		g.drawSourceDestination(f, source, dest);
		g.drawPath(f, getPred(), dest);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Dijkstra))
			return false;
		
		Dijkstra that = (Dijkstra) obj;
		return g.equals(that.g) && source == that.source && dest == that.dest;
	}
}
