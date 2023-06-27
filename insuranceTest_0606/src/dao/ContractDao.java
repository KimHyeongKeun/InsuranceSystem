package dao;

import contract.Contract;
import contract.ContractList;

public interface ContractDao {
    public int create(Contract contract);

    public void deleteByID(int ID);

    public Contract retrieveByID(int ID);

    public ContractList retrieve();

    public void updateID(int ID, Contract contract);

    public void updateIDCustomer(Contract contract);
}
