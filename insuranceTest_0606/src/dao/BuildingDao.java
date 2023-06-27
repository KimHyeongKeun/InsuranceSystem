package dao;

import customer.Building;

public interface BuildingDao {
    public void create(Building building, int customerID);

    public Building retrieveByID(int buildingID);

}
