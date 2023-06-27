package insurance;

import java.util.ArrayList;

public class InsuranceListImpl implements InsuranceList {
    private ArrayList<Insurance> insuranceList;

    public InsuranceListImpl() {
        this.insuranceList = new ArrayList<Insurance>();
    }

    public ArrayList<Insurance> getInsuranceList() {
        return insuranceList;
    }
    public void setInsuranceList(ArrayList<Insurance> insuranceList) {
        this.insuranceList = insuranceList;
    }

    public void finalize() throws Throwable {

    }

    public boolean add(Insurance insurance) {
        if (this.insuranceList.add(insurance)) return true;
        else return false;
    }

    public boolean delete(int insuranceId) {
        for (Insurance insurance : this.insuranceList) {
            if (insurance.getInsuranceID() == insuranceId) {
                if (this.insuranceList.remove(insurance)) return true;
                break;
            }
        }
        return false;
    }

    public Insurance search(int insuranceId) {
        for (Insurance insurance : this.insuranceList) {
            if (insurance.getInsuranceID() == insuranceId) return insurance;
        }
        return null;
    }

    public Insurance searchByInsuranceName(String insuranceName) {
        for (Insurance insurance : this.insuranceList) {
            if (insurance.getInsuranceName().equals(insuranceName)) return insurance;
        }
        return null;
    }

}