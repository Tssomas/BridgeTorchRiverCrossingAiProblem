package rivercrossing;

import cm3038.search.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Thomas Sutherland 1805815
 */
public class RunBridgeTorchProblem {

    private Scanner reader;

    private final ArrayList<Person> westStartPeople = new ArrayList<>();
    private final ArrayList<Person> eastStartPeople = new ArrayList<>();
    private final ArrayList<Person> goalList = new ArrayList<>();

    private Bridge bridge;

    private boolean torchStartPos;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /*/ Un-comment START
        RunBridgeTorchProblem mBridgeTorchProb = new RunBridgeTorchProblem();

        mBridgeTorchProb.initBridge();
        mBridgeTorchProb.initPeople();

        mBridgeTorchProb.start();
        //Un-comment END */

        // ============== Un-comment above and comment below to try a custom problem
        // Below is the standard problem

        // Comment START =====
        ArrayList<Person> startList = new ArrayList<>();
        Person Ben = new Person("Ben", 2);
        Person Claire = new Person("Claire", 5);
        Person Adam = new Person("Adam", 1);
        Person Doris = new Person("Doris", 8);

        startList.add(Ben);
        startList.add(Claire);
        startList.add(Adam);
        startList.add(Doris);

        // A bridge have at least 2 places
        Bridge bridge = new Bridge(2);

        // Sort the list
        Collections.sort(startList);

        // Initial state with everyone on the west side and empty on east side, with torch on west side
        PersonState start = new PersonState(startList, new ArrayList<>(), true, bridge);
        start.setShowInitialState(true);
        System.out.print(start.toString());

        // Opposite of initial state
        PersonState goal = new PersonState(new ArrayList<>(), startList, false, bridge);
        goal.setShowGoalState(true);
        System.out.print(goal.toString());

        AStar astar = new AStar(start, goal);

        Path path = astar.search();

        if (path != null) {
            path.print();
        }
        if (astar.search() == null) {
            System.out.println("No solution");
        } else {
            System.out.println("Nodes visited: " + astar.nodeVisited + ", Cost: " + Cost.cost);
        }
        // Comment END =====
    }

    private void initBridge() {
        reader = new Scanner(System.in);

        System.out.println("Configure the bridge.\n");
        System.out.print("Max capacity of the bridge:\n");
        System.out.print("(MINIMUM = 2 | MAXIMUM = 4)");

        int maxBridgeCapacity = reader.nextInt();
        bridge = new Bridge(maxBridgeCapacity);

        if (maxBridgeCapacity < 2 || maxBridgeCapacity > 4) {
            System.out.print(" ===== INVALID CAPACITY ===== (" + maxBridgeCapacity + ") ======\n");
        }
    }

    private void initPeople() {
        reader = new Scanner(System.in);

        System.out.print("\nIs the torch starting on the West side?:\n");
        System.out.print("(Enter 'true' or 'false')\n");
        System.out.print("(default = true)\n");
        try {
            torchStartPos = reader.nextBoolean();
        } catch (InputMismatchException e) {
            torchStartPos = true;
        }

        System.out.print("\nHow many people are involved?: ");
        int nPeople = reader.nextInt();

        for (int i = 1; i < nPeople + 1; i++) {
            String personName;
            int crossingTime;
            boolean startOnWest;

            System.out.println("\nSet person " + i + ": ");

            System.out.print("Name: ");
            personName = reader.next();

            System.out.print("Time taken to cross: ");
            crossingTime = reader.nextInt();

            try {
                System.out.print("Start on the west side of the bridge:\n");
                System.out.print("(Enter 'true' or 'false')\n");
                System.out.print("(default = true)\n");
                startOnWest = reader.nextBoolean();
            } catch (InputMismatchException e) {
                // Something unexpected happened... Set start on west side as default
                startOnWest = true;
                reader.next();
            }

            Person person = new Person(personName, crossingTime);
            // Add the person to their set side
            if (startOnWest) {
                westStartPeople.add(person);
            } else {
                eastStartPeople.add(person);
            }

            // Add it to the goal state ArrayList
            goalList.add(person);
        }
    }

    private void start() {
        Collections.sort(westStartPeople);
        Collections.sort(eastStartPeople);
        Collections.sort(goalList);

        PersonState initialState = new PersonState(westStartPeople, eastStartPeople, torchStartPos, bridge);
        PersonState goalState = new PersonState(new ArrayList<>(), goalList, false, bridge);

        // Print initial and goal states
        initialState.setShowInitialState(true);
        System.out.print(initialState.toString());
        goalState.setShowGoalState(true);
        System.out.print(goalState.toString());

        AStar astar = new AStar(initialState, goalState);

        Path path = astar.search();

        if (path != null) {
            path.print();
        }
        if (astar.search() == null) {
            System.out.println("No solution");
        } else {
            System.out.println("Nodes visited: " + astar.nodeVisited + ", Cost: " + Cost.cost);
        }
    }
}
