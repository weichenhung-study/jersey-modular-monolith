package com.ntou.db.billofmonth;

import java.util.ArrayList;

public class BillofmonthImpl implements BillofmonthSvc {
    private final BillofmonthDAO billofmonthDAO;
    public BillofmonthImpl(BillofmonthDAO billofmonthDAO) {
        this.billofmonthDAO = billofmonthDAO;
    }

    @Override
    public int insertBill(BillofmonthVO vo) throws Exception {
        return billofmonthDAO.insertBill(vo);
    }

    @Override
    public int updatePayDate(BillofmonthVO vo) throws Exception {
        return billofmonthDAO.updatePayDate(vo);
    }

    @Override
    public ArrayList<BillofmonthVO> findBills(BillofmonthVO vo) throws Exception {
        return billofmonthDAO.findBills(vo);
    }
}
