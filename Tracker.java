import java.util.ArrayList;

public class Tracker {
    private ArrayList<Persona> incontri = new ArrayList<Persona>();

    public void add(Persona p) {
        incontri.add(p);
    }

    public ArrayList<Persona> getArrayIncontri () {
        return this.incontri;
    }

}
