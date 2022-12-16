package avengers;

import javax.print.attribute.Size2DSyntax;

/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) 
    {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

        // read file names from command line
        String LocateTitanInputFile = args[0];
        String LocateTitanOutputFile = args[1];

        // Set the input file.
        StdIn.setFile(LocateTitanInputFile);

        int vertices = StdIn.readInt();

        double[] functionality = new double [vertices];

        for(int i = 0; i < vertices; i++)
        {
            functionality[StdIn.readInt()] = StdIn.readDouble();
        }

        int[][] adjency = new int[vertices][vertices];

        for(int i = 0; i < vertices; i++)
        {
            for(int j = 0; j < vertices; j++)
            {
                adjency[i][j] = StdIn.readInt();

                double calc = adjency[i][j] / (functionality[i] * functionality[j]);

                adjency[i][j] = (int) calc;

                adjency[j][i] = adjency[i][j];

            }
        }

        //Dijkstra's Algorithm

        int minCost[] = new int [vertices];
        boolean dij[] = new boolean [vertices];

        for (int i = 0; i < vertices; i++)
        {
            if (i == 0)
            {
                minCost[i] = 0;
            } else
            {
                minCost[i] = Integer.MAX_VALUE;
                dij[i] = false;
            }
        }

        for (int i = 0; i < vertices - 1; i++)
        {
            // int currentSource = minDistance(minCost, dij, vertices);
            int currentSource = vertices - 1;

            for (int j = 0; j < vertices; j++)
            {
                if (minCost[j] < minCost[currentSource] && dij[j] == false)
                currentSource = j;
            }

            dij[currentSource] = true;

            for (int k = 0; k < vertices; k++)
            {
                if (dij[k] == false && minCost[currentSource] != Integer.MAX_VALUE && adjency[currentSource][k] > 0 && minCost[currentSource] + adjency[currentSource][k] < minCost[k])
                {
                    minCost[k] = minCost[currentSource] + adjency[currentSource][k];
                }
            }
        }

        // Set the output file
        
        StdOut.setFile(LocateTitanOutputFile);

        StdOut.print(minCost[vertices - 1]);
    	// WRITE YOUR CODE HERE
    }
}
