import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private Hashtable<String, Graph.Node> nodeTable;

    public Graph() {
        nodeTable = new Hashtable<>();
    }

    public synchronized Node getNodeAddIfAbsent(String word) {
        if (!nodeTable.containsKey(word)) {
            nodeTable.put(word, new Node(word));
        }
        return nodeTable.get(word);
    }

    public synchronized void addEdge(String word1, String word2) {
        if (word1.equals(word2)) return;
        Node word1Node = getNodeAddIfAbsent(word1);
        Node word2Node = getNodeAddIfAbsent(word2);
        word1Node.add_neighbour(word2Node);
        word2Node.add_neighbour(word1Node);
    }

    public synchronized void removeEdge(String word1, String word2) {
        if (word1.equals(word2)) return;
        Node word1Node = getNodeAddIfAbsent(word1);
        Node word2Node = getNodeAddIfAbsent(word2);
        word1Node.remove_neighbour(word2Node);
        word2Node.remove_neighbour(word1Node);
    }

    public List<String> getAllReachableNodes(String word) {
        Node node = getNodeAddIfAbsent(word);
        Set<Node> visited = new HashSet<>();
        Queue<Node> bfs = new LinkedList<>();
        bfs.add(node);
        while (!bfs.isEmpty()) {
            Node polled = bfs.poll();
            visited.add(polled);
            for (Node neighbour : polled.neighbours) {
                if (!visited.contains(neighbour)) {
                    bfs.add(neighbour);
                }
            }
        }
        return visited.stream().map(Node::getWord).collect(Collectors.toList());
    }

    static class Node implements Comparable<Node> {
        private String word;

        private Queue<Node> neighbours;

        public Node(String word) {
            this.word = word;
            this.neighbours = new LinkedList<>();
        }

        public synchronized void add_neighbour(Node node) {
            this.neighbours.add(node);
        }

        public synchronized void remove_neighbour(Node node) {
            this.neighbours.remove(node);
        }

        public String getWord() {
            return this.word;
        }

        @Override
        public int compareTo(Node other) {
            return this.word.compareTo(other.word);
        }
    }
}
