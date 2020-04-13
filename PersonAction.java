package rivercrossing;

import cm3038.search.Action;

import java.util.ArrayList;

/**
 * @author Thomas Sutherland 1805815
 */
public class PersonAction extends Action {

    ArrayList<Person> person;
    boolean torchIsWest;

    /**
     * @param person
     * @param torchIsWest
     */
    public PersonAction(ArrayList<Person> person, boolean torchIsWest) {
        this.person = person;
        this.torchIsWest = torchIsWest;
    }

    /**
     * @return
     */
    public ArrayList<Person> getPerson() {
        return person;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        for (int i = 0; i < person.size(); i++) {
            Cost.cost += person.get(i).getTimeToCross();
        }
        String str = "ACTION:  Moving ";

        str = this.getPerson().stream().map((p) -> "Name: " + p.getName() + "(" + p.getTimeToCross() + ")")
                .reduce(str, String::concat);

        //str = this.getPerson().stream().map((p) -> p.getName()+"("+p.getWeight()+")["+p.isCanDrive()+"] - ").reduce(str, String::concat);

        if (this.torchIsWest) {
            str += "WEST TO EAST\n";
        } else {
            str += "EAST TO WEST\n";
        }

        return str;
    }

}
