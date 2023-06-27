package dao;

import insurance.SaleRecord;

public interface SaleRecordDao {
    public void create(SaleRecord saleRecord);

    public SaleRecord retrieve(int insuranceID);
}
