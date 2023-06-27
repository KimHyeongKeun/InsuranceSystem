package dao;

import accident.Accident;
import exemption.Exemption;
import exemption.ExemptionList;

public interface ExemptionDao {
    public void create(Exemption exemption);

    public void deleteAll();

    public void deleteByID(int ID);

    public ExemptionList retrieve();

    public void update(int state, Accident accident);

    public ExemptionList retrieveList();
}
