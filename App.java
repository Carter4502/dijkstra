import java.util.*;


public class App {

    static class Node {
        String name;
        Node prevNode;
        int lowestWeight;

        public Node(String n, Node p, int w) {
            this.name = n;
            this.prevNode = p;
            this.lowestWeight = w;
        }
    }


    static class NodeEdge {
        int weight;
        Node dest;

        public NodeEdge(int weight, Node d) {
            this.weight = weight;
            this.dest = d;
        }

    }


    public static void main(String[] args) throws Exception {
        HashMap<Node, List<NodeEdge>> adj = new HashMap<>();
        Node sf = new Node("San Francisco", null, Integer.MAX_VALUE);
        Node se = new Node("Seattle", null, Integer.MAX_VALUE);
        Node id = new Node("Idaho", null, Integer.MAX_VALUE);
        Node ch = new Node("Chicago", null, Integer.MAX_VALUE);
        Node ny = new Node("New York City", null, Integer.MAX_VALUE);
        List<NodeEdge> sfList = new ArrayList<>(Arrays.asList(new NodeEdge(3, se), new NodeEdge(5, id)));
        adj.put(sf, sfList);
        List<NodeEdge> seList = new ArrayList<>(Arrays.asList(new NodeEdge(3, sf),new NodeEdge(2, ch),new NodeEdge(1, id)));
        adj.put(se, seList);
        List<NodeEdge> idList = new ArrayList<>(Arrays.asList(new NodeEdge(5, sf), new NodeEdge(1, se), new NodeEdge(3, ch), new NodeEdge(6, ny)));
        adj.put(id, idList);
        List<NodeEdge> chList = new ArrayList<>(Arrays.asList(new NodeEdge(2, se), new NodeEdge(3, id), new NodeEdge(4, ny)));
        adj.put(ch, chList);
        List<NodeEdge> nyList = new ArrayList<>(Arrays.asList(new NodeEdge(6, id), new NodeEdge(4, ch)));
        adj.put(ny, nyList);
        List<Node> nodeList = new ArrayList<>(Arrays.asList(sf, se, ny, id, ch));
        dijkstra(sf, adj, nodeList); // Find smallest path from San Francisco to each other city

        
        for (Node City : nodeList) {
            System.out.println("[City: " + City.name + ", Shortest Path: "  + City.lowestWeight + "]");
        }

    }



    public static void dijkstra(Node source, HashMap<Node, List<NodeEdge>> adj, List<Node> nodeList) {
        source.prevNode = source;
        source.lowestWeight = 0;
        PriorityQueue<Node> pQ = new PriorityQueue<>((a, b) -> (a.lowestWeight - b.lowestWeight));
        Set<Node> seen = new HashSet<>();
        for (Node n : nodeList) {
            pQ.offer(n);
        }
        while (!pQ.isEmpty()) {
            Node curNode = pQ.poll();
            seen.add(curNode);
            for (NodeEdge child : adj.get(curNode)) {
                if (!seen.contains(child.dest)) {
                    Integer distance = curNode.lowestWeight + child.weight;
                    if (child.dest.lowestWeight > distance || child.dest.prevNode == null) {
                        child.dest.lowestWeight = distance;
                        child.dest.prevNode = curNode;
                        pQ.remove(child.dest);
                        pQ.offer(child.dest);
                    }
                }
            }
        }

    }



}

