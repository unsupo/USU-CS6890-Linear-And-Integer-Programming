package utilities.spanningtree;

public class Edge implements Comparable<Edge>{
    private Node start, end;
    private double weight = 1;

    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
    }
    public Edge(String edge){
        String[] v = edge.replaceAll("[\\(|\\)]", "").split(",");
        this.start = new Node(v[0]);
        this.end = new Node(v[1]);
        init();
    }

    private void init() {
        start.addToEdge(this);
        end.addFromEdge(this);
    }

    public Edge(int nodeA, int nodeB) {
        this.start = new Node(nodeA+"");
        this.end = new Node(nodeB+"");
        init();
    }

    public Edge(String nodeA, String nodeB) {
        this.start = new Node(nodeA+"");
        this.end = new Node(nodeB+"");
        init();
    }

    public Edge(Node nodeA, Node nodeB, double weight) {
        this.start = nodeA;
        this.end = nodeB;
        this.weight = weight;
        init();
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (Double.compare(edge.weight, weight) != 0) return false;
        if (start != null ? !start.equals(edge.start) : edge.start != null) return false;
        return end != null ? end.equals(edge.end) : edge.end == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = start != null ? start.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Edge o) {
        return Double.valueOf(weight).compareTo(o.weight);
    }
}
