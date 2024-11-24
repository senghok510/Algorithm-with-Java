

/* TD10. Plus courts chemins */

//Algorithme de Dijkstra bidirectionnel
class BiDijkstra {
	final Graph g; // le graphe de travail
	final int source; // source du plus court chemin recherche
	final int dest; // destination du plus court chemin recherche
	final Dijkstra forward; // recherche de plus courts chemins depuis la source
	final Dijkstra backward; // recherche de plus courts chemins depuis la destination
	Dijkstra currentDijkstra, otherDijkstra; // sens de la prochaine iteration et celui oppose 
	
	private int last; // sommet traite par la derniere iteration
	
	private Fenetre f; // fenetre pour la visualisation
		
	/* Méthodes à compléter */ 
	
	// Question 3.1
	
	// constructeur
	BiDijkstra(Graph g, int source, int dest) {
		this.g = g;
		this.source = source;
		this.dest = dest;

		
		this.backward = new Dijkstra(g.reverse(), dest, source);
		this.forward = new Dijkstra(g, source, dest);
		this.currentDijkstra = this.forward;
		this.otherDijkstra = this.backward;
		this.last = source;

	}

	// Question 3.1
	
	// changer la direction de recherche
	void flip() {

		Dijkstra tmp = this.currentDijkstra;
		this.currentDijkstra = this.otherDijkstra;
	
		this.otherDijkstra = tmp;
	}

	// Question 3.1
	
	// une iteration de Dijkstra bidirectionnel
	void oneStep() {
		//the forward
		last = currentDijkstra.oneStep();
		
	}
	
	// Question 3.1
	
	// test de terminaison
	boolean isOver() {
		return forward.settled[last] && backward.settled[last];
		
	}
		
	// Questions 3.1 et 3.2
	
	// renvoyer la longueur du plus cours chemin
	int getMinPath() {
		int curr = forward.dist[last] + backward.dist[last];

		for(Node n: backward.unsettled){
			int id = n.id;

			if(forward.settled[id]){
				int dist = forward.dist[id] + backward.dist[id];
				if(dist < curr){
					curr = dist;
					last = id;
				}


			}


		}
		return curr;





		
	}
	
	// Question 3.1
	
	// algorithme de Dijkstra bidirectionnel
	int compute() {

		while(isOver() != true){
			oneStep();
			flip();
		}
		return getMinPath();
		
	}
	
	// Question 4
	
	public int getSteps () {
		return forward.getSteps() + backward.getSteps();
		
	}

	/* Méthodes à ne pas modifier */ 
	
	int getLast() { return last; }
	
	public void setFenetre (Fenetre f) {
		this.f = f;
		forward.setFenetre(f);
		backward.setFenetre(f);
	}
	
	public void draw () {
	    g.drawSourceDestination(f, source, dest);
	    g.drawPath(f, forward.getPred(), backward.getPred(), last);
	}
}

