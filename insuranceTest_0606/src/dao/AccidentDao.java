package dao;

import accident.Accident;
import accident.AccidentList;

public interface AccidentDao {
	 public int create(Accident accident);

	    public void deleteAll();

	    public void deleteByID(int ID);

	    public AccidentList retrieve();

	    public AccidentList retrieveNotcompleted();

	    public AccidentList retrievecommpleted();

	    public int retrieveAccidentCnt(int customerID);

	    public void update(Accident accident);

	    public void createInfo(Accident accident);

	    public void createInvestigation(Accident daccident);
	    
	    public void updateInvestigation(Accident accident);
	    
	    public void updateAccidentComplete(Accident accident);

	    public void updateJudged(Accident accident);
}
