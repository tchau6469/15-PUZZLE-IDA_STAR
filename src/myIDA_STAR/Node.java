package myIDA_STAR;

import java.util.*;

public class Node {
    public List<Node> children = new ArrayList<Node>();
    public int[] puzzle;
    public int indexOfZero;
    public int columns;
    public String movesString = "";
    public String puzzleString = "";

    //data members for manhattan distance method
    int g = 0;
    int h = 0;
    int f = 0;

    //data members for misplaced tiles method
    int hMisplaced = 0;
    int fMisplaced = 0;

    //default constructor. when a node is created, its puzzle is set
    public Node(int[] puzzleParameter, int length) {

        puzzle = new int[length];
        columns = (int)Math.sqrt(puzzle.length);
        setPuzzle(puzzleParameter);
        //setting manhattan data members
        h = getManhattanDist();
        f = g + h;

        //setting misplaced tiles data members
        hMisplaced = getMisplacedTiles();
        fMisplaced = g + hMisplaced;

    }


    //method to set puzzle data member to the puzzleParameter
    public void setPuzzle(int[] puzzleParameter) {
        for (int i = 0; i < puzzle.length; i ++) {
            puzzle[i] = puzzleParameter[i];
            puzzleString += puzzleParameter[i];

            if (puzzle[i] == 0) { //finds the index of 0 in the puzzle
                indexOfZero = i;
            }
        }

    }

    //method to move right. adds new child to children list
    public void moveRight() {

        if (indexOfZero % columns < columns - 1) {
            int[] puzzleClone = puzzle.clone();
            puzzleClone[indexOfZero] = puzzleClone[indexOfZero + 1];
            puzzleClone[indexOfZero + 1] = 0;


            Node child = new Node(puzzleClone, puzzle.length);
            child.movesString = this.movesString + "R";

            child.g = this.g + 1;
            child.f = child.g + child.h;

            //special f data member for misplaced f heuristic
            child.fMisplaced = child.g + child.hMisplaced;

            children.add(child);

        }

    }

    //method to move left. adds new child to children list
    public void moveLeft() {
        if (indexOfZero % columns != 0) {
            int[] puzzleClone = puzzle.clone();
            puzzleClone[indexOfZero] = puzzleClone[indexOfZero - 1];
            puzzleClone[indexOfZero - 1] = 0;

            Node child = new Node(puzzleClone, puzzle.length);
            child.movesString = this.movesString + "L";

            child.g = this.g + 1;
            child.f = child.g + child.h;

            //special f data member for misplaced f heuristic
            child.fMisplaced = child.g + child.hMisplaced;

            children.add(child);

        }
    }

    //method to move up. adds new child to children list
    public void moveUp() {
        if (indexOfZero >= columns) {
            int[] puzzleClone = puzzle.clone();
            puzzleClone[indexOfZero] = puzzleClone[indexOfZero - columns];
            puzzleClone[indexOfZero - columns] = 0;

            Node child = new Node(puzzleClone, puzzle.length);
            child.movesString = this.movesString + "U";

            child.g = this.g + 1;
            child.f = child.g + child.h;

            //special f data member for misplaced f heuristic
            child.fMisplaced = child.g + child.hMisplaced;

            children.add(child);

        }
    }

    //method to move down. adds new child to children list
    public void moveDown() {
        if (indexOfZero < columns * (columns - 1)) {
            int[] puzzleClone = puzzle.clone();
            puzzleClone[indexOfZero] = puzzleClone[indexOfZero + columns];
            puzzleClone[indexOfZero + columns] = 0;

            Node child = new Node(puzzleClone, puzzle.length);
            child.movesString = this.movesString + "D";

            child.g = this.g + 1;
            child.f = child.g + child.h;

            //special f data member for misplaced f heuristic
            child.fMisplaced = child.g + child.hMisplaced;

            children.add(child);

        }
    }

    //method to check if the node is the goal
    public boolean reachedGoal() {
        if (puzzle[puzzle.length-1] != 0) { //if the last index is not 0, then puzzle is not solved
            return false;
        }

        int lesserValue = puzzle[0]; //get the int value at the first index to compare to next index

        //goal is reached if the array is in order. if array is not in order, return false
        for (int i = 1; i < puzzle.length -1; i++) {
            if (lesserValue > puzzle[i]) {
                return false;
            }
            lesserValue = puzzle[i];
        }

        return true; //goal reached
    }

    //method to expand parent node, adds new child nodes to the children data member
    public void expandNode() {


        //expands in order: up, down, left, right
        moveUp();
        moveDown();
        moveLeft();
        moveRight();






    }

    //prints out puzzle. used for debugging
    public void printOutPuzzle() {
        for (int i = 0; i < puzzle.length; i++) {
            if (i % columns == 0) {
                System.out.println();
            }
            System.out.print(puzzle[i] + " ");
        }
        System.out.println();
    }

    //prints out the moves to reach goal
    public void printMoves() { //prints the moves string
        System.out.println("Moves: " + movesString);
    }

    //method to find the number of tiles away a number is on the puzzle.
    public int findNumTilesAway(int index, int numberAtIndex) {
        return Math.abs((numberAtIndex - 1) % columns - index % columns) + Math.abs((numberAtIndex- 1)/ columns - index/ columns);
    }

    //method to get manhattan distance heuristic of node
    public int getManhattanDist() {
        int accumulator = 0;
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] != i + 1 && puzzle[i] != 0) { //if the number at current index does not belong there
                accumulator += findNumTilesAway(i, puzzle[i]);
            }
        }
        return accumulator;
    }

    //method to get misplaced tiles heuristic of node
    public int getMisplacedTiles() {
        int accumulator = 0;
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] != i + 1 && puzzle[i] != 0) { //if the number at current index does not belong there
                accumulator++;
            }
        }
        return accumulator;
    }
}//end of Node class
