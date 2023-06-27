package accident;

import java.util.ArrayList;

public interface AccidentList {
    public boolean add(Accident accident);

    public boolean delete(int accidentNum);

    public Accident search(int accidentNum);

    public ArrayList<Accident> getAccidentList();
}