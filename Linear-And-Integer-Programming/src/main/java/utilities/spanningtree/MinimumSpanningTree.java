package utilities.spanningtree;

import utilities.Ratio;

import java.util.*;

public class MinimumSpanningTree {
    private HashMap<String,Node> nodes = new HashMap<>();
    private List<Edge> edgeList = new ArrayList<>(),
            spanningTreeEdges = new ArrayList<>();
    private Node rootNode;
    private Node endNode;


    public MinimumSpanningTree(String flowNetwork) {
        parse(flowNetwork);
    }

    public MinimumSpanningTree setBalance(String balance) {
        for(String s : balance.split(",")){
            String[] v = s.split("=");
            nodes.get(v[0]).setBalance(Integer.parseInt(v[1]));
        }

        return this;
    }

    private void parse(String flowNetwork) {
        String[] edges = flowNetwork.replaceAll("[{|} ]", "").split(",\\(");
        for(String ss : edges) {
            String[] v = ss.replaceAll("[\\(|\\)]", "").split("=");
            double weight = 1;
            if(v.length == 2) {
                v[1] = v[1].replace("inf",Integer.MAX_VALUE+"").replace(",","");
                weight = Double.parseDouble(v[1]);
            }
            Edge e = new Edge(v[0]);
            e.setWeight(weight);
            Node start = e.getStart(), end = e.getEnd();
            if(nodes.containsKey(e.getStart().getName()))
                start = nodes.get(e.getStart().getName());
            else
                nodes.put(start.getName(),start);
            if(nodes.containsKey(e.getEnd().getName()))
                end = nodes.get(e.getEnd().getName());
            else
                nodes.put(end.getName(),end);
            e.setStart(start);
            e.setEnd(end);
            start.addToEdge(e);
            end.addFromEdge(e);
            edgeList.add(e);
        }
    }

    public HashMap<String, Node> getNodes() {
        return nodes;
    }

    public void setNodes(HashMap<String, Node> nodes) {
        this.nodes = nodes;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    /**
     * Implementation using Prim's Algorithm to get the Minimum Spanning Tree
     * Select Random Arbitrary node  A add A to visited list
     * Select smallest node from A->B  add B to visited list
     * Continue this process
     */
    public List<Edge> getMinimumSpanningTree(){
        Node a = getNodes().get("1");//getRandomNode();
        visited.add(a);
        while (visited.size() < nodes.size()) {
            List<Edge> edges = new ArrayList<>();
            visited.forEach(aa->edges.addAll(aa.getToEdges()));
            Collections.sort(edges);
            if(edges.size() == 0)
                break;
            for(Edge e : edges)
                if(!visited.contains(e.getEnd())){
                    visited.add(e.getEnd());
                    spanningTreeEdges.add(e);
                    break;
                }
        }
        return spanningTreeEdges;
    }
    private Set<Node> visited = new HashSet<>();
    private Random r = new Random();
    public Node getRandomNode() {
        return new ArrayList<>(nodes.values()).get(r.nextInt(nodes.size()));
    }

    /**
     * The root node has no ins only outs
     * @return
     */
    public Node getRootNode() {
        for(Node n : nodes.values())
            if(n.getFromEdges().isEmpty())
                rootNode = n;
        return rootNode;
    }

    public Node getEndNode() {
        for(Node n : nodes.values())
            if(n.getToEdges().isEmpty())
                endNode = n;
        return endNode;
    }
}
