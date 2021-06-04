package myIDA_STAR;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
	// write your code here


        Scanner scanner = new Scanner(System.in);
        long startTime = 0; //starting time variable
        long endTime = 0; //endtime variable

        IDASTAR theIDASTAR;



        //lets you test out any number of puzzles
        while (true) {

            //getting user input
            String intString = scanner.nextLine();
            //cut out extra spaces before and after numbers
            intString = intString.trim();



            //split numbers into a string array and then populating it into an int array
            String[] numberArray = intString.split(" ");
            int[] puzzle = new int[numberArray.length];

            for (int i = 0; i < puzzle.length; i++) {
                puzzle[i] = Integer.parseInt(numberArray[i]);
            }

            //declaring root node
            Node root;

            //*******************MANHATTAN DISTANCE METHOD**************************************************//

            //creating new objects so then the misplaced tiles method data members will be wiped
            theIDASTAR = new IDASTAR();
            root = new Node(puzzle, puzzle.length); //new root is needed, since the last one was expanded already
            System.out.println("\nMANHATTAN DISTANCE METHOD:");

            //start time
            startTime = System.nanoTime();

            //doing the search (manhattan style)
            theIDASTAR.idsManhattan(root);

            //end time
            endTime = System.nanoTime();

            System.out.println("Time taken in milliseconds: " + TimeUnit.NANOSECONDS.toMillis(endTime- startTime));
            System.out.println("Memory used: " + theIDASTAR.getMemoryUsed() + " kb");

            //***************** MISPLACED TILES METHOD*************************************************//
            theIDASTAR = new IDASTAR();
            root = new Node(puzzle, puzzle.length);
            System.out.println("\nMISPLACED TILES METHOD:");

            //start time
            startTime = System.nanoTime();

            //doing the search (misplaced tiles style)
            theIDASTAR.idsMisplaced(root);

            //end time
            endTime = System.nanoTime();

            System.out.println("Time taken in milliseconds: " + TimeUnit.NANOSECONDS.toMillis(endTime- startTime));
            System.out.println("Memory used: " + theIDASTAR.getMemoryUsed() + " kb");


        }
    }//end main()


}
