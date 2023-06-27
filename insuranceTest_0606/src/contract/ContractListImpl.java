package contract;

import customer.Customer;

import java.util.ArrayList;

public class ContractListImpl implements ContractList {
    private ArrayList<Contract> contractList;

    public ContractListImpl() {
        this.setContractList(new ArrayList<Contract>());
    }

    public boolean add(Contract contract) {
        if (this.contractList.add(contract)) return true;
        else return false;
    }

    public boolean delete(int contractID) {
        for (Contract contract : this.contractList) {
            if (contract.getContractID() == contractID) {
                if (this.contractList.remove(contract)) return true;
                break;
            }
        }
        return false;
    }

    public Contract search(int contractID) {
        for (Contract contract : this.contractList) {
            if (contract.getContractID() == contractID) return contract;
        }
        return null;
    }

    public ArrayList<Contract> searchByCustomer(int customerID) {
        ArrayList<Contract> contracts=new ArrayList<Contract>();
        for (Contract contract : this.contractList) {
            if (contract.getCustomer().getCustomerID() == customerID) {
                contracts.add(contract);
                return contracts;
            }
        }
        return null;
    }

    public Contract searchByCustomer2(Customer customer) {
        for (Contract contract : this.contractList) {
            if (contract.getCustomer().getCustomerName().equals(customer.getCustomerName())) {
                return contract;
            }
        }
        return null;
    }

    public ArrayList<Contract> searchByCustomer3(int customerID) {
        ArrayList<Contract> contracts=new ArrayList<Contract>();
        for (Contract contract : this.contractList) {
            if (contract.getCustomer().getCustomerID() == customerID) {
                contracts.add(contract);
            }
        }
        return contracts;
    }

    public ArrayList<Contract> searchByContractID(int contractID){
        ArrayList<Contract> contracts=new ArrayList<Contract>();
        for (Contract contract : this.contractList) {
            if (contract.getContractID() == contractID) {
                contracts.add(contract);
                return contracts;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Contract> getCustomerList() {
        return null;
    }

    public ArrayList<Contract> searchByCustomerName(String customerName) {
        ArrayList<Contract> contracts=new ArrayList<Contract>();
        for (Contract contract : this.contractList) {
            if (contract.getCustomer().getCustomerName() == customerName) {
                contracts.add(contract);
                return contracts;
            }
        }
        return null;
    }

    public ArrayList<Contract> searchByInsuranceName(String InsuranceName) {
        ArrayList<Contract> contracts=new ArrayList<Contract>();
        for (Contract contract : this.contractList) {
            if (contract.getCustomer().getCustomerName() == InsuranceName) {
                contracts.add(contract);
                return contracts;
            }
        }
        return null;
    }

    public ArrayList<Contract> getContractList() {
        return contractList;
    }
    public void setContractList(ArrayList<Contract> contractList) {
        this.contractList = contractList;
    }
}
