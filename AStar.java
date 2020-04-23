package com.thomas_sutherland_1805815.bridgetorchprob;

import cm3038.search.Node;
import cm3038.search.State;
import cm3038.search.informed.BestFirstSearchProblem;

import java.util.Collections;

/**
 * @author Thomas Sutherland 1805815
 */
public class AStar extends BestFirstSearchProblem {

    /**
     * @param start Initial state
     * @param goal  Goal state
     */
    public AStar(State start, State goal) {
        super(start, goal);
    }

    @Override
    public double evaluation(Node node) {
        return node.getCost() + this.heuristic(node.state);
    }

    public int heuristic(State state) {
        PersonState activeState = (PersonState) state;

        // The cost will be the time taken to cross the bridge..
        if (activeState.isTorchWest()) {// torch is west
            // Since the fastest person is sent back from the east side, hopefully the fastest person will be selected
            // from the west side to go with the slowest person.. seems to work
            return activeState.isGoal() ? 0 : Collections.max(activeState.listPeopleWest).getTimeToCross();
        } else { // Torch is east
            // Send back the fastest person
            return activeState.isGoal() ? 0 : Collections.min(activeState.listPeopleEast).getTimeToCross();
        }
    }

    /**
     * @param state State to check if it is the goal
     * @return Boolean goal state or not
     */
    @Override
    public boolean isGoal(State state) {
        return state.equals(this.goalState);
    }
}
