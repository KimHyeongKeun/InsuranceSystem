package contract;

import customer.Customer;

import java.util.ArrayList;

public interface ContractList {
    public boolean add(Contract registration);

    public boolean delete(int registrationID);

    public Contract search(int registraionID);

    public ArrayList<Contract> searchByCustomer(int customerID);

    public Contract searchByCustomer2(Customer customer);

    public ArrayList<Contract> searchByCustomerName(String customerName);

    public ArrayList<Contract> searchByInsuranceName(String insuranceName);

    public ArrayList<Contract> getContractList();

    public ArrayList<Contract> searchByContractID(int contractID);

    public ArrayList<Contract> getCustomerList();

    ArrayList<Contract> searchByCustomer3(int customerID);
}
