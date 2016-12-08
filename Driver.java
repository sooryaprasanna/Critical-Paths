/**
 * Driver Program
 * @author Soorya Prasanna Ravichandran
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class Driver 
{
	public static void main(String[] args) throws FileNotFoundException 
	{
		Scanner in;
		if (args.length > 0) 
		{
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} 
		else 
		{
			in = new Scanner(System.in);
		}
		Graph g = Graph.readDirectedGraph(in);
		for(Vertex u: g)
		{
			u.d = in.nextInt();
		}

		// Get the size of the vertex in graph g
		int size = g.v.size();

		// Connecting start node s to other nodes	
		Vertex s = g.v.get(size - 2); // Since start node s is n-1
		Vertex t = g.v.get(size - 1); // Since end node t is n
		
		for(Vertex v : g.v)
		{
			if(v != null && v.revAdj.isEmpty() && v != s && v != t)
			{
				// s will be the node with no incoming edges
				g.addEdge(s, v, 1);
			}
		}

		// Connecting end node t to other nodes
		for(Vertex v : g.v)
		{
			if(v != null && v.adj.isEmpty() && v != s && v != t)
			{
				// t will be the node with no outgoing edges
				g.addEdge(v, t, 1);
			}
		}

		Timer timer = new Timer();
		CriticalPaths cp = new CriticalPaths(g);
		cp.findCriticalPaths();
		System.out.println(timer.end());
	}
}

