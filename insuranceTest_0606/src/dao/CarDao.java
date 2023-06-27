package dao;

import customer.Car;

public interface CarDao {
    public void create(Car car, int customerID);

    public Car retrieveByID(int carID);

}
