// Ryan Sheffield, ry257606
// COP 3503, fall 2019

import java.util.*;
import java.io.*;

public class TopoPaths
{
    // This function will take advantage of the fact that there can only be at most
    // one path that is also a valid topological sort, and will check if such a
    // path exists
    public static int countTopoPaths(String filename) throws IOException
    {
        // Scanner for reading file
        Scanner scan = new Scanner(new File(filename));
        // The order of numbers is such that the first number in the file will be
        // the number of vertices in the graph, take that number in from the start
        // and use adjacency List
        int n = scan.nextInt();
        // Adjacency list using an arrayList of linked lists
        ArrayList<LinkedList<Integer>> adjList = new ArrayList<LinkedList<Integer>>();
        // Variable for keeping track of vertex number in while loop
        int v = 0;
        // Loop through the rest of the file until there is nothing left
        while (scan.hasNextInt())
        {
            // The first number read each time should be the number of outgoing
            // edges to this vertex, loop through that number of times to store all edges
            int num = scan.nextInt();
            adjList.add(new LinkedList<Integer>());
            for (int i = 0; i < num; i++)
                adjList.get(v).add(scan.nextInt() - 1);
            // The next number should be the number of outgoing edges of the next
            // vertex, so increment the vertice number
            v++;
        }
        // Creates the topological sort, and then checks if it has a path
        ArrayList<Integer> topoSort = createTopoSort(adjList);
        if (hasPath(adjList, topoSort))
            return 1;

        return 0;
    }

    // Credit to Dr. Szumlanski. I heavily referenced his TopoSort program from
    // his lecture on Traversal Applications and Topological Sort after struggling
    // to come up with my own

    // This function also takes advantage of the fact that for a graph to have a
    // valid topopath, it will only have one possible topological sort, so it is
    // only good for finding one of the possible topological sorts in a given graph

    // Function for checking if an adjacency List has a valid topological sort
    // and returning it in the form of an ArrayList
    public static ArrayList<Integer> createTopoSort(ArrayList<LinkedList<Integer>> adjList)
    {
        // Array containing the number of incoming edges to each vertex
        int [] incoming = new int[adjList.size()];
        // Sorted ArrayList that we'll be returning
        ArrayList<Integer> topoSorted = new ArrayList<>();
        // Null arrayList, returned if no valid TopoSort exists
        ArrayList<Integer> failure = new ArrayList<>();
        failure = null;
        // Runs through the adjacency List and checks how many edges point to
        // each vertex
        for (int i = 0; i < adjList.size(); i++)
            for (int j = 0; j < adjList.size(); j++)
                if (adjList.get(i).contains(j))
                    incoming[j] += 1;
        // Uses an ArrayDeque to act as the queue for vertices to be visited in the
        // valid TopoSort
        Queue<Integer> queue = new ArrayDeque<Integer>();

        // Looks for vertices with no incoming edges, and add them to the front
        // of the queue, since they're the only vertices that can be visited at this point
        for (int i = 0; i < adjList.size(); i++)
            if (incoming[i] == 0)
                queue.add(i);
        // Takes the first vertices placed in the queue, adds them to the topoSorted
        // arraylist, and finds the next valid vertex to visit in the adjacency List
        while (!queue.isEmpty())
        {
            // Stores the vertex to be looked at
            int node = queue.remove();
            // Places the vertex in the TopoSort
            topoSorted.add(node);
            //System.out.println(adjList.get(node));
            // Loops through the adjacency List, and if one of the vertices that
            // had it's edges decremented is zero, and the current vertex points
            // to it, then add it to the queue
            for (int i = 0; i < adjList.size(); i++)
                if ((adjList.get(node).contains(i)) && (--incoming[i] == 0))
                    queue.add(i);
        }
        // If the TopoSort is not the same size as the adjacency List, then
        // there was a cycle in the graph, preventing a valid TopoSort
        if (topoSorted.size() != adjList.size())
            return failure;
        // Returns the valid TopoSort
        return topoSorted;
    }

    // Function for checking if the topological sort of a graph has a valid path
    public static boolean hasPath(ArrayList<LinkedList<Integer>> adjList,
            ArrayList<Integer> topoSorted)
    {
        // If topoSorted is null, then there wasn't a valid topological sort
        if (topoSorted == null)
            return false;
        // Run through the TopoSort, and see if the order given matches the edges
        // presented in the adjacency list, if not then the TopoSort wasn't a path,
        // so there was never a possible toposort
        for (int i = 0; i < topoSorted.size(); i++)
        {
            if ((i + 1) < topoSorted.size())
                if (!adjList.get(topoSorted.get(i)).contains(topoSorted.get(i + 1)))
                    return false;
        }
        return true;
    }

    public static double difficultyRating()
    {
        return 3;
    }

    public static double hoursSpent()
    {
        return 7;
    }
}
