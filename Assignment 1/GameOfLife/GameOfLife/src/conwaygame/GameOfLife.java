package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */

    public GameOfLife (String file) {
        StdIn.setFile(file);
        int rows = StdIn.readInt();
        int columns = StdIn.readInt();

        grid = new boolean[rows][columns];
        
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                grid[i][j] = StdIn.readBoolean();
            }
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        return grid[row][col];

        // WRITE YOUR CODE HERE
        // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid[i].length; j++)
            {
                if (grid[i][j] == true)
                {
                    return true;
                }
            }
        }

        return false; // update this line, provided so that code compiles
        // WRITE YOUR CODE HERE
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {
        int countNeighbors = 0;

        for (int i = row-1; i <= row+1; i++) {
            for (int j = col-1; j <= col+1; j++) {

                if (row == i && col == j)
                {
                continue;
                }
                
                if (grid[(i + grid.length) % grid.length][(j + grid[0].length) % grid[0].length])
                {
                    countNeighbors++;
                }
            }
        }
        // WRITE YOUR CODE HERE
        return countNeighbors; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {
        //update each cell
        boolean[][] newgrid = new boolean[grid.length][grid[0].length]; 
        
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[0].length; j++)
            {
                int neighbors = numOfAliveNeighbors(i, j);
                
                if (grid[i][j]) {
                    if (neighbors == 2 || neighbors == 3)
                    {
                        newgrid[i][j] = true;
                    }
                    else
                        newgrid[i][j] = false;
                }
                else {
                    if (neighbors == 3)
                        newgrid[i][j] = true;
                    else
                        newgrid[i][j] = false;
                }
            }
        }
        // WRITE YOUR CODE HERE
        return newgrid;// update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {

        grid = computeNewGrid();
        // WRITE YOUR CODE HERE
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        for (int i = 0; i < n; i++)
        {
            computeNewGrid();
            nextGeneration();
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() 
    {
        WeightedQuickUnionUF QuickUnionFind = new WeightedQuickUnionUF(grid.length, grid[0].length);

        ArrayList<Integer> roots = new ArrayList<Integer>();

        for (int i = 0; i < grid.length; i++) 
        {
            for (int j = 0; j < grid[0].length; j++) 
            {

                for (int k = i-1; k <= i+1; k++) 
                {
            
                    for (int l = j-1; l <= j+1; l++) 
                    {
                        
                        if (i == k && j == l)
                        {
                            QuickUnionFind.union(i, j, k, l);
                        }
        
                        if (grid[i][j] && grid[(k + grid.length) % grid.length][(l + grid[0].length) % grid[0].length])
                        {
                            QuickUnionFind.union(i, j, (k + grid.length) % grid.length, (l + grid[0].length) % grid[0].length);
                        }

                    }
                }
            }
        }
            
        for (int i = 0; i < grid.length; i++) 
        {
            for (int j = 0; j < grid[0].length; j++) 
            {
                if (grid[i][j])
                roots.add(QuickUnionFind.find(i, j));
            }
        }

        ArrayList<Integer> newRoots = duplicateNuker(roots);

        // WRITE YOUR CODE HERE
        return newRoots.size(); // update this line, provided so that code compiles
    }
    

    private static <Integer> ArrayList<Integer> duplicateNuker(ArrayList<Integer> input)
    {
        ArrayList<Integer> noDupes = new ArrayList<Integer>();
  
        for (Integer uniquetree : input) 
        {
            if (!noDupes.contains(uniquetree)) 
            {
                noDupes.add(uniquetree);
            }
        }

        return noDupes;
    }
}
