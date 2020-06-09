public class Tampone {
    double C;
    int speseTampone;

    public Tampone(double C) {
        this.C = C;
    }

    public boolean testTampone(Persona p) {
        speseTampone += C;
        return p.isInfected();
    }
}
