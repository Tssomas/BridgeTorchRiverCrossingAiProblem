package rivercrossing;

/**
 * @author Thomas Sutherland 1805815
 */
public class Person implements Comparable<Person> {

    private String name;
    private int timeToCross;

    public Person(String name, int timeToCross) {
        this.name = name;
        this.timeToCross = timeToCross;
    }

    public String getName() {
        return name;
    }

    public int getTimeToCross() {
        return timeToCross;
    }

    @Override
    public int compareTo(Person o) {
        return this.timeToCross - o.getTimeToCross();
    }
}
