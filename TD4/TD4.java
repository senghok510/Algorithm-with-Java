/**
 *  TD4. Rebroussement, solution et génération de labyrinthes <br>
 * Ce fichier contient 2 classes: <br>
 * 	- ExtendCell fournit à une case du labyrinthe des opérations pour calculer un chemin vers la sortie et générer un labyrinthe de manière récursive <br>
 * 	- Maze modélise un labyrinthe.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Cette classe permet d'étendre et enrichir la représentation d'une case du labyrinthe. <br>
 * Elle fournit à une case les opérations permettant de: <br>
 * -) trouver un chemin vers la sortie <br>
 * -) Génerer un labyrinthe de manière récursive
 * 
 */
class ExtendedCell extends Cell {
	
	public ExtendedCell(Maze maze) {
		super(maze);
	}
	
	// Question 1

	/**
	 * Teste s'il existe un chemin de la case actuelle vers une sortie
	 * 
	 * @return  true ssi il existe un chemin de la case actuelle vers une sortie
	 */
	boolean searchPath() {
		maze.slow(); // ralentit l'animation de la recherche (pour aider à debugger)
		//point de départ
		this.isMarked();

		for( Cell c : getNeighbors(false)){
			if(!c.isMarked() && c.searchPath()){
				return true;
			}

		}
		this.setMarked(false);
		return false;

		
		

		
	}

	// Question 2

	/**
	 * Génère un labyrinthe parfait par rebroussement récursif
	 */
	void generateRec() {
		maze.slow();
		List<Cell> neighbor = this.getNeighbors(true);
		Collections.shuffle(neighbor);
		for(Cell c: neighbor){
			if(c.isIsolated()){
				this.breakWall(c);
				c.generateRec();
			}
		}

	}

}

/**
 * Cette classe modélise un labyrinthe
 */
class Maze {

	private int height, width;
	/** La grille (tableau de cellules) représentant le labyrinthe */
	private Cell[][] grid;


	// Question 3

	/**
	 * Génère un labyrinthe parfait par rebroussement itératif
	 */
	void generateIter(int selectionMethod) {
		Bag cells = new Bag(selectionMethod);
		cells.add(getFirstCell());

		while(!cells.isEmpty()) {
			slow();
			Cell cell = cells.peek();
			List<Cell> voisin = cell.getNeighbors(true);
			Collections.shuffle(voisin);
			boolean pop = true;  //for giving the condition
			potato:
			for(Cell c: voisin){
				if(c.isIsolated()){
					cell.breakWall(c);
					cells.add(c);
					pop = false;
					break potato;
				}
			}
			if(pop)
			cells.pop();
		}
		


	}


	// Question 4
	
	/**
	 * Génère un labyrinthe avec l'algorithme de Wilson
	 */
	void generateWilson() {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		
		for(Cell[] row : grid){
			for(Cell cell  : row){
				cells.add(cell);
			}
		}
		Collections.shuffle(cells);


		//Choisir une case uniformement au hasard et la marquer.
		Cell c0= cells.get(0);
		c0.setMarked(true);
		cells.remove(c0);
		while(!cells.isEmpty()){
			Cell c1 = cells.get(0);
			cells.remove(0);
			Cell temp = c1;
			while(!temp.isMarked()){
				List<Cell> voisin = temp.getNeighbors(true);
				Collections.shuffle(voisin);
				Cell cell = voisin.get(0);
				temp.next = cell;
				temp = temp.next;
				}



			temp  = c1;

			while(!temp.isMarked()){
				slow();
				
			temp.breakWall(temp.next);
			temp.setMarked(true);
			temp = temp.next;			
			}



		}


		
		//stocker une liste-chainée sans prendre en compte le mur
	

			
		}
		
		
			
			


		


	private int getSize(ArrayList<Cell> cells) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getSize'");
	}


	/**
	 * Renvoie la cellule avec les coordonnées (i, j)
	 * 
	 * @return  la cellule avec les coordonnées (i, j)
	 */
	Cell getCell(int i, int j) {
		if(i < 0 || i >= height || j < 0 || j >= width)
			throw new IllegalArgumentException("invalid indices");

		return grid[i][j];
	}

	/**
	 * Renvoie la cellule avec les coordonnées (0, 0)
	 * 
	 * @return  la cellule avec les coordonnées (0, 0)
	 */
	Cell getFirstCell() {
		return getCell(0, 0);
	}

	// traduit coordonnées en numéro de cellule
	int coordToInt(int i, int j) {
		if(i < 0 || i >= height || j < 0 || j >= width)
			throw new IndexOutOfBoundsException();

		return i*width + j;
	}

	// traduit un numéro de cellule en Coordinate
	Coordinate intToCoord(int x) {
		if(x < 0 || x >= height*width)
			throw new IndexOutOfBoundsException();

		return new Coordinate(x/width, x%width);
	}


	// ralentit l'affichage du labyrinthe si une fenêtre graphique est ouverte
	void slow(){
		if (frame == null) return;

		try {
			Thread.sleep(10);
			frame.repaint();
		} catch (InterruptedException e) {}
	}

	private MazeFrame frame;
	private static final int step = 20;

	Maze(int height, int width) {
		this(height, width, true);
	}

	Maze(int height, int width, boolean window) {
		if((height <= 0) || (width <= 0))
			throw new IllegalArgumentException("height and width of a Maze must be positive");

		this.height = height;
		this.width = width;

		grid = new Cell[height][width];

		for(int i = 0; i < height; ++i)
			for(int j = 0; j < width; ++j)
				grid[i][j] = new ExtendedCell(this);

		for(int i = 0; i < height; ++i) {
			for(int j = 0; j < width; ++j) {
				if(i < height - 1) {
					grid[i][j].addNeighbor(grid[i+1][j]);
					grid[i+1][j].addNeighbor(grid[i][j]);
				}

				if(j < width - 1) {
					grid[i][j].addNeighbor(grid[i][j+1]);
					grid[i][j+1].addNeighbor(grid[i][j]);
				}
			}
		}

		grid[height-1][width-1].setExit(true);

		if(window)
			frame = new MazeFrame(grid, height, width, step);
	}

	Maze(String path) throws IOException {
		this(Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8));
	}

	Maze(String path, boolean window) throws IOException {
		this(Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8), window);
	}

	Maze(List<String> lines) {
		this(lines, true);
	}

	Maze(List<String> lines, boolean window) {
		if(lines.size() < 2)
			throw new IllegalArgumentException("too few lines");

		this.height = Integer.parseInt(lines.get(0));
		this.width = Integer.parseInt(lines.get(1));

		this.grid = new Cell[height][width];
		for(int i = 0; i < height; ++i)
			for(int j = 0; j < width; ++j)
				grid[i][j] = new ExtendedCell(this);

		for(int i = 0; i < height; ++i) {
			for(int j = 0; j < width; ++j) {
				if(i < height - 1) {
					grid[i][j].addNeighbor(grid[i+1][j]);
					grid[i+1][j].addNeighbor(grid[i][j]);
				}

				if(j < width - 1) {
					grid[i][j].addNeighbor(grid[i][j+1]);
					grid[i][j+1].addNeighbor(grid[i][j]);
				}
			}
		}

		grid[height-1][width-1].setExit(true);

		int i = 0;
		int j = 0;

		for(String line : lines.subList(2, lines.size())) {

			for(int k = 0; k < line.length(); ++k) {
				switch(line.charAt(k)) {
					case 'N':
						grid[i][j].breakWall(grid[i-1][j]);
						break;
					case 'E':
						grid[i][j].breakWall(grid[i][j+1]);
						break;
					case 'S':
						grid[i][j].breakWall(grid[i+1][j]);
						break;
					case 'W':
						grid[i][j].breakWall(grid[i][j-1]);
						break;
					case '*':
						grid[i][j].setMarked(true);
						break;
					default:
						throw new IllegalArgumentException("illegal character");
				}
			}
			++j;
			if(j >= width) {
				j = 0;
				++i;
			}
		}

		if(window)
			frame = new MazeFrame(grid, height, width, step);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(height);
		sb.append('\n');
		sb.append(width);
		sb.append('\n');

		for(int i = 0; i < height; ++i) {
			for(int j = 0; j < width; ++j) {
				if(i > 0 && grid[i][j].hasPassageTo(grid[i-1][j]))
					sb.append('N');
				if(j < width-1 && grid[i][j].hasPassageTo(grid[i][j+1]))
					sb.append('E');
				if(i < height-1 && grid[i][j].hasPassageTo(grid[i+1][j]))
					sb.append('S');
				if(j > 0 && grid[i][j].hasPassageTo(grid[i][j-1]))
					sb.append('W');
				if(grid[i][j].isMarked())
					sb.append('*');
				sb.append('\n');
			}
		}

		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Maze))
			return false;
		Maze that = (Maze)o;

		return this.toString().equals(that.toString());
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	boolean isPerfect() {
		UnionFind uf = new UnionFind(height*width);

		// union find cycle detection
		for(int i = 0; i < height; ++i) {
			// horizontal edges
			for(int j = 0; j < width-1; ++j) {
				if(grid[i][j].hasPassageTo(grid[i][j+1])) {
					if(uf.sameClass(coordToInt(i,j), coordToInt(i,j+1)))
						return false;
					uf.union(coordToInt(i,j), coordToInt(i,j+1));
				}
			}

			// there are no vertical edges in last row, so we're done
			if(i == height-1)
				continue;

			// vertical edges
			for(int j = 0; j < width; ++j) {
				if(grid[i][j].hasPassageTo(grid[i+1][j])) {
					if(uf.sameClass(coordToInt(i,j), coordToInt(i+1,j)))
						return false;
					uf.union(coordToInt(i,j), coordToInt(i+1,j));
				}
			}
		}

		// check if connected
		return (uf.getSize(0) == height*width);
	}

	void clearMarks() {
		for (Cell[] row : grid)
			for (Cell c : row)
				c.setMarked(false);
	}
}

