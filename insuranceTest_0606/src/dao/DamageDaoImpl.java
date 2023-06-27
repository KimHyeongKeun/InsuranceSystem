package dao;

import reward.RewardInfo;

import java.sql.ResultSet;

public class DamageDaoImpl extends Dao implements DamageDao {

    public DamageDaoImpl() {
        try {
            super.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(RewardInfo rewardInfo) {
        String query = "insert into damageaccess ( accidentID, payment, reason ) values ( " +
                +rewardInfo.getAccident().getAccidentID() + ", " +
                " '" + rewardInfo.getPayment() + "', " +
                " '" + rewardInfo.getAssessReason() + "' )";
        try {
            this.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RewardInfo retrieve() {
        return null;
    }

    @Override
    public void update(RewardInfo rewardInfo) {

    }
    public String retrieveCustomerEmail(int accidentID) {
        String query = "select eMail from customer inner join accident on customer.customerID = accident.customerID";
        String customerEMail = "";
        try {
            ResultSet resultSet = this.retrieve(query);
            while (resultSet.next()) {
                customerEMail = resultSet.getString("eMail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerEMail;
    }

    @Override
    public String retrieveAccidentByID(int accidentID) {
        String query = "select accidentType from accident where accidentID = " + accidentID;
        String accidentType = "";
        try {
            ResultSet resultSet = this.retrieve(query);
            //this.execute(query);
            while (resultSet.next()) {
                accidentType = resultSet.getString("accidentType");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accidentType;
    }
}
