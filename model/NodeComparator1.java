package model;

import java.util.Comparator;

public class NodeComparator1 implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        if (o1.heuristicFunction1()> o2.heuristicFunction1()) {
            return 1;
        } else if (o1.equals(o2)) {
            return 0;
        } else {
            return -1;
        }
    }

}
