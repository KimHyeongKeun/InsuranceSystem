package dao;

import customer.Customer;
import customer.CustomerList;

public interface CustomerDao {
    public int create(Customer customer);

    public void deleteByID(int ID);

    public Customer retrieveByID(int ID);

    public CustomerList retrieve();

    public void updateID(int ID, Customer customer);

    boolean update(Customer customer);

    boolean createManagePcustomer(Customer customer);
}
