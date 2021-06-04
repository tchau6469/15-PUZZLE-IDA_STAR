package myIDA_STAR;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import java.util.*;

public class IDASTAR {
    Stack<Node> stack = new Stack<>();
    HashSet<String> exploredList = new HashSet<String>();
    int numNodesExpanded = 0;
    int nodesUsed = 0;
    long nodeMemory = 0;

    boolean foundManhattan = false;
    boolean foundMisplaced = false;


    int numIteration = 1;
    PriorityQueue<Integer> intqueue;


    public void idsManhattan(Node root) {
        //value of memory of a node object in bytes
        nodeMemory = ObjectSizeCalculator.getObjectSize(root);
        intqueue = new PriorityQueue<>(11, new intComparator());
        int limit = 0;

        intqueue.add(root.f);


        while (!foundManhattan) {
            if (!intqueue.isEmpty()) {
                limit = intqueue.poll();
            }


            //System.out.println("THIS IS ITERATION: " + numIteration);
            idastarManhattan(limit, root);
            numIteration++;

        }
    }

    //A-STAR USING MANHATTAN DISTANCE HEURISTIC
    public void idastarManhattan (int limit, Node root) {

        //stack used, priority queue is used for the lowest depth limit that passed limit
        stack = new Stack<Node>();
        intqueue = new PriorityQueue<>(11, new intComparator());
        exploredList = new HashSet<>();

        //push root node into the stack to initialize a*
        stack.push(root);

        //while the stack not empty, start popping nodes and checking for goal and adding more nodes to stack
        while (!stack.isEmpty()) {

            //setting currentNode as the Node in front of stack and popping the stack
            Node currentNode = stack.pop();

            //adding currentNode to explored list of Nodes
            exploredList.add(currentNode.puzzleString);

            //check to see if the currentNode is the goal
            if (currentNode.reachedGoal()) {
                currentNode.printMoves();
                printNumNodesExpanded();
                printIteration();
                foundManhattan = true;
                return;
            }

            currentNode.expandNode();
            numNodesExpanded++;

            //check each child of currentNode and do stuff with it
            for (Node child : currentNode.children) {
                nodesUsed++;

                //checks if child's f is less than or equal to the depth limit. if it is, then it can be added to the stack if not already in it
                if (child.f <= limit) {
                    if (!stackContains(child.puzzleString) && !exploredContains(child.puzzleString)) {
                        stack.push(child);
                    }
                }

                //if not, the child's f heuristic will be added to the priority queue for the next iteration
                //the f heuristic that is the least will be used as the next iteration's limit(first number in the priority queue intqueue)
                else {
                    intqueue.add(child.f);
                }

            }
        }
    }

    public void idsMisplaced(Node root) {
        //value of memory of a node object in bytes
        nodeMemory = ObjectSizeCalculator.getObjectSize(root);
        intqueue = new PriorityQueue<>(11, new intComparator());
        int limit = 0;

        intqueue.add(root.fMisplaced);


        while (!foundMisplaced) {
            if (!intqueue.isEmpty()) {
                limit = intqueue.poll();
            }


            //System.out.println("THIS IS ITERATION: " + numIteration);
            idastarMisplaced(limit, root);
            numIteration++;

        }

    }


    //A-STAR USING MANHATTAN DISTANCE HEURISTIC
    public void idastarMisplaced (int limit, Node root) {

        //stack used, priority queue is used for the lowest depth limit that passed limit
        stack = new Stack<Node>();
        intqueue = new PriorityQueue<>(11, new intComparator());
        exploredList = new HashSet<>();

        //push root node into the priority queue to initialize a*
        stack.push(root);

        //while the stack not empty, start popping nodes and checking for goal and adding more nodes to stack
        while (!stack.isEmpty()) {

            //setting currentNode as the Node in front of stack and popping the stack
            Node currentNode = stack.pop();

            //adding currentNode to explored list of Nodes
            exploredList.add(currentNode.puzzleString);

            //check to see if the currentNode is the goal
            if (currentNode.reachedGoal()) {
                currentNode.printMoves();
                printNumNodesExpanded();
                printIteration();
                foundMisplaced = true;
                return;
            }

            currentNode.expandNode();
            numNodesExpanded++;

            //check each child of currentNode and do stuff with it
            for (Node child : currentNode.children) {
                nodesUsed++;


                //checks if child's f is less than or equal to the depth limit. if it is, then it can be added to the stack if not already in it
                if (child.fMisplaced <= limit) {
                    if (!stackContains(child.puzzleString) && !exploredContains(child.puzzleString)) {
                        stack.push(child);
                    }
                }

                //if not, the child's f heuristic will be added to the priority queue intqueue for the next iteration
                //the f heuristic that is the least will be used as the next iteration's limit(first number in the priority queue intqueue)
                else {
                    intqueue.add(child.fMisplaced);
                }

            }
        }
    }

    //method to check if the stack contains in the sent child puzzle string
    public boolean stackContains(String theString) {
        for (Node node : stack) {
            if (node.puzzleString.equals(theString)) {
                return true;
            }
        }
        return false;
    }

    //method to check if the sent child puzzle string is explored yet
    public boolean exploredContains(String theString) {
        if (exploredList.contains(theString)) {
            return true;
        }
        return false;
    }

    //method to print number of nodes expanded
    public void printNumNodesExpanded() {
        System.out.println("number of nodes expanded: " + numNodesExpanded);
    }

    //method to memory used
    public long getMemoryUsed() { //calculates memory based on size of node object * num nodes used
        return (nodeMemory * nodesUsed) / 1000;
    }

    public void printIteration() {
        System.out.println("solution found on iteration: " + numIteration);
    }
}
