/* Jeu de Hex
   https://fr.wikipedia.org/wiki/Hex

   grille n*n

   cases jouables : (i,j) avec 1 <= i, j <= n

   bords bleus (gauche et droite) : i=0 ou i=n+1, 1 <= j <= n
   bords rouges (haut et bas) : 1 <= i <= n, j=0 ou j=n+1

   note : les quatre coins n'ont pas de couleur

   adjacence :      i,j-1   i+1,j-1

                 i-1,j    i,j   i+1,j

                    i-1,j+1    i,j+1

*/

public class Hex {
  private int n;
  private Player[][] grid;
  private Player currentPlayer;
  private int[] link; // Union-Find structure
    private int[] rank; // Union-Find structure for rank (used for path compression)
    private int blueLeftRight; // Virtual node for BLUE connecting left and right
    private int redTopBottom;  // Virtual node for RED connecting top and bottom
   

  enum Player {
    NOONE, BLUE, RED
  }


  // crée un plateau vide de taille n*n
  Hex(int n) {
    this.n =n;
    this.grid = new Player[n+2][n+2];
    this.currentPlayer = Player.RED;
    this.link = new int[n * n +2 ]; // +2 for the two virtual nodes
    this.rank = new int[n * n+2];


    //blueLeftRight = n * n; // Virtual node for BLUE left-right
    //redTopBottom = n * n+1 ; // Virtual node for RED top-bottom
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        grid[i][j] = Player.NOONE;
      }
  }
    int tab[] = {0,n+1};
    for(int i: tab){
      for(int j =1; j<= n;j++){
        grid[i][j] = Player.BLUE;
      }

    }
    for(int j: tab){
      for(int i =1; i<=n;i++){
        grid[i][j] = Player.RED;
      }
    }



    for (int i = 0; i < n * n + 2; i++) {
      link[i] = i;
      rank[i] = 0;
}
}


   
  

  // renvoie la couleur de la case i,j
  //get color
  Player get(int i, int j) {
    if (i < 0 || i >= n || j < 0 || j >= n) {
      return Player.NOONE; // Out of bounds
  }
    return grid[i][j];
   
  }
  private int find(int x) {
    if (link[x] != x) {
        link[x] = find(link[x]);
    }
    return link[x];
}
  private void union(int x, int y) {
    int rootX = find(x);
    int rootY = find(y);

    if (rootX != rootY) {
        if (rank[rootX] > rank[rootY]) {
          link[rootY] = rootX;
            
        } else if (rank[rootX] < rank[rootY]) {
          link[rootX] = rootY;
            
        } else {
            link[rootY] = rootX;
            rank[rootX]++;
        }
    }
  }


  // Met à jour le plateau après que le joueur avec le trait joue la case (i, j).
  // Ne fait rien si le coup est illégal.
  // Renvoie true si et seulement si le coup est légal.

  //update the plateau(if le coup is legal) and return true
  boolean click(int i, int j) {
    if (i < 0 || i > n || j < 0 || j > n) {
      return false;
  }
    if(i==n || j ==n){
      return true;

    }


    if( grid[i][j] != Player.NOONE){
      return false;
    }
    
    grid[i][j] = currentPlayer;
    

      // After a valid move, switch the player
    currentPlayer = (currentPlayer == Player.BLUE) ? Player.RED : Player.BLUE;
   

    return true;
   

  }

  // Renvoie le joueur avec le trait ou Player.NOONE si le jeu est terminé par
  // la victoire d'un joueur.
  Player currentPlayer() {
    if (winner() != Player.NOONE) {
        return Player.NOONE;  // 
    }
    return currentPlayer;
}
  


  // Renvoie le joueur gagnant, ou Player.NOONE si aucun joueur n'est encore
  // gagnant
  

  int label(int i, int j) {
    return i+(n+2)*j;
  }
  private int toIndex(int i, int j) {
    return i * n + j;
}


Player winner() {
  // Check if BLUE has connected the left and right virtual nodes
  if (find(blueLeftRight) == find(toIndex(0, n ))) {
      return Player.BLUE;
  }

  // Check if RED has connected the top and bottom virtual nodes
  if (find(redTopBottom) == find(toIndex(n, 0))) {
      return Player.RED;
  }

  return Player.NOONE;
}


  public static void main(String[] args) {
    HexGUI.createAndShowGUI();
  }
}
