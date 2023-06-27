package reward;

import accident.Accident;
import employee.Employee;

public class RewardInfo {
    private String payment, assessReason;
    private Accident accident;
    private Employee employee;

    public String getPayment() {
        return payment;
    }
    public void setPayment(String payment) {
        this.payment = payment;
    }
    public String getAssessReason() {
        return assessReason;
    }
    public void setAssessReason(String assessReason) {
        this.assessReason = assessReason;
    }
    public Accident getAccident() {
        return accident;
    }
    public void setAccident(Accident accident) {
        this.accident = accident;
    }
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
