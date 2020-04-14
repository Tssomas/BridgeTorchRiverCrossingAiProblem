package rivercrossing;

import cm3038.search.ActionStatePair;
import cm3038.search.State;

import java.util.*;

/**
 * @author Thomas Sutherland 1805815
 */
public class PersonState implements State {

    public ArrayList<Person> listPeopleWest, listPeopleEast;
    private final boolean isTorchWest;
    private final Bridge bridge;

    private boolean showInitialState = false;
    private boolean showGoalState = false;

    public PersonState(ArrayList<Person> listPersonsWest, ArrayList<Person> listPersonsEast, boolean torchIsWest, Bridge bridge) {
        this.listPeopleWest = listPersonsWest;
        this.listPeopleEast = listPersonsEast;
        this.isTorchWest = torchIsWest;
        this.bridge = bridge;
    }

    /**
     * @return All possible successor ActionStatePair
     */
    @Override
    public List<ActionStatePair> successor() {
        List<ActionStatePair> successors = new ArrayList<>();

        // Check if invalid
        if (this.isInvalid()) return successors;

        // Get the list of people who are on the same side as the torch
        ArrayList<Person> peopleList = isTorchWest ? listPeopleWest : listPeopleEast;

        if (isTorchWest) {
            // Work out every possible combination of people crossing
            ArrayList<ArrayList<Person>> pass = new ArrayList<>();

            for (int i = 0; i != peopleList.size(); i++) {
                for (int e = 0; e != peopleList.size(); e++) {
                    ArrayList<Person> temp = new ArrayList<>();
                    temp.add(peopleList.get(i));
                    if (!temp.contains(peopleList.get(e))) temp.add(peopleList.get(e));

                    // Sort it so we can check if it already has that permutation
                    Collections.sort(temp);

                    if (!pass.contains(temp)) {
                        pass.add(temp);
                    }
                }
            }

            // Turn those crossing combinations into actions
            for (int i = 0; i != pass.size(); i++) {
                PersonAction pAction = new PersonAction(pass.get(i), isTorchWest);
                successors.add(new ActionStatePair(pAction, getSuccessor(pass.get(i))));
            }
        } else {
            // Torch is east, bring back the fastest person
            Person fastestPerson = peopleList
                    .stream()
                    .min(Comparator.comparing(Person::getTimeToCross))
                    .orElse(null);

            ArrayList<Person> pass = new ArrayList<>();
            pass.add(fastestPerson);

            PersonAction pAction = new PersonAction(pass, isTorchWest);
            successors.add(new ActionStatePair(pAction, getSuccessor(pass)));
        }

        return successors;
    }

    /**
     * @param pass Passengers to move from west->east or east->west
     * @return Successor PersonState
     */
    @SuppressWarnings("unchecked")
    private PersonState getSuccessor(ArrayList<Person> pass) {
        ArrayList<Person> nWest = (ArrayList<Person>) listPeopleWest.clone();
        ArrayList<Person> nEast = (ArrayList<Person>) listPeopleEast.clone();

        ArrayList<Person> initialState = isTorchWest ? nWest : nEast;
        ArrayList<Person> goalState = isTorchWest ? nEast : nWest;

        initialState.removeAll(pass);
        goalState.addAll(pass);

        return new PersonState(nWest, nEast, !isTorchWest, bridge);
    }

    /**
     * @return The state in string format
     */
    @Override
    public String toString() {

        String str = "";

        if (showInitialState) {
            str += "Initial state:  ";
            showInitialState = false;
        } else if (showGoalState) {
            str += "Goal state:  ";
            showGoalState = false;
        } else {
            str += "STATE:  ";
        }

        // List everyone on east side
        str = listPeopleWest.stream().map((p) -> p.getName() + "(" + p.getTimeToCross() + ") ")
                .reduce(str, String::concat);

        // Add torch if it is east
        if (isTorchWest) str += "torch";

        // Show bridge position with capacity
        str += " [---(" + bridge.getMaxCapacity() + ")---] ";

        str = listPeopleEast.stream().map((p) -> p.getName() + "(" + p.getTimeToCross() + ") ")
                .reduce(str, String::concat);

        if (!isTorchWest) {
            str += "torch\n\n";
        } else {
            str += "\n\n";
        }

        return str;
    }


    /**
     * @param paramObject to compare
     * @return if the two objects are the same
     */
    @Override
    public boolean equals(Object paramObject) {

        if (!(paramObject instanceof PersonState)) {
            return false;
        }
        PersonState state = (PersonState) paramObject;

        Collections.sort(listPeopleWest);
        Collections.sort(listPeopleEast);

        return listPeopleWest.equals(state.listPeopleWest) && listPeopleEast.equals(state.listPeopleEast);
    }

    /**
     * @return hash of the torch's position
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash = hash + (isTorchWest ? 1 : 0);
        return hash;
    }

    public boolean isGoal() {
        return listPeopleWest.isEmpty();
    }

    // Torch position
    public boolean isTorchWest() {
        return isTorchWest;
    }

    // Boolean for toString method to show goal and initial states
    public void setShowGoalState(boolean showGoalState) {
        this.showGoalState = showGoalState;
    }

    public void setShowInitialState(boolean showInitialState) {
        this.showInitialState = showInitialState;
    }

    // Check if the torch position is valid
    private boolean isInvalid() {
        if (isTorchWest && listPeopleWest.size() == 0) return true;
        if (!isTorchWest && listPeopleEast.size() == 0) return true;

        return false;
    }
}
