package rivercrossing;

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
     * @param goal Goal state
     */
    public AStar(State start, State goal) {
        super(start, goal);
    }

    @Override
    public double evaluation(Node node) {
        return node.getCost() + this.heuristic(node.state);
    }

    public int heuristic(State state) {
        PersonState sState = (PersonState) state;

        if (sState.isTorchWest()) {

            return sState.isGoal() ? 0 : Collections.max(sState.listPeopleWest).getTimeToCross();
        } else {
            return sState.isGoal() ? 0 : Collections.min(sState.listPeopleEast).getTimeToCross();
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
