package me;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SkyscannerEmployees {

    //private static BinaryTree<String> bt = new Solution.BinaryTree<>();
    private static List<String[]> relations = new ArrayList<String[]>();

    private static TreeMap<String, Integer> tree = new TreeMap<String, Integer>();
    private static Integer level = 1;

    public static void main(String[] args) {
        readInput();

        String boss = relations.get(0)[0];
        tree.put(boss, 1);
        relations.forEach(rel -> insert(rel[0], rel[1]));

        Integer height = Collections.max(tree.entrySet(), Map.Entry.comparingByValue()).getValue();
        for(int i=1; i<=height; i++) {
            getEmployeesFromLevel(i).forEach(emp -> System.out.print(emp+" ("+getManagerOf(emp)+") "));
            System.out.println();
        }

    }

    private static void insert(String manager, String employee) {
        tree.putIfAbsent(employee, tree.getOrDefault(manager,1)+1);
    }

    private static Set<String> getEmployeesFromLevel(Integer level) {
        return tree.entrySet().stream()
                .filter(entry -> entry.getValue() == level)
                .map(entry -> entry.getKey())
                .collect(Collectors.toSet());
    }

    private static String getManagerOf(String emp) {
        return relations.stream()
                .filter(rel -> rel[1].equalsIgnoreCase(emp))
                .map(rel -> rel[0])
                .findFirst().orElse("");
    }

    private static void readInput() {
        Scanner in = new Scanner(System.in);

        System.out.print("Number of relations to read: ");
        int nLines = Integer.parseInt(in.nextLine());

        for(int i=0; i<nLines; i++) {
            System.out.println("Enter relation #" + i + ": ");
            String input = in.nextLine();
            String[] tuple = input.split("\\s");
            relations.add(tuple);
        }
    }

    /*
    public static class BinaryTree<T extends Comparable<?>> {

        public static class Node<T extends Comparable<?>> {
            protected Node<T> left;
            protected Node<T> right;
            protected T data;

            public Node(T data, Node<T> left, Node<T> right) {
                this.data = data;
                this.left = left;
                this.right = right;
            }

            public List<Node<T>> getChildren() {
                return Stream.of(left, right)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
           }
        }

        Node<T> root;

        public BinaryTree(){
            root = null;
        }

        public void insert(T data){
            root = insert(root, data);
        }

        public Node<T> insert(Node<T> node, T data ) {
            Node<T> newNode = new Node<T>(data, null, null);
            if(node == null) {
                node = newNode;
                return node;
            }
            Queue<Node<T>> queue = new LinkedBlockingQueue<Node<T>>();
            queue.add(node);
            while(!queue.isEmpty()) {
                Node<T> temp = queue.poll();
                if(temp.left != null) {
                    queue.add(temp.left);
                } else {
                    temp.left = newNode;
                    return node;
                }
                if(temp.right != null) {
                    queue.add(temp.right);
                } else {
                    temp.right = newNode;
                    return node;
                }
            }
            return node; 
        }

        BinaryTreePrinter<T> btp = new BinaryTreePrinter<T>();
    }
    */

}