package day12;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

    private char name;

    private LinkedList<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    private Map<Node, Integer> adjacentNodes = new HashMap<>();

    public Node(char name) {
        this.name = name;
    }

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(LinkedList<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public void reset() {
        distance = Integer.MAX_VALUE;
        shortestPath.clear();

    }
}
