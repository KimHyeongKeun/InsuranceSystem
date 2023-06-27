package dao;

import insurance.*;

import java.sql.ResultSet;

public class InsuranceDaoImpl extends Dao implements InsuranceDao {

    public InsuranceDaoImpl() {
        try {
            super.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int create(Insurance insurance) {
        String query = "select * from insurance where insuranceName = '" + insurance.getInsuranceName() + "'";

        int insuranceID = 0;
        try {
            ResultSet resultSet = this.retrieve(query);
            while (resultSet.next()) insuranceID = resultSet.getInt("insuranceID");
            if (insuranceID != 0) return 0;
            query = "insert into insurance ( insuranceName, insuranceCost, contents, insuranceType ) values ( " +
                    " '" + insurance.getInsuranceName() + "', " +
                    " '" + insurance.getInsuranceCost() + "', " +
                    " '" + insurance.getContents() + "', " +
                    " '" + insurance.getInsuranceType() + "')";
            this.execute(query);
            query = "select LAST_INSERT_ID() as ID";
            resultSet = this.retrieve(query);
            while (resultSet.next()) insuranceID = resultSet.getInt("ID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insuranceID;
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public void deleteByName(String name) {
    }

    @Override
    public InsuranceList retrieve() {
        String query = "select * from insurance";
        InsuranceList insuranceList = new InsuranceListImpl();
        try {
            ResultSet resultSet = this.retrieve(query);
            while (resultSet.next()) {
                Insurance insurance = new Insurance();
                insurance.setInsuranceID(resultSet.getInt("insuranceID"));
                insurance.setInsuranceName(resultSet.getString("insuranceName"));
                insurance.setInsuranceType(resultSet.getString("insuranceType"));
                insurance.setInsuranceCost(resultSet.getString("insuranceCost"));
                insurance.setContents(resultSet.getString("contents"));
                insuranceList.add(insurance);
            }
            for (int i = 0; i < insuranceList.getInsuranceList().size(); i++) {
                Insurance insurance = insuranceList.getInsuranceList().get(i);
                query = "select * from coverage where insuranceID = " + insurance.getInsuranceID();
                resultSet = this.retrieve(query);
                while (resultSet.next()) {
                    Coverage coverage = new Coverage();
                    coverage.setCoverageContent(resultSet.getString("coverageContent"));
                    coverage.setCoverageCost(Integer.parseInt(resultSet.getString("coverageCost")));
                    coverage.setCoverageID(resultSet.getInt("coverageID"));
                    coverage.setInsuranceID(resultSet.getInt("insuranceID"));
                    String size = resultSet.getString("coverageCondition");
                    coverage.setCoverageCondition(size);
                    switch (size) {
                        case "high" -> insurance.setM_hcoverage(coverage);
                        case "middle" -> insurance.setM_mcoverage(coverage);
                        case "low" -> insurance.setM_lcoverage(coverage);
                    }
                }
                query = "select * from approve where insuranceID = " + insurance.getInsuranceID();
                resultSet = this.retrieve(query);
                while (resultSet.next()) {
                    Approve approve = new Approve();
                    approve.setPermissionDate(resultSet.getString("permissionDate"));
                    int approved = resultSet.getInt("approved");
                    approve.setApproved(approved);
                    if (approved == 0) {
                        approve.setInsuranceProblem(resultSet.getString("insuranceProblem"));
                        approve.setPermissionRefuse(resultSet.getString("permissionRefuse"));
                    }
                    insurance.setM_approve(approve);
                }
                query = "select * from salerecord where insuranceID = " + insurance.getInsuranceID();
                resultSet = this.retrieve(query);
                while (resultSet.next()) {
                    SaleRecord salerecord = new SaleRecord();
                    salerecord.setGoalCnt(resultSet.getInt("goalCnt"));
                    salerecord.setSaleCnt(resultSet.getInt("saleCnt"));
                    insurance.setM_SaleRecord(salerecord);
                }
            }
            return insuranceList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public InsuranceList retrieveNoApprove() {
        String query = "select * from insurance left outer join approve using(insuranceID) where approve.insuranceID IS NULL";
        InsuranceList insuranceList = new InsuranceListImpl();
        try {
            ResultSet resultSet = this.retrieve(query);
            while (resultSet.next()) {
                Insurance insurance = new Insurance();
                insurance.setInsuranceID(resultSet.getInt("insuranceID"));
                insurance.setInsuranceName(resultSet.getString("insuranceName"));
                insurance.setInsuranceType(resultSet.getString("insuranceType"));
                insurance.setInsuranceCost(resultSet.getString("insuranceCost"));
                insurance.setContents(resultSet.getString("contents"));
                insuranceList.add(insurance);
            }
            return insuranceList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public InsuranceList retrieveApprove() {
        String query = "select * from insurance left outer join approve using(insuranceID) where approve.approved = 1";
        InsuranceList insuranceList = new InsuranceListImpl();
        try {
            ResultSet resultSet = this.retrieve(query);
            while (resultSet.next()) {
                Insurance insurance = new Insurance();
                insurance.setInsuranceID(resultSet.getInt("insuranceID"));
                insurance.setInsuranceName(resultSet.getString("insuranceName"));
                insurance.setInsuranceType(resultSet.getString("insuranceType"));
                insurance.setInsuranceCost(resultSet.getString("insuranceCost"));
                insurance.setContents(resultSet.getString("contents"));
                insuranceList.add(insurance);
            }

            for (int i = 0; i < insuranceList.getInsuranceList().size(); i++) {
                Insurance insurance = insuranceList.getInsuranceList().get(i);
                query = "select * from coverage where insuranceID = " + insurance.getInsuranceID();
                resultSet = this.retrieve(query);
                while (resultSet.next()) {
                    Coverage coverage = new Coverage();
                    coverage.setCoverageContent(resultSet.getString("coverageContent"));
                    coverage.setCoverageCost(Integer.parseInt(resultSet.getString("coverageCost")));
                    coverage.setCoverageID(resultSet.getInt("coverageID"));
                    coverage.setInsuranceID(resultSet.getInt("insuranceID"));
                    String size = resultSet.getString("coverageCondition");
                    coverage.setCoverageCondition(size);
                    switch (size) {
                        case "high" -> insurance.setM_hcoverage(coverage);
                        case "middle" -> insurance.setM_mcoverage(coverage);
                        case "low" -> insurance.setM_lcoverage(coverage);
                    }
                }
                query = "select * from approve where insuranceID = " + insurance.getInsuranceID();
                resultSet = this.retrieve(query);
                while (resultSet.next()) {
                    Approve approve = new Approve();
                    approve.setPermissionDate(resultSet.getString("permissionDate"));
                    int approved = resultSet.getInt("approved");
                    approve.setApproved(approved);
                    if (approved == 0) {
                        approve.setInsuranceProblem(resultSet.getString("insuranceProblem"));
                        approve.setPermissionRefuse(resultSet.getString("permissionRefuse"));
                    }
                    insurance.setM_approve(approve);
                }
                query = "select * from salerecord where insuranceID = " + insurance.getInsuranceID();
                resultSet = this.retrieve(query);
                while (resultSet.next()) {
                    SaleRecord salerecord = new SaleRecord();
                    salerecord.setGoalCnt(resultSet.getInt("goalCnt"));
                    salerecord.setSaleCnt(resultSet.getInt("saleCnt"));
                    insurance.setM_SaleRecord(salerecord);
                }
            }
            return insuranceList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateName(String name) {
    }

    @Override
    public String extendComplete(Insurance insurance) {
        String query = "select * from insurance where contents = null";
        int insuranceID = 0;
        String content;
        try {
            ResultSet resultSet = this.retrieve(query);
            while (resultSet.next()) {
//              insuranceID = resultSet.getInt("insuranceID");
//              content = resultSet.getCursorName("content");
//              content = resultSet.getString(content);
//              approve.setPermissionDate(resultSet.getString("permissionDate"));
                content = resultSet.getString("contents");
            }
            query = "update insurance set contents = '보험' where contents=null";
            this.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
