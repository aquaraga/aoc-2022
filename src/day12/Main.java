package day12;

import util.FileUtil;

import java.util.*;
import java.util.stream.Collectors;

//class Graph {
//
//    public void add(Node node) {
//
//    }
//}

//class Node {
//
//    private final Node pred;
//    private final int distance;
//    private char val;
//
//    private List<Node> outNodes;
//
//    public Node(char val) {
//
//        this.val = val;
//        this.pred = null;
//        this.distance = -1;
//        outNodes = new ArrayList<>();
//    }
//}
class Pair {
    int row;
    int col;

    public Pair(int row, int col) {

        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return row == pair.row && col == pair.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        List<String> inputs = FileUtil.readStrings("src/day12/test.txt");
        Map<Pair, Node> nodeMap = new HashMap<>();
        Graph graph = new Graph();
        Node start = null, end = null;
        for (int i = 0; i < inputs.size(); i++) {
            String s = inputs.get(i);
            for (int j = 0; j < s.length(); j++) {
                char val = s.charAt(j);
                Node node = new Node(val == 'S' ? 'a': val == 'E' ? 'z': val);
                if (val == 'S') {
                    start = node;
                }
                if (val == 'E') {
                    end = node;
                }
                nodeMap.put(new Pair(i, j), node);
                graph.addNode(node);
            }
        }
        for (int i = 0; i < inputs.size(); i++) {
            String s = inputs.get(i);
            for (int j = 0; j < s.length(); j++) {
                addAdjacent(nodeMap, i, j);
            }
        }


        List<Node> aNodes = graph.getNodes().stream().filter(x -> x.getName() == 'a').toList();
        int smallestDistance = Integer.MAX_VALUE;
        for (Node aNode :
                aNodes) {
            graph.reset();
            graph = Djikstra.calculateShortestPathFromSource(graph, aNode);
            int possibleShortestPath = end.getShortestPath().size();
//            System.out.println(possibleShortestPath);
            if (possibleShortestPath != 0 && possibleShortestPath < smallestDistance) {
                smallestDistance = possibleShortestPath;
            }

        }
        System.out.println(smallestDistance);


//        graph = Djikstra.calculateShortestPathFromSource(graph, start);
//        System.out.println(graph);
//        System.out.println(end.getShortestPath().size());




    }

    private static void addAdjacent(Map<Pair, Node> nodeMap, int i, int j) {
        Node node = nodeMap.get(new Pair(i, j));
        List<Pair> adjacentLocations = Arrays.asList(new Pair(i + 1, j), new Pair(i - 1, j), new Pair(i, j + 1), new Pair(i, j - 1));
        for (Pair adjacentLocation :
                adjacentLocations) {
            Node adjacentNode = nodeMap.get(adjacentLocation);
            if (adjacentNode == null) {
                continue;
            }
            //if node is c
            //adjacent can be d, c, b, a

            int diff = adjacentNode.getName() - node.getName();
            if (diff <= 1) {
                node.addDestination(adjacentNode, 1);
            }

        }
    }
}
