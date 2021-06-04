package myIDA_STAR;

import java.util.Comparator;

//class that sorts nodes in ascending order FOR MANHATTAN DISTANCE HEURISTIC

public class myComparator implements Comparator<Node> {
    //swaps if a positive number is returned, doesnt if a negative number is returned. USES DATA MEMBER f (manhattan distance h value)
    public int compare(Node n1, Node n2) {

        return n1.f - n2.f;
    }

}