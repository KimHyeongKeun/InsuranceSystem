package dao;

import java.sql.ResultSet;

import accident.Accident;
import exemption.Exemption;
import exemption.ExemptionList;
import exemption.ExemptionListImpl;

public class ExemptionDaoImpl extends Dao implements ExemptionDao {
    public ExemptionDaoImpl() {
        try {
            super.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Exemption exemption) {
        String query = "insert into exemption ( accidentID, subFile, reason, legacy ) values ( " +
                exemption.getAccidentID() + ", " +
                " '" + exemption.getSubFile() + "', " +
                " '" + exemption.getReason() + "', " +
                " '" + exemption.getLegacy() + "' )";
        try {
            this.execute(query);
            query = "select LAST_INSERT_ID() as ID";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public void deleteByID(int ID) {
    }

    public ExemptionList retrieve() {
        String query = "select * from exemption";
        ExemptionList exemptionList = new ExemptionListImpl();
        try {
            ResultSet resultSet = this.retrieve(query);
            while (resultSet.next()) {
                Exemption exemption = new Exemption();
                exemption.setAccidentID(resultSet.getInt("accidentID"));
                exemption.setExemptionID(resultSet.getInt("exemptionID"));
                exemption.setSubFile(resultSet.getInt("subFile"));
                exemption.setReason(resultSet.getString("reason"));
                exemption.setLegacy(resultSet.getString("legacy"));
                exemptionList.add(exemption);
            }
            return exemptionList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(int state, Accident accident) {
    }

    @Override
    public ExemptionList retrieveList() {
        String query = "select * from accident left join ainvestigation on ainvestigation.accidentID = accident.accidentID left join exemption on exemption.accidentID = accident.accidentID  where accident.judgementComplete = 1  ";
        ExemptionList accidentList = new ExemptionListImpl();
        try {
            ResultSet resultSet = this.retrieve(query);
            while (resultSet.next()) {
                Exemption exemption = new Exemption();
                exemption.setAccidentID(resultSet.getInt("accidentID"));
                exemption.setAccidentDate(resultSet.getString("accidentDate"));
                exemption.setAccidentTitle(resultSet.getString("accidentTitle"));
                exemption.setSubFile(resultSet.getInt("subFile"));
                exemption.setReason(resultSet.getString("reason"));
                exemption.setLegacy(resultSet.getString("legacy"));
                exemption.setCustomerID(resultSet.getInt("customerID"));
                exemption.setJudgementComplete(resultSet.getInt("judgementComplete"));
                accidentList.add(exemption);
            }
            return accidentList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
