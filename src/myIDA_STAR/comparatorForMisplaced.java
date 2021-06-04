package myIDA_STAR;

import java.util.Comparator;

//class that sorts nodes in ascending order FOR MISPLACED TILES HEURISTIC

public class comparatorForMisplaced implements Comparator<Node> {
    //swaps if a positive number is returned, doesnt if a negative number is returned. USES DATA MEMBER fMisplaced (misplaced tiles h value)
    public int compare(Node n1, Node n2) {

        return n1.fMisplaced - n2.fMisplaced;
    }
}
