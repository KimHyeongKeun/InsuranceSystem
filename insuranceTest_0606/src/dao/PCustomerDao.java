package dao;

import pCustomer.PCustomer;
import pCustomer.PCustomerList;

public interface PCustomerDao {
    public void create(PCustomer pcustomer);

    public void deleteByID(int ID);

    public PCustomer retrieveByID(int ID);

    public PCustomerList retrieve();

    public void updateID(int ID, PCustomer pCustomer);

}
