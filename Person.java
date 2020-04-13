package rivercrossing;

/**
 * @author Thomas Sutherland 1805815
 */
public class Person implements Comparable<Person> {

    private String name;
    private int timeToCross;
    private boolean startOnWestSide;

    public Person(String name, int timeToCross, boolean startOnWestSide) {
        this.name = name;
        this.timeToCross = timeToCross;
        this.startOnWestSide = startOnWestSide;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeToCross() {
        return timeToCross;
    }

    public void setTimeToCross(int timeToCross) {
        this.timeToCross = timeToCross;
    }

    public boolean getStartOnWestSide() {
        return startOnWestSide;
    }

    public void setStartOnWestSide(boolean startOnWestSide) {
        this.startOnWestSide = startOnWestSide;
    }

    @Override
    public int compareTo(Person o) {
        return this.timeToCross - o.getTimeToCross();
    }
}
