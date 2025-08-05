/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author alysamiller
 */


import java.util.*;

public class Dijkstra {
    static final int INF = Integer.MAX_VALUE; // Represents 'infinity' (no direct connection)
    static final int N = 6; // Number of nodes in the graph
    static String[] nodes = {"u", "v", "w", "x", "y", "z"}; // Node labels

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] cost = new int[N][N]; // Adjacency matrix to store edge costs

        // Initialize the cost matrix with INF (no direct connection)
        for (int i = 0; i < N; i++)
            Arrays.fill(cost[i], INF);

        // Prompt user to input edge costs between nodes
        System.out.println("Enter the cost for direct edges (enter -1 if no direct connection):");

        // Collect cost inputs for each defined edge
        inputCost(sc, cost, "u", "v");
        inputCost(sc, cost, "u", "w");
        inputCost(sc, cost, "u", "x");
        inputCost(sc, cost, "v", "x");
        inputCost(sc, cost, "v", "w");
        inputCost(sc, cost, "w", "y");
        inputCost(sc, cost, "w", "z");
        inputCost(sc, cost, "x", "w");
        inputCost(sc, cost, "x", "y");
        inputCost(sc, cost, "y", "z");

        // Run Dijkstra’s algorithm starting from node 'u' (index 0)
        runDijkstra(cost, 0);
    }

    /**
     * Prompts user for the cost of the edge between two nodes.
     * Updates the cost matrix for an undirected graph.
     */
    private static void inputCost(Scanner sc, int[][] cost, String from, String to) {
        System.out.print("Cost from " + from + " to " + to + ": ");
        int c = sc.nextInt();
        if (c >= 0) {
            int i = Arrays.asList(nodes).indexOf(from);
            int j = Arrays.asList(nodes).indexOf(to);
            cost[i][j] = c;
            cost[j][i] = c; // Since the graph is undirected
        }
    }

    /**
     * Implements Dijkstra's algorithm and displays the steps and current distances.
     */
    private static void runDijkstra(int[][] cost, int start) {
        int[] dist = new int[N];         // Shortest known distances from start
        int[] prev = new int[N];         // Predecessor array to store the path
        boolean[] visited = new boolean[N]; // Tracks visited nodes
        Arrays.fill(dist, INF);          // Start with all distances as infinite
        Arrays.fill(prev, -1);           // No predecessors yet
        dist[start] = 0;                 // Distance to starting node is 0

        List<Integer> Nset = new ArrayList<>(); // Nodes added to the final shortest path set

        // Print table header
        System.out.printf("%-6s %-6s", "Step", "N'");
        for (String n : nodes)
            System.out.printf(" D(%s) p(%s)", n, n);
        System.out.println();

        // Loop for all nodes
        for (int step = 0; step < N; step++) {
            int u = -1, minDist = INF;

            // Find unvisited node with smallest known distance
            for (int i = 0; i < N; i++) {
                if (!visited[i] && dist[i] < minDist) {
                    u = i;
                    minDist = dist[i];
                }
            }
            if (u == -1) break; // All reachable nodes have been visited

            visited[u] = true; // Mark this node as visited
            Nset.add(u);       // Add it to the shortest path set

            // Update distance values for neighbors of u
            for (int v = 0; v < N; v++) {
                if (!visited[v] && cost[u][v] != INF && dist[u] + cost[u][v] < dist[v]) {
                    dist[v] = dist[u] + cost[u][v]; // Update distance if shorter path found
                    prev[v] = u;                    // Set predecessor to current node
                }
            }

            // Print current state of distances and previous nodes
            System.out.printf("%-6d ", step);
            System.out.print("{");
            for (int i = 0; i < Nset.size(); i++) {
                System.out.print(nodes[Nset.get(i)]);
                if (i < Nset.size() - 1) System.out.print(",");
            }
            System.out.print("}   ");

            for (int i = 0; i < N; i++) {
                String d = (dist[i] == INF) ? "∞" : String.valueOf(dist[i]);
                String p = (prev[i] == -1) ? "-" : nodes[prev[i]];
                System.out.printf(" %-5s %-3s", d, p);
            }
            System.out.println();
        }
    }
}

