package dao;

import insurance.Approve;

public interface ApproveDao {
    public void create(Approve approve);

    public Approve retrieveByInsuranceID(int insuranceID);

}
