package dao;

import insurance.Coverage;

public interface CoverageDao {
    public int create(Coverage coverage);

    public Coverage retrieveByID(int coverageID);

}
