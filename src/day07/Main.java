package day07;

import util.FileUtil;

import java.util.*;

class Node {

    private Map<String, Node> childDirectories = new HashMap<>();
    private Map<String, Integer> files = new HashMap<>();
    String name;
    private Node parent;

    public Node(String name, Node parent) {
        this.name = name;
        this.parent = parent;
    }

    public Node getOrCreateDir(String directory) {
        if (!childDirectories.containsKey(directory)) {
            childDirectories.put(directory, new Node(directory, this));
        }
        return childDirectories.get(directory);
    }

    public Node parent() {
        return parent;
    }

    public void createFileIfNotPresent(String fileName, int fileSize) {
        if (!files.containsKey(fileName)) {
            files.put(fileName, fileSize);
        }
    }

    public int size() {
        return childDirectories.values().stream().map(Node::size).mapToInt(Integer::intValue).sum() +
                files.values().stream().mapToInt(Integer::intValue).sum();
    }
}

class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        return Integer.compare(o1.size(), o2.size());
    }
}



public class Main {
    public static void main(String[] args) throws Exception {
        List<String> entries = FileUtil.readStrings("src/day07/test-v2.txt");
        Node root = new Node("/", null);

        Node current = root;
        List<String> listingAcc = new ArrayList<>();
        Set<Node> allDirectoriesSeen = new HashSet<>();
        allDirectoriesSeen.add(root);
        for (String entry :
                entries) {
            if (entry.startsWith("$")) {
                //Reconcile any listing
                reconcileListing(current, listingAcc, allDirectoriesSeen);
                listingAcc.clear();
            }
            if (entry.equalsIgnoreCase("$ cd /")) {
                current = root;
                continue;
            }
            if (entry.equals("$ cd ..")) {
                current = current.parent();
                continue;
            }
            if (entry.startsWith("$ cd ")) {
                String directory = entry.substring("$ cd ".length());
                current = current.getOrCreateDir(directory);
                allDirectoriesSeen.add(current);
                continue;
            }

            if (!entry.startsWith("$ ls")) {
                //Must be a listing, add it to accumulator
                listingAcc.add(entry);
            }
        }
        reconcileListing(current, listingAcc, allDirectoriesSeen);
        int totalSize = 0;
        for (Node d : allDirectoriesSeen) {
            int size = d.size();
            if (size <= 100000) {
                totalSize += size;
            }
        }
        System.out.println(totalSize);

        int totalUsedSpace = root.size();
        int unusedSpace = 70000000 - totalUsedSpace;

        int freeSizeNeeded = 30000000;

        TreeSet<Node> nodes = new TreeSet<>(new NodeComparator());
        nodes.addAll(allDirectoriesSeen);
        for (Node d : nodes) {
            int size = d.size();
            if (unusedSpace + size >= freeSizeNeeded) {
                System.out.println("Size of directory that needs to be deleted " + size);
                break;
            }

        }

    }

    private static void reconcileListing(Node current, List<String> listingAcc, Set<Node> allDirectoriesSeen) {
        for (String listing :
                listingAcc) {
            if (listing.startsWith("dir ")) {
                String directory = listing.substring("dir ".length());
                allDirectoriesSeen.add(current.getOrCreateDir(directory));
            } else {
                String[] s = listing.split(" ");
                current.createFileIfNotPresent(s[1], Integer.parseInt(s[0]));
            }
        }
    }
}