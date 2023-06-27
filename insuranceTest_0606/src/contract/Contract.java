package contract;

import customer.Customer;
import employee.Employee;
import insurance.Insurance;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Contract {
    private int contractID, price;
    private Customer customer;
    private Insurance insurance;
    private String date, endDate;
    private float insuranceRatio;
    private Employee employee;
    private ContractList contractList;

    public int getContractID() {
        return contractID;
    }
    public void setContractID(int contractID) {
        this.contractID = contractID;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Insurance getInsurance() {
        return insurance;
    }
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public ContractList getContractList() {
        return contractList;
    }
    public void setContractList(ContractList contractList) {
        this.contractList = contractList;
    }
    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public float getInsuranceRatio() {
        return insuranceRatio;
    }
    public void setInsuranceRatio(float insuranceRatio) {
        this.insuranceRatio = insuranceRatio;
    }

    // 요율 pass...
    public float calculateRatio() {
        switch (insurance.getInsuranceType()) {
            case "building":
                if (Integer.parseInt(customer.getM_building().getBuildingPrice()) > 1000000000) insuranceRatio *= 1.2;
                else insuranceRatio *= 1.0;
                if (customer.getM_building().getBuildingHeight() > 100) insuranceRatio *= 1.2;
                else if (customer.getM_building().getBuildingHeight() <= 50) insuranceRatio *= 0.9;
                else insuranceRatio *= 1.0;
                if (customer.getM_building().getYear() < 2000) insuranceRatio *= 1.3;
                else if (customer.getM_building().getYear() < 2018) insuranceRatio *= 1.1;
                else insuranceRatio *= 0.9;
                break;
            case "car":
                switch (customer.getM_car().getCarType()) {
                    case "small" -> insuranceRatio *= 0.8;
                    case "freightCar" -> insuranceRatio *= 1.1;
                    case "motocycle" -> insuranceRatio *= 1.2;
                    case "agricultureCar" -> insuranceRatio *= 0.9;
                    default -> {
                    }
                }
                switch (customer.getJob()) {
                    case "학생" -> insuranceRatio *= 0.8;
                    case "사무직" -> insuranceRatio *= 0.9;
                    case "농부" -> insuranceRatio *= 1.1;
                    case "화물차운전자" -> insuranceRatio *= 1.2;
                    case "배달종사자" -> insuranceRatio *= 1.3;
                    default -> {
                    }
                }
                switch (customer.getSex()) {
                    case "남" -> insuranceRatio *= 1.1;
                    case "여" -> insuranceRatio *= 1.0;
                }
                break;
            case "driver":
                switch (customer.getM_car().getCarType()) {
                    case "small" -> insuranceRatio *= 0.8;
                    case "freightCar" -> insuranceRatio *= 1.1;
                    case "motocycle" -> insuranceRatio *= 1.2;
                    case "agricultureCar" -> insuranceRatio *= 0.9;
                    default -> {
                    }
                }
                int calculateAge = calcualteCustomerAge();
                if (calculateAge >= 10 && calculateAge < 20) insuranceRatio *= 0;
                else if (calculateAge < 30) insuranceRatio *= 1.2;
                else if (calculateAge < 40) insuranceRatio *= 1.1;
                else if (calculateAge < 50) insuranceRatio *= 1.3;
                else if (calculateAge < 60) insuranceRatio *= 1.3;
                else if (calculateAge < 70) insuranceRatio *= 1.3;
                else insuranceRatio *= 1.3;
                switch (customer.getJob()) {
                    case "학생" -> insuranceRatio *= 0.8;
                    case "사무직" -> insuranceRatio *= 0.9;
                    case "농부" -> insuranceRatio *= 1.1;
                    case "화물차운전자" -> insuranceRatio *= 1.2;
                    case "배달종사자" -> insuranceRatio *= 1.3;
                    default -> {
                    }
                }
                switch (customer.getSex()) {
                    case "남" -> insuranceRatio *= 1.1;
                    case "여" -> insuranceRatio *= 1.0;
                }
                break;
        }
        this.setInsuranceRatio(insuranceRatio);
        return this.getInsuranceRatio();
    }

    public int calcualteCustomerAge() {
        String[] strArrary = customer.getCustomerNumber().split("");
        int ageYear;
        if (Integer.parseInt(strArrary[0]) != 0)
            ageYear = 1900 + Integer.parseInt(strArrary[0]) * 10 + Integer.parseInt(strArrary[1]);
        else ageYear = 2000 + Integer.parseInt(strArrary[1]);
        GregorianCalendar today = new GregorianCalendar();
        int year = today.get(Calendar.YEAR);
        return year - ageYear;
    }
}
