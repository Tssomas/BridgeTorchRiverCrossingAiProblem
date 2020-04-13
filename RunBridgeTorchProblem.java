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

    private final ArrayList<Person> listPeople = new ArrayList<>();
    private Bridge bridge;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*RunBridgeTorchProblem mBridgeTorchProb = new RunBridgeTorchProblem();
        
        mBridgeTorchProb.initBridge();
        mBridgeTorchProb.initPeople();
        
        mBridgeTorchProb.start();*/

        ArrayList<Person> listPersons = new ArrayList<>();
        listPersons.add(new Person("Ben", 2, true));
        listPersons.add(new Person("Claire", 5, true));
        listPersons.add(new Person("Adam", 1, false));
        //listPersons.add(new Person("Doris", 8, true));

        // A bridge have at least 2 places
        Bridge bridge = new Bridge(2);

        // Sort list
        Collections.sort(listPersons);

        // Initial state with everyone on the west side and empty on east side, with torch on west side
        PersonState start = new PersonState(listPersons, new ArrayList<>(), true, bridge);
        start.setShowInitialState(true);
        System.out.print(start.toString());

        // Opposite of initial state
        PersonState goal = new PersonState(new ArrayList<>(), listPersons, false, bridge);
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
    }

    private void initBridge() {
        reader = new Scanner(System.in);

        System.out.println("Configure the bridge.");
        System.out.print("\nMax capacity of the bridge: ");

        int maxBridgeCapacity = reader.nextInt();
        bridge = new Bridge(maxBridgeCapacity);
    }

    private void initPeople() {
        reader = new Scanner(System.in);

        System.out.print("\nHow many people are crossing the bridge: ");
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
                System.out.print("Start on the west side of the bridge: ");
                System.out.print("(Enter 'true' or 'false')");
                System.out.print("(default = true)");
                startOnWest = reader.nextBoolean();
            } catch (InputMismatchException e) {
                // Something unexpected happened... Set start on west side as default
                startOnWest = true;
                reader.next();
            }

            Person person = new Person(personName, crossingTime, startOnWest);
            listPeople.add(person);
        }
    }

    private void start() {
        Collections.sort(listPeople);

        PersonState start = new PersonState(listPeople, new ArrayList<>(), true, bridge);
        PersonState goal = new PersonState(new ArrayList<>(), listPeople, false, bridge);

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
    }
}
