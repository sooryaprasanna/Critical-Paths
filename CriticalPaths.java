/**
 * Class to find the critical path
 * @author Soorya Prasanna Ravichandran
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class CriticalPaths 
{
	// List of topological order
	LinkedList<Vertex> topologicalOrder = new LinkedList<>();
	LinkedList<Vertex> criticalPath = new LinkedList<>();
	Graph g;

	HashMap<Integer, Integer> pathLength = new HashMap<>();
	ArrayList<Vertex[]> allPaths = new ArrayList<>(); // to print in order
	int numberOfCriticalNodes = 0;

	/*
	 * Run DFS from a given source node to find
	 * the topological order of the graph
	 * Initially all the nodes will be marked white
	 */
	private void DFS(Graph g)
	{
		for (Vertex u : g.v) 
		{
			if(u != null)
				u.color = 'W';		
		}
		for (Vertex u : g.v) 
		{
			if(u != null && u.color == 'W')
			{
				dfsVisit(g,u);
			}
		}
	}

	/*
	 * DFS Visit method to check whether the graph is visited or not
	 * Maintain color codes W - white, G - Gray, B - Black
	 * W - all nodes marked white initially
	 * G - Marked gray when we visit the node
	 * B - Marked black when the node is visited
	 */
	private void dfsVisit(Graph g,Vertex u)
	{
		u.color = 'G';
		for (Edge e : u.adj) 
		{
			Vertex v = e.otherEnd(u);
			if(v != null && v.color == 'W')
				dfsVisit(g, v);
		}
		u.color = 'B';
		topologicalOrder.addFirst(u); // addFirst to get the order
	}

	CriticalPaths(Graph g) 
	{
		this.g = g;
	}

	// Enumerate all paths in a DAG from s to t
	private void enumeratePaths(Vertex[] path, Vertex u, int index, Vertex t)
	{
		path[index] = u;

		if(u != null && u == t)
		{
			visit(path, index);
		}
		else
		{
			for(Edge e : u.adj)
			{
				Vertex v = e.otherEnd(u);
				if((u.lc == u.ec) && (v.ec == v.lc) && (u.lc + v.d == v.lc))
				{
					enumeratePaths(path, v, index + 1, t);
				}
			}
		}
	}
	private void visit(Vertex[] path, int index)
	{	
		Vertex[] tempPath = path.clone();
		pathLength.put(allPaths.size(), index);
		allPaths.add(tempPath);
	}

	// Method to find the critical path in a given graph g
	public void findCriticalPaths() 
	{
		DFS(g); // DFS call
		int size = g.v.size(); // Get the size of the vertex in graph g

		// EARLIEST COMPLETION TIME
		Vertex s = g.v.get(size - 2); // Since start node s is n-1
		for(Vertex u : g.v)
		{
			if(u != null)
			{
				u.ec = Integer.MIN_VALUE;
			}
		}
		s.ec = 0;
		/*
		 * EC is calculated by comparing all the incoming edges to the vertex v
		 * and take the maximum value of all incoming edges
		 */
		for(Vertex u : topologicalOrder)
		{
			for(Edge e : u.adj)
			{
				Vertex v = e.otherEnd(u);
				v.ec = Math.max(v.ec, u.ec + v.d);
			}
		}

		// LATEST COMPLETION TIME
		Vertex t = g.v.get(size - 1); // Since end node t is n
		t.lc = t.ec; // initial value of lc will be equal to ec
		for(Vertex u : g.v)
		{
			if(u != null)
				u.lc = t.lc;
		}
		/*
		 * LC is calculated by comparing all the incoming edges to the vertex v
		 * and take the minimum value of all incoming edges
		 * For this, we need to reverse the topological order to traverse back
		 */
		Iterator<Vertex> reverseIt = topologicalOrder.descendingIterator();
		while(reverseIt.hasNext())
		{
			Vertex u = reverseIt.next();

			for(Edge e : u.revAdj)
			{
				Vertex p = e.otherEnd(u);
				p.lc = Math.min(p.lc, u.lc - u.d);
			}
		}

		/*
		 * Slack is the difference between latest completion and earliest completion time
		 */
		for(Vertex v : g.v)
		{
			if(v != null)
			{
				v.slack = v.lc - v.ec;
				if(v.slack == 0 && v != s && v != t)
				{
					numberOfCriticalNodes++; // calculating number of critical nodes
				}
			}
		}
		// To store critical path
		Vertex[] path = new Vertex[g.v.size() - 1];
		enumeratePaths(path, s, 0, t);

		// Printing one critical path by iterating over the paths
		System.out.println("\n" + t.lc); // length of the critical path
		Iterator<Entry<Integer, Integer>> it1 = pathLength.entrySet().iterator();
		if(it1.hasNext())
		{
			Map.Entry<Integer, Integer> pair = (Entry<Integer, Integer>) it1.next();
			Integer key = pair.getKey();
			int value = pair.getValue();

			Vertex[] temp = allPaths.get(key);

			for(int i = 0; i <= value; i++)
			{
				if(temp[i] != null && temp[i] != s && temp[i] != t)
				{
					System.out.print(temp[i].name + " ");
				}
			}
			System.out.println("\n");
		}

		// Printing EC, LC and Slack
		System.out.println("Task" + "\t" + "EC" + "\t" + "LC" + "\t" + "Slack");
		int task = 0;
		for(Vertex u : g.v)
		{
			if(u != null && u != s && u != t)
			{
				System.out.println(++task + "\t" + u.ec + "\t" + u.lc + "\t" + u.slack);
			}
		}

		// Printing number of critical nodes and number of critical paths
		System.out.println("\n" + numberOfCriticalNodes);
		System.out.println(allPaths.size());

		// Printing all Critical Paths by iterating over the paths
		Iterator<Entry<Integer, Integer>> it = pathLength.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<Integer, Integer> pair = (Entry<Integer, Integer>) it.next();
			Integer key = pair.getKey();
			int value = pair.getValue();

			Vertex[] temp = allPaths.get(key);

			for(int i = 0; i <= value; i++)
			{
				if(temp[i] != null && temp[i] != s && temp[i] != t)
				{
					System.out.print(temp[i].name + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}