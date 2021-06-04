package myIDA_STAR;

import java.util.Comparator;

//class that sorts integers in ascending order to find minimum f heuristic of child
public class intComparator implements Comparator<Integer> {

    public int compare(Integer first, Integer second) {
        return first - second;
    }

}
