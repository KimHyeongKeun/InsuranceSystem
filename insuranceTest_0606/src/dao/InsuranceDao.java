package dao;

import insurance.Insurance;
import insurance.InsuranceList;

public interface InsuranceDao {
    public int create(Insurance insurance);

    public void deleteAll();

    public void deleteByName(String name);

    public InsuranceList retrieve();

    public InsuranceList retrieveNoApprove();

    public InsuranceList retrieveApprove();

    public void updateName(String name);
    
    public String extendComplete(Insurance insurance);


}
