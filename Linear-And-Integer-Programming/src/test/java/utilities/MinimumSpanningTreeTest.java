package utilities;

import org.junit.Test;
import utilities.spanningtree.Edge;
import utilities.spanningtree.MinimumSpanningTree;
import utilities.spanningtree.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MinimumSpanningTreeTest {

    MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree("{(1,2)=2,(1,3)=3,(1,4)=4,(2,3)=4,(2,5)=3,(3,4),(3,5)=5,(4,5)=6,(5,inf)=-inf}")
            .setBalance("1=12,2=7,3=3,4=-8,5=-14");
    @Test public void testParsing(){

        assertEquals(new ArrayList<>(minimumSpanningTree.getNodes().keySet()),new ArrayList<>(Arrays.asList("inf",1+"",2+"",
                3+"",4+"",5+"")));
        List<Integer> a = Arrays.asList(12, 7, 3, -8, -14);
        List<Node> nodes= new ArrayList<>();
        nodes.add(new Node("inf"));
        for (int i = 0; i < a.size(); i++)
            nodes.add(new Node((i+1)+"",a.get(i)));
        assertEquals(new ArrayList<>(minimumSpanningTree.getNodes().values()),nodes);
        ArrayList<Edge> edges = new ArrayList<Edge>(Arrays.asList(new Edge(nodes.get(1), nodes.get(2), 2),
                new Edge(nodes.get(1), nodes.get(3),3),new Edge(nodes.get(1), nodes.get(4),4),
                new Edge(nodes.get(2), nodes.get(3),4), new Edge(nodes.get(2), nodes.get(5),3),
                new Edge(nodes.get(3), nodes.get(4),1), new Edge(nodes.get(3), nodes.get(5),5),
                new Edge(nodes.get(4), nodes.get(5),6),new Edge(nodes.get(5), nodes.get(0),1)));
        assertEquals(minimumSpanningTree.getEdgeList(), edges);
    }

    /**
     * Kruskal algorithm
     * start pick smallest edge weight
     * find next smallest edge that doesn't form a cycle
     * keep going until all nodes are in the spanning tree.
     */
//    @Test public void testKruskalStepOne(){
////        minimumSpanningTree.sortEdgesByWeight();
//        assertEquals(minimumSpanningTree.getEdgeList().get(0),new Edge(new Node(3+"",3),new Node(4+"",-8),1));
//    }
}
