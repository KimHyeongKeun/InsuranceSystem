package pCustomer;

import java.util.ArrayList;

public interface PCustomerList {
    public boolean add(PCustomer pCustomer);

    public boolean delete(int customerID);

    public PCustomer search(int customerID);

    public boolean update(PCustomer pCustomer, int customerID);

    public ArrayList<PCustomer> getCustomerList();

}