package customer;

import java.util.ArrayList;
import java.util.List;

public interface CustomerList {

    public boolean add(Customer customer);

    public boolean delete(int customerID);

    public Customer search(int customerID);

    public Customer searchByPID(int customerID);

    public Customer search(Customer customer);

    public Customer search(String customerName);

    public Customer searchConsult(Customer customer);

    public boolean update(Customer customer, int customerID);

    public ArrayList<Customer> getCustomerList();

    public List<Customer> getList();

}