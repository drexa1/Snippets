package me;

import java.util.*;

public class Dijkstra {

    public static void main(String[] args) {

        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        Vertex D = new Vertex("D");
        Vertex F = new Vertex("F");
        Vertex K = new Vertex("K");
        Vertex J = new Vertex("J");
        Vertex M = new Vertex("M");
        Vertex O = new Vertex("O");
        Vertex P = new Vertex("P");
        Vertex R = new Vertex("R");
        Vertex Z = new Vertex("Z");

        // set edges and weights
        A.adjacencies = new Edge[]{ new Edge(M, 8) };
        B.adjacencies = new Edge[]{ new Edge(D, 11) };
        D.adjacencies = new Edge[]{ new Edge(B, 11) };
        F.adjacencies = new Edge[]{ new Edge(K, 23) };
        K.adjacencies = new Edge[]{ new Edge(O, 40) };
        J.adjacencies = new Edge[]{ new Edge(K, 25) };
        M.adjacencies = new Edge[]{ new Edge(R, 8) };
        O.adjacencies = new Edge[]{ new Edge(K, 40) };
        P.adjacencies = new Edge[]{ new Edge(Z, 18) };
        R.adjacencies = new Edge[]{ new Edge(P, 15) };
        Z.adjacencies = new Edge[]{ new Edge(P, 18) };

        long startTime = System.nanoTime();
        compute(A);
        long stopTime = System.nanoTime();

        System.out.println("Distance to " + Z + ": " + Z.minDistance);
        List<Vertex> path = getShortestPathTo(Z);
        System.out.println("Path: " + path);
        System.out.println("Execution time: " + (stopTime - startTime));
    }

    /**
     * Generally speaking, it is less work to track only the minimum element, using a heap.
     * A tree is more organized, and it requires more computation to maintain that organization.
     * But if you need to access any key, and not just the minimum, a heap will not suffice, and the extra overhead of the tree is justified.
     * If you have a large number of elements and a lot of remove/contains, then TreeSet/Map may be faster.
     */
    public static void compute(Vertex source) {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex head = vertexQueue.poll();
            // Visit each edge exiting head
            for (Edge adj : head.adjacencies) {
                Vertex v = adj.target;
                double distanceThrough = head.minDistance + adj.weight;
                if (distanceThrough < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThrough ;
                    v.previous = head;
                    vertexQueue.add(v);
                }
            }
        }
    }

    public static void compute2(Vertex source) {
        source.minDistance = 0.;
        TreeSet<Vertex> tree = new TreeSet<>();
        tree.add(source);

        while(!tree.isEmpty()) {
            Vertex lowest = tree.pollFirst();
            for (Edge adj : lowest.adjacencies) {
                Vertex v = adj.target;
                double distanceThrough = lowest.minDistance + adj.weight;
                if (distanceThrough < v.minDistance) {
                    tree.remove(v);
                    v.minDistance = distanceThrough ;
                    v.previous = lowest;
                    tree.add(v);
                }
            }
        }
    }


    public static List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
        }
        Collections.reverse(path);
        return path;
    }
}

class Vertex implements Comparable<Vertex> {
    final String key;
    Edge[] adjacencies;
    double minDistance = Double.POSITIVE_INFINITY;
    Vertex previous;
    public Vertex(String key) {
        this.key = key;
    }
    public String toString() {
        return this.key;
    }
    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }
}

class Edge {
    final Vertex target;
    final double weight;
    public Edge(Vertex target, double weight) {
        this.target = target;
        this.weight = weight;
    }
}