package dao;

import customer.Driver;

public interface DriverDao {
    public void create(Driver driver, int customerID);

    public Driver retrieveByID(int driverID);
}
