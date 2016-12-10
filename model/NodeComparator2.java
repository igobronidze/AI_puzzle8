
package model;

import java.util.Comparator;

public class NodeComparator2 implements Comparator<Node>{
    
    @Override
    public int compare(Node o1, Node o2) {
        if (o1.heuristicFunction2()> o2.heuristicFunction2()) {
            return 1;
        } else if (o1.equals(o2)) {
            return 0;
        } else {
            return -1;
        }
    }
    
}
