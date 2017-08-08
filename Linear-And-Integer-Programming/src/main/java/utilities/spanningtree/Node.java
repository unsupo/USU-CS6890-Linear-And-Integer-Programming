package utilities.spanningtree;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private int balance;
    private String name;
    private List<Edge> toEdges = new ArrayList<>(),
                        fromEdges = new ArrayList<>();

    public Node(String name) {
        this.name = name;
    }
    public Node(String name, int balance){
        this.name = name;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Edge> getToEdges() {
        return toEdges;
    }

    public void setToEdges(List<Edge> toEdges) {
        this.toEdges = toEdges;
    }

    public void addToEdge(Edge n){
        if(toEdges.contains(n))
            return;
        this.toEdges.add(n);
    }

    public List<Edge> getFromEdges() {
        return fromEdges;
    }

    public void setFromEdges(List<Edge> fromEdges) {
        this.fromEdges = fromEdges;
    }

    public void addFromEdge(Edge n){
        if(fromEdges.contains(n))
            return;
        this.fromEdges.add(n);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (balance != node.balance) return false;
        return name != null ? name.equals(node.name) : node.name == null;
    }

    @Override
    public int hashCode() {
        int result = balance;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
                "balance=" + balance +
                ", name='" + name + '\'' +
                '}';
    }
}
