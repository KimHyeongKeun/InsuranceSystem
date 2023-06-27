package dao;

import reward.RewardInfo;

public interface DamageDao {
    public void create(RewardInfo rewardInfo);

    public RewardInfo retrieve();

    public void update(RewardInfo rewardInfo);

    public String retrieveAccidentByID(int accidentID);

    public String retrieveCustomerEmail(int accidentID);
}
