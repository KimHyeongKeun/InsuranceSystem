package dao;

import customer.Customer;
import customer.CustomerList;
import customer.CustomerListImpl;

import java.sql.ResultSet;

public class CustomerDaoImpl extends Dao implements CustomerDao {

    public CustomerDaoImpl() {
        try {
            super.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int create(Customer customer) {
        String query = "insert into customer ( CustomerID, address, manageArrears, ArrearsCount, blackList, customerNumber, eMail, job, sex ) values ( " +
                customer.getCustomerID() + ", " +
                " '" + customer.getAddress() + "', " +
                " '" + customer.getManageArrears() + "', " +
                " '" + customer.getArrearsCount() + "', " +
                " '" + customer.getBlackList() + "', " +
                " '" + customer.getCustomerNumber() + "', " +
                " '" + customer.geteMail() + "', " +
                " '" + customer.getJob() + "', " +
                " '" + customer.getSex() + "' )";
        int customerID = 0;
        try {
            this.execute(query);
            query = "select LAST_INSERT_ID() as ID";
            ResultSet resultSet = this.retrieve(query);
            while (resultSet.next()) {
                customerID = resultSet.getInt("ID");
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return customerID;
    }

    @Override
    public boolean createManagePcustomer(Customer customer) {
        String query = "insert into customer ( pcustomerID, address, customerNumber, eMail, job, sex ) values ( " +
                customer.getPCustomerID()+","+
                " '" + customer.getAddress() + "', " +
                " '" + customer.getCustomerNumber() + "', " +
                " '" + customer.geteMail() + "', " +
                " '" + customer.getJob() + "', " +
                " '" + customer.getSex() + "') ";
        try {
            this.execute(query);
            query="update pcustomer set consultContext='"+
                    customer.getConsultContext()+"' where pcustomerID="+
                    customer.getPCustomerID();
            this.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public CustomerList retrieve() { //불러오기
        String query = "select * from customer inner join pcustomer on customer.pcustomerID = pcustomer.pcustomerID";
        CustomerList customerList = new CustomerListImpl();
        try {
            ResultSet resultSet = this.retrieve(query);
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setPCustomerID(resultSet.getInt("pcustomerID"));
                customer.setCustomerID(resultSet.getInt("customerID"));
                customer.setAddress(resultSet.getString("address"));
                customer.setManageArrears(resultSet.getInt("manageArrears") == 1);
                customer.setArrearsCount(resultSet.getInt("ArrearsCount"));
                customer.setBlackList(resultSet.getInt("blackList") == 1);
                customer.setCustomerNumber(resultSet.getString("customerNumber"));
                customer.seteMail(resultSet.getString("eMail"));
                customer.setJob(resultSet.getString("job"));
                customer.setSex(resultSet.getString("sex"));
                customer.setCustomerName(resultSet.getString("pcustomerName"));
                customer.setPhoneNumber(resultSet.getString("phoneNumber"));
                customer.setDate(resultSet.getString("date"));   // accidentDate에서 변경함
                customer.setConsultContext(resultSet.getString("consultContext"));

                customerList.add(customer);
            }

            return customerList;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

    @Override
    public Customer retrieveByID(int ID) {
        return null;
    }

    @Override
    public void deleteByID(int ID) {
    }

    //    @Override
//    public void updateID(int ID, Customer customer) {
//        String query = "update customer set " +
//                "customerNumber   = '" + customer.getCustomerNumber() + "', " +
//                "address      = '" + customer.getAddress() + "', " +
//                "eMail         = '" + customer.geteMail() + "', " +
//                "sex         = '" + customer.getSex() + "', " +
//                "job         = '" + customer.getJob() + "', " +
//                "manageArrears   = '" + (customer.getManageArrears() ? 1 : 0) + "', " +
//                "ArrearsCount = ArrearsCount + " + (customer.getManageArrears() ? 1 : 0) + ", " + //미납카운트 +1 증가시키기
//             "blackList      = '" + (customer.getBlackList() ? 1 : 0) + "' " +
//               // "Date = '" + customer.getConsultContext() + "', " +
//                "where customerID = " + ID;
//        try {
//            this.execute(query);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public void updateID(int ID, Customer customer) {
        try {
            String query = "update customer set " +
                    "customerNumber = '" + customer.getCustomerNumber() + "', " +
                    "address = '" + customer.getAddress() + "', " +
                    "eMail = '" + customer.geteMail() + "', " +
                    "sex = '" + customer.getSex() + "', " +
                    "job = '" + customer.getJob() + "', " +
                    "manageArrears = '" + (customer.getManageArrears() ? 1 : 0) + "', " +
                    "ArrearsCount = ArrearsCount + " + (customer.getManageArrears() ? 1 : 0) + ", " +
                    "blackList = '" + (customer.getBlackList() ? 1 : 0) + "' " +
                    "where customerID = " + ID;
            this.execute(query);
            query = "update pcustomer set " +
                    "consultContext = '" + customer.getConsultContext() + "' " +
                    "where pcustomerID = " + customer.getPCustomerID();
            this.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(Customer customer) {
        try {
            String query="update pcustomer set pcustomerName='"
                    +customer.getCustomerName()+"',"
                    +"phoneNumber='"+customer.getPhoneNumber()+"'"
                    +"where pcustomerID="+customer.getPCustomerID();
            this.execute(query);
            query="update customer set job='"
                    +customer.getJob()+"',"
                    +"eMail='"+customer.geteMail()+"',"
                    +"address='"+customer.getAddress()+"'"
                    +"where customerID="+customer.getCustomerID();
            this.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}